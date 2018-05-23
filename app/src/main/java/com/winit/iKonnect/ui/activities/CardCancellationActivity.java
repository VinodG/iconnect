package com.winit.iKonnect.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.CardCancellationDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Ojha.Sandeep on 10/25/2017.
 */

public class CardCancellationActivity extends FormActivity {

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
    //Member Details
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
    @Bind(R.id.tv_reason_for_card_cancellation)
    TextView tvReasonForCardCancellation;


    @Nullable
    @Bind(R.id.et_other)
    EditText others;


    private CardCancellationDO cardCancellationDO;

    @Override
    protected void initializeForm() {

        cardCancellationDO = (CardCancellationDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.C3_Card_Cancellation_Service_Request));

        inflater.inflate(R.layout.card_cancellation, flFormBody, true);
        //ll_reson_for_request.setVisibility(View.GONE);
        ButterKnife.bind(this);


        ll_reson_for_request.setVisibility(View.GONE);
        bindData();

        if (cardCancellationDO != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    public void showAllContent() {


        if (cardCancellationDO.getReasonForCancelation().equalsIgnoreCase("Change of Bank Account")) {
            tvReasonForCardCancellation.setText(cardCancellationDO.getReasonForCancelation());
        } else {
            others.setVisibility(View.VISIBLE);
            tvReasonForCardCancellation.setText("Others");
            others.setText(cardCancellationDO.getReasonForCancelation());
        }

        tv_brach_id.setText(cardCancellationDO.getBranchID());
//        companyName.setText(cardCancellationDO.getCompanyName());
        companyName.setText("Al seer");
        et_authorized_person.setText(cardCancellationDO.getAuthorizedPersone());
        tv_contact_number.setText(cardCancellationDO.getContactNumber());
        tv_CarMemberdName.setText(cardCancellationDO.getCardMemberFullName());
        etCardHolderMobileNum.setText(cardCancellationDO.getCardholderMobileNumber());
//        etEmployeeIDno.setText(cardCancellationDO.getEmployee_ID_No());
        etEmployeeIDno.setText(preference.getStringFromPreference(Preference.STAFF_ID, "N/A"));
        etC3PayrollCardNum.setText(cardCancellationDO.getC3PayrollCardNumber());

    }

    @Override
    public void showAlert(String type) {

        hideLoader();
        String message = "";
        switch (type) {
            case EMPTY_REASON:
                message = "Please specify reason for cancellation";
                break;

            case EMPTY_CARDHOLDER_MOBILE_NUMBER:
                message = "Please enter Card Holder's Mobile Number";
                break;

            case EMPTY_SIGNATURE:
                message = getString(R.string.empty_signature);
                break;


            case OTHER_INSTRUCTIONS:
                message = "Please specify reason for cancellation";
                break;


            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(CardCancellationActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(CardCancellationActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        AppConstants.arrAttachments=iFormPresenter.getAttachments();

        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.submitCardCancellation(
                tvReasonForCardCancellation.getText().toString(),
                "company details",
                companyName.getText().toString(),
                tv_brach_id.getText().toString(),
                et_authorized_person.getText().toString(),
                tv_contact_number.getText().toString(),
                tv_CarMemberdName.getText().toString(),
                etCardHolderMobileNum.getText().toString(),
                etEmployeeIDno.getText().toString(),
                etC3PayrollCardNum.getText().toString(),
                others.getText().toString()
        );
    }


    public void bindData() {

        etCardHolderMobileNum.setText(preference.getStringFromPreference(Preference.STAFF_MOBILE, ""));
        companyName.setText("Al seer");
        tv_brach_id.setText("12039");
        tv_contact_number.setText("043725300");
        tv_CarMemberdName.setText(preference.getStringFromPreference(Preference.FIRST_NAME, "N/A") + " " + preference.getStringFromPreference(Preference.LAST_NAME, "N/A"));
        etEmployeeIDno.setText(preference.getStringFromPreference(Preference.STAFF_ID, "N/A"));

        tvReasonForCardCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerShow(tvReasonForCardCancellation);
            }
        });
    }


    public void spinnerShow(final View view) {
        AlertDialog dialog;
        final CharSequence str[] = {"Change of Bank Account", "Others"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reason for cancellation");
        builder.setItems(str, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int position) {
                //here you can use like this... str[position]

                ((TextView) view).setText(str[position] + "");

                if (str[position].equals(str[1])) {
                    others.setVisibility(View.VISIBLE);
                } else {
                    others.setVisibility(View.GONE);
                }

            }

        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please submit salary transfer request form to be proceed")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ServiceDO serviceDO = new ServiceDO();
                        serviceDO.serviceName = IKonnectApplication.mContext.getString(R.string.Salary_Transfer_Request);
                        serviceDO.serviceLogo = R.drawable.form_one;
                        serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_REQUEST;
                        Intent intent = new Intent(CardCancellationActivity.this, SalaryTransferRequestBankAccount.class);
                        intent.putExtra(ConstantExtraKey.SERVICE_OBJECT, serviceDO);
                        intent.putExtra("ATTACHMENTS",iFormPresenter.getAttachments());
                        startActivity(intent);
                        finish();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.app_color));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_color));

            }
        });
        alert.setTitle("Card Cancellation");
        alert.show();
    }
}
