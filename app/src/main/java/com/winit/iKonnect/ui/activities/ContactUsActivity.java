package com.winit.iKonnect.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.util.NetworkUtility;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.BusinessCitiesDO;
import com.winit.iKonnect.dataobject.ContactListDO;
import com.winit.iKonnect.dataobject.ContactsListDOArrayList;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.dataobject.SubTopicModelDO;
import com.winit.iKonnect.module.ContactUs.ContactUsPresenter;
import com.winit.iKonnect.module.ContactUs.IContactUsView;
import com.winit.iKonnect.module.ContactUs.IContactusPresenter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by Ashoka.Reddy on 5/26/2017.
 */

public class ContactUsActivity extends BaseActivity implements IContactUsView {


    private IContactusPresenter iContactusPresenter;
    private EditText et_messege;
    private Button btn_submit;
    private String to = "aaa@aaa.com";
    private String from = "from@from.com";
    private IContactusPresenter icontactuspresenter;
    private TextView tv_subject, textView2,tvTopic;
    private TextView tvUser_Name, tvUserDesignation, tvUser_Id,
            tvMobileNo, tvEmailId, tvGrade, tvReligion, tv_Nationality, tvFirstNo, tvSecNo;
    private ImageView ivUserPic;
    private TextView tvheader;
    private LinearLayout llcall, llmail;
    private HashMap<String, ArrayList<SubTopicModelDO>> contactUsListMap;
    private ArrayList<String> topicName;
    private ArrayList<String> subHeadignNames;

    private Spinner mainHeading, subHeading;
    private ArrayAdapter<String> subHeadingAdapter;
    ArrayAdapter<String> mainHeaderAdapter;
    private ArrayList<SubTopicModelDO> subTopicModelDOArrayList;

    private ImageView ivArrow;

    //Newly added
    TextView tv_selectTopName, tv_sub_header, tv_subTopic_label;
    private boolean isSubHeaderAvilable;


    @Override
    protected void initialize() {
        inflater.inflate(R.layout.contactus, flBody, true);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iContactusPresenter = new ContactUsPresenter(this);
        setToolbarTitle("" + getString(R.string.Contact_Us));
        initializeControls();
        setStaffDetail();

        String profilPic = preference.getStringFromPreference(preference.STAFF_PHOTO_URL, "");
        if (!StringUtils.isEmpty(profilPic))
            IKonnectApplication.setImageUrl(ivUserPic, ServiceUrls.PROFILE_PIC + profilPic, R.drawable.profile_pic);
        ivUserPic.setTag(R.string.isRound, true);
        llmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"HRemployee.relations@alseer.com"});
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
        tvFirstNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+966(11)5287902"));
                startActivity(intent);

            }
        });
        tvSecNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+966(11)5287901"));
                startActivity(intent);

            }
        });

        mainHeading.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Item Selected ", "Item seected name");
                String key = mainHeading.getSelectedItem().toString();
                subTopicModelDOArrayList = contactUsListMap.get(key);

                for (int i = 0; i < subTopicModelDOArrayList.size(); i++) {
                    subHeadignNames.add(subTopicModelDOArrayList.get(i).SubTopicName);
                }
                subHeadingAdapter.notifyDataSetChanged();


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tv_selectTopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerShow(tv_selectTopName);
            }
        });


        tv_sub_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerSubShow(tv_sub_header);
            }
        });


        showLoder(getResources().

                getString(R.string.pleaseWait));
        iContactusPresenter.fetchAllContactList();


    }

    private void setStaffDetail() {
        tvUser_Name.setText("" + preference.getStringFromPreference(Preference.STAFF_NAME, "N/A"));
        tvUserDesignation.setText("" + preference.getStringFromPreference(Preference.STAFF_POSITION, "N/A"));
        tvUser_Id.setText("[" + preference.getStringFromPreference(Preference.STAFF_NUMBER, "N/A") + "]");
        tvMobileNo.setText("" + preference.getStringFromPreference(preference.STAFF_MOBILE, "N/A") + ",");
        tvEmailId.setText("" + preference.getStringFromPreference(preference.STAFF_EMAIL, "N/A") + ",");
        tvGrade.setText("" + preference.getStringFromPreference(preference.STAFF_GRADE, "N/A") + ",");
        //tvReligion.setText("" + preference.getStringFromPreference(preference.STAFF_RELIGION, "N/A") + ",");
        tv_Nationality.setText("" + preference.getStringFromPreference(preference.STAFF_NATIONALITY, "N/A"));
    }

    private void initializeControls() {

        et_messege = (EditText) findViewById(R.id.et_messege);
        tvheader = (TextView) findViewById(R.id.tvheader);
        textView2 = (TextView) findViewById(R.id.textView2);
        tvTopic = (TextView) findViewById(R.id.tvTopic);
        ivUserPic = (ImageView) findViewById(R.id.ivUserPic);
        tvUser_Name = (TextView) findViewById(R.id.tvUser_Name);
        tvUserDesignation = (TextView) findViewById(R.id.tvUserDesignation);
        tvUser_Id = (TextView) findViewById(R.id.tvUser_Id);
        tvMobileNo = (TextView) findViewById(R.id.tvMobileNo);
        tvEmailId = (TextView) findViewById(R.id.tvEmailId);
        tvGrade = (TextView) findViewById(R.id.tvGrade);
        tvReligion = (TextView) findViewById(R.id.tvReligion);
        tvFirstNo = (TextView) findViewById(R.id.tvFirstNo);
        tvSecNo = (TextView) findViewById(R.id.tvSecNo);

        tv_Nationality = (TextView) findViewById(R.id.tv_Nationality);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.drawable.place_holder_image);
        llcall = (LinearLayout) findViewById(R.id.llcall);
        llmail = (LinearLayout) findViewById(R.id.llmail);
        mainHeading = (Spinner) findViewById(R.id.sp_header_name);
        subHeading = (Spinner) findViewById(R.id.sp_sub_header);


        contactUsListMap = new HashMap<>();
        topicName = new ArrayList<>();
        subHeadignNames = new ArrayList<>();
        subHeadingAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subHeadignNames);

        mainHeaderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, topicName);


        mainHeading.setAdapter(mainHeaderAdapter);
        subHeading.setAdapter(subHeadingAdapter);


        tv_selectTopName = (TextView) findViewById(R.id.tv_selectTopName);
        tv_sub_header = (TextView) findViewById(R.id.tv_sub_header);
        tv_subTopic_label = (TextView) findViewById(R.id.tv_subTopic_label);
        ivArrow     =(ImageView)findViewById(R.id.ivArrow);
    }

    @Override
    protected void setTypeFace() {
        IKonnectApplication.setTypeFace(et_messege, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(textView2, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvTopic, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tv_subTopic_label, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvheader, AppConstants.MEDIUM);
    }

    @Override
    public void showAlert(String type) {

        String message = "";
        switch (type) {
            case ENTER_SUBJECT:
                message = getString(R.string.Please_enterSubject);
                showCustomDialog(ContactUsActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
                break;
            case ENTER_MESSEGE:
                message = "Please enter Remarks.";
                showCustomDialog(ContactUsActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
                break;
            case EMPTY_TOPIC:
                message = "Please select Topic";
                showCustomDialog(ContactUsActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
                break;
            case EMPTY_SUB_TOPIC:
                message = "Please select Sub Topic";
                showCustomDialog(ContactUsActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
                break;

            case AppConstants.Logout:
                showCustomDialog(ContactUsActivity.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
                break;
            default:
//                message = type;
                message = "Thank you We will get back to you soon.";
                showCustomDialog(ContactUsActivity.this, getString(R.string.success), message, getString(R.string.OK), "", "finish");
                break;
        }
        hideLoader();
    }

    @Override
    public void onButtonYesClick(String from) {
        if (from.equalsIgnoreCase("finish")) {
            finish();
        } else
            super.onButtonYesClick(from);
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {

    }

    @Override
    public void gotFormData(HashMap<String, ServiceResponseData> hmFormDataDetail) {

    }

    @Override
    public void showLoder(String msg) {

    }

    @Override
    public void hideLoder() {

    }

    public void submitContactUsData(View v) {
        if (NetworkUtility.isNetworkConnectionAvailable(ContactUsActivity.this)) {
            showLoader("");
            // iContactusPresenter.submitForm(et_sub.getText().toString(), et_messege.getText().toString(), to, from);

            iContactusPresenter.submitForm(et_messege.getText().toString(),
                    tv_selectTopName.getText().toString(),
                    tv_sub_header.getText().toString(),
                    preference.getStringFromPreference(Preference.STAFF_ID, "N/A"),
                    isSubHeaderAvilable);

        } else
            showCustomDialog(ContactUsActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);
    }


    //get data from service for contact Name as well as services

    @Override
    public void getData(ContactsListDOArrayList contactListDO) {

        if (contactListDO!=null) {
            ArrayList<ContactListDO> contactListDOS = contactListDO.getTopicModels();

            for (int i = 0; i < contactListDOS.size(); i++) {
                if (contactUsListMap.containsKey(contactListDOS.get(i).getTopicName())) {
                    contactUsListMap.put(contactListDOS.get(i).getTopicName(), contactListDOS.get(i).getSubTopicModel());
                } else {
                    contactUsListMap.put(contactListDOS.get(i).getTopicName(), contactListDOS.get(i).getSubTopicModel());
                    topicName.add(contactListDOS.get(i).getTopicName());
                }
            }
        }
    }
    public void spinnerShow(final View view) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Topics");
        builder.setItems(topicName.toArray(new String[topicName.size()]), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int position) {
                //here you can use like this... str[position]
                tv_selectTopName.setText(topicName.get(position) + "");
                subHeadignNames.clear();
                // BusinessCitiesDO cityDetailsDO = businessCitiesDOVector.get(position);


                String key = tv_selectTopName.getText().toString().trim();
                subTopicModelDOArrayList = contactUsListMap.get(key);

                for (int i = 0; i < subTopicModelDOArrayList.size(); i++) {
                    subHeadignNames.add(subTopicModelDOArrayList.get(i).SubTopicName);
                }

                if (subTopicModelDOArrayList.size() > 0) {
                    tv_subTopic_label.setVisibility(View.VISIBLE);
                    tv_sub_header.setVisibility(View.VISIBLE);
                    ivArrow.setVisibility(View.VISIBLE);
                    isSubHeaderAvilable = true;

                } else {
                    tv_subTopic_label.setVisibility(View.GONE);
                    tv_sub_header.setVisibility(View.GONE);
                    ivArrow.setVisibility(View.GONE);
                    isSubHeaderAvilable = false;
                    tv_sub_header.setText("");
                    tv_sub_header.setHint("Please Select the Sub Topic.");
                }


            }

        });
        dialog = builder.create();
        dialog.show();

    }

    public void spinnerSubShow(final View view) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SubTopics");
        builder.setItems(subHeadignNames.toArray(new String[subHeadignNames.size()]), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int position) {
                //here you can use like this... str[position]

                if (position < 0) {
                    tv_sub_header.setText("No data found");
                } else {
                    tv_sub_header.setText(subHeadignNames.get(position) + "");
                }

                // BusinessCitiesDO cityDetailsDO = businessCitiesDOVector.get(position);

            }

        });
        dialog = builder.create();
        dialog.show();

    }
}
