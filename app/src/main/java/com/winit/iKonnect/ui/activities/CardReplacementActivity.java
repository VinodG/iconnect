package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.C3CardReplacementDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Ojha.Sandeep on 10/25/2017.
 */

public class CardReplacementActivity extends FormActivity {

    @Nullable
    @Bind(R.id.rb_lost_card_type)
    RadioButton rb_lost_card_type;

    @Nullable
    @Bind(R.id.rb_damaged_card)
    RadioButton rb_damaged_card;

    @Nullable
    @Bind(R.id.rb_capture_retained)
    RadioButton rb_capture_retained;
    //two checkbox replaced with radio button
    @Nullable
    @Bind(R.id.rb_capture_card_noissue)
    CheckBox rb_capture_card_noissue;

    @Nullable
    @Bind(R.id.rb_capture_card)
    CheckBox rb_capture_card;

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

    @Nullable
    @Bind(R.id.ll_capture_card_issue_if_yes)
    LinearLayout ll_capture_card_issue_if_yes;
    String lostType = "", cardRetainment = "";


    private C3CardReplacementDO c3CardReplacementDO;

    @Override
    protected void initializeForm() {


        c3CardReplacementDO = (C3CardReplacementDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.C3_Card_Replacement_Service_Request));

        inflater.inflate(R.layout.card_replacement, flFormBody, true);
        ll_reson_for_request.setVisibility(View.GONE);
        ButterKnife.bind(this);

        bindData();

        if (c3CardReplacementDO != null) {
            showAllHistoryContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }

    }


    public void showAllHistoryContent() {
        if (c3CardReplacementDO.getCardType().equalsIgnoreCase(this.getResources().getString(R.string.lost_card))
                ) {
            rb_lost_card_type.setChecked(true);
        } else if (c3CardReplacementDO.getCardType().equalsIgnoreCase(this.getResources().getString(R.string.damaged_card))) {
            rb_damaged_card.setChecked(true);
        } else {
            rb_capture_retained.setChecked(true);
            ll_capture_card_issue_if_yes.setVisibility(View.VISIBLE);
            if (c3CardReplacementDO.getCapturedCard_Immediate().equalsIgnoreCase("Captured Card (Immediate Re-Issuance)")) {
                rb_capture_card_noissue.setChecked(true);
            } else {
                rb_capture_card.setChecked(true);
            }
        }
        tv_brach_id.setText(c3CardReplacementDO.getBranchID());
        companyName.setText(c3CardReplacementDO.getCompanyName());
        et_authorized_person.setText(c3CardReplacementDO.getAuthorizedPerson());
        tv_contact_number.setText(c3CardReplacementDO.getContactNumber());
        tv_CarMemberdName.setText(c3CardReplacementDO.getCardMemberFullName());
        etCardHolderMobileNum.setText(c3CardReplacementDO.getCardholderMobileNumber());
        etEmployeeIDno.setText(c3CardReplacementDO.getEmployeeID());
        etC3PayrollCardNum.setText(c3CardReplacementDO.getC3PayrollCardNumber());

    }

    private void bindData() {
        etCardHolderMobileNum.setText(preference.getStringFromPreference(Preference.STAFF_MOBILE, ""));
        companyName.setText("Al Seer");
        tv_brach_id.setText("12039");
        tv_contact_number.setText("043725300");
        tv_CarMemberdName.setText(preference.getStringFromPreference(Preference.STAFF_NAME, "N/A"));
        etEmployeeIDno.setText(preference.getStringFromPreference(Preference.STAFF_ID, "N/A"));
    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {

            case EMPTY_CARD_STATUS:
                message = "Please select reason for Card Replacement.";
                break;
            case EMPTY_CAPTURED_RETAINED_ATM:
                message = "Please select lost type.";
                break;
            case EMPTY_CARD_TYPE_STATUS:
                message = getString(R.string.reason_for_card_replacement);
                break;

            case EMPTY_CHECKBOX:
                message = getString(R.string.select_retain_type);
                break;

            case EMPTY_CARDHOLDER_MOBILE_NUMBER:
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
            showCustomDialog(CardReplacementActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(CardReplacementActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {

        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.submitCardReplacementService(
                etReason.getText().toString(),
                lostType.toString(),
                cardRetainment.toString(),
                companyName.getText().toString(),
                tv_brach_id.getText().toString(),
                et_authorized_person.getText().toString(),
                tv_contact_number.getText().toString(),
                tv_CarMemberdName.getText().toString(),
                etCardHolderMobileNum.getText().toString(),
                etEmployeeIDno.getText().toString(),
                etC3PayrollCardNum.getText().toString(),
                ""
        );
    }

    public void captureCardClick(View view) {
        switch (view.getId()) {
            case R.id.rb_capture_card:
                rb_capture_card_noissue.setChecked(false);
                cardRetainment = "Captured Card (Immediate Re-Issuance)";
                break;

            case R.id.rb_capture_card_noissue:
                rb_capture_card.setChecked(false);
                cardRetainment = "Captured Card (7 day hold until card returns from bank, if not re-issue)";
                break;
        }
    }

    /*@Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();

        showFormSubmitPopup("C3 Card Replacement Service Request", message);
    }*/

    public void lost_card_type(View view) {

        switch (view.getId()) {
            case R.id.rb_lost_card_type:
                lostType = getResources().getString(R.string.lost_card);
                ll_capture_card_issue_if_yes.setVisibility(View.GONE);
                break;

            case R.id.rb_damaged_card:
                lostType = getResources().getString(R.string.damaged_card);
                ll_capture_card_issue_if_yes.setVisibility(View.GONE);
                break;

            case R.id.rb_capture_retained:
                lostType = getResources().getString(R.string.capture_retained_by);
                ll_capture_card_issue_if_yes.setVisibility(View.VISIBLE);
                break;
        }
    }

}
