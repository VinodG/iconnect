package com.winit.iKonnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.response.EmpAbsenceDetails;

/**
 * Created by VIkash on 6/19/2017.
 */

public class AbsenceDetailsAdapter extends RecyclerView.Adapter<AbsenceDetailsAdapter.ViewHolder> {
    private Context context;
    private  EmpAbsenceDetails[] empAbsenceDetails;

    public AbsenceDetailsAdapter(Context context, EmpAbsenceDetails[] empAbsenceDetails) {
        this.empAbsenceDetails = empAbsenceDetails;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payslip_earning_cell,parent,false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final EmpAbsenceDetails empAbsenceDetail = empAbsenceDetails[position];
        viewHolder.tvEarningType.setText(""+empAbsenceDetail.getAbsentType());
        viewHolder.tvUnits.setText(""+empAbsenceDetail.getEndDate());
        viewHolder.tvRate.setText(""+empAbsenceDetail.getAbsentDays());
        viewHolder.tvNumber.setText(""+empAbsenceDetail.getStartDate());
//        viewHolder.tvAmount.setText(""+empAbsenceDetail.getAmount());
//        viewHolder.tvEarningType.setText(""+empAbsenceDetail.getWagetype());
        viewHolder.tvAmount.setVisibility(View.GONE);
        viewHolder.tvArrears.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if(empAbsenceDetails!=null && empAbsenceDetails.length>0){

            return empAbsenceDetails.length;
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
    public void refresh(EmpAbsenceDetails[] empAbsenceDetails){
        this.empAbsenceDetails=empAbsenceDetails;
        notifyDataSetChanged();
    }
}
