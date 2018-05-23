package com.winit.iKonnect.ui.activities;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.winit.common.Preference;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.PersonalChatRoomAdapter;
import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.dataobject.ChatMessage;
import com.winit.iKonnect.module.personalchat.PersonalChatSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public class PersonalChatRoomActivity extends BaseActivity
{
    public RecyclerView rvMessages;
    public EditText msg_input;
    public LinearLayout llComment;
    public ImageView ivSend;

    private int senderId,receiverId;
    private String chatRelationKey ="",temp_key="";
    private DatabaseReference root ;
    private DatabaseReference root_Count ;
    private ChatMemberDO chatMemberDO;
    private PersonalChatRoomAdapter chatAdapter;

    @Override
    protected void initialize()
    {
        inflater.inflate(R.layout.chat_room_activity,flBody,true);
        rvMessages = (RecyclerView) findViewById(R.id.rvMessages);
        msg_input = (EditText) findViewById(R.id.msg_input);
        llComment = (LinearLayout) findViewById(R.id.llComment);
        ivSend = (ImageView) findViewById(R.id.ivSend);

        if(getIntent().hasExtra("chatMemberDO"))
            chatMemberDO = (ChatMemberDO) getIntent().getExtras().get("chatMemberDO");

        if(chatMemberDO!=null)
        {
            receiverId =StringUtils.getInt(chatMemberDO.getStaffNumber());
            setToolbarTitle(""+chatMemberDO.getStaffName());
        }

        senderId = StringUtils.getInt(preference.getStringFromPreference(Preference.STAFF_NUMBER,""));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PersonalChatRoomActivity.this);

        rvMessages.setLayoutManager(mLayoutManager);
        rvMessages.setItemAnimator(new DefaultItemAnimator());
        rvMessages.setAdapter(chatAdapter = new PersonalChatRoomAdapter(PersonalChatRoomActivity.this, null));
        if(senderId>receiverId)
            chatRelationKey = senderId+"-"+ receiverId;
        else
            chatRelationKey = receiverId+"-"+senderId;
        root = FirebaseDatabase.getInstance().getReference().child("ChatPersonal").child(chatRelationKey).child("messages");
        root_Count = FirebaseDatabase.getInstance().getReference().child("ChatPersonal").child(chatRelationKey).child("LastReadTime");
        long now = System.currentTimeMillis();
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put(""+preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), now);
        root_Count.updateChildren(map3);

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isEmpty(msg_input.getText().toString()))
                    showCustomDialog(PersonalChatRoomActivity.this,getString(R.string.alert),getString(R.string.enter_your_message),getString(R.string.OK),"","");
                else
                {
                    for(int i=0;i<chatMemberDO.getFCMDeviceToken().size();i++)
                    {
                        PersonalChatSingleton.getInstance().notifiedData(msg_input.getText().toString(),preference.getStringFromPreference(Preference.STAFF_NAME, ""),preference.getStringFromPreference(Preference.STAFF_NUMBER, ""),chatMemberDO.getFCMDeviceToken().get(i));
                    }
                    showLoader(getString(R.string.pleaseWait));
                    Map<String, Object> map = new HashMap<String, Object>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);

                    DatabaseReference message_root = root.child(temp_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("userId", receiverId);
                    map2.put("senderName", preference.getStringFromPreference(Preference.STAFF_NAME, ""));
                    map2.put("text", msg_input.getText().toString());
                    map2.put("senderId", preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                    map2.put("photourl", preference.getStringFromPreference(Preference.STAFF_PHOTO_URL, ""));
                    long now = System.currentTimeMillis();
                    map2.put("chatTime",now);
                    message_root.updateChildren(map2);
                    msg_input.setText("");
                    root_Count = FirebaseDatabase.getInstance().getReference().child("ChatPersonal").child(chatRelationKey).child("LastReadTime");
                    Map<String, Object> map3 = new HashMap<String, Object>();
                    map3.put(""+preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), now);
                    root_Count.updateChildren(map3);
                }
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                hideLoader();
                AppendMessageToConversation(dataSnapshot);
                if(dataSnapshot.getKey().contains(senderId+""))
                {
                    Iterator i = dataSnapshot.getChildren().iterator();

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                hideLoader();
                AppendMessageToConversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void setTypeFace()
    {

    }

    private ArrayList<ChatMessage> chatMessages;

    private void AppendMessageToConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()){
            if(chatMessages == null)
                chatMessages = new ArrayList<>();
            try {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setChatTime((long) ((DataSnapshot) i.next()).getValue());
                chatMessage.setPhotoUrl((String) ((DataSnapshot) i.next()).getValue());
                chatMessage.setSenderId((String) ((DataSnapshot) i.next()).getValue());
                chatMessage.setUserName((String) ((DataSnapshot) i.next()).getValue());
                chatMessage.setMessage((String) ((DataSnapshot) i.next()).getValue());
                chatMessage.setUserId(((DataSnapshot) i.next()).getValue() + "");
                chatMessages.add(chatMessage);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        rvMessages.scrollToPosition(rvMessages.getAdapter().getItemCount() - 1);
        chatAdapter.refresh(chatMessages);
    }
}
