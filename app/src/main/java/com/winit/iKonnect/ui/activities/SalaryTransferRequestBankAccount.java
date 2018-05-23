package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.winit.common.constant.AppConstants;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.AttachmentsDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.SalaryTransferRequestBankAccountDo;
import com.winit.iKonnect.module.form.IFormView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ojha.Sandeep on 10/27/2017.
 */

public class SalaryTransferRequestBankAccount extends FormActivity implements IFormView {

    @Nullable
    @Bind(R.id.et_bank_name)
    EditText etBankName;

    @Nullable
    @Bind(R.id.et_account_number)
    EditText etAccountNumber;

    @Nullable
    @Bind(R.id.et_ibn)
    EditText etIBN;

    @Nullable
    @Bind(R.id.ll_salary_transfer_request_form)
    LinearLayout ll_salary_transfer_request_form;

    private SalaryTransferRequestBankAccountDo salaryTransferRequestBankAccountDo;

    @Override
    protected void initializeForm() {

        salaryTransferRequestBankAccountDo = (SalaryTransferRequestBankAccountDo) getIntent().getSerializableExtra(this.getResources().getString(R.string.Salary_Transfer_Request));

        inflater.inflate(R.layout.salary_transfer_request_bank_account, flFormBody, true);
        ButterKnife.bind(this);
        ll_reson_for_request.setVisibility(View.GONE);


        if (salaryTransferRequestBankAccountDo != null) {
            showAllHistoryContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
    }


    public void showAllHistoryContent() {
        etBankName.setText(salaryTransferRequestBankAccountDo.getBankName());
        etAccountNumber.setText(salaryTransferRequestBankAccountDo.getAccountNo());
        etIBN.setText(salaryTransferRequestBankAccountDo.getIBAN());
    }

    @Override
    public void showAlert(String type) {

        hideLoader();
        String message = "";
        switch (type) {

            case ENTER_BANK_NAME:
                message = getString(R.string.BankNameMsg);
                break;

            case ENTER_ACCOUNT_NO:
                message = getString(R.string.AccountNumberMsg);
                break;

            case ENTER_IBAN:
                message = getString(R.string.IbaNNumberMsg);
                break;

            case ENTER_23DIGITS_IBAN:
                message = "IBAN Number should be 23 alphanumeric character";
                break;

           /* case EMPTY_SIGNATURE:
                message = getResources().getString(R.string.empty_signature);*/
            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(SalaryTransferRequestBankAccount.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(SalaryTransferRequestBankAccount.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    //when click on submit
    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.submitBankAccountUpdateSalaryTransferRequest(etReason.getText().toString(),
                etBankName.getText().toString(),
                etAccountNumber.getText().toString(),
                etIBN.getText().toString(),
                "");
    }

}
