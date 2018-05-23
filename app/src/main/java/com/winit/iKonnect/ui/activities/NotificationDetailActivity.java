package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.NetworkUtility;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.HttpService;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.databinding.NotificationDetailActivityBinding;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.dataobject.NotificationDetailsDO;
import com.winit.iKonnect.dataobject.response.NotificationDetailResponse;
import com.winit.iKonnect.module.inbox.IInboxPresenter;
import com.winit.iKonnect.module.inbox.IInboxView;
import com.winit.iKonnect.module.inbox.InboxPresenter;
import com.winit.iKonnect.parser.NotificationParser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Girish Velivela on 5/28/2017.
 */

public class NotificationDetailActivity extends BaseActivity implements HttpService.HttpListener,IInboxView
{

    @Nullable
    @Bind(R.id.rvNotificationDetails)
    RecyclerView rvNotificationDetails;

    @Nullable
    @Bind(R.id.tvRemarks)
    TextView tvRemarks;

    NotificationDO notificationDO;
    boolean isFromNotification;
    private IInboxPresenter iInboxPresenter;

    @Override
    protected void initialize() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NotificationDetailActivityBinding notificationDetailActivityBinding = DataBindingUtil.inflate(inflater, R.layout.notification_detail_activity, flBody, true);
        ButterKnife.bind(this);
        flBody.setBackgroundColor(getResources().getColor(R.color.white));
        notificationDO = (NotificationDO) getIntent().getSerializableExtra(ConstantExtraKey.NOTIFICATION_OBJECT);
        isFromNotification = getIntent().getBooleanExtra(ConstantExtraKey.IS_FROM_NOTIFICATION,false);
       /* notificationDetailActivityBinding.setNotificationDO(notificationDO);
        setToolbarTitle(notificationDO.getTitle());
        if(!isFromNotification) {
            setDetails(notificationDO.getRemarks(),notificationDO.getAttachments());
        }else if (notificationDO.isHasAttachments()) {
            showLoader(getString(R.string.pleaseWait));
            HttpService httpService = new HttpService();
            httpService.executeAsyncTask(ServiceUrls.ServiceAction.NOTIFICATION_DETAILS, "?id=" + notificationDO.getId(), this);
        }
*/
        iInboxPresenter = new InboxPresenter(this);

        if(NetworkUtility.isNetworkConnectionAvailable(NotificationDetailActivity.this)) {
            iInboxPresenter.getInboxData();

            ///**************************Directly we are coming from notification****************************
            if(getIntent().hasExtra("message"))
            {
                String message = getIntent().getExtras().getString("message");
                NotificationParser notificationParser = new NotificationParser();
                notificationParser.parse(new StringBuilder(message));
                notificationDO = (NotificationDO) notificationParser.getData();
            }
            ////********************************************************************************************
            if(notificationDO!=null)
            {

                notificationDetailActivityBinding.setNotificationDO(notificationDO);
                setToolbarTitle(notificationDO.getTitle());
                setDetails(notificationDO.getRemarks(),notificationDO.getAttachments());
                if (notificationDO.isHasAttachments())
                {
                    showLoader(getString(R.string.pleaseWait));
                    HttpService httpService = new HttpService();
                    httpService.executeAsyncTask(ServiceUrls.ServiceAction.NOTIFICATION_DETAILS, "?id=" + notificationDO.getId(), this);
                }
            }
        }
    }

    private void setDetails(String remarks,List<String> attachments){
        if(attachments != null && attachments.size() >0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationDetailActivity.this);
            rvNotificationDetails.setLayoutManager(linearLayoutManager);
            rvNotificationDetails.setItemAnimator(new DefaultItemAnimator());
            rvNotificationDetails.setAdapter(new NotificationAttachmentAdapter(attachments));
        }
        if(!StringUtils.isEmpty(remarks)){
            tvRemarks.setVisibility(View.VISIBLE);
            tvRemarks.setText(remarks);
        }
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onInboxMessages(List<NotificationDO> inboxDOs) {

    }

    private class NotificationAttachmentAdapter extends RecyclerView.Adapter<NotificationAttachmentAdapter.ViewHolder>{

        private List<String> attachments;

        public NotificationAttachmentAdapter(List<String> attachments) {
            this.attachments = attachments;
        }

        @Override
        public NotificationAttachmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.notification_detail_cell,null);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.ivDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = (String) view.getTag();
                    IKonnectApplication.download(ServiceUrls.NOTIFICATION_DATA + name);
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(NotificationAttachmentAdapter.ViewHolder holder, int position) {
            holder.tvAttachmentName.setText(attachments.get(position)+"");
            holder.ivDownload.setTag(attachments.get(position));
        }

        @Override
        public int getItemCount() {
            if(attachments != null)
                return attachments.size();
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivDownload;
            private TextView tvAttachmentName;
            private LinearLayout linearLayout;
            public ViewHolder(View view) {
                super(view);
                tvAttachmentName = (TextView) view.findViewById(R.id.tvAttachmentName);
                linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
                ivDownload = (ImageView) view.findViewById(R.id.ivDownload);
            }

        }
    }
    NotificationDetailsDO notificationDetailsDO = null;
    @Override
    public void onResponseReceived(Response response) {
        hideLoader();
        if (response != null && response.data != null) {
            if (response.data instanceof NotificationDetailResponse) {
                NotificationDetailResponse notificationDetailResponse = (NotificationDetailResponse) response.data;
                if (notificationDetailResponse.getStatusCode() == 200)
                    notificationDetailsDO = notificationDetailResponse.getNotificationDetailModel();
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(notificationDetailsDO != null){
                    setDetails(notificationDetailsDO.getRemarks(),notificationDetailsDO.getAttachments());
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //listener for home
        if (item.getItemId() == android.R.id.home) {
            if (isBackAllowed)
            {
                if(isFromNotification)
                {
                    Intent in = new Intent(NotificationDetailActivity.this,DashboardActivity.class);
                    startActivity(in);
                    finish();
                }else
                    finish();
            }
            else
                drawerLayout.openDrawer(navigationView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(isFromNotification)
        {
            Intent in = new Intent(NotificationDetailActivity.this,DashboardActivity.class);
            startActivity(in);
            finish();
        }
        else
            super.onBackPressed();
    }

}
