package com.winit.iKonnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.response.EmpContributions;

/**
 * Created by VIkash on 6/19/2017.
 */

public class ContributionAdapter extends RecyclerView.Adapter<ContributionAdapter.ViewHolder> {
    private Context context;
    private  EmpContributions[] empContributions;

    public ContributionAdapter(Context context, EmpContributions[] empContributions) {
        this.empContributions = empContributions;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payslip_earning_cell,parent,false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final EmpContributions empContribution = empContributions[position];
        viewHolder.tvEarningType.setText(""+empContribution.getWageType());
        viewHolder.tvUnits.setText(""+empContribution.getUnits());
        viewHolder.tvRate.setText(""+empContribution.getRate());
        viewHolder.tvNumber.setText(""+empContribution.getNumber());
        viewHolder.tvAmount.setText(""+empContribution.getAmount());
        viewHolder.tvArrears.setText(""+empContribution.getRatro());
    }

    @Override
    public int getItemCount() {
        if(empContributions!=null && empContributions.length>0){

            return empContributions.length;
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
    public void refresh(EmpContributions[] empContributions){
        this.empContributions=empContributions;
        notifyDataSetChanged();
    }
}
