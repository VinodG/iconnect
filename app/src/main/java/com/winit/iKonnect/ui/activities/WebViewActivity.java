package com.winit.iKonnect.ui.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.winit.common.constant.ConstantExtraKey;
import com.winit.iKonnect.R;

public class WebViewActivity extends Activity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.web_view_activity);
		webView = (WebView) findViewById(R.id.webview);

		webView.setVisibility(View.VISIBLE);
		webView.setWebViewClient(new Webclient(WebViewActivity.this));
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+getIntent().getStringExtra(ConstantExtraKey.WEB_URL));

	}


	public void showLoader()
	{
		runOnUiThread(new RunShowLoader("Loading..."));
	}

	public void hideLoader()
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					if(progressDialog != null && progressDialog.isShowing())
						progressDialog.dismiss();
					progressDialog = null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/** This will shows a progress dialog with loading text, this is useful to call when some other functionality is taking place. **/
	public void showLoader(String msg)
	{
		try
		{
			runOnUiThread(new RunShowLoader(msg));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public ProgressDialog progressDialog;
	/**
	 * Implementing Runnable for runOnUiThread(), This will show a progress dialog
	 */

	class RunShowLoader implements Runnable
	{
		private String strMsg;

		public RunShowLoader(String strMsg) {
			this.strMsg = strMsg;
		}

		@Override
		public void run() {
			try {

				if (progressDialog == null || (progressDialog != null && !progressDialog.isShowing()))
				{
					progressDialog = ProgressDialog.show(WebViewActivity.this, "", strMsg);
				}
				else if (progressDialog == null || (progressDialog != null && progressDialog.isShowing()))
				{
					progressDialog.setMessage(strMsg);
				}

			} catch (Exception e) {
				e.printStackTrace();
				progressDialog = null;
			}
		}
	}


	public class Webclient extends WebViewClient
	{
		//		ProgressDialog dialog;
		Context context;

		public Webclient(Context context) {
			this.context = context;
		}
		boolean isDone=false;
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			if(!isDone)
				return super.shouldOverrideUrlLoading(view, url);
			else
				return false;
		}

		@Override
        public void onReceivedSslError(WebView view,final SslErrorHandler handler, SslError error) {
			hideLoader();
			Log.d("AuthorizationRequest", "onReceivedSslError");
		    AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
		    AlertDialog alertDialog = builder.create();
		    String message = "Certificate error.";
		    switch (error.getPrimaryError()) {
		        case SslError.SSL_UNTRUSTED:
		            message = "The certificate authority is not trusted.";
		            break;
		        case SslError.SSL_EXPIRED:
		            message = "The certificate has expired.";
		            break;
		        case SslError.SSL_IDMISMATCH:
		            message = "The certificate Hostname mismatch.";
		            break;
		        case SslError.SSL_NOTYETVALID:
		            message = "The certificate is not yet valid.";
		            break;
		    }
		    message += " Do you want to continue anyway?";
		    alertDialog.setTitle("SSL Certificate Error");
		    alertDialog.setMessage(message);
		    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new OnClickListener() {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		            Log.d("CHECK", "Button ok pressed");
		            // Ignore SSL certificate errors
		            handler.proceed();
		        }
		    });
		    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new OnClickListener() {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		            Log.d("CHECK", "Button cancel pressed");
		            handler.cancel();
		        }
		    });
		    alertDialog.show();
		    
		
        }

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			hideLoader();
			super.onPageFinished(view, url);
		}
	}
}
