package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.C3CardReplacementDO;
import com.winit.iKonnect.dataobject.C3CardStatementDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Ojha.Sandeep on 10/25/2017.
 */

public class CardStatementActivity extends FormActivity {

    @Nullable
    @Bind(R.id.rb_hard_copy)
    RadioButton rb_hard_copy;

    @Nullable
    @Bind(R.id.rb_softcopy)
    RadioButton rb_softcopy;

    @Nullable
    @Bind(R.id.tv_start_date)
    TextView tv_start_date;

    @Nullable
    @Bind(R.id.tv_end_date)
    TextView tv_end_date;

    @Nullable
    @Bind(R.id.et_compnay_name)
    TextView companyName;

    @Nullable
    @Bind(R.id.tv_brach_id)
    TextView tv_brach_id;

    @Nullable
    @Bind(R.id.et_authorized_person)
    EditText et_authorized_person;

    @Nullable
    @Bind(R.id.tv_contact_number)
    TextView tv_contact_number;

    @Nullable
    @Bind(R.id.tv_CarMemberdName)
    TextView tv_CarMemberdName;

    @Nullable
    @Bind(R.id.etCardHolderMobileNum)
    EditText etCardHolderMobileNum;

    @Nullable
    @Bind(R.id.etEmployeeIDno)
    TextView etEmployeeIDno;

    @Nullable
    @Bind(R.id.etC3PayrollCardNum)
    EditText etC3PayrollCardNum;

    String cardStatement = "";
    String startDate = "", endDate = "";

    private C3CardStatementDO cardStatementDO;

    @Override
    protected void initializeForm() {

        cardStatementDO = (C3CardStatementDO) getIntent().getSerializableExtra(getResources().getString(R.string.C3_Card_Statement_Service_Request));
        inflater.inflate(R.layout.card_statement, flFormBody, true);
        ll_reson_for_request.setVisibility(View.GONE);
        ButterKnife.bind(this);
        bindData();

        if (cardStatementDO != null) {
            showAllViewContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }

    }


    public void showAllViewContent() {

        if (cardStatementDO.getCardsStatement().equalsIgnoreCase(this.getResources().getString(R.string.hardcopy))) {
            rb_hard_copy.setChecked(true);
        } else {
            rb_softcopy.setChecked(true);

        }
        tv_start_date.setText(cardStatementDO.getStartDate().replace("/","-"));
        tv_end_date.setText(cardStatementDO.getEndDate().replace("/","-"));
        companyName.setText(cardStatementDO.getCompanyName());
        et_authorized_person.setText(cardStatementDO.getAuthorizedPerson());
        tv_contact_number.setText(cardStatementDO.getContactNumber());
        tv_CarMemberdName.setText(cardStatementDO.getCardMemberFullName());
        etCardHolderMobileNum.setText(cardStatementDO.getCardholderMobileNumber());
        etEmployeeIDno.setText(preference.getStringFromPreference(Preference.STAFF_ID, "N/A"));
        etC3PayrollCardNum.setText(cardStatementDO.getC3PayrollCardNumber());


    }


    private void bindData() {
        tv_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSelectedDateFromDatePicker(CardStatementActivity.this, tv_start_date);
            }
        });

        tv_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSelectedDateFromDatePicker(CardStatementActivity.this, tv_start_date.getText().toString(), tv_end_date);
            }
        });

        companyName.setText("Al Seer");
        tv_brach_id.setText("12039");
        tv_contact_number.setText("043725300");
        //tv_CarMemberdName.setText(preference.getStringFromPreference(Preference.FIRST_NAME, "N/A")+" "+preference.getStringFromPreference(Preference.LAST_NAME, "N/A"));
        tv_CarMemberdName.setText(preference.getStringFromPreference(Preference.STAFF_NAME, "N/A"));
        etEmployeeIDno.setText(preference.getStringFromPreference(Preference.STAFF_ID, "N/A"));
        etCardHolderMobileNum.setText(preference.getStringFromPreference(Preference.STAFF_MOBILE, ""));
    }


    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {

            case VALID_DATE_SELECTION:
                message = "End date should be greater than start date";
                break;

            case EMPTY_REASON:
                message = getString(R.string.PleaseEnterReason);
                break;

            case EMPTY_CARD_STATEMENT_TYPE:
                message = "Please select any option of Type of Copy";
                break;


            case EMPTY_DATE:
                message = getString(R.string.please_select_the_start_date);
                break;


            case EMPTY_END_DATE:
                message = getString(R.string.please_select_the_end_date);
                break;


            case ENTER_BANK_NAME:
                message = getString(R.string.BankNameMsg);
                break;

            case ENTER_ACCOUNT_NO:
                message = getString(R.string.AccountNumberMsg);
                break;

            case ENTER_IBAN:
                message = getString(R.string.IbaNNumberMsg);

                break;

            case EMPTY_CONTACTNUMBER:
                message = "Please enter contact Number";
                break;

            case EMPTY_MOBILE:
                message = "Please enter Card Holder's Mobile Number";
                break;

            case EMPTY_SIGNATURE:
                message = getString(R.string.empty_signature);
                break;

            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(CardStatementActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(CardStatementActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {


        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.submitCardStatementService(
                etReason.getText().toString(),
                cardStatement,
                tv_start_date.getText().toString(),
                tv_end_date.getText().toString(),
                companyName.getText().toString(),
                tv_brach_id.getText().toString(),
                et_authorized_person.getText().toString(),
                tv_contact_number.getText().toString(),
                tv_CarMemberdName.getText().toString(),
                etCardHolderMobileNum.getText().toString(),
                etEmployeeIDno.getText().toString(),
                etC3PayrollCardNum.getText().toString(), ""
        );

    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        showFormSubmitPopup("C3 Card Statement Service Request", message);
    }

    public void copyType(View view) {

        switch (view.getId()) {
            case R.id.rb_softcopy:
                cardStatement = ((RadioButton) view).getText().toString();
                break;

            case R.id.rb_hard_copy:
                cardStatement = ((RadioButton) view).getText().toString();
                break;
        }
    }

}
