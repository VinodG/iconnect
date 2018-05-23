package com.winit.iKonnect.ui.customview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.winit.iKonnect.R;

/**
 * Created by namashivaya.gangishe on 5/19/2017.
 */

public class CustomRelativeLayout extends RelativeLayout
{
    public LinearLayout llattchment,llDocuments;
    private ImageView img_Info,iv_attachment;

    private Context context;

    public CustomRelativeLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context = context;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.attachment_bottom_new,null);
        BitmapDrawable bd=(BitmapDrawable) this.getResources().getDrawable(R.drawable.pin);
        int height=bd.getBitmap().getHeight();
        llattchment = (LinearLayout)view.findViewById(R.id.linearLayout);
        llDocuments = (LinearLayout)view.findViewById(R.id.llDocuments);
        img_Info = (ImageView)view.findViewById(R.id.img_Info);
        iv_attachment = (ImageView)view.findViewById(R.id.iv_attachment);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0,height/2,10,0);
        llattchment.setLayoutParams(params);
        addView(view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        /*if(context instanceof EmploymentLetterActivity)
            setVisibility(GONE);
        else
            setVisibility(VISIBLE);*/
//        keep there your code where your attachment should visible.
    }
}
