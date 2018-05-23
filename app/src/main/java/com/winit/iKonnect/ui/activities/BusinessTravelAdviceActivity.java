package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.winit.common.constant.AppConstants;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.BusinessTravelDo;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.module.form.IFormView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Ojha.Sandeep on 10/21/2017.
 */

public class BusinessTravelAdviceActivity extends FormActivity {

    @Nullable
    @Bind(R.id.tv_start_date)
    TextView startDate;

    @Nullable
    @Bind(R.id.tv_end_date)
    TextView endDate;

    @Nullable
    @Bind(R.id.tv_destination)
    EditText tv_destination;

    @Nullable
    @Bind(R.id.et_purpose_of_travel)
    EditText et_purpose_of_travel;

    @Nullable
    @Bind(R.id.et_contact_number)
    EditText et_contact_number;

    @Nullable
    @Bind(R.id.rd_ticket_yes)
    RadioButton rd_ticket_yes;

    @Nullable
    @Bind(R.id.rd_ticket_no)
    RadioButton rd_ticket_no;

    @Nullable
    @Bind(R.id.et_sector_required)
    EditText et_sector_required;

    @Nullable
    @Bind(R.id.rd_visa_yes)
    RadioButton rd_visa_yes;

    @Nullable
    @Bind(R.id.rd_visa_no)
    RadioButton rd_visa_no;

    @Nullable
    @Bind(R.id.rd_hotel_accomidation_yes)
    RadioButton rd_hotel_accomidation_yes;

    @Nullable
    @Bind(R.id.rd_hotel_accomidation_no)
    RadioButton rd_hotel_accomidation_no;

    @Nullable
    @Bind(R.id.et_cost_debit_to)
    EditText et_cost_debit_to;

    @Nullable
    @Bind(R.id.et_another_requirement)
    EditText et_another_requirement;

    String ticket = "";
    String visa = "";
    String hostel_accomidation = "";

    @Nullable
    @Bind(R.id.ll_sector)
    LinearLayout ll_sector;

    private BusinessTravelDo businessTravelDo;

    @Override
    protected void initializeForm() {

        businessTravelDo = (BusinessTravelDo) getIntent().getSerializableExtra(this.getResources().getString(R.string.Business_Travel_Request));

        tvFormHeading.setText("Business Travel Request");
        tvFormHeading.setTextColor(getResources().getColor(R.color.app_color));
        ivFormIcon.setImageResource(R.drawable.form_three);
        inflater.inflate(R.layout.business_travel_advisor, flFormBody, true);
        ll_reson_for_request.setVisibility(View.GONE);
        ButterKnife.bind(this);
        bindData();

        if (businessTravelDo != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    public void showAllContent() {
        startDate.setText(businessTravelDo.getStartDate().replace("/","-"));
        endDate.setText(businessTravelDo.getEndDate().replace("/","-"));
        tv_destination.setText(businessTravelDo.getDestinations());
        et_contact_number.setText(businessTravelDo.getContactNumberduringBusinessTravel());

        if (businessTravelDo.getTicketRequired().equalsIgnoreCase("yes")) {
            rd_ticket_yes.setChecked(true);
            et_sector_required.setText(businessTravelDo.getSector());
            ll_sector.setVisibility(View.VISIBLE);
        } else {
            rd_ticket_no.setChecked(true);
        }

        if (businessTravelDo.getVisaRequired().equalsIgnoreCase("yes")) {
            rd_visa_yes.setChecked(true);
        } else {
            rd_visa_no.setChecked(true);
        }

        if (businessTravelDo.getHotelAccommodation().equalsIgnoreCase("yes")) {
            rd_hotel_accomidation_yes.setChecked(true);
        } else {
            rd_hotel_accomidation_no.setChecked(true);
        }

        et_another_requirement.setText(businessTravelDo.getAnyOtherRequirements());
        et_cost_debit_to.setText(businessTravelDo.getCostDebitto());
        et_purpose_of_travel.setText(businessTravelDo.getPurposeofTravel());
    }

    @Override
    public void showAlert(String type) {

        hideLoader();
        String message = "";
        switch (type) {

            case EMPTY_DESTINATION:
                message = "Please Enter the destination(s)";
                break;

            case EMPTY_PURPOSE_OF_TRAVEL:
                message = "Please enter the purpose of travel";
                break;

            case START_DATE:
                message = getString(R.string.please_select_the_start_date);
                break;

            case END_DATE:
                message = getString(R.string.please_select_the_end_date);
                break;

            case EMPTY_MOBILE:
                message = "Please enter Contact Number During Business Travel";
                break;

            case SELECT_TICKETTYPE:
                message = "Please select any option for Ticket Required.";
                break;

            case EMPTY_VISA:
                message = "Please select any option for Visa Required.";
                break;

            case HOSTEL_ACCOMIDATION:
                message = "Please select any option for Hotel Accommodation.";
                break;

            case COST_DEBIT_TO:
                message = "Please enter Cost Debit To";
                break;

            case EMPTY_SECTOR:
                message = "Please enter sector";
                break;

            case NUMBER_LENGHT:
                message = "Please enter valid mobile number";
                break;

            case VALID_DATE_SELECTION:
                message = "Please select the valid start and end date";
                break;

            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(BusinessTravelAdviceActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(BusinessTravelAdviceActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");

    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));

        rd_ticket_yes.getText().toString();

        iFormPresenter.submitBussinessTravelRequest(
                etReason.getText().toString(),
                tv_destination.getText().toString(),
                et_purpose_of_travel.getText().toString(),
                startDate.getText().toString(),
                endDate.getText().toString(),
                et_contact_number.getText().toString(),
                ticket,
                et_sector_required.getText().toString(),
                visa,
                hostel_accomidation,
                et_cost_debit_to.getText().toString(),
                et_another_requirement.getText().toString()
        );

    }

    private void bindData() {

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showDatePickerDialog(BusinessTravelAdviceActivity.this, startDate);
                getSelectedDateFromDatePicker(BusinessTravelAdviceActivity.this, startDate);
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showDatePickerDialog(BusinessTravelAdviceActivity.this, endDate);
                if (!StringUtils.isEmpty(startDate.getText().toString()))
                    getSelectedDateFromDatePicker(BusinessTravelAdviceActivity.this, startDate.getText().toString(), endDate);
                else {
                    showAlert(IFormView.START_DATE);
                }
            }
        });

    }

    public void checkTicket(View view) {

        boolean ischecked = ((RadioButton) view).isChecked();

        switch (view.getId()) {


            case R.id.rd_ticket_yes:
                if (ischecked) {
                    ticket = "yes";
                    ll_sector.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rd_ticket_no:
                if (ischecked) {
                    ticket = "false";
                    ll_sector.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void checkHotelAccomidation(View view) {
        boolean ischecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rd_hotel_accomidation_no:
                if (ischecked) {
                    hostel_accomidation = "No";
                }
                break;

            case R.id.rd_hotel_accomidation_yes:
                if (ischecked) {
                    hostel_accomidation = "yes";
                }
                break;
        }
    }

    public void checkVisa(View view) {
        boolean ischecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rd_visa_no:
                if (ischecked) {
                    visa = "No";
                }
                break;

            case R.id.rd_visa_yes:
                if (ischecked) {
                    visa = "yes";
                }
                break;
        }
    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();

        message = "Submitted to Division Manager/GM for approval";
        showFormSubmitPopup("Business Travel Request", message);
    }

}
