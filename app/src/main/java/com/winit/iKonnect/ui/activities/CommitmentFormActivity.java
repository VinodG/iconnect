package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.CommitmentFormDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommitmentFormActivity extends FormActivity {


    @Nullable
    @Bind(R.id.etContributionAmt)
    EditText ContributionAmount;
    @Nullable
    @Bind(R.id.etRemarks)
    EditText Remarks;
    @Nullable
    @Bind(R.id.etAmount)
    EditText etAmount;
    @Nullable
    @Bind(R.id.tvCurrency)
    TextView tvCurrency;
    private CommitmentFormDO commitmentFormDO;

    @Override
    protected void initializeForm() {

        commitmentFormDO = (CommitmentFormDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.Commitment_Form));

        inflater.inflate(R.layout.activity_commitment_form, flFormBody, true);
        ll_reson_for_request.setVisibility(View.GONE);
        ButterKnife.bind(this);

        if (preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "").equalsIgnoreCase(AppConstants.STAFF_WORK_COUNTRY)){
            tvCurrency.setText(" "+AppConstants.OMR);
            etAmount.setHint("Contribution Amount"+"("+AppConstants.OMR+")");
        }else {
            tvCurrency.setText(" "+AppConstants.AED);
            etAmount.setHint("Contribution Amount"+"("+AppConstants.AED+")");
        }
        if (commitmentFormDO != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    public void showAllContent() {
        etAmount.setText(commitmentFormDO.getContributionAmount());
        Remarks.setText(commitmentFormDO.getRemarks());
    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            case CONTRIBUTION_AMT:
                message = getString(R.string.contribution_amt_alert);
                break;
            default:
                message = type;
                break;
        }
        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(CommitmentFormActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(CommitmentFormActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.CommitmentForm(etAmount.getText().toString(), "");
    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        if (serviceRequestDO != null) {
            showFormSubmitPopup("Thank You for the Contribution!", "Submitted to HR Payroll for Processing");
        }

    }
}
