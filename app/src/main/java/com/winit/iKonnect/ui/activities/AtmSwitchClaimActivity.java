package com.winit.iKonnect.ui.activities;

import android.app.TimePickerDialog;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.vision.text.Line;
import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.C3CardATMSwitchClaimDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.winit.iKonnect.R.id.et_card_number;

/**
 * Created by Ojha.Sandeep on 10/25/2017.
 */

public class AtmSwitchClaimActivity extends FormActivity {

    @Nullable
    @Bind(et_card_number)
    EditText cardNumber;

    @Nullable
    @Bind(R.id.et_card_member_mobile_number)
    EditText cardMemberMobileNumber;

    @Nullable
    @Bind(R.id.tv_transaction_date_value)
    TextView transactionDate;

    @Nullable
    @Bind(R.id.tv_transaction_time_value)
    EditText transactionTime;

    @Nullable
    @Bind(R.id.et_amount_with_draw)
    EditText et_amount_with_draw;

    @Nullable
    @Bind(R.id.et_amount_recived)
    EditText et_amount_recived;

    @Nullable
    @Bind(R.id.et_amount_disputed)
    EditText et_amount_disputed;

    @Nullable
    @Bind(R.id.et_bank_name)
    EditText et_bank_name;

    @Nullable
    @Bind(R.id.et_atm_location)
    EditText et_atm_location;

    @Nullable
    @Bind(R.id.et_additional_comment)
    EditText et_additional_comment;

    @Nullable
    @Bind(R.id.ll_atm_switch_claim)
    LinearLayout ll_atm_switch_claim;


    C3CardATMSwitchClaimDO c3CardATMSwitchClaimDO;

    @Override
    protected void initializeForm() {

        c3CardATMSwitchClaimDO = (C3CardATMSwitchClaimDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.C3_Card_ATM_witch_Claim_Service_Request));

        inflater.inflate(R.layout.atm_switch_claim, flFormBody, true);
        ll_reson_for_request.setVisibility(View.GONE);
        ButterKnife.bind(this);
        bindListeners();

        if (c3CardATMSwitchClaimDO != null) {
            showAllHistoryContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
    }


    public void showAllHistoryContent() {
        cardNumber.setText(c3CardATMSwitchClaimDO.getCardNumber());
        cardMemberMobileNumber.setText(c3CardATMSwitchClaimDO.getCardmemberMobileNumber());
        transactionTime.setText(c3CardATMSwitchClaimDO.getTransactionTime());
        transactionDate.setText(c3CardATMSwitchClaimDO.getTransactionDate().replace("/","-"));
        et_amount_with_draw.setText(c3CardATMSwitchClaimDO.getAmountWithdrawn());
        et_amount_recived.setText(c3CardATMSwitchClaimDO.getAmountReceived());
        et_amount_disputed.setText(c3CardATMSwitchClaimDO.getAmountDisputed());
        et_bank_name.setText(c3CardATMSwitchClaimDO.getBankName());
        et_atm_location.setText(c3CardATMSwitchClaimDO.getATMLocation());
        et_additional_comment.setText(c3CardATMSwitchClaimDO.getAdditionalComments());


    }


    private void bindListeners() {


        transactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedDateFromDatePicker(AtmSwitchClaimActivity.this, transactionDate);
//                showDatePickerDialog(AtmSwitchClaimActivity.this, transactionDate);

            }
        });

        transactionTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AtmSwitchClaimActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //code to convert 24 hour formate to 12 hour formate
                        int hour = hourOfDay;
                        int minutes = minute;
                        String timeSet = "";
                        if (hour > 12) {
                            hour -= 12;
                            timeSet = "PM";
                        } else if (hour == 0) {
                            hour += 12;
                            timeSet = "AM";
                        } else if (hour == 12) {
                            timeSet = "PM";
                        } else {
                            timeSet = "AM";
                        }

                        String min = "";
                        if (minutes < 10)
                            min = "0" + minutes;
                        else
                            min = String.valueOf(minutes);

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hour).append(':')
                                .append(min).append(" ").append(timeSet).toString();

                        transactionTime.setText(aTime + " ");
                    }
                }, hour, minute, false);//is24HoursView ==true then it shows 24 hours in time opicker

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });


        TextWatcher textWatcherRecived = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (et_amount_with_draw.getText().toString().equalsIgnoreCase("")&&!editable.toString().equalsIgnoreCase("")) {
                    showAlert(EMPTY_AMOUNT_WITHDRAW);
                } else if ((StringUtils.toInt(editable.toString())) >= 0) {
                    et_amount_disputed.setText("" + ((StringUtils.toInt(et_amount_with_draw.getText().toString())) - (StringUtils.toInt(editable.toString()))));
                }

            }
        };

        et_amount_recived.addTextChangedListener(textWatcherRecived);

        et_amount_with_draw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_amount_disputed.setText("");
                et_amount_recived.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                et_amount_disputed.setText("");
                et_amount_recived.setText("");
            }
        });


        cardMemberMobileNumber.setText(preference.getStringFromPreference(Preference.STAFF_MOBILE, ""));


    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            case EMPTY_CARDHOLDER_MOBILE_NUMBER:
                message = "Please enter Card Holder's Mobile Number";
                break;
            case EMPTY_TIME:
                message = "Please Select the transaction time.";
                break;
            case EMPTY_DATE:
                message = "Please Select the transaction date.";
                break;
            case EMPTY_AMOUNT_WITHDRAW:
                message = "Please enter the amount withdraw.";
                break;
            case EMPTY_AMOUNT_RECIVED:
                message = "Please enter the amount received.";
                break;
            case EMPTY_AMOUNTDISPUTED:
                message = "Please enter the amount disputed.";
                break;
            case EMPTY_ATM_LOCATION:
                message = "Please enter the ATM Location.";
                break;
            case EMPTY_SIGNATURE:
                message = "Please enter the  signature";
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

            case ZEROVALUE:
                message = "Amount Withdrawn can not be 0.";
                break;

            case RANGEEXCEED:
                message = "Amount Received can not more than Amount Withdrawn.";
                break;

            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(AtmSwitchClaimActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(AtmSwitchClaimActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {

        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.submitAtmSwitchClaim(
                etReason.getText().toString(),
                cardNumber.getText().toString(),
                cardMemberMobileNumber.getText().toString(),
                transactionDate.getText().toString(),
                transactionTime.getText().toString(),
                et_amount_with_draw.getText().toString(),
                et_amount_recived.getText().toString(),
                et_amount_disputed.getText().toString(),
                et_bank_name.getText().toString(),
                et_atm_location.getText().toString(),
                et_additional_comment.getText().toString(),
                ""
        );

    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();

        showFormSubmitPopup("C3 Card ATM Switch Claim Service Request", message);

    }

}
