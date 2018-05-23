package com.winit.iKonnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.response.EmpDeductions;

/**
 * Created by VIkash on 6/19/2017.
 */

public class DeductionAdapter extends RecyclerView.Adapter<DeductionAdapter.ViewHolder> {
    private Context context;
    private  EmpDeductions[] empDeductions;

    public DeductionAdapter(Context context, EmpDeductions[] empDeductions) {
        this.empDeductions = empDeductions;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payslip_earning_cell,parent,false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final EmpDeductions empDeduction = empDeductions[position];
        viewHolder.tvEarningType.setText(""+empDeduction.getWageType());
        viewHolder.tvUnits.setText(""+empDeduction.getUnits());
        viewHolder.tvRate.setText(""+empDeduction.getRate());
        viewHolder.tvNumber.setText(""+empDeduction.getNumber());
        viewHolder.tvAmount.setText(""+empDeduction.getAmount());
        viewHolder.tvArrears.setText(""+empDeduction.getRatro());
    }

    @Override
    public int getItemCount() {
        if(empDeductions!=null && empDeductions.length>0){

            return empDeductions.length;
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
    public void refresh(EmpDeductions[] empDeductions){
        this.empDeductions=empDeductions;
        notifyDataSetChanged();
    }
}
