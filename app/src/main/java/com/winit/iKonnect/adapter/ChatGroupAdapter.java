package com.winit.iKonnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.ChatGroupDO;
import com.winit.iKonnect.dataobject.MessageDO;
import com.winit.iKonnect.ui.activities.ChatActivity;
import com.winit.iKonnect.ui.activities.ChatRoomActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Girish Velivela on 5/15/2017.
 */

public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupAdapter.ViewHolder>  {

    private Context context;
    private List<ChatGroupDO> chatGroupDOs;
    private String grpName="";
    private HashMap<String,Vector<MessageDO>> hmGroupUnreadMessages;
    private HashMap<String,String> hmChatGroupKeys = new HashMap<>();

    public ChatGroupAdapter(Context context, List<ChatGroupDO> chatGroupDOs) {
        this.context = context;
        this.chatGroupDOs = chatGroupDOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_group_cell, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ChatGroupDO chatGroupDO = (ChatGroupDO) v.getTag();
                if(((ChatActivity)context).hmChatGroupKeys!=null && ((ChatActivity)context).hmChatGroupKeys.containsKey(chatGroupDO.getId()))
                {
                    Intent intent = new Intent(context,ChatRoomActivity.class);
                    chatGroupDO.setGroupKey( ((ChatActivity)context).hmChatGroupKeys.get(chatGroupDO.getId()));
                    intent.putExtra("chatGroupDO", chatGroupDO);
                    context.startActivity(intent);
                }
                else
                    ((ChatActivity)context).showAlert("Unable to connect","");
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatGroupDO chatGroupDO = chatGroupDOs.get(position);
        holder.tvGroupName.setText(chatGroupDO.getName());
        holder.tvGroupDescription.setText(chatGroupDO.getDescription());


        if(hmGroupUnreadMessages!=null && hmGroupUnreadMessages.containsKey(""+chatGroupDO.getId()) && hmGroupUnreadMessages.get(""+chatGroupDO.getId()).size()>0)
        {
            holder.tv_count.setVisibility(View.VISIBLE);
            holder.tv_count.setText(hmGroupUnreadMessages.get(""+chatGroupDO.getId()).size()+"");
        }
        else
        {
            holder.tv_count.setVisibility(View.GONE);
            holder.tv_count.setText("");
        }

        if(hmChatGroupKeys!=null && hmChatGroupKeys.containsKey(""+chatGroupDO.getId()))
            chatGroupDO.setGroupKey(hmChatGroupKeys.get(""+chatGroupDO.getId()));

        holder.view.setTag(chatGroupDO);
        if(!TextUtils.isEmpty(grpName) && chatGroupDO.getName().equalsIgnoreCase(grpName)) {
            holder.view.performClick();
            grpName="";
        }
    }

    @Override
    public int getItemCount() {
        if(chatGroupDOs != null)
            return chatGroupDOs.size();
        return 0;
    }

    public void refresh(List<ChatGroupDO> chatGroupDOs) {
        this.chatGroupDOs = chatGroupDOs;
        notifyDataSetChanged();
    }
    public void refreshforNotification(List<ChatGroupDO> chatGroupDOs, String grpName) {
        this.chatGroupDOs = chatGroupDOs;
        this.grpName = grpName;
        notifyDataSetChanged();
    }

    public void refreshChatGroupForCout(HashMap<String,Vector<MessageDO>> hmGroupUnreadMessages) {
        this.hmGroupUnreadMessages = hmGroupUnreadMessages;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGroupName,tvGroupDescription, tv_count;
        View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tvGroupName = (TextView) view.findViewById(R.id.tvGroupName);
            tvGroupDescription = (TextView) view.findViewById(R.id.tvGroupDescription);
            tv_count = (TextView) view.findViewById(R.id.tv_count);
        }
    }
}


