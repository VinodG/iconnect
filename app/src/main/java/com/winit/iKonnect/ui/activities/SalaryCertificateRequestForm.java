package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.SalaryCertificateRequestDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SalaryCertificateRequestForm extends FormActivity {


    @Nullable
    @Bind(R.id.etAddressedto)
    EditText etAddressedto;

    @Nullable
    @Bind(R.id.etPurpose)
    EditText etPurpose;
    @Nullable
    @Bind(R.id.etOther_Instru)
    EditText otherInstructions;

    private SalaryCertificateRequestDO salaryCertificateRequestDO;

    @Override
    protected void initializeForm() {

        salaryCertificateRequestDO = (SalaryCertificateRequestDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.Salary_Certificate_Request));

        ll_reson_for_request.setVisibility(View.GONE);

        inflater.inflate(R.layout.activity_salary_certificate_request_form, flFormBody, true);
        ButterKnife.bind(this);
        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptureImage();
            }
        });

        if (salaryCertificateRequestDO != null) {
            disableAllChilds(ll_form);
            showAllContent();
            btnSubmit.setVisibility(View.GONE);
        }
    }


    public void showAllContent() {
        etAddressedto.setText(salaryCertificateRequestDO.getAddressedto());
        etPurpose.setText(salaryCertificateRequestDO.getPurpose());
        otherInstructions.setText(salaryCertificateRequestDO.getOtherInstructions());

    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            case ADDRESSEDTO:
                message = "Please enter Addressed To";
                break;
            case PURPOSE:
                message = getString(R.string.Purpose);
                break;
            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(SalaryCertificateRequestForm.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(SalaryCertificateRequestForm.this, getString(R.string.alert), message, getString(R.string.OK), "", "");

    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.SalaryCertificateRequest(etAddressedto.getText().toString()
                , etPurpose.getText().toString()
                , otherInstructions.getText().toString());
    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();

        showFormSubmitPopup("Salary Certificate Request", "Submitted for Approval to Reporting Manager");
    }
}
