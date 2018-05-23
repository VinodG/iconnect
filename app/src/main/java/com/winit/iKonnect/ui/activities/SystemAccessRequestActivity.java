package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.SystemAccessDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SystemAccessRequestActivity extends FormActivity {

    @Nullable
    @Bind(R.id.cbSap)
    CheckBox cbSap;
    @Nullable
    @Bind(R.id.cbNetwork)
    CheckBox cbNetwork;
    @Nullable
    @Bind(R.id.cbEmail)
    CheckBox cbEmail;
    @Nullable
    @Bind(R.id.cbInternet)
    CheckBox cbInternet;
    @Nullable
    @Bind(R.id.cbExtension)
    CheckBox cbExtension;
    @Nullable
    @Bind(R.id.etRemarks)
    EditText remarks;
    private String sap="", network="", email="", internet="", extension="";
    private String StaffNo, Department, Designation;
    private SystemAccessDO systemAccessDO;

    @Override
    protected void initializeForm() {

        systemAccessDO=(SystemAccessDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.System_Access_Request));

        inflater.inflate(R.layout.activity_system_access_request, flFormBody, true);
        ButterKnife.bind(this);

        StaffNo = preference.getStringFromPreference(Preference.STAFF_NUMBER, "");
        Department = preference.getStringFromPreference(Preference.DEPARTMENT, "");
        Designation = preference.getStringFromPreference(Preference.STAFF_PERSONAL_AREA, "");

        if (systemAccessDO!=null){
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
        ll_reson_for_request.setVisibility(View.GONE);
    }


    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            case SAP:
            case SYSTEM_NETWORK:
            case EMAIL:
            case INTERNET:
            case TELEPHONE_EXT:
                message = getString(R.string.access_req);
                break;
            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(SystemAccessRequestActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(SystemAccessRequestActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        checkFields();
        iFormPresenter.SystemAccess(StaffNo, Department, Designation, sap, network, email, internet, extension, remarks.getText().toString());
    }

    private void checkFields() {
        if (cbSap.isChecked()) {
            sap = "Yes";
        } else {
            sap = "";
        }
        if (cbNetwork.isChecked()) {
            network = "Yes";
        } else {
            network = "";
        }
        if (cbEmail.isChecked()) {
            email = "Yes";
        } else {
            email = "";
        }
        if (cbInternet.isChecked()) {
            internet = "Yes";
        } else {
            internet = "";
        }
        if (cbExtension.isChecked()) {
            extension = "Yes";
        } else {
            extension = "";
        }
    }

    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        if (serviceRequestDO != null) {
            showFormSubmitPopup(serviceRequestDO.serviceName, "Submitted for Approval to the Division Head/GM");
        }

    }

    private void showAllContent() {

        sap=systemAccessDO.getSAP();
        if (sap.equalsIgnoreCase("Yes"))
            cbSap.setChecked(true);
        else
            cbSap.setChecked(false);

        network=systemAccessDO.getSystemNetwork();

        if (network.equalsIgnoreCase("Yes"))
            cbNetwork.setChecked(true);
        else
            cbNetwork.setChecked(false);

        email=systemAccessDO.getEmail();

        if (email.equalsIgnoreCase("Yes"))
            cbEmail.setChecked(true);
        else
            cbEmail.setChecked(false);

        internet=systemAccessDO.getInternet();

        if (internet.equalsIgnoreCase("Yes"))
            cbInternet.setChecked(true);
        else
            cbInternet.setChecked(false);

        extension=systemAccessDO.getTelephoneExtension();

        if (extension.equalsIgnoreCase("Yes"))
            cbExtension.setChecked(true);
        else
            cbExtension.setChecked(false);

        remarks.setText(systemAccessDO.getRemarks());
    }
}
