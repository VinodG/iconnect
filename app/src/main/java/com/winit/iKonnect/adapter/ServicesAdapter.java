package com.winit.iKonnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.ConstantExtraKey;

import com.winit.iKonnect.R;
import com.android.databinding.library.baseAdapters.BR;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.ui.activities.BusinessCardActivity;
import com.winit.iKonnect.ui.activities.BusinessTravelAdviceActivity;
import com.winit.iKonnect.ui.activities.CommitmentFormActivity;
import com.winit.iKonnect.ui.activities.DashboardActivity;
import com.winit.iKonnect.ui.activities.HouseAllowanceActivity;
import com.winit.iKonnect.ui.activities.PassportReleaseActivity;
import com.winit.iKonnect.ui.activities.SalaryTransferRequestBankAccount;
import com.winit.iKonnect.ui.activities.SystemAccessRequestActivity;
import com.winit.iKonnect.ui.activities.TransportLoanRequestActivity;

import java.util.ArrayList;


/**
 * Created by Girish Velivela on 5/15/2017.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServiceDO> serviceDOs;
    private ServiceBinder serviceBinder;


    public ServicesAdapter(Context context, ArrayList<ServiceDO> serviceDOs) {
        this.context = context;
        this.serviceDOs = serviceDOs;
        serviceBinder = new ServiceBinder();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.service_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ServiceDO serviceDO = serviceDOs.get(position);
        holder.bind(serviceDO);
    }

    @Override
    public int getItemCount() {
        if (serviceDOs != null)
            return serviceDOs.size();
        return 0;
    }

    public void refresh() {
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ServiceDO serviceDO) {
            binding.setVariable(BR.serviceDO, serviceDO);
            binding.setVariable(BR.serviceBinder, serviceBinder);

            binding.executePendingBindings();
        }
    }

    public class ServiceBinder {
        public void onClick(ServiceDO serviceDO) {
            Intent intent = null;
            if (serviceDO != null) {
                switch (serviceDO.serviceType) {
                    case BANK_ACCOUNT_UPDATE:
                        if (((DashboardActivity) context).preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "").equalsIgnoreCase("UAE")) {
                            ((DashboardActivity) context).showFormPopup(IKonnectApplication.mContext.getString(R.string.Bank_Account_Update), serviceDO.bankAccountUpdateDos);
                        }
                        break;


                    case SALARY_TRANSFER_REQUEST:
                        intent = new Intent(context, SalaryTransferRequestBankAccount.class);
                        break;


                    case HR_SERVICE_REQUEST:
                        ((DashboardActivity) context).showFormPopup(IKonnectApplication.mContext.getString(R.string.HR_Service_Request_For_Leave), serviceDO.hrServiceRequestDos);
                        break;

                    case VISIT_VISA:
                        ((DashboardActivity) context).showFormPopup(IKonnectApplication.mContext.getString(R.string.Visit_Visa), serviceDO.visaVisitDos);
                        break;

                    case SALARY_REQUEST:
                        ((DashboardActivity) context).showFormPopup(IKonnectApplication.mContext.getString(R.string.Salary_Request), serviceDO.salaryRequestDos);
                        break;

                    case PASSPORT_RELEASE_REQUEST:
                        intent = new Intent(context, PassportReleaseActivity.class);

                        break;
                    case HOUSING_ALLOWANCE:
                        intent = new Intent(context, HouseAllowanceActivity.class);
                        break;
                    case TRANSPORT_LOAN_REQUEST:
                        intent = new Intent(context, TransportLoanRequestActivity.class);
                        break;
                    case COMMITMENT_FORM:
                        intent = new Intent(context, CommitmentFormActivity.class);
                        break;

                    case BUSINESS_CARD_REQUEST:
                        intent = new Intent(context, BusinessCardActivity.class);
                        break;

                    case BUSINESS_TRAVEL_REQUEST:
                        intent = new Intent(context, BusinessTravelAdviceActivity.class);
                        break;


                    case SYSTEM_ACCESS_REQUEST:
                        intent = new Intent(context, SystemAccessRequestActivity.class);
                        break;
                }
            }
            if (intent != null) {

                intent.putExtra(ConstantExtraKey.SERVICE_OBJECT, serviceDO);
                context.startActivity(intent);
            }
        }
    }
}


