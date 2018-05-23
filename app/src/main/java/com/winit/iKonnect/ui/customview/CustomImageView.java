package com.winit.iKonnect.ui.customview;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.util.LogUtils;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.FeedsDO;
import com.winit.iKonnect.module.feed.FeedBinder;

/**
 * Created by Gufran.Khan on 6/3/2017.
 */

public class CustomImageView  extends ImageView{

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImage(String path){
        IKonnectApplication.setImageUrl(this, path);
    }

    @BindingAdapter("onClick")
    public static void viewFeedImage(ImageView imageView, Object feedBinder, Object feedsDO){
        ((FeedBinder)feedBinder).viewFeedImage((FeedsDO) feedsDO);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try{
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            Bitmap b = ((BitmapDrawable) drawable).getBitmap();
            if(b == null) {
                LogUtils.debug(LogUtils.LOG_TAG,"Bitmap null");
                return;
            }else if(b.isRecycled()){
                String url = getTag(R.string.IMAGE_PATH)==null?null:(String)getTag(R.string.IMAGE_PATH);
                if(!StringUtils.isEmpty(url)){
                    if(getTag(R.string.isRound) != null){
                        IKonnectApplication.setImageUrl(this, url,getResources().getDrawable(R.drawable.profile_pic));
                    }else{
                        IKonnectApplication.setImageUrl(this, url);
                    }
                }
            }
            super.onDraw(canvas);
        }catch (Exception e){
            String url = getTag(R.string.IMAGE_PATH)==null?null:(String)getTag(R.string.IMAGE_PATH);
            if(!StringUtils.isEmpty(url)){
                if(getTag(R.string.isRound) != null){
                    IKonnectApplication.setImageUrl(this, url,getResources().getDrawable(R.drawable.profile_pic));
                }else{
                    IKonnectApplication.setImageUrl(this, url);
                }
            }
            e.printStackTrace();
        }
        try {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            Bitmap b = ((BitmapDrawable) drawable).getBitmap();
            if(b == null) {
                LogUtils.debug(LogUtils.LOG_TAG,"Bitmap null");
                return;
            }else if(b.isRecycled()){
                String url = getTag(R.string.IMAGE_PATH)==null?null:(String)getTag(R.string.IMAGE_PATH);
                if(!StringUtils.isEmpty(url)){
                    if(getTag(R.string.isRound) != null){
                        IKonnectApplication.setImageUrl(this, url,getResources().getDrawable(R.drawable.profile_pic));
                    }else{
                        IKonnectApplication.setImageUrl(this, url);
                    }
                }
            }
            super.onDraw(canvas);
        }catch (Exception e){
            String url = getTag(R.string.IMAGE_PATH)==null?null:(String)getTag(R.string.IMAGE_PATH);
            if(!StringUtils.isEmpty(url)){
                if(getTag(R.string.isRound) != null){
                    IKonnectApplication.setImageUrl(this, url,getResources().getDrawable(R.drawable.profile_pic));
                }else{
                    IKonnectApplication.setImageUrl(this, url);
                }
            }
            e.printStackTrace();
        }
    }
}
