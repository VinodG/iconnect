package com.winit.iKonnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;

import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.dataobject.MessageDO;
import com.winit.iKonnect.ui.activities.PersonalChatRoomActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Rohitmanohar on 13-07-2017.
 */

public class PersonalChatMemberAdapter extends RecyclerView.Adapter<PersonalChatMemberAdapter.ViewHolder>
{
    private Context context ;
    private ArrayList<ChatMemberDO> arrChatMember;
    private String Name;
    private HashMap<String,Vector<MessageDO>> hmPersonalChatCount = new HashMap<>();
    public PersonalChatMemberAdapter(Context context, ArrayList<ChatMemberDO> arrChatMember)
    {
      this.context=context;
      this.arrChatMember=arrChatMember;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.personal_chat_member_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final ChatMemberDO chatMemberDO = arrChatMember.get(position);
        holder.tvGroupName.setText(chatMemberDO.getStaffName());
        if(hmPersonalChatCount!=null && hmPersonalChatCount.containsKey(chatMemberDO.getStaffNumber()) && hmPersonalChatCount.get(chatMemberDO.getStaffNumber()).size()>0)
        {
            holder.tv_count.setVisibility(View.VISIBLE);
            holder.tv_count.setText(""+hmPersonalChatCount.get(chatMemberDO.getStaffNumber()).size());
        }
        else
            holder.tv_count.setVisibility(View.GONE);
        holder.tvGroupDescription.setText(chatMemberDO.getStaffNumber());
        holder.ivProfile.setTag(R.string.isRound,true);
        IKonnectApplication.setImageUrl(holder.ivProfile, ServiceUrls.PROFILE_PIC+ chatMemberDO.getPhotoUrl(),R.drawable.profile_pic);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PersonalChatRoomActivity.class);
                intent.putExtra("chatMemberDO",chatMemberDO);
                context.startActivity(intent);

            }
        });
        if(!TextUtils.isEmpty(chatMemberDO.getStaffName()) && chatMemberDO.getStaffName().equalsIgnoreCase(Name)) {
            holder.view.performClick();
            Name="";
        }
    }

    @Override
    public int getItemCount() {
        if(arrChatMember != null)
            return arrChatMember.size();
        return 0;
    }

    public void refresh(ArrayList<ChatMemberDO> arrChatMember) {
        this.arrChatMember = arrChatMember;
        notifyDataSetChanged();
    }
    public void refreshfornotification(ArrayList<ChatMemberDO> arrChatMember, String Name) {
        this.arrChatMember = arrChatMember;
        this.Name=Name;
        notifyDataSetChanged();
    }
    public void refreshPersonalChatForCount(HashMap<String,Vector<MessageDO>> hmPersonalChatCount)
    {
        this.hmPersonalChatCount=hmPersonalChatCount;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvGroupName,tvGroupDescription,tv_count;
    View view;
    ImageView ivProfile;
    public ViewHolder(View view) {
        super(view);
        this.view = view;
        tvGroupName = (TextView) view.findViewById(R.id.tvGroupName);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        tvGroupDescription = (TextView) view.findViewById(R.id.tvGroupDescription);
        ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
    }
}
}
