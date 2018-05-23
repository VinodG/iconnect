package com.winit.iKonnect.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.winit.common.Preference;
import com.winit.common.TouchImageView;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.httpmanager.HttpImageManager;
import com.winit.iKonnect.R;
import com.winit.iKonnect.ui.activities.BaseActivity;

/**
 * Created by Girish Velivela on 5/23/2017.
 */

public class FullImageDialog extends Dialog{

    public FullImageDialog(Context context, String imagePath)
    {
        super(context, R.style.Dialog);
        LayoutInflater inflater = getLayoutInflater();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Preference preference = Preference.getInstance();
        View view = inflater.inflate(R.layout.layout_full_image, null);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 600),
                preference.getIntFromPreference(Preference.DEVICE_DISPLAY_HEIGHT, 600)-((BaseActivity)context).getStatusBarHeight());
        setContentView(view,layoutParams);
        TouchImageView ivZoom = (TouchImageView)view.findViewById(R.id.ivZoom);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        final Uri uri = Uri.parse(imagePath);
        Bitmap bitmap = ((IKonnectApplication)context.getApplicationContext()).getHttpImageManager().loadImage(
                new HttpImageManager.LoadRequest(uri, ivZoom, imagePath));

        if (bitmap != null) {
            ivZoom.setImageBitmap(bitmap);
        } else {
            ivZoom.setImageResource(R.drawable.app_bar_logo);
        }
        ivZoom.setMaxZoom(4f);
    }

}
