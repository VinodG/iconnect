package com.winit.common.webAccessLayer;

import android.webkit.MimeTypeMap;

import com.winit.common.util.LogUtils;
import com.winit.common.util.StringUtils;

/*import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;*/

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by Girish Velivela on 11-07-2016.
 */
public class HttpHelper {

    private int TIMEOUT_CONNECT_MILLIS = (1*10*1000);
    private int TIMEOUT_READ_MILLIS = (3*60*1000);

    /*
    * It's always better to send response object not inputstream because there will be scenario that
    * we will need some response properties like content length etc.
    */
//arrFamilydetail
    public Object sendRequest(String requestUrl, String method, Map<String,String> headers, String postData){
        LogUtils.debug(LogUtils.HTTP_HELPER_TAG, "sendRequest - Started");
        LogUtils.debug(LogUtils.HTTP_HELPER_TAG, "requestUrl - "+requestUrl);
        URL url;
        HttpURLConnection connection  = null;

        try {
            url = new URL(requestUrl.replace(" ","%20"));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(TIMEOUT_CONNECT_MILLIS);
            connection.setReadTimeout(TIMEOUT_READ_MILLIS);
            connection.setDoInput(true);
            if(headers != null && headers.size() >0){
                Set<String> keySet = headers.keySet();
                for(String key : keySet)
                    connection.setRequestProperty(key,headers.get(key));
            }
            if(!StringUtils.isEmpty(postData)){
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                outputStream.close();
            }


            int httpCode = connection.getResponseCode();
            String resMsg = connection.getResponseMessage();
            LogUtils.debug(LogUtils.HTTP_HELPER_TAG,"Response Code - "+httpCode);
            LogUtils.debug(LogUtils.HTTP_HELPER_TAG,"Response Message - "+resMsg);
            Response response = new Response();
            response.contentLength = connection.getContentLength();
//            response.data = StringUtils.convertStreamToString(connection.getInputStream());
            response.data = connection.getInputStream();
            return response;
        }catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            LogUtils.debug(LogUtils.HTTP_HELPER_TAG,"sendRequest - Ended");
        }
        return null;
    }

    public void close(InputStream inputStream){
        LogUtils.debug(LogUtils.HTTP_HELPER_TAG, "close - Started");
        if(inputStream != null){
            try {
                inputStream.close();
            }catch (Exception e){
                LogUtils.errorLog(LogUtils.HTTP_HELPER_TAG, e.toString());
                e.printStackTrace();
            }
        }
        LogUtils.debug(LogUtils.HTTP_HELPER_TAG, "close - Ended");
    }

    public Response uploadFile(String baseUrl, String file, Map<String,String> headers){

        URL url;
        HttpURLConnection connection = null;
        String uploadStatus = "";
        Response response=null;
        InputStream inputStream=null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(baseUrl);
            File uploadFile = new File(file);
            MimeTypeMap map = MimeTypeMap.getSingleton();
            if(uploadFile.exists()){
                MultipartEntity mpEntity = new MultipartEntity();
                ContentBody cbFile = new FileBody(uploadFile, ""+map.getMimeTypeFromExtension(StringUtils.getExtentionOfFile(file)));
                mpEntity.addPart("FileName", cbFile);
                httppost.setEntity(mpEntity);

                HttpResponse response1;
                response1 = httpclient.execute(httppost);
                HttpEntity resEntity = response1.getEntity();
                inputStream = resEntity.getContent();
            }
            response = new Response();
            response.contentLength = inputStream.available();
            response.data = StringUtils.convertStreamToString(inputStream);
//            response.data = connection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.disconnect();
        }
        return response;
//        return "";
    }
}
