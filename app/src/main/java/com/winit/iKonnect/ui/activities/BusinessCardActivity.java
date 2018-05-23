package com.winit.iKonnect.ui.activities;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.BusinessCardDo;
import com.winit.iKonnect.dataobject.BusinessCitiesDO;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ojha.Sandeep on 10/20/2017.
 */

public class BusinessCardActivity extends FormActivity {

    @Nullable
    @Bind(R.id.tv_city)
    TextView tv_city;

    @Nullable
    @Bind(R.id.et_po_box)
    EditText et_po_box;


    @Nullable
    @Bind(R.id.et_ext)
    EditText et_ext;


    @Nullable
    @Bind(R.id.et_tel_no)
    EditText et_tel_no;


    @Nullable
    @Bind(R.id.et_mobile_cug)
    EditText et_mobile_cug;

    @Nullable
    @Bind(R.id.et_fax_no)
    EditText et_fax_no;


    @Nullable
    @Bind(R.id.et_email)
    EditText et_email;


    @Nullable
    @Bind(R.id.et_website)
    EditText et_website;


    private Vector<BusinessCitiesDO> businessCitiesDOVector;
    private BusinessCitiesDO businessCitiesDO;


    private BusinessCardDo businessCardDo;


    @Override
    protected void initializeForm() {

        businessCardDo = (BusinessCardDo) getIntent().getSerializableExtra(this.getResources().getString(R.string.Business_Card_Request));

        inflater.inflate(R.layout.business_card, flFormBody, true);
        ButterKnife.bind(this);
        tv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerShow(tv_city);
            }
        });
        ll_reson_for_request.setVisibility(View.GONE);
        businessCitiesDOVector = new Vector<>();
        loadCity();
        if (businessCardDo != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    public void showAllContent() {

        tv_city.setText(businessCardDo.getCity());
        et_po_box.setText(businessCardDo.getPOBox());
        et_tel_no.setText(businessCardDo.getTelNo());
        et_mobile_cug.setText(businessCardDo.getMobile());
        et_fax_no.setText(businessCardDo.getFax());
        et_email.setText(businessCardDo.getEmail());
        et_website.setText(businessCardDo.getWebsite());
        et_ext.setText(businessCardDo.getExt());
    }


    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {

        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.submitBussinessCard(
                etReason.getText().toString(),
                tv_city.getText().toString(),
                et_po_box.getText().toString(),
                et_tel_no.getText().toString(),
                et_ext.getText().toString(),
                et_fax_no.getText().toString(),
                et_mobile_cug.getText().toString(),
                et_email.getText().toString(),
                et_website.getText().toString()

        );
    }

    @Override
    public void showAlert(String type) {
        hideLoader();
        String message = "";
        switch (type) {
            case EMPTY_REASON:
                message = getString(R.string.PleaseEnterReason);
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

            case EMPTY_CITY:
                message = " Please select the City";
                break;

            case EMPTY_PO_BOX:
                message = "Please enter post box number";
                break;

            case EMPTY_TELNO:
                message = "Please enter the Tel No";
                break;

            case EMPTY_FAX:
                message = "Please enter the fax";
                break;

            case EMPTY_MOBILE:
//                message = "Please enter contact number during Business Travel";
                message = "Please enter Mobile CUG Number";
                break;

            case EMPTY_EMAIL:
                message = "Please enter your email";
                break;

            case EMPTY_WEBSITE:
                message = "Please enter the website id";
                break;

            case VALID_EMAIL:
                message = "Please enter valid email";
                break;

            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(BusinessCardActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(BusinessCardActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");

    }


    public void spinnerShow(final View view) {
        AlertDialog dialog;
        final CharSequence str[];
        if (preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "").equalsIgnoreCase("Oman")) {
            str = new CharSequence[]{"Barka", "Ruwi", "Salalah", "Sohar", "Nizwa", "Al Kamil","Tharmed"};
        } else {
            str = new CharSequence[]{"Abu Dhabi (A&T)", "Abu Dhabi (Other Divisions)", "Al Ain", "Dubai", "Ras Al Khaimah", "Khor Fakkan"};
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select City");
        builder.setItems(str, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int position) {
                //here you can use like this... str[position]
                tv_city.setText(str[position] + "");
                BusinessCitiesDO cityDetailsDO = businessCitiesDOVector.get(position);
                et_po_box.setText(cityDetailsDO.getPOBox());
                et_tel_no.setText(cityDetailsDO.getTelNo());
                et_fax_no.setText(cityDetailsDO.getFax());


            }

        });
        dialog = builder.create();
        dialog.show();

    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();

        message = "  Submitted for Approval to Reporting Manager";
        showFormSubmitPopup("Business Card Request", message);
    }


    public void loadCity() {

        if (preference.getStringFromPreference(Preference.STAFF_NATIONALITY, "").equalsIgnoreCase("omani")) {
            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Barka");
            businessCitiesDO.setPOBox("1936, PC: 130, Al Harm- Barka");
            businessCitiesDO.setTelNo("+ 968 268929");
            businessCitiesDO.setFax("+ 968 26892934");
            businessCitiesDOVector.add(businessCitiesDO);


            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Ruwi");
            businessCitiesDO.setPOBox("1936, PC:130, Al Harm - Barka");
            businessCitiesDO.setTelNo("+968 24707487");
            businessCitiesDO.setFax("+968 24703893");
            businessCitiesDOVector.add(businessCitiesDO);


            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Salalah");
            businessCitiesDO.setPOBox("1936, PC:130, Al Harm - Barka");
            businessCitiesDO.setTelNo("+968 23211097,23211087");
            businessCitiesDO.setFax("+968 23211553");
            businessCitiesDOVector.add(businessCitiesDO);


            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Sohar");
            businessCitiesDO.setPOBox("1936, PC:130, Al Harm - Barka");
            businessCitiesDO.setTelNo("+968 26846317");
            businessCitiesDO.setFax("+968 26846629 (Sales)");
            businessCitiesDOVector.add(businessCitiesDO);


            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Nizwa");
            businessCitiesDO.setPOBox("1936, PC: 130, Al Harm- Barka");
            businessCitiesDO.setTelNo("+ 968 25449838");
            businessCitiesDO.setFax("+ 968 25449201");
            businessCitiesDOVector.add(businessCitiesDO);

            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Al Kamil");
            businessCitiesDO.setPOBox("1936, PC: 130, Al Harm- Barka");
            businessCitiesDO.setTelNo("+ 968 25558286");
            businessCitiesDO.setFax("+968 25557339");
            businessCitiesDOVector.add(businessCitiesDO);

            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Tharmed");
            businessCitiesDO.setPOBox("1936, PC: 130, Al Harm- Barka");
            businessCitiesDO.setTelNo("+ 968 26815091");
            businessCitiesDO.setFax("+ 968 26815091");
            businessCitiesDOVector.add(businessCitiesDO);


        } else {
            //1
            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Abu Dhabi (A&T)");
            businessCitiesDO.setPOBox("27599, Abu Dhabi, U.A.E");
            businessCitiesDO.setTelNo("+971 2 6948300");
            businessCitiesDO.setFax("+971 2 6732313");
            businessCitiesDOVector.add(businessCitiesDO);

            //2

            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Abu Dhabi (Other Divisions)");
            businessCitiesDO.setPOBox("106573, Abu Dhabi, U.A.E");
            businessCitiesDO.setTelNo("+971 2 6948300");
            businessCitiesDO.setFax("+971 2 6732313");
            businessCitiesDOVector.add(businessCitiesDO);

            //3
            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Al Ain");
            businessCitiesDO.setPOBox("69711, Al Ain, U.A.E");
            businessCitiesDO.setTelNo("+971 3 7632941");
            businessCitiesDO.setFax("+971 3 7633208");
            businessCitiesDOVector.add(businessCitiesDO);

            //4
            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Dubai");
            businessCitiesDO.setPOBox("31587, Dubai, U.A.E");
            businessCitiesDO.setTelNo("+971 4 3725329");
            businessCitiesDO.setFax("+971 4 3473549");
            businessCitiesDOVector.add(businessCitiesDO);

            //5
            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Ras Al Khaimah");
            businessCitiesDO.setPOBox("115, Ras Al Khaimah, U.A.E");
            businessCitiesDO.setTelNo("+971 7 2021502");
            businessCitiesDO.setFax("+971 7 2589457");
            businessCitiesDOVector.add(businessCitiesDO);

            //6
            businessCitiesDO = new BusinessCitiesDO();
            businessCitiesDO.setEmirate("Khor Fakkan");
            businessCitiesDO.setPOBox("10139, Khor Fakkan, U.A.E");
            businessCitiesDO.setTelNo("+971 9 2386084/+971 9 2386463");
            businessCitiesDO.setFax("+971 9 2387793");
            businessCitiesDOVector.add(businessCitiesDO);
        }
    }
}
