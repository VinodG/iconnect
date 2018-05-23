package com.winit.iKonnect.ui.activities;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.TransportLoanDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class TransportLoanRequestActivity extends FormActivity {

    @Nullable
    @Bind(R.id.rbDL_yes)
    RadioButton rbDL_yes;

    @Nullable
    @Bind(R.id.rbDL_no)
    RadioButton rbDL_no;

    @Nullable
    @Bind(R.id.tvStaffName)
    TextView StaffName;

    @Nullable
    @Bind(R.id.llPreowned)
    LinearLayout llPreowned;
    @Nullable
    @Bind(R.id.llNew)
    LinearLayout llNew;
    @Nullable
    @Bind(R.id.etNewVehicleMake)
    EditText NewVehicleMake;
    @Nullable
    @Bind(R.id.etNewVehicleModel)
    EditText NewVehicleModel;
    @Nullable
    @Bind(R.id.etNewChassisNo)
    EditText NewChassisNo;

    @Nullable
    @Bind(R.id.etVehicleMake)
    EditText VehicleMake;
    @Nullable
    @Bind(R.id.etVehicleModel)
    EditText VehicleModel;
    @Nullable
    @Bind(R.id.etOdometerRead)
    EditText OdometerRead;
    @Nullable
    @Bind(R.id.etChassisNo)
    EditText ChassisNo;

    @Nullable
    @Bind(R.id.etAmount)
    EditText etAmount;

    @Nullable
    @Bind(R.id.rbBrandNew)
    RadioButton rbBrandNew;

    @Nullable
    @Bind(R.id.rbPre_owned)
    RadioButton rbPre_owned;

    @Nullable
    @Bind(R.id.etEngineNo)
    EditText etEngineNo;
    @Nullable
    @Bind(R.id.etNewEngineNo)
    EditText etNewEngineNo;
    @Nullable
    @Bind(R.id.tvCurrency)
    TextView tvCurrency;

    private String StaffNo, Department, Designation, Name;
    private String BrandNeworPre = "", IsUAEDri_License = "";

    private TransportLoanDO transportLoanDO;
    private boolean isNew=false;

    @Override
    protected void initializeForm() {


        transportLoanDO = (TransportLoanDO) getIntent().getSerializableExtra(getResources().getString(R.string.Transport_Loan_Request));

        inflater.inflate(R.layout.activity_transport_loan_request, flFormBody, true);
        ButterKnife.bind(this);

        StaffNo = preference.getStringFromPreference(Preference.STAFF_NUMBER, "");
        Department = preference.getStringFromPreference(Preference.DEPARTMENT, "");
        Designation = preference.getStringFromPreference(Preference.STAFF_PERSONAL_AREA, "");
        Name = preference.getStringFromPreference(Preference.STAFF_NAME, "");

        if (!StringUtils.isEmpty(Name)) {
            StaffName.setText(Name + ", ");
        }

        if (transportLoanDO != null) {

            disableAllChilds(ll_form);
            showAllContent();
            btnSubmit.setVisibility(View.GONE);
        }

        if (preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "").equalsIgnoreCase(AppConstants.STAFF_WORK_COUNTRY)){
            tvCurrency.setText(AppConstants.OMR);
        } else {
            tvCurrency.setText(AppConstants.AED);
        }
        ll_reson_for_request.setVisibility(View.GONE);

        VehicleModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNew=false;
                show(isNew);
            }
        });
        NewVehicleModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNew=true;
                show(isNew);
            }
        });


    }

    public void showAllContent() {

        if (transportLoanDO.getIsUAEDri_License().equalsIgnoreCase("Yes")) {
            rbDL_yes.setChecked(true);
        } else {
            rbDL_no.setChecked(true);
        }

        StaffName.setText(transportLoanDO.getName());
        etAmount.setText(transportLoanDO.getLoanamount());

        if (transportLoanDO.getBrandNeworPre().equalsIgnoreCase("Pre-Owned")) {
            llPreowned.setVisibility(View.VISIBLE);
            VehicleModel.setText(transportLoanDO.getVehicleModel());
            VehicleMake.setText(transportLoanDO.getVehicleMake());
            OdometerRead.setText(transportLoanDO.getOdometerRead());
            ChassisNo.setText(transportLoanDO.getChassisNo());
            etEngineNo.setText(transportLoanDO.getEnginenumber());
            rbPre_owned.setChecked(true);
        } else {
            rbBrandNew.setChecked(true);
            llNew.setVisibility(View.VISIBLE);
            NewVehicleModel.setText(transportLoanDO.getVehicleModel());
            NewVehicleMake.setText(transportLoanDO.getVehicleMake());
            NewChassisNo.setText(transportLoanDO.getChassisNo());
            etNewEngineNo.setText(transportLoanDO.getEnginenumber());
        }
    }

    public void drivingLicensde(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbDL_yes:
                if (isChecked) {
                    IsUAEDri_License = "Yes";
                }
                break;
            case R.id.rbDL_no:
                if (isChecked) {
                    IsUAEDri_License = "No";
                    showAlert("DRI_LICENSE");
                }
                break;
            default:
                break;
        }
    }

    public void brandedPreowned(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rbBrandNew:
                if (isChecked) {
                    BrandNeworPre = "New";
                    llNew.setVisibility(View.VISIBLE);
                    llPreowned.setVisibility(View.GONE);
                }
                break;
            case R.id.rbPre_owned:
                if (isChecked) {
                    BrandNeworPre = "Pre-Owned";
                    llPreowned.setVisibility(View.VISIBLE);
                    llNew.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {

            case TRANSPORT_LOAN_NOT_ELIGIBLE:
                message = "You are not eligible for Transport Loan";
                break;
            case TRANSPORT_LOAN:
                message = "Only confirmed employees are eligible for this request";
                break;
            case UAE_DRI_LICENSE:
                message = getString(R.string.license);
                break;
            case DRI_LICENSE:
                message = getString(R.string.warning_license);
                break;
            case STAFF_NO:
                message = getString(R.string.staff_no);
                break;
            case STAFF_NAME:
                message = getString(R.string.staff_name);
                break;
            case DEPARTMENT:
                message = getString(R.string.department);
                break;
            case AMOUNT:
                message = "Please enter Amount";
                break;
            case BRAND_NEW_OR_PRE:
                message = getString(R.string.brand_new_or_pre);
                break;
            case VEHICLE_MAKE:
                message = getString(R.string.vehicle_make_alesrt);
                break;
            case VEHICLE_MODEL:
                message = getString(R.string.vehicle_model_alert);
                break;
            case VEHICLE_MODEL_:
                message = getString(R.string.vehicle_model_alert1);
                break;
            case ODOMETER_READ:
                message = getString(R.string.odometer_read);
                break;
            case ODOMETER_READ_:
                message = getString(R.string.odometer_read_alert);
                break;
            case CHASSIS_NO:
                message = getString(R.string.chassis_no_alert);
                break;
            case NEW_VEHICLE_MAKE:
                message = getString(R.string.vehicle_make_alesrt);
                break;
            case NEW_VEHICLE_MODEL:
                message = getString(R.string.vehicle_model_alert);
                break;
            case NEW_CHASSIS_NO:
                message = getString(R.string.chassis_no_alert);
                break;
            case ENGINE_NO:
                message = "Please enter Engine No.";
                break;
            case ATTACHMENT_TRANSP:
                message = getString(R.string.transportLoan_attach_popup1);
                break;
            case ATTACHMENT_TRANSP_NEW:
                message = getString(R.string.transportLoan_attach_new);
                break;
            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(TransportLoanRequestActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(TransportLoanRequestActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.TransportLoan(IsUAEDri_License, StaffNo, Name, Department, Designation, BrandNeworPre
                , VehicleMake.getText().toString()
                , VehicleModel.getText().toString()
                , OdometerRead.getText().toString()
                , ChassisNo.getText().toString()
                , NewVehicleMake.getText().toString()
                , NewVehicleModel.getText().toString()
                , NewChassisNo.getText().toString()
                , etAmount.getText().toString()
                ,etEngineNo.getText().toString()
                ,etNewEngineNo.getText().toString()
                ,MaxPos
        );
    }

    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        if (serviceRequestDO != null) {
            showFormSubmitPopup(serviceRequestDO.serviceName, "Submitted to Reporting Manager for Approval");
        }
    }


    public void show(final boolean isNew)
    {

        final Dialog d = new Dialog(TransportLoanRequestActivity.this);
//        d.setTitle("Select Vehicle Model (Year)");
//        d.getWindow().setTitleColor(getResources().getColor(R.color.text_color_dark));
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog);
        Button bntOk = (Button) d.findViewById(R.id.bntOk);
        final Button btnCancel = (Button) d.findViewById(R.id.btnCancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        TextView tvTitle=(TextView)d.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.select_model));

       /* for (int i=0;i<=20;i++)
            list.add((i+2000)+"");

        ArrayAdapter adapter = new ArrayAdapter<String>( getApplicationContext(),android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VehicleModel.setText(String.valueOf(list.get(i)));
                d.dismiss();
            }
        });*/
//        colorDialogTitle(d,getResources().getColor(R.color.text_color_dark));

        int currentYear=Integer.parseInt(CalendarUtil.getCurrentYear());

        final String[] displayValues=getDisplayValues(currentYear-10,currentYear);


        np.setMaxValue(displayValues.length-1);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setDisplayedValues(displayValues);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                VehicleModel.setText(String.valueOf(np.getValue()));
            }
        });

        bntOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (isNew){
//                    NewVehicleModel.setText(String.valueOf(np.getValue()));
                    NewVehicleModel.setText(displayValues[np.getValue()]);
                }else {
//                    VehicleModel.setText(String.valueOf(np.getValue()));
                    VehicleModel.setText(displayValues[np.getValue()]);
                }
                d.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
        d.getWindow().setLayout(width-100, width-100);
    }

    public String[] getDisplayValues(int minimumInclusive, int maximumInclusive) {

        ArrayList<String> result = new ArrayList<String>();

        for(int i = maximumInclusive; i >= minimumInclusive; i--) {
            result.add(Integer.toString(i));
        }

        return result.toArray(new String[0]);
    }

    public static void colorDialogTitle(Dialog dialog, int color) {
        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        if (dividerId != 0) {
            int titleDividerId = dialog.getContext().getResources().getIdentifier("titleDivider", "id", "android");
            View titleDivider = dialog.getWindow().getDecorView().findViewById(titleDividerId);
            View divider = dialog.findViewById(dividerId);
            divider.setBackgroundColor(color);
        }
    }

    private void setDividerColor(NumberPicker picker, Drawable customDrawable) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(picker, customDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
