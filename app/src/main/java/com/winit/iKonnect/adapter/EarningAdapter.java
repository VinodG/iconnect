package com.winit.iKonnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.response.EmpEarnings;

import java.util.HashMap;

/**
 * Created by VIkash on 6/19/2017.
 */

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.ViewHolder> {
    private Context context;
    private  EmpEarnings[] empEarnings;
    private HashMap<Integer, Integer> hmHeight;

    public EarningAdapter(Context context, EmpEarnings[] empEarnings) {
        this.empEarnings = empEarnings;
        this.context = context;
    }

    public EarningAdapter(Context context, EmpEarnings[] empEarnings,RecyclerView rvEarnings,HashMap<Integer, Integer> hmHeight) {
        this.empEarnings = empEarnings;
        this.context = context;
        hmHeight.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payslip_earning_cell,parent,false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final EmpEarnings empEarning = empEarnings[position];
        if(empEarning.getWagetype()!=null)
            viewHolder.tvEarningType.setText(""+empEarning.getWagetype());
        else
            viewHolder.tvEarningType.setText("N/A");
        viewHolder.tvUnits.setText(""+empEarning.getUnits());
        viewHolder.tvRate.setText(""+empEarning.getRatro());
        viewHolder.tvNumber.setText(""+empEarning.getNumber());
        viewHolder.tvAmount.setText(""+empEarning.getAmount());

        viewHolder.itemView.post(new Runnable() {
            @Override
            public void run() {

//                int cellWidth = viewHolder.itemView.getWidth();// this will give you cell width dynamically
                int cellHeight = viewHolder.itemView.getHeight();// this will give you cell height dynamically

//                if(context instanceof PaySlipActivity)
//                    ((PaySlipActivity)context).HeightChange(position, cellHeight); //call your iterface hear
            }
        });
    }

    @Override
    public int getItemCount() {
        if(empEarnings!=null && empEarnings.length>0){

            return empEarnings.length;
        }
        else
            return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNumber,tvUnits,tvRate,tvArrears,tvAmount, tvEarningType;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNumber = (TextView)itemView.findViewById(R.id.tvNumber);
            tvUnits= (TextView)itemView.findViewById(R.id.tvUnits);
            tvRate= (TextView)itemView.findViewById(R.id.tvRate);
            tvArrears= (TextView)itemView.findViewById(R.id.tvArrears);
            tvAmount= (TextView)itemView.findViewById(R.id.tvAmount);
            tvEarningType= (TextView)itemView.findViewById(R.id.tvEarningType);
        }
    }
    public void refresh(EmpEarnings[] empEarnings){
        this.empEarnings=empEarnings;
        notifyDataSetChanged();
    }
    public void refresh(EmpEarnings[] empEarnings,HashMap<Integer, Integer> hmHeight){
        this.empEarnings=empEarnings;
        hmHeight.clear();
        notifyDataSetChanged();
    }

    public interface DynamicHeight {
        void HeightChange (int position, int height);
    }
}
