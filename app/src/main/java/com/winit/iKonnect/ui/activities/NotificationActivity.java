package com.winit.iKonnect.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.ui.dialog.CustomDialog;


public class NotificationActivity extends BaseActivity
{
	private boolean boolisFirst = true, isAppRunning;
	private ProgressDialog progressDialog;
	//	private Button btnYesPopup;
//	private TextView tvMsg, tvTitlePopup;
	private LinearLayout layout;
	private CustomDialog customDialog;
	private Preference preference;
	private String msg,title;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		layout = (LinearLayout) getLayoutInflater().inflate(R.layout.notification_popup, null);
		setContentView(layout);
		preference 	= new Preference(getApplicationContext());
		msg = getIntent().getExtras().getString("message");
		title = getIntent().getExtras().getString("title");
		Log.d("app","onCreate");
		setTypeFace(layout);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("app","onResume");
		showCustomDialog(NotificationActivity.this, title, msg, getResources().getString(R.string.OK), null, "forcelogout");
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from)
	{
		runOnUiThread(new RunshowCustomDialogs(context, strTitle, strMessage,firstBtnName, secondBtnName, from, true));
	}
	@Override
	protected void onPause()
	{
		super.onPause();
	}

	public void setTypeFace(ViewGroup group)
	{
		int count = group.getChildCount();
		View v;
		for(int i = 0; i < count; i++) {
			v = group.getChildAt(i);
			if(v instanceof TextView || v instanceof Button || v instanceof EditText/*etc.*/)
				((TextView)v).setTypeface(AppConstants.REGULAR);
			else if(v instanceof ViewGroup)
				setTypeFace((ViewGroup)v);
		}
	}

	@Override
	protected void setTypeFace() {

	}

	class RunshowCustomDialogs implements Runnable
	{
		private String strTitle;// Title of the dialog
		private String strMessage;// Message to be shown in dialog
		private String firstBtnName;
		private String secondBtnName;
		private String from;
		private boolean isCancelable=false;
		private OnClickListener posClickListener;
		private OnClickListener negClickListener;

		public RunshowCustomDialogs(Context context, String strTitle,String strMessage, String firstBtnName, String secondBtnName,	String from, boolean isCancelable)
		{
			this.strTitle 		= strTitle;
			this.strMessage 	= strMessage;
			this.firstBtnName 	= firstBtnName;
			this.secondBtnName	= secondBtnName;
			this.isCancelable 	= isCancelable;
			if (from != null)
				this.from = from;
			else
				this.from = "";
		}
		public void setTypeFaceRobotoREGULAR(ViewGroup group)
		{
			int count = group.getChildCount();
			View v;
			for(int i = 0; i < count; i++) {
				v = group.getChildAt(i);
				if(v instanceof TextView || v instanceof Button || v instanceof EditText/*etc.*/)
					((TextView)v).setTypeface(AppConstants.REGULAR);
				else if(v instanceof ViewGroup)
					setTypeFaceRobotoREGULAR((ViewGroup)v);
			}
		}

		@Override
		public void run()
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				if (!isFinishing()) {
					final AlertDialog.Builder builder = new AlertDialog.Builder(NotificationActivity.this);
					final AlertDialog alertDialog = builder.create();
					alertDialog.setTitle("iKonnect");
//					alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//					alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
					TextView myMsg = new TextView(NotificationActivity.this);
					myMsg.setTextColor(getResources().getColor(R.color.text_color_dark));
					myMsg.setText(""+getString(R.string.user_login));
					if (preference.getStringFromPreference(Preference.LANGUAGE, "").equalsIgnoreCase(AppConstants.ENGLISH)) {
						myMsg.setPadding(40, 15, 0, 0);//left,top,right,bottom
						myMsg.setGravity(Gravity.LEFT);
					} else {
						myMsg.setPadding(0, 15, 40, 0);
						myMsg.setGravity(Gravity.RIGHT);
					}
					builder.setView(myMsg);
					alertDialog.setView(myMsg);
//                    alertDialog.setMessage(strMessage);
					alertDialog.setCancelable(false);
					alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, firstBtnName, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							onButtonYesClick(from);
							clearPreference();
//							Intent intent = new Intent(NotificationActivity.this, UserNavigationActivity.class);
							Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
							intent.putExtra("From","menu");
							startActivity(intent);
							finish();
						}
					});
					if (secondBtnName != null && !secondBtnName.equalsIgnoreCase("")) {
						alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, secondBtnName, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								onButtonNoClick(from);
							}
						});
					}
					alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
						@Override
						public void onShow(DialogInterface arg0) {
							alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_color));
							Button button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
							if (button != null) {
								button.setTextColor(getResources().getColor(R.color.app_color));
							}
						}
					});
					alertDialog.show();
				}
			} else {
				if (customDialog != null && customDialog.isShowing())
					customDialog.dismiss();

				View view = inflater.inflate(R.layout.custom_common_popup, null);
				customDialog = new CustomDialog(NotificationActivity.this, view, preference
						.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40, ViewGroup.LayoutParams.WRAP_CONTENT, true);
				customDialog.setCancelable(isCancelable);
				TextView tvTitle = (TextView) view.findViewById(R.id.tvTitlePopup);
//				View ivDivider = view.findViewById(R.id.ivDividerPopUp);
//				View view_middle = view.findViewById(R.id.view_middle);
				TextView tvMessage = (TextView) view.findViewById(R.id.tvMessagePopup);
				Button btnYes = (Button) view.findViewById(R.id.btnYesPopup);
				Button btnNo = (Button) view.findViewById(R.id.btnNoPopup);

				tvTitle.setTypeface(AppConstants.MEDIUM);
				tvMessage.setTypeface(AppConstants.REGULAR);
				btnYes.setTypeface(AppConstants.MEDIUM);
				btnNo.setTypeface(AppConstants.MEDIUM);
				if (!StringUtils.isEmpty(strTitle)) {
					tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					tvTitle.setText("" + strTitle);
				} else {
					tvTitle.setVisibility(View.GONE);
//					ivDivider.setVisibility(View.GONE);
				}
				tvMessage.setText("" + strMessage);
				btnYes.setText("" + firstBtnName);

				if (secondBtnName != null && !secondBtnName.equalsIgnoreCase(""))
					btnNo.setText("" + secondBtnName);
				else {
					btnNo.setVisibility(View.GONE);
//					view_middle.setVisibility(View.GONE);
				}

				if (posClickListener == null)
					btnYes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							customDialog.dismiss();
							clearPreference();
//							Intent intent = new Intent(NotificationActivity.this, UserNavigationActivity.class);
							Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
							intent.putExtra("From","menu");
							startActivity(intent);
							onButtonYesClick(from);
						}
					});
				else
					btnYes.setOnClickListener(posClickListener);

				if (negClickListener == null)
					btnNo.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							customDialog.dismiss();
							onButtonNoClick(from);
						}
					});
				else
					btnNo.setOnClickListener(negClickListener);
				try {
					if (!customDialog.isShowing())
						customDialog.showCustomDialog();
				} catch (Exception e) {
				}
			}
		}
	}
	@Override
	public void initialize() {

	}

	@Override
	public void onBackPressed() {
		try{
			if (customDialog != null && !customDialog.isShowing())
				customDialog.dismiss();
		}catch(Exception e){}
		finish();
	}
}
