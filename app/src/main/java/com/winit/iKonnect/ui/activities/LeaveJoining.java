package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.LeaveJoiningDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ojha.Sandeep on 10/21/2017.
 */

public class LeaveJoining extends FormActivity {

    @Nullable
    @Bind(R.id.tv_start_date)
    TextView tvStartDate;

    @Nullable
    @Bind(R.id.et_support_document)
    EditText et_support_document;
    private LeaveJoiningDO leaveJoiningDO;

    @Override
    protected void initializeForm() {


        leaveJoiningDO = (LeaveJoiningDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.Leave_Joining));
        inflater.inflate(R.layout.leave_joining, flFormBody, true);
        ll_reson_for_request.setVisibility(View.GONE);
        ButterKnife.bind(this);
        bindData();

        if (leaveJoiningDO != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    private void showAllContent() {
        tvStartDate.setText(leaveJoiningDO.getResumedWork().replace("/","-"));
        et_support_document.setText(leaveJoiningDO.getReasoniflatewithsupportingdocuments());

    }

    private void bindData() {

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedDateFromDatePicker(LeaveJoining.this, tvStartDate);
            }
        });
    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            case EMPTY_DATE:
                message = "Please select Resumed Work.";
                break;

            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(LeaveJoining.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(LeaveJoining.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.submitLeaveJoining(
                etReason.getText().toString(),
                tvStartDate.getText().toString(),
                et_support_document.getText().toString()
        );
    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        message = " Submitted to Reporting Manager for approval";
        if (serviceRequestDO != null)
            showFormSubmitPopup(serviceRequestDO.serviceName, message);
        else {
            showFormSubmitPopup("Leave Joining", message);
        }
    }
}
