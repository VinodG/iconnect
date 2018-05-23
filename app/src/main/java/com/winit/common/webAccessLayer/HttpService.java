package com.winit.common.webAccessLayer;

import com.winit.common.constant.AppConstants;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.LogUtils;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.module.ContactUs.interactor.AsyncContactUsInteractor;
import com.winit.iKonnect.parser.BaseJsonHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Girish Velivela on 11-07-2016.
 */
public class HttpService {
    private HttpListener httpListener;
    private ServiceUrls.ServiceAction serviceAction;
    private String requestUrl;
    private String request;
    private int serviceId;
    private int formid;
    private ArrayList<String> arrReqFilesPaths;

    public void executeImageUploadAsyncTask(ServiceUrls.ServiceAction soapAction, ArrayList<String> arrReqFilesPaths, int serviceId, HttpListener httpListener) {
        this.serviceAction = soapAction;
        this.arrReqFilesPaths = arrReqFilesPaths;
        this.serviceId = serviceId;
        this.httpListener = httpListener;
        Thread httpThread = new Thread(new HttpAttachementUploadRunnable());
        httpThread.start();
    }


    public void executeImageUploadAsyncTask(ServiceUrls.ServiceAction soapAction, ArrayList<String> arrReqFilesPaths, int serviceId, int formid, HttpListener httpListener) {
        this.serviceAction = soapAction;
        this.arrReqFilesPaths = arrReqFilesPaths;
        this.serviceId = serviceId;
        this.formid = formid;
        this.httpListener = httpListener;
        Thread httpThread = new Thread(new HttpUploadSignatureRunnable());
        httpThread.start();
    }


    public void executeAsyncTask(ServiceUrls.ServiceAction soapAction, String request, HttpListener httpListener) {
        this.serviceAction = soapAction;
        this.request = request;
        this.httpListener = httpListener;
        Thread httpThread = new Thread(new HttpRunnable());
        httpThread.start();
    }

    public Response executeTask(ServiceUrls.ServiceAction soapAction, String request) {
        this.serviceAction = soapAction;
        this.request = request;
        return execute();
    }

    public void executeTask(String requestUrl, HttpListener httpListener) {
        this.requestUrl = requestUrl;
        this.httpListener = httpListener;
        execute();
    }

    public void executeAsyncTask(ServiceUrls.ServiceAction serviceHistory, String request) {
        this.serviceAction = serviceHistory;
        this.request = request;
        Thread httpThread = new Thread(new HttpRunnable());
        httpThread.start();
    }

    public void executeTask(ServiceUrls.ServiceAction serviceHistory, String request, HttpListener httpListener) {
        this.httpListener=httpListener;
        this.serviceAction = serviceHistory;
        this.request = request;
        Thread httpThread = new Thread(new HttpRunnable());
        httpThread.start();
    }

    private class HttpRunnable implements Runnable {
        @Override
        public void run() {
            execute();
        }
    }

    private class HttpAttachementUploadRunnable implements Runnable {
        @Override
        public void run() {
            executeAttachementUpload();
        }
    }


    private class HttpUploadSignatureRunnable implements Runnable {
        @Override
        public void run() {
            executeUploadSignature();
        }
    }


    private Response executeUploadSignature() {
        Response response = new Response("Failure", "Unable to connect server, please try again.", null);
        try {
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ " + CalendarUtil.getDate(new Date(), CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));
            LogUtils.infoLog("Request Format : ", "" + request);
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ " + CalendarUtil.getDate(new Date(), CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));

            for (String path : arrReqFilesPaths) {
                Response httpResponseDO = (Response) new RestCilent().processSignatureRequest(serviceAction, path, serviceId, formid);
                if (httpResponseDO != null && httpResponseDO.data != null) {
                    response = httpResponseDO;
                } else
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, " InputStream is NULL ");
            }


        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.errorLog(LogUtils.SERVICE_LOG_TAG, e.getMessage());
        } finally {
            if (httpListener != null)
                httpListener.onResponseReceived(response);
        }
        return response;
    }

    private Response execute() {
        Response response = new Response("Failure", "Unable to connect server, please try again.", null);
        try {
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ " + CalendarUtil.getDate(new Date(), CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));
            LogUtils.infoLog("Request Format : ", "" + request);
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ " + CalendarUtil.getDate(new Date(), CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));

            Response httpResponseDO = (Response) new RestCilent().processRequest(serviceAction, request);
            if (httpResponseDO != null && httpResponseDO.data != null) {
                BaseJsonHandler baseHandler = BaseJsonHandler.getParser(serviceAction);
                if (baseHandler != null) {
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, String.valueOf(serviceAction) + " Parsing started");
                    baseHandler.parse(StringUtils.convertStreamToStringBuilder((InputStream) httpResponseDO.data));
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, String.valueOf(serviceAction) + " Parsing completed");

                    response.method = serviceAction;
                    response.data = baseHandler.getData();
                } else
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "JsonBaseParser  null");
            } else
                LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, " InputStream is NULL ");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.errorLog(LogUtils.SERVICE_LOG_TAG, e.getMessage());
        } finally {
            if (httpListener != null)
                httpListener.onResponseReceived(response);
        }
        return response;
    }

    private Response executeAttachementUpload() {
        Response response = new Response("Failure", "Unable to connect server, please try again.", null);
        try {
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ " + CalendarUtil.getDate(new Date(), CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));
            LogUtils.infoLog("Request Format : ", "" + request);
            LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, "************************************ " + CalendarUtil.getDate(new Date(), CalendarUtil.DATE_PATTERN_dd_MMM_YYYY));

            if (arrReqFilesPaths==null){
                arrReqFilesPaths= AppConstants.arrAttachments;
                AppConstants.arrAttachments.clear();
            }
            for (String path : arrReqFilesPaths) {
                Response httpResponseDO = (Response) new RestCilent().processAttachementRequest(serviceAction, path, serviceId);
                if (httpResponseDO != null && httpResponseDO.data != null) {
                    response = httpResponseDO;
                } else
                    LogUtils.infoLog(LogUtils.SERVICE_LOG_TAG, " InputStream is NULL ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.errorLog(LogUtils.SERVICE_LOG_TAG, e.getMessage());
        } finally {
            if (httpListener != null)
                httpListener.onResponseReceived(response);
        }
        return response;
    }

    public interface HttpListener {
        void onResponseReceived(Response response);
    }

}