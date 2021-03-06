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


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.util.LogUtils;
import com.winit.common.util.NetworkUtility;
import com.winit.common.webAccessLayer.HttpHelper;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
 *     ---------------<br>
 *     memory cache<br>
 *     ---------------<br>
 *     persistent storage (DB/FS)<br>
 *     ---------------<br>
 *     network loader<br>
 *     ---------------
 *
 * <p>
 * HttpImageManager will first look up the memory cache, return the image bitmap if it was already
 * cached in memory. Upon missing, it will further look at the 2nd level cache, 
 * which is the persistence layer. It only goes to network if the resource has never been downloaded.
 *
 * <p>
 * The downloading process is handled in asynchronous manner. To get notification of the response, 
 * one can add an OnLoadResponseListener to the LoadRequest object.
 *
 * <p>
 * HttpImageManager is usually used for ImageView to display a network image. To simplify the code, 
 * One can register an ImageView object as target to the LoadRequest instead of an 
 * OnLoadResponseListener. HttpImageManager will try to feed the loaded resource to the target ImageView
 * upon successful download. Following code snippet shows how it is used in a customer list adapter.
 *
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
 *
 * @author zonghai@gmail.com
 */
public class HttpImageManager{

    private static final String TAG = "HttpImageManager";
    private static final boolean DEBUG = false;

    public static final int DEFAULT_CACHE_SIZE = 64;
    public static final int UNCONSTRAINED = -1;
    public static final int DECODING_MAX_PIXELS_DEFAULT = 600 * 800;
    public static final int DECODING_MAX_PIXELS_LARGE = 1440 * 2560;


    public static class LoadRequest {
        public LoadRequest (Uri uri) {
            this(uri, null, null,null);
        }


        public LoadRequest(Uri uri, ImageView v, String path){
            this(uri, v, null, path);
        }

        public LoadRequest(Uri uri, ImageView v, String path, boolean isRounded, boolean isForceLoad)
        {
            this(uri, v, null, path, isRounded,isForceLoad);
        }

        public LoadRequest(Uri uri, OnLoadResponseListener l, String path){
            this( uri, null, l,path);
        }

        public LoadRequest(Uri uri, ImageView v, OnLoadResponseListener l, String path){
            if(uri == null)
                throw new NullPointerException("uri must not be null");
            String filename = path;
            filename = String.valueOf(filename.hashCode());
            mUri = Uri.parse(filename);
            mHashedUri = mUri.toString();
            mImageView = v;
            mListener = l;
            this.path = path;
        }

        //============================================newly added merchandise
        public LoadRequest(Uri uri, ImageView v, OnLoadResponseListener l, String path, boolean isRounded,boolean isForceLoad){
            if(uri == null)
                throw new NullPointerException("uri must not be null");
            String filename = path;
            filename = String.valueOf(filename.hashCode());
            mUri = Uri.parse(filename);
            mHashedUri = mUri.toString();
            mImageView = v;
            this.isRounded=isRounded;
            this.isForceLoad=isForceLoad;
            mListener = l;
            this.path = path;
        }


        public ImageView getImageView() {
            return mImageView;
        }


        public Uri getUri() {
            return mUri;
        }


        public String getHashedUri () {
            return this.mHashedUri;
        }


        @Override
        public int hashCode() {
            return mUri.hashCode();
        }


        @Override
        public boolean equals(Object b){
            if(b instanceof LoadRequest)
                return mUri.equals(((LoadRequest)b).getUri());
            return false;
        }

        private Uri mUri;
        private String mHashedUri;
        private String path;
        private OnLoadResponseListener mListener;
        private ImageView mImageView;
        private boolean isRounded;
        private boolean isForceLoad;
    }


    public static interface OnLoadResponseListener {
        public void onLoadResponse(LoadRequest r, Bitmap data);
        public void onLoadProgress(LoadRequest r, long totalContentSize, long loadedContentSize);
        public void onLoadError(LoadRequest r, Throwable e);
    }


    /**
     *
     * Give a chance to apply any future processing on the bitmap retrieved from network. 
     */
    public static interface BitmapFilter {
        public Bitmap filter(final Bitmap in);
    }


    ////////HttpImageManager
    public HttpImageManager (BitmapCache cache,  BitmapCache persistence ) {
        mCache = cache;
        mPersistence = persistence;
        if (mPersistence == null) {
            throw new IllegalArgumentException(" persistence layer should be specified");
        }
    }


    public HttpImageManager ( BitmapCache persistence ) {
        this(null, persistence);
    }


    public void setDecodingPixelConstraint (int max) {
        mMaxNumOfPixelsConstraint = max;
    }


    public int getDecodingPixelConstraint()
    {
        return mMaxNumOfPixelsConstraint;
    }


    public void setBitmapFilter (BitmapFilter filter) {
        mFilter = filter;
    }


    static public BitmapCache createDefaultMemoryCache() {
        return new BasicBitmapCache(DEFAULT_CACHE_SIZE);
    }

    static public BitmapCache createDefaultMemoryCache(int size) {
        return new LRUBitmapCache(size);
    }


    public Bitmap loadImage(Uri uri) {
        return loadImage(new LoadRequest(uri));
    }


    /**
     * Nonblocking call, return null if the bitmap is not in cache.
     * @param r
     * @return
     */
    public Bitmap loadImage(LoadRequest r ) {
        if(r == null || r.getUri() == null || TextUtils.isEmpty(r.getUri().toString()))
            throw new IllegalArgumentException( "null or empty request");

        ImageView iv = r.getImageView();
        if(iv != null){
            synchronized ( iv ) {
                iv.setTag(r.getUri()); // bind URI to the ImageView, to prevent image write-back of earlier requests.
            }
        }

        String key = r.getHashedUri();

        if(mCache != null && mCache.exists(key) && !r.isForceLoad) {
            return mCache.loadData(key);
        }
        else {
            // not ready yet, try to retrieve it asynchronously.
            mExecutor.execute( newRequestCall(r));
            return null;
        }
    }




    ////PRIVATE
    private Runnable newRequestCall(final LoadRequest request) {
        return new Runnable() {

            @SuppressWarnings("unused")
            public void run() {

                // if the request dosen't represent the intended ImageView, do nothing.
                if(request.getImageView() != null) {
                    final ImageView iv = request.getImageView();
                    synchronized ( iv ) {
                        if ( iv.getTag() != request.getUri() ) {
                            if(DEBUG)  LogUtils.debug(TAG, "give up loading: " + request.getUri().toString());
                            return;
                        }
                    }
                }

                synchronized (mActiveRequests) {
                    // If there's been already request pending for the same URL, we just wait until it is handled.
                    while (mActiveRequests.contains(request)) {
                        try {
                            mActiveRequests.wait();
                        } catch(InterruptedException e) {}
                    }

                    mActiveRequests.add(request);
                }

                Bitmap data = null;
                String key = request.getHashedUri();

                try {
                    //first we lookup memory cache
                    if(request.isForceLoad) {
                        if (mCache != null)
                            mCache.removeData(key);
                    }else if (mCache != null)
                        data = mCache.loadData(key);

                    if(data == null)
                    {
                        if(DEBUG)  LogUtils.debug(TAG, "cache missing " + request.getUri().toString());
                        //then check the persistent storage
                        data = mPersistence.loadData(key);
                        if(data != null) {
                            if(DEBUG)  LogUtils.debug(TAG, "found in persistent: " + request.getUri().toString());
                            data = modify(request.getImageView(),data);
                            // load it into memory
                            if (mCache != null)
                                mCache.storeData(key, data);

                            fireLoadProgress(request, 1, 1); // fire progress done
                        }
                        else {
                            // we go to network
                            if(DEBUG)  LogUtils.debug(TAG, "go to network " + request.getUri().toString());
                            long millis = System.currentTimeMillis();

                            byte[] binary = null;
                            InputStream responseStream = null;

                            try {
                                try {
                                    responseStream = request.getImageView().getContext().getAssets().open(request.path);
                                } catch (Exception e) {
                                    File file = new File(request.path);
                                    if(responseStream==null && file.exists()){
                                        responseStream = new FileInputStream(file);
                                    }
                                    if(responseStream==null && NetworkUtility.isNetworkConnectionAvailable(IKonnectApplication.mContext)){
                                        Response response = (Response) new HttpHelper().sendRequest(request.path, ServiceUrls.METHOD_GET, null, null);
                                        responseStream = (InputStream)response.data;
                                    }
                                }
                                if(responseStream!=null){
                                    responseStream = new FlushedInputStream(responseStream); //patch the inputstream
                                    long contentSize = responseStream.available();
                                    binary = readInputStreamProgressively(responseStream, (int) contentSize, request);
                                    if(mMaxNumOfPixelsConstraint>= binary.length)
                                        data = BitmapFactory.decodeByteArray(binary, 0, binary.length);
                                    else
                                        data = BitmapUtil.decodeByteArray(binary, mMaxNumOfPixelsConstraint);
                                }

                            }
                            finally {
                                if(responseStream != null) {
                                    try { responseStream.close(); } catch (IOException e) {}
                                }
                            }

                            if(data == null)
                                throw new RuntimeException("data from remote can't be decoded to bitmap");

                            if(DEBUG) LogUtils.debug(TAG, "decoded image: " + data.getWidth() + "x" + data.getHeight() );
                            if(DEBUG) LogUtils.debug(TAG, "time consumed: " + (System.currentTimeMillis() - millis));

                            if(data != null)
                                data = modify(request.getImageView(),data);

                            //apply filter(s)
                            if (mFilter != null) {
                                try {
                                    Bitmap newData = mFilter.filter(data);
                                    if (newData != null) data = newData;
                                }
                                catch (Throwable e) {}
                            }
                            // load it into memory
                            if (mCache != null)
                                mCache.storeData(key, data);

                            // persist it. Save the file as-is, preserving the format.
                            mPersistence.storeData(key, binary);
                        }
                    }

                    if(data != null && request.getImageView() != null) {
                        final Bitmap finalData = data;
                        final ImageView iv = request.getImageView();

                        synchronized ( iv ) {
                            if ( iv.getTag() == request.getUri() ) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if ( iv.getTag() == request.getUri() && !finalData.isRecycled()) {
                                            iv.setImageBitmap(finalData);
                                        }
                                    }
                                });
                            }
                        }
                    }

                    // callback listener if any
                    fireLoadResponse(request, data);
                }
                catch (Throwable e) {
                    fireLoadFailure(request, e);
                }
                finally{
                    synchronized (mActiveRequests) {
                        mActiveRequests.remove(request);
                        mActiveRequests.notifyAll();  // wake up pending requests who's querying the same URL. 
                    }
                    if (DEBUG) LogUtils.debug(TAG, "finished request for: " + request.getUri());
                }
            }
        };
    }


    private Bitmap modify(ImageView imageView, Bitmap bitmap){
        if(imageView.getTag(R.string.isRound) != null){
            /*Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
//            canvas.drawOval(rectF, paint);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

//            bitmap.recycle();*/
            int w = imageView.getWidth();
            return bitmap.isRecycled()? bitmap : getCroppedBitmap(bitmap.copy(Bitmap.Config.ARGB_8888, true),w);
        }else
            return bitmap;
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

    /**
     * Make memory cache empty, release all bitmap reference held. 
     */
    public void emptyCache () {
        if ( mCache != null)
            mCache .clear();
    }


    /**
     * Remove the persistent data. This is a blocking call. 
     */
    public void emptyPersistence () {
        if (mPersistence != null)
            mPersistence .clear();
    }


    ////////PRIVATE
    public byte[] readInputStreamProgressively (InputStream is, int totalSize, LoadRequest r)
            throws IOException {

        fireLoadProgress(r, 3, 1); // compensate 33% of total time, which was consumed by establishing HTTP connection

        if (totalSize > 0 && r.mListener!=null) { // content length is known
            byte[] data = new byte[totalSize];
            int offset = 0;
            int readed;

            while (offset < totalSize && (readed = is.read(data, offset, totalSize - offset)) != -1) {
                offset += readed;
                fireLoadProgress(r, totalSize, (totalSize + offset) >> 1 );
            }

            if (offset != totalSize)
                throw new IOException("Unexpected readed size. current: " + offset + ", excepted: " + totalSize);

            return data;

        }
//        else if (totalSize == 0) {
//            return new byte[0];
//        }
        else {
            // content length is unknown
            byte[] buf = new byte[1024];
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            long count = 0;
            int readed;
            while ((readed = is.read(buf)) != -1) {
                output.write(buf, 0, readed);
                count += readed;
            }

            fireLoadProgress(r, count, count);

            if (count > Integer.MAX_VALUE)
                throw new IOException("content too large: " + (count / (1024 * 1024 )) + " M");

            return output.toByteArray();
        }
    }


    private void fireLoadResponse(final LoadRequest r, final Bitmap image) {
        if ( r.mListener != null) {
            try {
                r.mListener.onLoadResponse(r, image);
            }
            catch (Throwable t) {}
        }
    }


    private void fireLoadProgress(final LoadRequest r, final long totalContentSize, final long loadedContentSize) {
        if ( r.mListener != null) {
            try {
                r.mListener.onLoadProgress(r, totalContentSize, loadedContentSize);
            }
            catch (Throwable t) {}
        }
    }


    private void fireLoadFailure(final LoadRequest r, final Throwable e) {
        if ( r.mListener != null) {
            try {
                r.mListener.onLoadError(r, e);
            }
            catch (Throwable t) {}
        }
    }


    private int mMaxNumOfPixelsConstraint = DECODING_MAX_PIXELS_DEFAULT;
    private BitmapCache mCache;
    private BitmapCache mPersistence;
    private Handler mHandler = new Handler();
    private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(3, 5, 5, TimeUnit.SECONDS, new LinkedBlockingStack<Runnable>());
    private Set<LoadRequest> mActiveRequests = new HashSet<LoadRequest>();
    private BitmapFilter mFilter;

    public BitmapCache getmCache(){
        return mCache;
    }

    public BitmapCache getmPersistence(){
        return mPersistence;
    }
    /*
     * The BitmapFactory.decodeStream() method fails to read a JPEG image (i.e.
     * returns null) if the skip() method of the used InputStream skip less bytes
     * than the required amount.
     * 
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

