package com.winit.iKonnect.ui.activities;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.LeaveApplicationDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.winit.iKonnect.module.form.IFormView.ADVANCE_SALARY;

/**
 * Created by Ojha.Sandeep on 10/21/2017.
 */

public class LeaveApplicationActivtiy extends FormActivity {


    @Nullable
    @Bind(R.id.et_leave_request)
    EditText et_leave_request;

    @Nullable
    @Bind(R.id.tv_start_date)
    TextView startDate;

    @Nullable
    @Bind(R.id.tv_end_date)
    TextView endDate;

    @Nullable
    @Bind(R.id.ll_reason)
    LinearLayout ll_reason;

    @Nullable
    @Bind(R.id.etReason_leave_reason)
    EditText et_leaveReason;

    @Nullable
    @Bind(R.id.et_contact_number)
    EditText et_contact_number;

    @Nullable
    @Bind(R.id.rb_advance_salary_no)
    RadioButton rb_advance_salary_no;

    @Nullable
    @Bind(R.id.rb_advance_salary_yes)
    RadioButton rb_advance_salary_yes;

    @Nullable
    @Bind(R.id.totalDay)
    TextView totalDay;

    @Nullable
    @Bind(R.id.ll_days)
    LinearLayout ll_days;

    @Nullable
    @Bind(R.id.ll_leave_form)
    LinearLayout ll_leave_form;

    @Nullable
    @Bind(R.id.ll_salary_request_advance)
    LinearLayout ll_salary_request_advance;

    String SalaryAdvance = "";
    String doYouWant_leave_Salry_Advance = "";

    private LeaveApplicationDO leaveApplicationDO = null;


    @Override
    protected void initializeForm() {


        //This will get From TrackDetail Activity for history purpose
        leaveApplicationDO = (LeaveApplicationDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.Leave_Application));


        inflater.inflate(R.layout.leave_application_form, flFormBody, true);
        ButterKnife.bind(this);
        ll_reson_for_request.setVisibility(View.GONE);
        ll_leave.setVisibility(View.VISIBLE);
        bindData();
        //for band 3,4,5,6
        String strBand = preference.getStringFromPreference(Preference.BAND, "");

        if (strBand.equalsIgnoreCase("B3") || strBand.equalsIgnoreCase("B4") ||
                strBand.equalsIgnoreCase("B5") || strBand.equalsIgnoreCase("B6")) {
            ll_salary_request_advance.setVisibility(View.GONE);
        } else {
            ll_salary_request_advance.setVisibility(View.VISIBLE);
        }


        //for showing view and making all the view non editable as well as binding previously submoited form data
        if (leaveApplicationDO != null) {
            showAllHistoryContent();
            disableAllChilds(ll_leave_form);
            btnSubmit.setVisibility(View.GONE);
        }


    }


    //Track Detail History purpose
    private void showAllHistoryContent() {

        et_leave_request.setText(leaveApplicationDO.getTypeofLeave());
//        startDate.setText(leaveApplicationDO.getLeaveStartDate().replace("/","-"));
        startDate.setText(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(leaveApplicationDO.getLeaveStartDate(),CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.FULL_DATE_FORMATE));
//        endDate.setText(leaveApplicationDO.getLeaveEndDate().replace("/","-"));
        endDate.setText(CalendarUtil.dd_MMM_yyyy_to_dd_MM_yyyy(leaveApplicationDO.getLeaveEndDate(),CalendarUtil.DD_MMM_YYYY_PATTERN,CalendarUtil.FULL_DATE_FORMATE));
        et_contact_number.setText(leaveApplicationDO.getContactNo());
        doYouWant_leave_Salry_Advance = leaveApplicationDO.getDoyouWantLeaveSalary();
        if (doYouWant_leave_Salry_Advance.equalsIgnoreCase("yes")) {
            rb_advance_salary_yes.setChecked(true);
        } else {
            rb_advance_salary_no.setChecked(true);
        }
        if (leaveApplicationDO.getTypeofLeave().equalsIgnoreCase("Emergency Leave")) {
            ll_reason.setVisibility(View.VISIBLE);
            et_leaveReason.setText(leaveApplicationDO.getReasonEmergency());
        }
        totalDay.setText(leaveApplicationDO.getTotalLeaves());


    }


    @Override
    public void showAlert(String type) {

        hideLoader();
        String message = "";
        switch (type) {
            case EMPTY_TYPE_LEAVE:
                message = "Please Select Any One Option for Type of Leave";
                break;

            case START_DATE:
                message = "Please select Leave start date";
                break;

            case END_DATE:
                message = "Please select Leave end date";
                break;
            case SELEC_SALARY_ADVANCE:
                message = "Please select Leave start date and end date";
                break;

            case EMPTY_MOBILE:
                message = "Please enter your contact number";
                break;

            case DOYOUWANT_LEAVE_SALRY_ADVANCE:
                message = "Please select any option for Salary Advance";
                break;

            case ADVANCE_SALARY:
                message = "You are not eligible for Salary Advance, as the leave period is less than 15 Days";
                break;
            case ATTACHMENT:
                message = getString(R.string.sick_leave);
                break;

            case EMPTY_EMERGENCY:
                message = "Please state the reason";
                break;

            case WEEKEND:
                message = "You cannnot apply leave for weekend days";
                break;
            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(LeaveApplicationActivtiy.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(LeaveApplicationActivtiy.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {

        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.submitLeaveApplicationForm(
                etReason.getText().toString(),
                et_leave_request.getText().toString(),
                startDate.getText().toString(),
                endDate.getText().toString(),
                totalDay.getText().toString(),
                et_contact_number.getText().toString(),
                doYouWant_leave_Salry_Advance,
                MaxPos,
                et_leaveReason.getText().toString()


        );
    }


    private void bindData() {

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(LeaveApplicationActivtiy.this, startDate);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(startDate.getText().toString())) {
                    getSelectedDateFromDatePicker(LeaveApplicationActivtiy.this, startDate.getText().toString(), endDate, totalDay);
                    ll_days.setVisibility(View.VISIBLE);

                } else {
                    showAlert(START_DATE);
                }
            }
        });
        et_leave_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerShow(et_leave_request);
            }
        });
    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        message = "Submitted to Reporting Manager Approval";
        if (serviceRequestDO != null)
            showFormSubmitPopup(serviceRequestDO.serviceName, message);

        else {
            showFormSubmitPopup("Leave Application", message);
        }
    }

    public void spinnerShow(final View view) {
        AlertDialog dialog;


        final CharSequence str[];
        if (preference.getStringFromPreference(Preference.STAFF_NATIONALITY, "").equalsIgnoreCase("omani")) {
            str = new CharSequence[]{"Annual Leave",
                    "Emergency Leave",
                    "Sick Leave",
                    "Maternity Leave",
                    "Hajj Leave",
                    "Study Leave",
                    "Marriage Leave",
                    "Paid Leave - No Quota"};
        } else {
            str = new CharSequence[]{"Annual Leave",
                    "Medical Leave",
                    "Emergency Leave",
                    "Hajj Leave",
                    "Maternity Leave"};
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Leave Type");
        builder.setItems(str, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int position) {
                //here you can use like this... str[position]
                et_leave_request.setText(str[position] + "");

                if (str[position].toString().equalsIgnoreCase("Emergency Leave")) {
                    ll_reason.setVisibility(View.VISIBLE);
                } else {
                    ll_reason.setVisibility(View.GONE);
                }

            }

        });
        dialog = builder.create();
        dialog.show();
    }

    public void wantLeave(View view) {
        switch (view.getId()) {
            case R.id.rb_advance_salary_yes:
                doYouWant_leave_Salry_Advance = "yes";
                break;
            case R.id.rb_advance_salary_no:
                doYouWant_leave_Salry_Advance = "No";
                break;
        }
    }


}
