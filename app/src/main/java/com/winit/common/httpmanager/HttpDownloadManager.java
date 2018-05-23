//  Copyright 2012 Zonghai Li. All rights reserved.
//
//  Redistribution and use in binary and source forms, with or without modification,
//  are permitted for any project, commercial or otherwise, provided that the
//  following conditions are met:
//  
//  Redistributions in binary form must display the copyright notice in the About
//  view, website, and/or documentation.
//  
//  Redistributions of source code must retain the copyright notice, this list of
//  conditions, and the following disclaimer.
//
//  THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
//  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
//  PARTICULAR PURPOSE AND NONINFRINGEMENT OF THIRD PARTY RIGHTS. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
//  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
//  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THIS SOFTWARE.


package com.winit.common.httpmanager;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.LogUtils;
import com.winit.common.util.NetworkUtility;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.HttpHelper;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * HttpImageManager uses 3-level caching to download and store network images.
 * <p>
 * ---------------<br>
 * memory cache<br>
 * ---------------<br>
 * persistent storage (DB/FS)<br>
 * ---------------<br>
 * network loader<br>
 * ---------------
 * <p>
 * <p>
 * HttpImageManager will first look up the memory cache, return the image bitmap if it was already
 * cached in memory. Upon missing, it will further look at the 2nd level cache,
 * which is the persistence layer. It only goes to network if the resource has never been downloaded.
 * <p>
 * <p>
 * The downloading process is handled in asynchronous manner. To get notification of the response,
 * one can add an OnLoadResponseListener to the LoadRequest object.
 * <p>
 * <p>
 * HttpImageManager is usually used for ImageView to display a network image. To simplify the code,
 * One can register an ImageView object as target to the LoadRequest instead of an
 * OnLoadResponseListener. HttpImageManager will try to feed the loaded resource to the target ImageView
 * upon successful download. Following code snippet shows how it is used in a customer list adapter.
 * <p>
 * <p>
 * <pre>
 *         ...
 *         String imageUrl = userInfo.getUserImage();
 *         ImageView imageView = holder.image;
 *
 *         imageView.setImageResource(R.drawable.default_image);
 *
 *         if(!TextUtils.isEmpty(imageUrl)){
 *             Bitmap bitmap = mHttpImageManager.loadImage(new HttpImageManager.LoadRequest(Uri.parse(imageUrl), imageView));
 *            if (bitmap != null) {
 *                imageView.setImageBitmap(bitmap);
 *            }
 *        }
 *
 * </pre>
 *
 * @author zonghai@gmail.com
 */
public class HttpDownloadManager {

    private static final String TAG = "HttpDownloadManager";
    private static final boolean DEBUG = false;

    public static final int DEFAULT_CACHE_SIZE = 64;
    public static final int DECODING_MAX_PIXELS_DEFAULT = 600 * 800;
    public static int notificationId;

    public static class LoadRequest {

        public LoadRequest(Uri uri) {
            this(uri, null, null);
        }

        public LoadRequest(Uri uri, String path) {
            this(uri, null, path);
        }

        public LoadRequest(Uri uri, OnLoadResponseListener onLoadResponseListener, String path) {
            if (uri == null)
                throw new NullPointerException("uri must not be null");
            String filename = path;
            filename = String.valueOf(filename.hashCode());
            mUri = Uri.parse(filename);
            mListener = onLoadResponseListener;
            this.path = path;
        }

        public Uri getUri() {
            return mUri;
        }

        @Override
        public int hashCode() {
            return mUri.hashCode();
        }

        @Override
        public boolean equals(Object b) {
            if (b instanceof LoadRequest)
                return mUri.equals(((LoadRequest) b).getUri());
            return false;
        }

        private Uri mUri;
        private String path;
        private String mHashedUri;
        private OnLoadResponseListener mListener;

    }


    public interface OnLoadResponseListener {
        void onLoadResponse(LoadRequest r, Bitmap data);

        void onLoadProgress(LoadRequest r, long totalContentSize, long loadedContentSize);

        void onLoadError(LoadRequest r, Throwable e);
    }


    /**
     * Nonblocking call, return null if the bitmap is not in cache.
     *
     * @param r
     * @return
     */

    public void download(LoadRequest r) {
        if (r == null || r.getUri() == null || TextUtils.isEmpty(r.getUri().toString()))
            throw new IllegalArgumentException("null or empty request");
        notificationId++;
        mExecutor.execute(newRequestCall(r));
    }


    ////PRIVATE
    private Runnable newRequestCall(final LoadRequest request) {
        return new Runnable() {

            public void run() {

                synchronized (mActiveRequests) {
                    // If there's been already request pending for the same URL, we just wait until it is handled.
                    while (mActiveRequests.contains(request)) {
                        try {
                            mActiveRequests.wait();
                        } catch (InterruptedException e) {
                        }
                    }

                    mActiveRequests.add(request);
                }
                String fileName = StringUtils.getFileName(request.path);
                NotificationManager mNotifyManager =
                        (NotificationManager) IKonnectApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(IKonnectApplication.mContext);
                notificationBuilder.setContentTitle(fileName)
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.app_logo)
                        .setProgress(100, 0, false);
                mNotifyManager.notify(notificationId, notificationBuilder.build());
                try {
                    if (DEBUG) LogUtils.debug(TAG, "go to network " + request.getUri().toString());
                    InputStream responseStream = null;
                    try {
                        long contentSize = 0;
                        if (responseStream == null && NetworkUtility.isNetworkConnectionAvailable(IKonnectApplication.mContext)) {
                            Response response = (Response) new HttpHelper().sendRequest(request.path, ServiceUrls.METHOD_GET, null, null);
                            responseStream = (InputStream) response.data;
                            contentSize = response.contentLength;
                        }
                        if (responseStream != null) {
                            File fileDir = new File(AppConstants.DOWNLOAD_PATH);
                            if (!fileDir.exists())
                                fileDir.mkdirs();
                            FileOutputStream fout = new FileOutputStream(new File(AppConstants.DOWNLOAD_PATH, fileName));
                            byte[] buffer = new byte[1024];
                            int len;
                            int readBytes = 0;

                            try {
                                while ((len = responseStream.read(buffer)) != -1) {
                                    readBytes += len;
                                    if (notificationBuilder != null)
                                        notificationBuilder.setContentText("Downloading").setProgress(100, ((int) (readBytes * 100 / contentSize)), false);
                                    Log.d("iKonnect", (int) (readBytes * 100 / contentSize) + "");
                                    if (mNotifyManager != null)
                                        mNotifyManager.notify(notificationId, notificationBuilder.build());
                                    fout.write(buffer, 0, len);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Thread.sleep(1000);
                            if (notificationBuilder != null) {
                                notificationBuilder.setContentText("Download complete")
                                        .setProgress(0, 0, false);
                                Uri uri = Uri.parse("file://" + AppConstants.DOWNLOAD_PATH + "/" + fileName);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
//                                intent.setDataAndType(uri, "resource/folder");
//                                intent.setDataAndType(uri, "image/*");
                                intent.setDataAndType(uri, "" + getMimeType(uri.toString()));


                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                PendingIntent pendingIntent = PendingIntent.getActivity(IKonnectApplication.mContext, AppConstants.DOWNLOAD_CODE, Intent.createChooser(intent, "Open Folder"), PendingIntent.FLAG_CANCEL_CURRENT);
                                notificationBuilder.setContentIntent(pendingIntent);
                                notificationBuilder.setAutoCancel(true);
                            }
                            if (mNotifyManager != null) {
                                mNotifyManager.notify(notificationId, notificationBuilder.build());
                            }
                        } else {
                            if (notificationBuilder != null)
                                notificationBuilder.setContentText("Unable to download")
                                        .setProgress(0, 0, false);
                            if (mNotifyManager != null)
                                mNotifyManager.notify(notificationId, notificationBuilder.build());
                        }
                    } finally {
                        if (responseStream != null) {
                            try {
                                responseStream.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    synchronized (mActiveRequests) {
                        mActiveRequests.remove(request);
                        mActiveRequests.notifyAll();  // wake up pending requests who's querying the same URL. 
                    }
                    if (DEBUG) LogUtils.debug(TAG, "finished request for: " + request.getUri());
                }
            }
        };
    }

    public static String getMimeType(String path) {
        String extention = path.substring(path.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extention);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
        return mimeType;
    }

    private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(3, 5, 5, TimeUnit.SECONDS, new LinkedBlockingStack<Runnable>());
    private Set<LoadRequest> mActiveRequests = new HashSet<LoadRequest>();


    /**
     * The BitmapFactory.decodeStream() method fails to read a JPEG image (i.e.
     * returns null) if the skip() method of the used InputStream skip less bytes
     * than the required amount.
     * <p>
     * author: public domain
     */

    public static class FlushedInputStream extends FilterInputStream {

        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int byt = read();
                    if (byt < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

}

