package com.winit.iKonnect.ui.activities;

import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.winit.common.Preference;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.ChatAdapter;
import com.winit.iKonnect.dataobject.ChatGroupDO;
import com.winit.iKonnect.dataobject.ChatMessage;
import com.winit.iKonnect.module.personalchat.PersonalChatSingleton;
import com.winit.iKonnect.ui.customview.CustomImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by filipp on 6/28/2016.
 */
public class ChatRoomActivity extends BaseActivity {

    private ImageView ivSend;
    private EditText input_msg;
    private RecyclerView rvMessages;

    private ChatGroupDO chatGroupDO;
    private DatabaseReference root,root_Count ;
    private String temp_key;
    private ChatAdapter chatAdapter;
    private String titile;
    @Override
    protected void initialize() {
        inflater.inflate(R.layout.chat_room_activity,flBody,true);

        ivProfile = (CircleImageView) findViewById(R.id.ivProfile);
        ivSend = (ImageView) findViewById(R.id.ivSend);
        input_msg = (EditText) findViewById(R.id.msg_input);
        rvMessages = (RecyclerView) findViewById(R.id.rvMessages);

        chatGroupDO = (ChatGroupDO) getIntent().getExtras().get("chatGroupDO");
        setToolbarTitle(chatGroupDO.getName());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ChatRoomActivity.this);
        rvMessages.setLayoutManager(mLayoutManager);
        rvMessages.setItemAnimator(new DefaultItemAnimator());
        rvMessages.setAdapter(chatAdapter = new ChatAdapter(ChatRoomActivity.this, null));

        root = FirebaseDatabase.getInstance().getReference().child("ChatGroups").child(chatGroupDO.getGroupKey()).child("messages");
        root_Count = FirebaseDatabase.getInstance().getReference().child("ChatGroups").child(chatGroupDO.getGroupKey()).child("LastReadTime");
        long now = System.currentTimeMillis();
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put(""+preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), now);
        root_Count.updateChildren(map3);

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(StringUtils.isEmpty(input_msg.getText().toString()))
                    showCustomDialog(ChatRoomActivity.this,getString(R.string.alert),getString(R.string.enter_your_message),getString(R.string.OK),"","");
                else
                {
                    try
                    {
                        if (preference.getStringFromPreference(Preference.STAFF_NAME, "").contains(" "))
                            titile = preference.getStringFromPreference(Preference.STAFF_NAME, "").split(" ")[0] + " @ " + chatGroupDO.getName();
                        else
                            titile = preference.getStringFromPreference(Preference.STAFF_NAME, "") + " @ " + chatGroupDO.getName();
                    }
                    catch (Exception e)
                    {
                        titile = chatGroupDO.getName();
                    }

                    PersonalChatSingleton.getInstance().notifiedDataforGrp(input_msg.getText().toString(),chatGroupDO.getName(),titile, preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                    showLoader(getString(R.string.pleaseWait));
                    Map<String, Object> map = new HashMap<String, Object>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);
                    long now = System.currentTimeMillis();

                    DatabaseReference message_root = root.child(temp_key);
                    Map<String, Object> mapMessageData = new HashMap<String, Object>();
                    mapMessageData.put("userId", preference.getStringFromPreference(Preference.STAFF_NUMBER, ""));
                    mapMessageData.put("senderName", preference.getStringFromPreference(Preference.STAFF_NAME, ""));
                    mapMessageData.put("text", input_msg.getText().toString());
                    mapMessageData.put("senderId", preference.getStringFromPreference(Preference.GCM_ID, ""));
                    mapMessageData.put("photourl", preference.getStringFromPreference(Preference.STAFF_PHOTO_URL, ""));
                    mapMessageData.put("chatTime", now);
                    message_root.updateChildren(mapMessageData);

                    Map<String, Object> mapLastReadTime = new HashMap<String, Object>();
                    mapLastReadTime.put(""+preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), now);
                    root_Count.updateChildren(mapLastReadTime);
                    input_msg.setText("");
                }
            }
        });

        root.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                hideLoader();
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                hideLoader();
                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

    }

    @Override
    protected void setTypeFace() {

    }

    private ArrayList<ChatMessage> chatMessages;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {
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
        chatAdapter.refresh(chatMessages);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvMessages.scrollToPosition(rvMessages.getAdapter().getItemCount() - 1);
            }
        }, 10);
    }
}
