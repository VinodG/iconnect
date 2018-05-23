package com.winit.iKonnect.ui.activities;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.constant.AppConstants;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.PassportReleaseDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ojha.Sandeep on 10/21/2017.
 */

public class PassportReleaseActivity extends FormActivity {


    @Nullable
    @Bind(R.id.tv_start_borrow_date)
    TextView startDate;

    @Nullable
    @Bind(R.id.tv_end_return_date)
    TextView endDate;

    @Nullable
    @Bind(R.id.tv_city)
    TextView tv_city;
    @Nullable
    @Bind(R.id.etOther)
    EditText etOther;

    private PassportReleaseDO passportReleaseDO;

    @Override
    protected void initializeForm() {

        passportReleaseDO = (PassportReleaseDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.Passport_Release_Request));
        inflater.inflate(R.layout.passport_release, flFormBody, true);
        ButterKnife.bind(this);
        bindData();

        ll_reson_for_request.setVisibility(View.GONE);

        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaptureImage();//remove camera option from the list
            }
        });


        if (passportReleaseDO != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }

    }


    public void showAllContent() {
        startDate.setText(passportReleaseDO.getLeave_StartDate().replace("/","-"));
        endDate.setText(passportReleaseDO.getLeave_EndDate().replace("/","-"));

        tv_city.setText(passportReleaseDO.getPurposeofpassportrelease());

        startDate.setText(passportReleaseDO.getDateborrowed().replace("/","-"));
        endDate.setText(passportReleaseDO.getDatetobereturned().replace("/","-"));

        if (passportReleaseDO.getPurposeofpassportrelease().equalsIgnoreCase("Others")) {
            etOther.setVisibility(View.VISIBLE);
            etOther.setText(passportReleaseDO.getPurposeofpassportrelease());
        }
    }


    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            case PURPOSE_OF_RELEASE:
                message = getString(R.string.purpose_of_release);
                break;
            case PURPOSE_OF_RELEASE_OTHERS:
                message = "Please specify Purpose Of Passport Release";
                break;
            case START_DATE:
                message = "Please Select Date Borrowed";
                break;
            case END_DATE:
                message = "Please Select date to be returned";
                break;
            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(PassportReleaseActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(PassportReleaseActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        String startdate = startDate.getText().toString();
        String enddate = endDate.getText().toString();
        if (!StringUtils.isEmpty(startdate) && !StringUtils.isEmpty(enddate)) {

            boolean istrue = CalendarUtil.compareDates(startdate, enddate);

            if (istrue) {
                showAlert("Return date cannot be less than Borrowed date");
            } else {
                iFormPresenter.PassportRelease(
                        tv_city.getText().toString()
                        , startDate.getText().toString()
                        , endDate.getText().toString()
                        , etOther.getText().toString());
            }
        } else {
            iFormPresenter.PassportRelease(
                    tv_city.getText().toString()
                    , startDate.getText().toString()
                    , endDate.getText().toString()
                    , etOther.getText().toString());
        }
    }


    private void bindData() {

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(PassportReleaseActivity.this, startDate);
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(PassportReleaseActivity.this, endDate);
            }
        });
        tv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinner(tv_city);
            }
        });

    }

    private void showSpinner(final View view) {

        AlertDialog alertDialog;
        final CharSequence items[] = getResources().getStringArray(R.array.passport_release_purpose);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Purpose of passport release");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_city.setText(items[which] + "");

                if (tv_city.getText().toString().equalsIgnoreCase("Others")) {
                    etOther.setVisibility(View.VISIBLE);
                } else {
                    etOther.setVisibility(View.GONE);
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }


    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        if (serviceRequestDO != null) {
            showFormSubmitPopup(serviceRequestDO.serviceName, "Submitted for Approval to Reporting Manager");
        }
    }
}

