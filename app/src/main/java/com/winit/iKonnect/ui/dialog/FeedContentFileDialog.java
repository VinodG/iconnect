package com.winit.iKonnect.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;

import com.winit.common.Preference;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.FeedContentAdapter;
import com.winit.iKonnect.dataobject.CmspostcontentfileModels;
import com.winit.iKonnect.ui.activities.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *  Description of class : This class having Meaningful Use dialog. 
 * 
 */
public class FeedContentFileDialog extends Dialog
{
	@Bind(R.id.rvContentFile)
	public RecyclerView rvContentFile;

	public FeedContentFileDialog(Context context, List<CmspostcontentfileModels> cmspostcontentfileModelsList)
	{
		super(context, R.style.Dialog);
		LayoutInflater inflater = getLayoutInflater();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		LinearLayout llFeedContent = (LinearLayout) inflater.inflate(R.layout.feed_content_popup, null);
		llFeedContent.setGravity(Gravity.CENTER);
		Preference preference = Preference.getInstance();
		setContentView(llFeedContent,new LayoutParams(preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 0) - (int)(20 * BaseActivity.px), LayoutParams.WRAP_CONTENT));
		ButterKnife.bind(this,llFeedContent);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
		rvContentFile.setLayoutManager(linearLayoutManager);
		rvContentFile.setItemAnimator(new DefaultItemAnimator());
		rvContentFile.setAdapter(new FeedContentAdapter(context, cmspostcontentfileModelsList));

	}
}
 