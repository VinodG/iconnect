package com.winit.iKonnect.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.winit.common.Preference;
import com.winit.iKonnect.R;
import com.winit.iKonnect.ui.activities.BaseActivity;

/**
 *  Description of class : This class having Meaningful Use dialog. 
 * 
 */
public class DownloadProgressStatusDialog extends Dialog
{
	public DownloadProgressStatusDialog(Context context)
	{
		super(context, R.style.Dialog);
		LayoutInflater inflater = getLayoutInflater();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		LinearLayout llProgressDialog = (LinearLayout) inflater.inflate(R.layout.progressbar_popup, null);
		llProgressDialog.setGravity(Gravity.CENTER);
		Preference preference = Preference.getInstance();
		setContentView(llProgressDialog,new LayoutParams(preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 0) - (int)(20 * BaseActivity.px), LayoutParams.WRAP_CONTENT));
		
		
		ProgressBar pbbarDownload = (ProgressBar) llProgressDialog.findViewById(R.id.pbbarDownload);
		pbbarDownload.setLayoutParams(new LinearLayout.LayoutParams(preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 0)-(int)(80 * BaseActivity.px), LayoutParams.WRAP_CONTENT));
		pbbarDownload.setMax(100);
		pbbarDownload.setMinimumHeight((int) (10 * BaseActivity.px));
		pbbarDownload.setPadding(0, (int)(15 * BaseActivity.px), 0, (int)(15 * BaseActivity.px));
	}
}
 