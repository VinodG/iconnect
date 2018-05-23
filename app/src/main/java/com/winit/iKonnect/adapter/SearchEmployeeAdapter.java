package com.winit.iKonnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.ui.activities.PersonalChatRoomActivity;

import java.util.ArrayList;

/**
 * Created by Rohitmanohar on 12-07-2017.
 */

public class SearchEmployeeAdapter extends RecyclerView.Adapter<SearchEmployeeAdapter.MyViewHolder>
{
    private ArrayList<ChatMemberDO> arrAvailableMember;
    private Context context;
    public SearchEmployeeAdapter(Context context,ArrayList<ChatMemberDO> arrAvailableMember)
    {
        this.arrAvailableMember = arrAvailableMember;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.serach_emp_cell,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        final ChatMemberDO chatMemberDO=arrAvailableMember.get(position);
        holder.tv_EmpId.setText(""+chatMemberDO.getStaffNumber());
        holder.tv_EmpName.setText(""+chatMemberDO.getStaffName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, PersonalChatRoomActivity.class);
                intent.putExtra("chatMemberDO",chatMemberDO);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if(arrAvailableMember!=null && arrAvailableMember.size()>0)
            return arrAvailableMember.size();
        else
            return 0;
    }

    public void refresh(ArrayList<ChatMemberDO> arrAvailableMember) {
        this.arrAvailableMember = arrAvailableMember;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv_EmpId,tv_EmpName;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            tv_EmpId   = (TextView) itemView.findViewById(R.id.tv_EmpId);
            tv_EmpName = (TextView) itemView.findViewById(R.id.tv_EmpName);
        }
    }
}
