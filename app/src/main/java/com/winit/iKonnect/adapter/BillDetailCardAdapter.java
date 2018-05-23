package com.winit.iKonnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.BillDetailDo;

import java.util.ArrayList;

/**
 * Created by Rohitmanohar on 23-05-2017.
 */
public class BillDetailCardAdapter  extends RecyclerView.Adapter<BillDetailCardAdapter.MyViewHolder> {

    private Context c;
    private ArrayList<BillDetailDo> arrBillDetailDo;
    public View viewPD, viewFC,viewSAR,viewRemarks;

    public BillDetailCardAdapter(Context insuranceCard, ArrayList<BillDetailDo> arrInsurancedetail) {
        this.c=insuranceCard;
        this.arrBillDetailDo=arrInsurancedetail;
    }
    public void refresh(ArrayList<BillDetailDo> arrInsurancedetail)
    {
        this.arrBillDetailDo=arrInsurancedetail;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public EditText etpaymentDetails,etFcurrent, etSarEquip, etRemarks;
        public TextChangeListnerpaymentDetails textChangeListnerpaymentDetails;
        public TextChangeListnerFcurrent textChangeListnerFcurrent;
        public TextChangeListnerSarEquip textChangeListnerSarEquip;
        public TextChangeListnerRemarks  textChangeListnerRemarks;

        public MyViewHolder(View view) {
            super(view);
            etpaymentDetails = (EditText) view.findViewById(R.id.etpaymentDetails);
            etFcurrent = (EditText) view.findViewById(R.id.etFcurrent);
            etSarEquip = (EditText) view.findViewById(R.id.etSarEquip);
            etRemarks = (EditText) view.findViewById(R.id.etRemarks);
            etpaymentDetails.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b)
                    {
                        viewPD=view;
                    }else
                        viewPD=null;
                }
            });
            etFcurrent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b)
                    {
                        viewFC=view;
                    }else
                        viewFC=null;
                }
            });
            etSarEquip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b)
                    {
                        viewSAR=view;
                    }else
                        viewSAR=null;
                }
            });
            etRemarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b)
                    {
                        viewRemarks=view;
                    }else
                        viewRemarks=null;
                }
            });

            textChangeListnerpaymentDetails = new TextChangeListnerpaymentDetails();
            textChangeListnerFcurrent= new TextChangeListnerFcurrent();
            textChangeListnerSarEquip= new TextChangeListnerSarEquip();
            textChangeListnerRemarks= new TextChangeListnerRemarks();
        }
    }


    @Override
    public BillDetailCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.billdetail, viewGroup, false);

        return new BillDetailCardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BillDetailCardAdapter.MyViewHolder holder, int position) {

        BillDetailDo billDetailDo = arrBillDetailDo.get(position);

        holder.etpaymentDetails.setTag(R.string.key_get_data,billDetailDo);
        holder.etFcurrent.setTag(R.string.key_get_data,billDetailDo);
        holder.etSarEquip.setTag(R.string.key_get_data,billDetailDo);
        holder.etRemarks.setTag(R.string.key_get_data,billDetailDo);

        holder.etpaymentDetails.addTextChangedListener(holder.textChangeListnerpaymentDetails);
        holder.etFcurrent.addTextChangedListener(holder.textChangeListnerFcurrent);
        holder.etSarEquip.addTextChangedListener(holder.textChangeListnerSarEquip);
        holder.etRemarks.addTextChangedListener(holder.textChangeListnerRemarks);

        if(billDetailDo!=null)
        {
            holder.etpaymentDetails.setText(""+billDetailDo.getPaymentDetails());
            holder.etFcurrent.setText(""+billDetailDo.getFCurr());
            holder.etSarEquip.setText(""+billDetailDo.getSAREqui());
            holder.etRemarks.setText(""+billDetailDo.getRemarks());
        }
        holder.etpaymentDetails.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    viewPD=view;
                else
                    viewPD=null;

            }
        });
        holder.etFcurrent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    viewFC=view;
                else
                    viewFC=null;

            }
        });
        holder.etSarEquip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    viewSAR=view;
                else
                    viewSAR=null;

            }
        });
        holder.etRemarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    viewRemarks=view;
                else
                    viewRemarks=null;

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrBillDetailDo.size();
    }

    class TextChangeListnerpaymentDetails implements TextWatcher
    {
        public boolean isActive=true;

        public void setActive(boolean isActive)
        {
            this.isActive=isActive;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            if(viewPD!=null)
            {
                BillDetailDo billDetailDo = (BillDetailDo) viewPD.getTag(R.string.key_get_data);
                if(billDetailDo!=null)
                {
                    billDetailDo.setPaymentDetails(charSequence.toString());
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    }
    class TextChangeListnerFcurrent implements TextWatcher
    {
        public boolean isActive=true;

        public void setActive(boolean isActive)
        {
            this.isActive=isActive;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            if(viewFC!=null)
            {
                BillDetailDo billDetailDo = (BillDetailDo) viewFC.getTag(R.string.key_get_data);
                if(billDetailDo!=null)
                {
                    billDetailDo.setFCurr(charSequence.toString());
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    }
    class TextChangeListnerSarEquip implements TextWatcher
    {
        public boolean isActive=true;

        public void setActive(boolean isActive)
        {
            this.isActive=isActive;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            if(viewSAR!=null)
            {
                BillDetailDo billDetailDo = (BillDetailDo) viewSAR.getTag(R.string.key_get_data);
                if(billDetailDo!=null)
                {
                    billDetailDo.setSAREqui(charSequence.toString());
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    }
    class TextChangeListnerRemarks implements TextWatcher
    {
        public boolean isActive=true;

        public void setActive(boolean isActive)
        {
            this.isActive=isActive;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            if(viewRemarks!=null)
            {
                BillDetailDo billDetailDo = (BillDetailDo) viewRemarks.getTag(R.string.key_get_data);
                if(billDetailDo!=null)
                {
                    billDetailDo.setRemarks(charSequence.toString());
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    }
}
