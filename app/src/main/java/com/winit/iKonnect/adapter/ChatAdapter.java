package com.winit.iKonnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.util.CalendarUtil;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Girish Velivela on 5/15/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>  {

    private Context context;
    private List<ChatMessage> chatMessages;
    private String userId;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
        userId = Preference.getInstance().getStringFromPreference(Preference.STAFF_NUMBER,"");
//        userId = "9963";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_message_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        TextView tvTemp,tvTime;
        ImageView ivTemp;
        if(userId.equalsIgnoreCase(chatMessage.getUserId())){
            tvTemp = holder.tvShelfMessage;
            tvTime = holder.tvShelftime;
            ivTemp = holder.ivShelfProfile;
            holder.rlFriend.setVisibility(View.GONE);
            holder.rlShelf.setVisibility(View.VISIBLE);
        }else{
            holder.tvFriendName.setText(chatMessage.getUserName());
            tvTemp = holder.tvFriendText;
            tvTime = holder.tvFriendTime;
            ivTemp = holder.ivProfile;
            holder.tvFriendName.setText(chatMessage.getUserName());
            holder.rlShelf.setVisibility(View.GONE);
            holder.rlFriend.setVisibility(View.VISIBLE);
        }
        tvTemp.setText(chatMessage.getMessage());
        Date date = new Date(chatMessage.getChatTime());
        java.text.DateFormat format = new SimpleDateFormat(CalendarUtil.SEC_DATE_PATTERN);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = format.format(date);
        tvTime.setText(CalendarUtil.getDetailTime(formatted));
//        tvTime.setText(CalendarUtil.getDetailTime(""+chatMessage.getChatTime()));
        ivTemp.setTag(R.string.isRound,true);
        IKonnectApplication.setImageUrl(ivTemp, ServiceUrls.PROFILE_PIC+ chatMessage.getPhotoUrl(),R.drawable.profile_pic);
    }

    @Override
    public int getItemCount() {
        if(chatMessages != null)
            return chatMessages.size();
        return 0;
    }

    public void refresh(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlFriend,rlShelf;
        ImageView ivProfile,ivShelfProfile;
        TextView tvFriendText,tvShelfMessage,tvShelftime,tvFriendTime,tvFriendName;
        public ViewHolder(View view) {
            super(view);
            rlFriend = (RelativeLayout) view.findViewById(R.id.rlFriend);
            rlShelf = (RelativeLayout) view.findViewById(R.id.rlShelf);
            ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
            ivShelfProfile = (ImageView) view.findViewById(R.id.ivShelfProfile);
            tvFriendName = (TextView) view.findViewById(R.id.tvFriendName);
            tvFriendText = (TextView) view.findViewById(R.id.tvFriendText);
            tvShelftime = (TextView) view.findViewById(R.id.tvShelftime);
            tvFriendName = (TextView) view.findViewById(R.id.tvFriendName);
            tvFriendTime = (TextView) view.findViewById(R.id.tvFriendTime);
            tvShelfMessage = (TextView) view.findViewById(R.id.tvShelfMessage);
        }
    }
}


