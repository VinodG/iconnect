package com.winit.iKonnect.ui.activities;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.HousingAllowanceDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ojha.Sandeep on 10/21/2017.
 */

public class HouseAllowanceActivity extends FormActivity {


 /*   @Nullable
    @Bind(R.id.tv_advance_leave_request)
    TextView advanceLeaveRequest;*/

    @Nullable
    @Bind(R.id.rbOneMonth)
    RadioButton rbOneMonth;
    @Nullable
    @Bind(R.id.rbTwoMonths)
    RadioButton rbTwoMonths;
    @Nullable
    @Bind(R.id.rbThreeMonths)
    RadioButton rbThreeMonths;

    private HousingAllowanceDO housingAllowanceDO;
    private String advanceLeaveRequest="";


    @Override
    protected void initializeForm() {

        housingAllowanceDO = (HousingAllowanceDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.Housing_Allowance));
        ll_reson_for_request.setVisibility(View.GONE);
        inflater.inflate(R.layout.house_allowance, flFormBody, true);
        ButterKnife.bind(this);
//        bindData();

        if (housingAllowanceDO != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    private void showAllContent() {
//        advanceLeaveRequest.setText(housingAllowanceDO.getAdvanceRequest());
        String noOfMonths=housingAllowanceDO.AdvanceRequest;
        if (!StringUtils.isEmpty(noOfMonths)){
            if (noOfMonths.equalsIgnoreCase("1 Month")){
                rbOneMonth.setChecked(true);
            }else if (noOfMonths.equalsIgnoreCase("2 Months")){
                rbTwoMonths.setChecked(true);
            }else if (noOfMonths.equalsIgnoreCase("3 Months")){
                rbThreeMonths.setChecked(true);
            }
        }
    }

    @Override
    public void showAlert(String type) {

        hideLoader();
        String message = "";
        switch (type) {

            case ADVANCEREQUEST:
                message = "Please select any option for Housing Allowance Advance Request";
                break;

            case CONFIRMATION_EMPTY:
                message = "You will be eligible for HRA advance upon successful completion of your probation period.";
                break;

            case CONFIRMATION_NOT_EMPTY:
                message = "You will be eligible for HRA advance upon successful completion of your probation period.";
                break;

            case COMPANY_ACCOMIDATION:
                message = "Employees residing in the company accommodation are not eligible for HRA Advance.";
                break;


            case BANK_LOAN:
                message = "Employees who have obtained salary transfer letter from the company cannot avail Housing Allowance Advance. Please contact HR for further details.";
                break;

            case OUTSTANDING_HRA:
                message = "You have pre-existing housing allowance. You are not eligible for advance housing allowance.";
                break;

            case ATTACHMENT_EMPTY:
                message = "Please attach Tenancy Contract for Advance Request";
                break;
            default:
                message=type;
                break;


        }
        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(HouseAllowanceActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(HouseAllowanceActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {

        showLoader(getResources().getString(R.string.pleaseWait));
        preference.saveIntInPreference(Preference.ATTACHMENT_COUNT, MaxPos);

        long chekDate = StringUtils.toInt(preference.getStringFromPreference(Preference.HOUSING_CUTOFF, "0"));
        int currentDate = Calendar.getInstance().get(Calendar.DATE);

//        if (currentDate <= chekDate) {
            iFormPresenter.submitHouseAllowance(
                    preference.getStringFromPreference(preference.STAFF_NAME, "N/A"),
                    etReason.getText().toString(),
                    "Location",
                    "Designation",
                    preference.getStringFromPreference(preference.DEPARTMENT, "N/A"),
                    advanceLeaveRequest,
                    preference.getStringFromPreference(preference.STAFF_ID, "N/A")
            );
//        } else {
//            showAlert("You can submit this request till "+chekDate+"th day of every month");
//        }
    }

/*
    public void bindData() {
        advanceLeaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerShow(advanceLeaveRequest);
            }
        });
    }
*/


    public void spinnerShow(final View view) {
        AlertDialog dialog;
        final CharSequence str[] = {"1", "2", "3"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select no of months");
        builder.setItems(str, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int position) {
                //here you can use like this... str[position]
                ((TextView) view).setText(str[position] + "");

            }

        });
        dialog = builder.create();
        dialog.show();

    }

    public void selectNoOfMonths(View view){
        switch (view.getId()){
            case R.id.rbOneMonth:
                advanceLeaveRequest="1";
                break;
            case R.id.rbTwoMonths:
                advanceLeaveRequest="2";
                break;
            case R.id.rbThreeMonths:
                advanceLeaveRequest="3";
                break;
        }
    }
}
