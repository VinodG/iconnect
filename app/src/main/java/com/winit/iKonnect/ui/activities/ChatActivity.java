package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.ChatGroupAdapter;
import com.winit.iKonnect.adapter.PersonalChatMemberAdapter;
import com.winit.iKonnect.dataobject.ChatGroupDO;
import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.dataobject.MessageDO;
import com.winit.iKonnect.module.chat.ChatPresenter;
import com.winit.iKonnect.module.chat.IChatPresenter;
import com.winit.iKonnect.module.chat.IChatView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ChatActivity extends BaseActivity implements IChatView{

    private TextView tvNoGroups;
    private RecyclerView rvChatGroup,rv_PersonalChat;
    private ChatGroupAdapter chatGroupAdapter;
    private IChatPresenter iChatPresenter;
    public TextView tv_GroupMsg;
    public TextView tv_Pernal;
    private FloatingActionButton fab;
    private HashMap<String,ChatMemberDO> hmChatMembers= new HashMap<String,ChatMemberDO>();
    private ArrayList<ChatMemberDO> arrData= new ArrayList<>();
    private PersonalChatMemberAdapter personalChatMemberAdapter;
    private View indicator;
    private LinearLayout.LayoutParams indicatorParams;
    private String from="", title="", Staffnumber="";
    private  String grpname="";
    private boolean noGrpFound=false;
    public HashMap<Integer, String> hmChatGroupKeys;

    @Override
    protected void initialize()
    {
        inflater.inflate(R.layout.chat_activity,flBody,true);
        if(getIntent().hasExtra("type"))
            from = getIntent().getExtras().getString("type");
        if(getIntent().hasExtra("whom"))
            title = getIntent().getExtras().getString("whom");
        if(getIntent().hasExtra("Staffnumber"))
            Staffnumber = getIntent().getExtras().getString("Staffnumber");
        if(getIntent().hasExtra("grpname"))
            grpname = getIntent().getExtras().getString("grpname");
        setToolbarTitle(getString(R.string.Chat));
        iChatPresenter = new ChatPresenter(this);
        initializecontrols();


        rvChatGroup.setVisibility(View.VISIBLE);
        rv_PersonalChat.setVisibility(View.GONE);
        indicator.setLayoutParams(indicatorParams = new LinearLayout.LayoutParams((preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH,0)/2),(int)(2*BaseActivity.px)));

        //***************************Group Chat List Adapter**********************************************************************
        RecyclerView.LayoutManager layoutManagerGroupChat = new LinearLayoutManager(ChatActivity.this);
        rvChatGroup.setLayoutManager(layoutManagerGroupChat);
        rvChatGroup.setItemAnimator(new DefaultItemAnimator());
        rvChatGroup.setAdapter(chatGroupAdapter = new ChatGroupAdapter(ChatActivity.this,new ArrayList<ChatGroupDO>()));
        //**************************************************************************************************************************

        //***************************Personal Chat List Adapter**********************************************************************
        RecyclerView.LayoutManager layoutManagerPersonalChat = new LinearLayoutManager(ChatActivity.this);
        rv_PersonalChat.setLayoutManager(layoutManagerPersonalChat);
        rv_PersonalChat.setItemAnimator(new DefaultItemAnimator());
        rv_PersonalChat.setAdapter(personalChatMemberAdapter = new PersonalChatMemberAdapter(ChatActivity.this,new ArrayList<ChatMemberDO>()));
        //*****************************************************************************************************************************
        showLoader(getString(R.string.pleaseWait));
        if(isNetworkConnectionAvailable())
            iChatPresenter.getChatGroups();
        else
            showCustomDialog(ChatActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);

        tv_GroupMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                rv_PersonalChat.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                indicatorParams.setMargins(0,0,0,0);
                indicatorParams.setMarginStart(0);
                indicator.setLayoutParams(indicatorParams);
                if(noGrpFound) {
                    rvChatGroup.setVisibility(View.GONE);
                    tvNoGroups.setVisibility(View.VISIBLE);
                }
                else
                {   tvNoGroups.setVisibility(View.GONE);
                    rvChatGroup.setVisibility(View.VISIBLE);
                }
            }
        });

        tv_Pernal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                rvChatGroup.setVisibility(View.GONE);
                rv_PersonalChat.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                indicatorParams.setMarginStart((preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH,0)/2));
                indicator.setLayoutParams(indicatorParams);
            }
        });
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ChatActivity.this,SearchEmployee.class);
                startActivity(in);
            }
        });
        if(from!=null && from.equalsIgnoreCase("notification"))
        {
            if(!TextUtils.isEmpty(Staffnumber)) {
                tv_Pernal.performClick();
                rvChatGroup.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void UpdateMessageCount(HashMap<String, Vector<MessageDO>> hmPersonalChatCount,HashMap<String,Vector<MessageDO>> hmGroupUnreadMessages)
    {
        super.UpdateMessageCount(hmPersonalChatCount,hmGroupUnreadMessages);
        personalChatMemberAdapter.refreshPersonalChatForCount(hmPersonalChatCount);
        chatGroupAdapter.refreshChatGroupForCout(hmGroupUnreadMessages);

    }

    @Override
    public void refreshSoloChatMembers(DataSnapshot dataSnapshot)
    {
        refreshSoloChatMemberData(dataSnapshot);
    }

    @Override
    public void refreshGroupChatKeys(HashMap<Integer, String> hmChatGroupKeys)
    {
        this.hmChatGroupKeys=hmChatGroupKeys;
    }

    @Override
    public void onButtonYesClick(String from) {
        if(from.equalsIgnoreCase("gotoDashboard"))
            finish();
        else
            super.onButtonYesClick(from);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void initializecontrols() {
        tv_GroupMsg = (TextView) findViewById(R.id.tv_GroupMsg);
        tv_Pernal= (TextView) findViewById(R.id.tv_Pernal);
        rv_PersonalChat = (RecyclerView) findViewById(R.id.rv_PersonalChat);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plus_white);

        tvNoGroups = (TextView) findViewById(R.id.tvNoGroups);
        rvChatGroup = (RecyclerView) findViewById(R.id.listView);
        indicator = (View) findViewById(R.id.indicator);
    }

    public void refreshSoloChatMemberData(DataSnapshot dataSnapshot)
    {
        String key = dataSnapshot.getKey();
        if(key.contains(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")))
        {
            key = key.replace(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),"");
            key=key.replace("-","");
            ChatMemberDO chatMemberDO = new ChatMemberDO();
            chatMemberDO.setStaffNumber(key);
            hmChatMembers.put(key,chatMemberDO);
            iChatPresenter.getChatMemberInfo(key);
        }
    }
    @Override
    protected void setTypeFace()
    {
        tv_GroupMsg.setTypeface(AppConstants.BOLD);
        tv_Pernal.setTypeface(AppConstants.BOLD);
    }

    @Override
    public void onGroupsFetched(List<ChatGroupDO> chatGroupDOs) {
        hideLoader();
        if(from!=null && from.equalsIgnoreCase("notification"))
        {
            if(TextUtils.isEmpty(Staffnumber)){
                tv_GroupMsg.performClick();
                if (chatGroupAdapter != null && arrData != null)
                    chatGroupAdapter.refreshforNotification(chatGroupDOs,grpname);
                from = "";
                title = "";
            }
        }else
        {
            if(TextUtils.isEmpty(Staffnumber)) {
                tvNoGroups.setVisibility(View.GONE);
                rvChatGroup.setVisibility(View.VISIBLE);
                chatGroupAdapter.refresh(chatGroupDOs);
                noGrpFound=false;
            }
        }
    }

    @Override
    public void noGroups() {
        hideLoader();
        rvChatGroup.setVisibility(View.GONE);
        tvNoGroups.setVisibility(View.VISIBLE);
        noGrpFound=true;
    }

    @Override
    public void getChatMemberInfo(final ArrayList<ChatMemberDO> arrEmployee)
    {
        if(arrEmployee!=null&& arrEmployee.size()>0)
        {
            final ChatMemberDO chatMemberDO = arrEmployee.get(0);
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    hmChatMembers.put(chatMemberDO.getStaffNumber(),chatMemberDO);
                    generateChatmemberData(hmChatMembers);
                }
            });

        }
    }

    public void generateChatmemberData(HashMap<String,ChatMemberDO> hmChatMembers)
    {
        arrData= new ArrayList<ChatMemberDO>();
        for(String key : hmChatMembers.keySet()) {
            arrData.add(hmChatMembers.get(key));
        }
        if(personalChatMemberAdapter !=null && arrData!=null)
            personalChatMemberAdapter.refresh(arrData);

        if(from!=null && from.equalsIgnoreCase("notification") && personalChatMemberAdapter !=null && arrData!=null) {
            if(!TextUtils.isEmpty(Staffnumber)) {

                tv_Pernal.performClick();
                if (personalChatMemberAdapter != null && arrData != null)
                    personalChatMemberAdapter.refreshfornotification(arrData, title);
                from = "";
                title = "";
            }
        }
        hideLoader();
    }

    @Override
    public void showAlert(String type) {
        if(type.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(ChatActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    @Override
    public void onBackPressed() {

        if(from!=null && from.equalsIgnoreCase("notification")) {
            Intent in = new Intent(ChatActivity.this,DashboardActivity.class);
            startActivity(in);
            finish();
        }
        else
            super.onBackPressed();
    }


}
