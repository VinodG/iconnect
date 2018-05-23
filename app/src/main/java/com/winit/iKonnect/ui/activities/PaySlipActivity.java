package com.winit.iKonnect.ui.activities;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.databinding.PaySlipLayoutBinding;
import com.winit.iKonnect.dataobject.response.EmpAbsenceDetails;
import com.winit.iKonnect.dataobject.response.EmpContributions;
import com.winit.iKonnect.dataobject.response.EmpDeductions;
import com.winit.iKonnect.dataobject.response.EmpEarnings;
import com.winit.iKonnect.dataobject.response.EmpPaySlip;
import com.winit.iKonnect.dataobject.response.PaySlipResponseDo;
import com.winit.iKonnect.module.paySlip.IPaySlipPresenter;
import com.winit.iKonnect.module.paySlip.IPaySlipView;
import com.winit.iKonnect.module.paySlip.PaySlipPresenter;
import com.winit.iKonnect.ui.customview.CustomImageView;

import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by Rahul.Yadav on 6/20/2017.
 */

public class PaySlipActivity extends BaseActivity implements IPaySlipView {

    private IPaySlipPresenter iPaySlipPresenter;
    private LinearLayout llBelow,llEarnings, llDeduction, llContribution, llAbsenceDetails,llEarningHeader,llContributionHeader,llAbsentHeader,
            llDeductionHeader,llyeartodatepaymentdetails,llarreardetails;
    //private EarningAdapter earningAdapter;
    PaySlipLayoutBinding paySlipLayoutBinding;
    EmpPaySlip[] empPaySlipDO;
    private CustomImageView ivUserPic;
    private TextView tv_total,tvDepartment,tvDivision,tvBank,tvPaymentMode,tvNationality,tvLocation,
            tv_total1,
            tv_digit,
            tv_total_gross_pay,
            tv_total_gross_ded,
            tv_diff_from_prev,
            tv_total_net_pay,
            tv_amnt1,
            tvTotalTontribution,
            tvNetAmountInWords,
            tv_digit2,
            tvUserDesignation,
            tvUser_Id,
            total,tvUser_Name,
            tv_total_gross_pay_current,tv_total_gross_pay_ytd,
            tv_total_gross_ded_current,tv_total_gross_ded_ytd,
            tv_total_net_pay_current,tv_total_net_pay_ytd,
            tvytdtpdescription_header,tvytdtpcurrentperiod_header,tvytdtpytd_header;
    private boolean isArrearFound=false;

    @Override
    protected void initialize() {
        ButterKnife.bind(this);
        setToolbarTitle(getString(R.string.pay_Slip));
        paySlipLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.pay_slip_layout, flBody, true);
        iPaySlipPresenter = new PaySlipPresenter(this);
        initializeControls();

//        showCustomDialog(PaySlipActivity.this,getString(R.string.alert),getString(R.string.no_data_found),""+getString(R.string.OK),"","finish");
    }

    private void initializeControls() {
//        iv_empDetail = (ImageView) findViewById(R.id.iv_empDetail);
//        llEmployeeDetailes = (LinearLayout) findViewById(R.id.llEmployeeDetailes);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tvUser_Name = (TextView) findViewById(R.id.tvUser_Name);
        total = (TextView) findViewById(R.id.total);
        tvUserDesignation = (TextView) findViewById(R.id.tvUserDesignation);
        ivUserPic = (CustomImageView) findViewById(R.id.ivUserPic);
        tvUser_Id = (TextView) findViewById(R.id.tvUser_Id);
        tv_total1 = (TextView) findViewById(R.id.tv_total1);
        tv_digit = (TextView) findViewById(R.id.tv_digit);
        tv_total_gross_pay = (TextView) findViewById(R.id.tv_total_gross_pay);
        tv_total_gross_ded = (TextView) findViewById(R.id.tv_total_gross_ded);
        tv_diff_from_prev = (TextView) findViewById(R.id.tv_diff_from_prev);
        tv_total_net_pay = (TextView) findViewById(R.id.tv_total_net_pay);
        tv_amnt1 = (TextView) findViewById(R.id.tv_amnt1);
        tv_digit2 = (TextView) findViewById(R.id.tv_digit2);
        tvTotalTontribution = (TextView) findViewById(R.id.tv_total_contribution);
        tvNetAmountInWords = (TextView) findViewById(R.id.tv_net_amt_in_words);
        tvDepartment = (TextView) findViewById(R.id.tv_department);
        tvDivision = (TextView) findViewById(R.id.tv_division);
        tvBank = (TextView) findViewById(R.id.tv_bank);
        tvPaymentMode = (TextView) findViewById(R.id.tv_payment_mode);
        tvNationality = (TextView) findViewById(R.id.tv_nationality);
        tvLocation = (TextView) findViewById(R.id.tv_location);

        tv_total_gross_pay_current = (TextView) findViewById(R.id.tv_total_gross_pay_current);
        tv_total_gross_pay_ytd = (TextView) findViewById(R.id.tv_total_gross_pay_ytd);
        tv_total_gross_ded_current = (TextView) findViewById(R.id.tv_total_gross_ded_current);
        tv_total_gross_ded_ytd = (TextView) findViewById(R.id.tv_total_gross_ded_ytd);
        tv_total_net_pay_current = (TextView) findViewById(R.id.tv_total_net_pay_current);
        tv_total_net_pay_ytd = (TextView) findViewById(R.id.tv_total_net_pay_ytd);


        tvytdtpdescription_header = (TextView) findViewById(R.id.tvytdtpdescription_header);
        tvytdtpcurrentperiod_header = (TextView) findViewById(R.id.tvytdtpcurrentperiod_header);
        tvytdtpytd_header = (TextView) findViewById(R.id.tvytdtpytd_header);



        llEarningHeader = (LinearLayout) findViewById(R.id.ll_earning_header);
        llContributionHeader = (LinearLayout) findViewById(R.id.ll_contribution_header);
        llAbsentHeader = (LinearLayout) findViewById(R.id.ll_absent_header);
        llDeductionHeader = (LinearLayout) findViewById(R.id.ll_deduction_header);

        llEarnings = (LinearLayout) findViewById(R.id.ll_earnings);
        llBelow = (LinearLayout) findViewById(R.id.llBelow);
        llContribution = (LinearLayout) findViewById(R.id.ll_contributions);
        llAbsenceDetails = (LinearLayout) findViewById(R.id.ll_absent_details);
        llDeduction = (LinearLayout) findViewById(R.id.ll_deductions);
        llyeartodatepaymentdetails = (LinearLayout) findViewById(R.id.llyeartodatepaymentdetails);

        llEarningHeader = (LinearLayout) findViewById(R.id.ll_earning_header);
        llarreardetails = (LinearLayout) findViewById(R.id.llarreardetails);


        llarreardetails.setVisibility(View.GONE);
        llEarningHeader.setVisibility(View.GONE);
        llContributionHeader.setVisibility(View.GONE);
        llAbsentHeader.setVisibility(View.GONE);
        llDeductionHeader.setVisibility(View.GONE);
        llBelow.setVisibility(View.GONE);
        llyeartodatepaymentdetails.setVisibility(View.GONE);

        setStaffDetail();//if not in preference move to onDataFetch



    }

    @Override
    protected void setTypeFace() {

        tv_total.setTypeface(AppConstants.BOLD);
        tvUser_Name.setTypeface(AppConstants.BOLD);
        tv_total1.setTypeface(AppConstants.BOLD);
        tv_digit.setTypeface(AppConstants.BOLD);

        tvytdtpdescription_header.setTypeface(AppConstants.BOLD);
        tvytdtpcurrentperiod_header.setTypeface(AppConstants.BOLD);
        tvytdtpytd_header.setTypeface(AppConstants.BOLD);

        tv_amnt1.setTypeface(AppConstants.BOLD);
        tv_digit2.setTypeface(AppConstants.BOLD);
        tvTotalTontribution.setTypeface(AppConstants.BOLD);
        tvDepartment.setTypeface(AppConstants.BOLD);
        tvDivision.setTypeface(AppConstants.BOLD);
        tvBank.setTypeface(AppConstants.BOLD);
        tvPaymentMode.setTypeface(AppConstants.BOLD);
        tvNationality.setTypeface(AppConstants.BOLD);
        tvLocation.setTypeface(AppConstants.BOLD);
        total.setTypeface(AppConstants.BOLD);
    }

    private void setStaffDetail(){
        tvUser_Name.setText("" + preference.getStringFromPreference(preference.STAFF_NAME, "N/A"));
        tvUserDesignation.setText(""+preference.getStringFromPreference(preference.STAFF_POSITION,"N/A"));
        tvUser_Id.setText("["+preference.getStringFromPreference(preference.STAFF_NUMBER,"N/A")+"]");
        IKonnectApplication.setImageUrl(ivUserPic, ServiceUrls.PROFILE_PIC+preference.getStringFromPreference(preference.STAFF_PHOTO_URL,""),R.drawable.place_holder_image);
//        tvDepartment.setText(preference.getStringFromPreference(Preference.STAFF_GRADE,"N/A"));
//        tvDivision.setText(preference.getStringFromPreference(Preference.STAFF_GRADE,"N/A"));
//        tvBank.setText(preference.getStringFromPreference(Preference.STAFF_GRADE,"N/A"));
//        tvPaymentMode.setText(preference.getStringFromPreference(preference.STAFF_GRADE,"N/A"));
//        tvNationality.setText(preference.getStringFromPreference(preference.STAFF_NATIONALITY,"N/A"));
//        tvLocation.setText(preference.getStringFromPreference(preference.STAFF_GRADE,"N/A"));
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void Error() {
//        Toast.makeText(PaySlipActivity.this, "" + getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataFetch(PaySlipResponseDo paySlipResponseDo) {
        hideLoader();
        if (paySlipResponseDo != null && paySlipResponseDo.getResult() != null) {
            empPaySlipDO = paySlipResponseDo.getResult().getEmpPaySlip();

            if (empPaySlipDO != null && empPaySlipDO.length > 0) {
                llEarningHeader.setVisibility(View.VISIBLE);
                llContributionHeader.setVisibility(View.VISIBLE);
                llAbsentHeader.setVisibility(View.VISIBLE);
                llDeductionHeader.setVisibility(View.VISIBLE);
                llBelow.setVisibility(View.VISIBLE);
                llyeartodatepaymentdetails.setVisibility(View.VISIBLE);
                paySlipLayoutBinding.setEmpPaySlipDO(empPaySlipDO[0]);
                paySlipLayoutBinding.executePendingBindings();

                if(paySlipResponseDo.getResult().getEmpEarnings()!=null && paySlipResponseDo.getResult().getEmpEarnings().length>0)
                    bindEarnings(paySlipResponseDo.getResult().getEmpEarnings());
                else
                    llEarningHeader.setVisibility(View.GONE);
                if(paySlipResponseDo.getResult().getEmpContributions()!=null && paySlipResponseDo.getResult().getEmpContributions().length>0)
                    bindContribution(paySlipResponseDo.getResult().getEmpContributions());
                else
                    llContributionHeader.setVisibility(View.GONE);
                if(paySlipResponseDo.getResult().getEmpAbsenceDetails()!=null && paySlipResponseDo.getResult().getEmpAbsenceDetails().length>0)
                    bindAbsenceDetails(paySlipResponseDo.getResult().getEmpAbsenceDetails());
                else
                    llAbsentHeader.setVisibility(View.GONE);
                if(paySlipResponseDo.getResult().getEmpDeductions()!=null && paySlipResponseDo.getResult().getEmpDeductions().length>0) {
                    bindDeduction(paySlipResponseDo.getResult().getEmpDeductions());
                }
                else {
                    llDeductionHeader.setVisibility(View.GONE);
                }
                if(paySlipResponseDo.getResult().getEmpPaySlip()!=null && paySlipResponseDo.getResult().getEmpPaySlip().length>0) {
                    tv_digit.setText(paySlipResponseDo.getResult().getEmpPaySlip()[0].getNetPayment());
                    tvNetAmountInWords.setText(paySlipResponseDo.getResult().getEmpPaySlip()[0].getNetPaymentInWords());
                }
                else{
                    tv_digit.setText("0.00");
                    tvNetAmountInWords.setText("Zero Riyals");
                }


                if(paySlipResponseDo.getResult().getEmpPaySlip()!=null && paySlipResponseDo.getResult().getEmpPaySlip().length>0) {
                    tv_total_gross_pay_current.setText(paySlipResponseDo.getResult().getEmpPaySlip()[0].getGrossPayment());
                }
                else
                    tv_total_gross_pay_current.setText("N/A");
                if(paySlipResponseDo.getResult().getEmpPaySlip()!=null && paySlipResponseDo.getResult().getEmpPaySlip().length>0) {
                    tv_total_gross_pay_ytd.setText(paySlipResponseDo.getResult().getEmpPaySlip()[0].getPaymentYTD());
                }
                else
                    tv_total_gross_pay_ytd.setText("N/A");
                if(paySlipResponseDo.getResult().getEmpPaySlip()!=null && paySlipResponseDo.getResult().getEmpPaySlip().length>0) {
                    tv_total_gross_ded_current.setText(paySlipResponseDo.getResult().getEmpPaySlip()[0].getGrossDeduction());
                }
                else
                    tv_total_gross_ded_current.setText("N/A");
                if(paySlipResponseDo.getResult().getEmpPaySlip()!=null && paySlipResponseDo.getResult().getEmpPaySlip().length>0) {
                    tv_total_gross_ded_ytd.setText(paySlipResponseDo.getResult().getEmpPaySlip()[0].getDeductionsYTD());
                }
                else
                    tv_total_gross_ded_ytd.setText("N/A");
                if(paySlipResponseDo.getResult().getEmpPaySlip()!=null && paySlipResponseDo.getResult().getEmpPaySlip().length>0) {
                    tv_total_net_pay_current.setText(paySlipResponseDo.getResult().getEmpPaySlip()[0].getNetPaymentValue());
                }
                else
                    tv_total_net_pay_current.setText("N/A");
                if(paySlipResponseDo.getResult().getEmpPaySlip()!=null && paySlipResponseDo.getResult().getEmpPaySlip().length>0) {
                    tv_total_net_pay_ytd.setText(paySlipResponseDo.getResult().getEmpPaySlip()[0].getNetPaymentYTD());
                }
                else
                    tv_total_net_pay_ytd.setText("N/A");

            }
            else {
                tvDepartment.setText("N/A");
                tvDivision.setText("N/A");
                tvBank.setText("N/A");
                tvPaymentMode.setText("N/A");
                tvNationality.setText("N/A");
                tvLocation.setText("N/A");
                if(paySlipResponseDo!=null && !TextUtils.isEmpty(paySlipResponseDo.getStatusMessageEn()))
                showCustomDialog(PaySlipActivity.this, getString(R.string.alert), "" +paySlipResponseDo.getStatusMessageEn(), getString(R.string.OK), "", "finish",false);
                else
                showCustomDialog(PaySlipActivity.this, getString(R.string.alert), "" + getString(R.string.no_data_found), getString(R.string.OK), "", "finish",false);
            }
        }
    }

    private void bindEarnings(EmpEarnings[] earnings)
    {
        double total = 0;
        if(llEarnings != null && llEarnings.getChildCount() > 0)
            llEarnings.removeAllViews();
        if(llarreardetails != null && llarreardetails.getChildCount() > 0)
            llarreardetails.removeAllViews();
        for(int loop=0; loop < earnings.length; loop++)
        {
            View itemView = inflater.inflate(R.layout.payslip_earning_cell_new, null);
            TextView tvEarningType,tvAmount,tvUnits;
            tvEarningType = (TextView)itemView.findViewById(R.id.tv_earning_type);
            tvAmount= (TextView)itemView.findViewById(R.id.tv_amount);
            tvUnits= (TextView)itemView.findViewById(R.id.tv_units);

            EmpEarnings empEarning = earnings[loop];

            if(empEarning.getWagetype()!=null)
                tvEarningType.setText(""+empEarning.getWagetype());
            else
                tvEarningType.setText("N/A");
            tvAmount.setText(""+empEarning.getAmount());
            tvUnits.setText(""+empEarning.getUnits());

            if(empEarning.getWagetype()!=null && empEarning.getWagetype().toLowerCase().contains("arrear"))
            {
                isArrearFound = true;
                llEarnings.addView(itemView);
            }
            else
            {

                total += StringUtils.getDouble(empEarning.getAmount());

                llEarnings.addView(itemView, loop);
            }
        }

        /*if(isArrearFound)
            llarreardetails.setVisibility(View.VISIBLE);*/
        tv_amnt1.setText(""+total+"0");
    }

    private void bindContribution(EmpContributions[] contributions)
    {
        double total = 0;

        if(llContribution != null && llContribution.getChildCount() > 0)
            llContribution.removeAllViews();
        for(int loop=0; loop < contributions.length; loop++)
        {
            View itemView = inflater.inflate(R.layout.payslip_earning_cell_new, null);
            TextView tvEarningType,tvAmount,tvUnits;
            tvEarningType = (TextView)itemView.findViewById(R.id.tv_earning_type);
            tvAmount= (TextView)itemView.findViewById(R.id.tv_amount);
            tvUnits= (TextView)itemView.findViewById(R.id.tv_units);

            EmpContributions empContributions = contributions[loop];
            if(empContributions.getWageType()!=null)
                tvEarningType.setText(""+empContributions.getWageType());
            else
                tvEarningType.setText("N/A");
            tvAmount.setText(""+empContributions.getAmount());
            tvUnits.setText(""+empContributions.getUnits());

            total+= StringUtils.getDouble(empContributions.getAmount());

            llContribution.addView(itemView, loop);
        }
        tvTotalTontribution.setText(""+total+"0");
    }

    private void bindDeduction(EmpDeductions[] deductions)
    {
        double total = 0;
        for(int loop=0; loop < deductions.length; loop++)
        {
            View itemView = inflater.inflate(R.layout.payslip_earning_cell_new, null);
            TextView tvEarningType,tvAmount,tvUnits;
            tvEarningType = (TextView)itemView.findViewById(R.id.tv_earning_type);
            tvAmount= (TextView)itemView.findViewById(R.id.tv_amount);
            tvUnits= (TextView)itemView.findViewById(R.id.tv_units);

            EmpDeductions empDeductions = deductions[loop];
            if(empDeductions.getWageType()!=null)
                tvEarningType.setText(""+empDeductions.getWageType());
            else
                tvEarningType.setText("N/A");
            tvAmount.setText(""+empDeductions.getAmount());
            tvUnits.setText(""+empDeductions.getUnits());

            total+= StringUtils.getDouble(empDeductions.getAmount());

            llDeduction.addView(itemView, loop);
        }
        tv_digit2.setText(""+total+"0");
    }

    private void bindAbsenceDetails(EmpAbsenceDetails[] absenseDetails)
    {
        for(int loop=0; loop < absenseDetails.length; loop++)
        {
            View itemView = inflater.inflate(R.layout.absent_details_cell, null);
            TextView tvAbsentType,tvStartDate,tvEndDate,tvTotalDays;
            tvAbsentType = (TextView)itemView.findViewById(R.id.tv_absent_type);
            tvStartDate= (TextView)itemView.findViewById(R.id.tv_start_date);
            tvEndDate= (TextView)itemView.findViewById(R.id.tv_end_date);
            tvTotalDays= (TextView)itemView.findViewById(R.id.tv_total_days);

            EmpAbsenceDetails empAbsenceDetail = absenseDetails[loop];

            tvAbsentType.setText(""+empAbsenceDetail.getAbsentType());
            tvStartDate.setText(""+empAbsenceDetail.getStartDate());
            tvEndDate.setText(""+empAbsenceDetail.getEndDate());
            tvTotalDays.setText(""+empAbsenceDetail.getAbsentDays());

            llAbsenceDetails.addView(itemView, loop);
        }

    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            default:
                message = type;
                break;
        }
        showCustomDialog(PaySlipActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", message);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkNetworkConnection()) {
            showLoader(getString(R.string.loading));
            iPaySlipPresenter.fetchPaySlip();
        }
        else
            showCustomDialog(PaySlipActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);
    }

    private HashMap<Integer, Integer> hmHeight = new HashMap<>();
//    @Override
//    public void HeightChange(int position, int height) {
//        hmHeight.put(position, height);
//        int sumHeight = SumHashItem (hmHeight);
//
////        float density = PaySlipActivity.this.getResources().getDisplayMetrics().density;
////        float viewHeight = sumHeight * density;
//        rvEarnings.getLayoutParams().height = sumHeight;
//
////        int i = rvEarnings.getLayoutParams().height;
//    }

//    int SumHashItem (HashMap<Integer, Integer> hashMap) {
//        int sum = 0;
//
//        for(Map.Entry<Integer, Integer> myItem: hashMap.entrySet())  {
//            sum += myItem.getValue();
//        }
//
//        return sum;
//    }

}
