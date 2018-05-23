package com.winit.common.util;

import android.text.TextUtils;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.winit.common.Preference;
import com.winit.iKonnect.dataobject.MessageDO;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

/**
 * Created by home on 10/8/2017.
 */

public class ChatCountUtils
{
    private DatabaseReference chatPersonalRef,groupChatRef;
    private HashMap<String,DataSnapshot> hmPersonalChatDetails=new HashMap<String,DataSnapshot>();
    private HashMap<String,DataSnapshot> hmGroupChatDetails = new HashMap<>();
    private HashMap<String,Vector<MessageDO>> hmPersonalUnreadMessage = new HashMap<>();
    private HashMap<String,Vector<MessageDO>> hmGroupUnreadMessages = new HashMap<>();
    private HashMap<Integer,String> hmChatGroupKeys = new HashMap<>();
    private Preference preference;
    private MessageCountListner listner;


    public ChatCountUtils(final MessageCountListner listner , DatabaseReference chatPersonalRef , DatabaseReference groupChatRef)
    {
        this.listner = listner;
        this.chatPersonalRef=chatPersonalRef;
        this.groupChatRef = groupChatRef;
        preference = Preference.getInstance();
        chatPersonalRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                if(!TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")) && dataSnapshot.getKey().contains(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")))
                {
                    String key = dataSnapshot.getKey().replace(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),"");
                    key=key.replace("-","");
                    hmPersonalChatDetails.put(key,dataSnapshot);
                    calculatePersonalUnreadChatMessages();
                }

                if(!TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")) )
                     listner.refreshSoloChatMembers(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                if(!TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")) && dataSnapshot.getKey().contains(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")))
                {
                    String key = dataSnapshot.getKey().replace(preference.getStringFromPreference(Preference.STAFF_NUMBER,""),"");
                    key=key.replace("-","");
                    hmPersonalChatDetails.put(key,dataSnapshot);
                    calculatePersonalUnreadChatMessages();
                }

                if(!TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")) )
                    listner.refreshSoloChatMembers(dataSnapshot);

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


        groupChatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                if(!TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")))
                {
                    for(DataSnapshot child : dataSnapshot.getChildren())
                    {
                        if(child.getKey().equalsIgnoreCase("GroupId"))
                        {
                            String keyValue =  child.getValue().toString(); // we are storing group id as Key in hmGroupChatDetails (HashMap) for calculation
                            hmGroupChatDetails.put(keyValue,dataSnapshot);
                            hmChatGroupKeys.put(StringUtils.getInt(keyValue),dataSnapshot.getKey());
                            calculateGroupUnreadMessages();
                        }
                    }
                }
                if(!TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")) )
                    listner.refreshGroupChatKeys(hmChatGroupKeys);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                if(!TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")))
                {
                    for(DataSnapshot child : dataSnapshot.getChildren())
                    {
                        if(child.getKey().equalsIgnoreCase("GroupId"))
                        {
                            String keyValue =  child.getValue().toString(); // we are storing group id as Key in hmGroupChatDetails (HashMap) for calculation
                            hmGroupChatDetails.put(keyValue,dataSnapshot);
                            hmChatGroupKeys.put(StringUtils.getInt(keyValue),dataSnapshot.getKey());
                            calculateGroupUnreadMessages();
                        }
                    }
                }
                if(!TextUtils.isEmpty(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")) )
                    listner.refreshGroupChatKeys(hmChatGroupKeys);
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

    public void calculatePersonalUnreadChatMessages()
    {
        if(hmPersonalChatDetails !=null && hmPersonalChatDetails.size()>0)
        {
            Set<String> keys = hmPersonalChatDetails.keySet();
            for(String key : keys)
            {
                DataSnapshot dataSnapshot  = hmPersonalChatDetails.get(key);
                long chatTime=0;
                if(dataSnapshot.child("LastReadTime").child(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")).getValue()!=null)
                    chatTime= (long) dataSnapshot.child("LastReadTime").child(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")).getValue();

                DataSnapshot messageSnapShot = dataSnapshot.child("messages");
                hmPersonalUnreadMessage.remove(key);
                for(DataSnapshot singelMsgShot : messageSnapShot.getChildren())
                {
                    HashMap<String,Object> hmMsData= (HashMap<String, Object>) singelMsgShot.getValue();
                    MessageDO messageDO = new MessageDO();
                    Set<String> msgKeys = hmMsData.keySet();
                    for(String childMsgObjKey : msgKeys)
                    {
                        Object value =  hmMsData.get(childMsgObjKey);
                        if(childMsgObjKey.equalsIgnoreCase("senderId"))
                            messageDO.setUserCode(((String)value));
                        else if(messageDO!=null && childMsgObjKey.equalsIgnoreCase("chatTime"))
                            messageDO.setTime(((long)value));
                        else if(messageDO!=null && childMsgObjKey.equalsIgnoreCase("text"))
                            messageDO.setMessage(((String)value));

                    }
                    Vector<MessageDO> vecMessage = hmPersonalUnreadMessage.get(key);
                    if(vecMessage==null)
                    {
                        vecMessage = new Vector<>();
                        if(messageDO!=null && messageDO.getTime()>chatTime && !(messageDO.getUserCode().equalsIgnoreCase(preference.getStringFromPreference(Preference.STAFF_ID,""))))
                            vecMessage.add(messageDO);
                        hmPersonalUnreadMessage.put(key,vecMessage);
                    }
                    else
                    {
                        if(messageDO!=null && messageDO.getTime()>chatTime && !(messageDO.getUserCode().equalsIgnoreCase(preference.getStringFromPreference(Preference.STAFF_ID,""))))
                            vecMessage.add(messageDO);
                    }
                    listner.UpdateMessageCount(hmPersonalUnreadMessage,hmGroupUnreadMessages);
                }
            }
        }
    }

    public void calculateGroupUnreadMessages()
    {
        if(hmGroupChatDetails !=null && hmGroupChatDetails.size()>0)
        {
            Set<String> keys = hmGroupChatDetails.keySet();
            for(String key : keys)
            {
                DataSnapshot dataSnapshot  = hmGroupChatDetails.get(key);
                long chatTime=0;

                if(dataSnapshot.child("LastReadTime").child(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")).getValue()!=null)
                    chatTime= (long) dataSnapshot.child("LastReadTime").child(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")).getValue();

                if(chatTime!=0)
                {
                    DataSnapshot messageSnapShot = dataSnapshot.child("messages");
                    hmGroupUnreadMessages.remove(key);
                    for(DataSnapshot singelMsgShot : messageSnapShot.getChildren())
                    {
                        HashMap<String,Object> hmMsData= (HashMap<String, Object>) singelMsgShot.getValue();
                        MessageDO messageDO = new MessageDO();
                        Set<String> msgKeys = hmMsData.keySet();
                        for(String childMsgObjKey : msgKeys)
                        {
                            Object value =  hmMsData.get(childMsgObjKey);
                            if(childMsgObjKey.equalsIgnoreCase("senderId"))
                                messageDO.setUserCode(((String)value));
                            else if(messageDO!=null && childMsgObjKey.equalsIgnoreCase("chatTime"))
                                messageDO.setTime(((long)value));
                            else if(messageDO!=null && childMsgObjKey.equalsIgnoreCase("text"))
                                messageDO.setMessage(((String)value));

                        }
                        Vector<MessageDO> vecMessage = hmGroupUnreadMessages.get(key);
                        if(vecMessage==null)
                        {
                            vecMessage = new Vector<>();
                            if(messageDO!=null && messageDO.getTime()>chatTime && !(messageDO.getUserCode().equalsIgnoreCase(preference.getStringFromPreference(Preference.STAFF_ID,""))))
                                vecMessage.add(messageDO);
                            hmGroupUnreadMessages.put(key,vecMessage);
                        }
                        else
                        {
                            if(messageDO!=null && messageDO.getTime()>chatTime && !(messageDO.getUserCode().equalsIgnoreCase(preference.getStringFromPreference(Preference.STAFF_ID,""))))
                                vecMessage.add(messageDO);
                        }
                        listner.UpdateMessageCount(hmPersonalUnreadMessage,hmGroupUnreadMessages);
                    }
                }
            }
        }
    }

    public void removeChatListner()
    {
//        if(chatPersonalRef!=null)
//            chatPersonalRef.addChildEventListener(null);
    }

    public interface MessageCountListner
    {
        public void UpdateMessageCount(HashMap<String,Vector<MessageDO>> hmPersonalChatCount,HashMap<String,Vector<MessageDO>> hmGroupUnreadMessages);
        public void refreshSoloChatMembers(DataSnapshot dataSnapshot);
        public void refreshGroupChatKeys(HashMap<Integer,String> hmChatGroupKeys);
    }

}
