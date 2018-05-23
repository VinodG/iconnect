package com.winit.iKonnect.module.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.os.Looper;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.LogUtils;
import com.winit.iKonnect.R;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Girish Velivela on 11/15/2016.
 *
 *
 * BasePresenter should have default construtor.
 */

public abstract class BasePresenter implements IBasePresenter {

    private IBaseView view;
    protected Preference preference;
    protected String userCode;
    protected MVPHandler handler;
    private final String WEEK_TAG = "WEEK REFERENCE";

    protected boolean isArabic;

    public BasePresenter(IBaseView iBaseView){
        preference = Preference.getInstance();
        isArabic = Preference.getInstance().getStringFromPreference(Preference.LANGUAGE,"").equalsIgnoreCase(AppConstants.ARABIC);
        this.view = iBaseView;
        handler = new MVPHandler();
    }

    protected class MVPHandler{
        Handler handler;
        public MVPHandler(){
            handler = new Handler(Looper.getMainLooper());
        }

        public void postResult(Runnable runnable){
            if(view instanceof Activity){
                if(view == null || ((Activity)view).isFinishing()){
                    LogUtils.debug(WEEK_TAG,"Activity Finished");
                    return;
                }
            }
            if(view instanceof Fragment){
                if(view == null || ((Fragment)view).isDetached() || !((Fragment)view).isAdded()){
                    LogUtils.debug(WEEK_TAG,"Fragment detached");
                    return;
                }
            }
            handler.post(runnable);
        }

    }

    public interface Predicate<T> {
        boolean apply(T type);
    }

    public static <T> Collection<T> filter(Collection<T> col, Predicate<T> predicate) {

        Collection<T> result = new ArrayList<T>();
        if(col!=null)
        {
            for (T element : col) {
                if (predicate.apply(element)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    public void test(){
        IKonnectApplication.mContext.getString(R.string.comments);
    }

}
