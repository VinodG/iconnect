package com.winit.iKonnect.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.StringUtils;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.AttachmentsDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.ServiceResponseData;
import com.winit.iKonnect.module.form.FormPresenter;
import com.winit.iKonnect.module.form.IFormPresenter;
import com.winit.iKonnect.module.form.IFormView;
import com.winit.iKonnect.ui.customview.CustomRelativeLayout;
import com.winit.iKonnect.ui.dialog.FullImageDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Gufran.Khan on 6/1/2017.
 */

public abstract class FormActivity extends BaseActivity implements IFormView {//Handler


    @Nullable
    @Bind(R.id.flFormBody)
    FrameLayout flFormBody;

    @Nullable
    @Bind(R.id.ll_reson_for_request)
    LinearLayout ll_reson_for_request;

    @Nullable
    @Bind(R.id.ll_leave)
    LinearLayout ll_leave;

    @Nullable
    @Bind(R.id.ll_form)
    LinearLayout ll_form;

    protected TextView tvUser_Name, tvUserDesignation, tvUser_Id,
            tvMobileNo, tvEmailId, tv_functional_area, tvReligion, tv_Nationality, tvPosition, tv_date_of_joining, tv_band, tv_leave_balance;
    protected Button btnSubmit;
    protected EditText etReason;
    protected TextView tvReasonRequest;
    private CustomRelativeLayout rlAttachments;
    protected IFormPresenter iFormPresenter;
    private CustomRelativeLayout crAttachments;
    protected ImageView img_Info, iv_attachment;
    private CircleImageView ivUserPic;
    private CircleImageView ivImage;
    private TextView tvDocumentDesc;
    private String s;
    private LinearLayout image_cell;
    private ImageView imgview;
    private int pos;
    private boolean update = false;
    private TextView tvAttachmentFileName;
    private boolean isRunning = false;
    int i = 0;
    LinearLayout ll = null;
    ImageView temp = null;
    TextView path1 = null;
    protected int MaxPos = 0;
    private ArrayList<AttachmentsDO> attachmentsDOS;

    @Override
    protected void initialize() {

        Log.i("TTTTTTTTTTTTTTTTTTTTT", "FormActivityc class");
        inflater.inflate(R.layout.form_activity, flBody, true);
        ButterKnife.bind(this);
        initializeControls();

        ServiceDO serviceDO = (ServiceDO) getIntent().getSerializableExtra(ConstantExtraKey.SERVICE_OBJECT);

        iFormPresenter = new FormPresenter(serviceDO, this);
        setStaffDetail();
        initializeForm();

        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaptureImage();
            }
        });
        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaptureImage();
                update = false;
            }
        });
        iFormPresenter.loadData();
        tvFormHeading.setTypeface(AppConstants.MEDIUM);
        tvFormHeading.setText(serviceDO.serviceName);
        ivFormIcon.setImageResource(serviceDO.serviceLogo);
        String profilPic = preference.getStringFromPreference(preference.STAFF_PHOTO_URL, "");
        if (!StringUtils.isEmpty(profilPic))
            IKonnectApplication.setImageUrl(ivUserPic, ServiceUrls.PROFILE_PIC + profilPic, R.drawable.profile_pic);
        ivUserPic.setTag(R.string.isRound, true);
//        if(NetworkUtility.isNetworkConnectionAvailable(FormActivity.this))
//            iFormPresenter.getFormActivationStatus();


        img_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(FormActivity.this, "", "" + getString(R.string.please_attach8), getString(R.string.OK), "", "");
            }
        });


        /***************** If we are coming from Track Service Details ********************************************/

        attachmentsDOS = (ArrayList<AttachmentsDO>)  getIntent().getSerializableExtra(ConstantExtraKey.ATTACHMENTS);
        if (attachmentsDOS!=null && attachmentsDOS.size()>0){
            for (AttachmentsDO attachmentsDO : attachmentsDOS)
                setAttachment("",attachmentsDO);
        }

    }

    public abstract void postData(View view);

    protected abstract void initializeForm();

    @Override
    protected void setTypeFace() {
        tvMobileNo.setTypeface(AppConstants.MEDIUM);
        tvEmailId.setTypeface(AppConstants.MEDIUM);
        tv_functional_area.setTypeface(AppConstants.MEDIUM);
        tvPosition.setTypeface(AppConstants.MEDIUM);
        tv_date_of_joining.setTypeface(AppConstants.MEDIUM);
        tv_band.setTypeface(AppConstants.MEDIUM);
        tv_leave_balance.setTypeface(AppConstants.MEDIUM);


        // tvGrade.setTypeface(AppConstants.MEDIUM);
        //   tvReligion.setTypeface(AppConstants.MEDIUM);
        tv_Nationality.setTypeface(AppConstants.MEDIUM);
        btnSubmit.setTypeface(AppConstants.MEDIUM);
    }

    private void initializeControls() {


        tvAttachmentFileName = (TextView) findViewById(R.id.tvAttachmentFileName);
        tvUser_Name = (TextView) findViewById(R.id.tvUser_Name);
        ivUserPic = (CircleImageView) findViewById(R.id.ivUserPic);
        tvUserDesignation = (TextView) findViewById(R.id.tvUserDesignation);
        tvUser_Id = (TextView) findViewById(R.id.tvUser_Id);
        tvMobileNo = (TextView) findViewById(R.id.tvMobileNo);
        tvEmailId = (TextView) findViewById(R.id.tvEmailId);
        tv_functional_area = (TextView) findViewById(R.id.tv_functional_area);
        //tvReligion = (TextView)findViewById(R.id.tvReligion);
        tv_Nationality = (TextView) findViewById(R.id.tv_Nationality);
        etReason = (EditText) findViewById(R.id.etReason);
        tvReasonRequest = (TextView) findViewById(R.id.tv_reason_request);
        rlAttachments = (CustomRelativeLayout) findViewById(R.id.cr_layout);
        iv_attachment = (ImageView) rlAttachments.findViewById(R.id.iv_attachment);
        img_Info = (ImageView) rlAttachments.findViewById(R.id.img_Info);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        crAttachments = (CustomRelativeLayout) findViewById(R.id.cr_layout);
        img_Info = (ImageView) crAttachments.findViewById(R.id.img_Info);
        iv_attachment = (ImageView) crAttachments.findViewById(R.id.iv_attachment);
        img_Info = (ImageView) crAttachments.findViewById(R.id.img_Info);
        tvPosition = (TextView) findViewById(R.id.tvPosition);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.drawable.place_holder_image);
        int width = bitmapDrawable.getBitmap().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        params.setMargins(10, 10, 10, 10);
        ivUserPic.setLayoutParams(params);

        tv_date_of_joining = (TextView) findViewById(R.id.tv_date_of_joining);
        // tv_grade = (TextView) findViewById(R.id.tv_grade);
        tv_band = (TextView) findViewById(R.id.tv_band);
        tv_leave_balance = (TextView) findViewById(R.id.tv_leave_balance);

    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        if (serviceRequestDO != null && message.equalsIgnoreCase("Service request placed successfully.")) {

            switch (serviceRequestDO.serviceType) {

                case BUSINESS_TRAVEL_REQUEST:
                    message = getResources().getString(R.string.Business_Travel_Request_submit_message);
                    break;

                case LEAVE_APPLICATION:
                    message = getResources().getString(R.string.Leave_Application_submit_message);
                    break;

                case LEAVE_JOINING:
                    message = getResources().getString(R.string.Leave_Joining_submit_message);
                    break;

                case TRANSPORT_LOAN_REQUEST:
                    message = getResources().getString(R.string.Transport_Loan_Request_submit_message);
                    break;
                case SYSTEM_ACCESS_REQUEST:
                    message = getResources().getString(R.string.System_Access_Request_submit_message);
                    break;
                case COMMITMENT_FORM:
                    message = getResources().getString(R.string.Commitment_Form_submit_message);
                    break;

                case SALARY_TRANSFER_LETTER_REQUEST:
                    message = getResources().getString(R.string.Salary_Transfer_Letter_Request_submit_message);
                    break;
                case SALARY_CERTIFICATE_REQUEST:
                    message = getResources().getString(R.string.Salary_Certificate_Request_submit_message);
                    break;
                case HOUSING_ALLOWANCE:
                    message = getResources().getString(R.string.Housing_Allowance_submit_message);
                    break;
                case PASSPORT_RELEASE_REQUEST:
                    message = getResources().getString(R.string.Passport_Release_Request_submit_message);
                    break;


                case VISIT_VISA_FOR_IN_LAWS_CHILDREN:
                case VISIT_VISA_FOR_SPOUSE_CHILDREN:
                case FAMILY_JOINING_VISA_FOR_PARENTS:
                case FAMILY_JOINING_VISA_FOR_SPOUSE_CHILDREN:
                    message = getResources().getString(R.string.visit_and_family_visa_submit_message);
                    break;


                default:
                    break;
            }

        }
        if (serviceRequestDO != null) {
            showFormSubmitPopup(serviceRequestDO.serviceName, message);
        } else {

        }

    }

    @Override
    protected void setAttachment(String path, final AttachmentsDO attachmentsDO) {
        if (MaxPos > 4 && !update)
            Toast.makeText(FormActivity.this, "" + getString(R.string.max), Toast.LENGTH_SHORT).show();
        else {
            iFormPresenter.setAttachment(path);
            tvAttachmentFileName.setVisibility(View.GONE);

            try {
                Bitmap bitmap = null;
                if (attachmentsDO==null) {

                    File image = new File(path);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inSampleSize = 10;

                    if (path.contains(".txt") || path.contains(".xml") || path.contains(".pdf") || path.contains(".docx") || path.contains(".xlsx") || path.contains(".doc")) {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.doc);
//                bitmap = Bitmap.createScaledBitmap(bitmap,60,60,true);

                    } else if (path.contains(".mp4") || path.contains(".mpeg") || path.contains(".mkv")) {
//                bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.doc);
                        Toast.makeText(FormActivity.this, "Unsupported Type", Toast.LENGTH_SHORT).show();
//                bitmap = Bitmap.createScaledBitmap(bitmap,60,60,true);
                    } else {

                        bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
//                  bitmap = Bitmap.createScaledBitmap(bitmap,60,60,true);
                        Matrix matrix = new Matrix();
//                    matrix.postRotate(90);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    }
                }
                image_cell = (LinearLayout) inflater.inflate(R.layout.image_cell, null);
                ivImage = (CircleImageView) image_cell.findViewById(R.id.ivImage);
                tvDocumentDesc = (TextView) image_cell.findViewById(R.id.tvDocumentDesc);


                tvDocumentDesc.setTag(R.string.ac1, path);
                tvDocumentDesc.setTag(R.string.ac2, tvDocumentDesc);
                image_cell.setTag(R.string.key_get_data, path);
                image_cell.setTag(R.string.like, image_cell);
                image_cell.setTag(R.string.barnch, tvDocumentDesc);
                if (update) {
                    image_cell.setTag(R.string.play_again, pos);
                } else {
                    image_cell.setTag(R.string.play_again, i);
                }

                if (attachmentsDO==null) {
                    ivImage.setImageBitmap(bitmap);
                    tvDocumentDesc.setText("" + path.substring(path.lastIndexOf("/") + 1));
                }else {
                    tvDocumentDesc.setText(attachmentsDO.FileName);
                    IKonnectApplication.setImageUrl(ivImage, ServiceUrls.ATTACHMENTS_HISTORY_URL+attachmentsDO.FilePath, R.drawable.profile_pic, true);
                }

                image_cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (attachmentsDO!=null) {
                            FullImageDialog fullImageDialog = new FullImageDialog(FormActivity.this, ServiceUrls.ATTACHMENTS_HISTORY_URL + attachmentsDO.FilePath);
                            fullImageDialog.show();
                        }else {
                            s = (String) view.getTag(R.string.key_get_data);
                            ll = (LinearLayout) view.getTag(R.string.like);
                            temp = (ImageView) ll.findViewById(R.id.ivImage);
                            path1 = (TextView) view.getTag(R.string.barnch);
                            pos = (int) view.getTag(R.string.play_again);
                            showCustomDialog(FormActivity.this, "", getString(R.string.deleteUpdateMsg), getString(R.string.Delete), "" + getString(R.string.update), "delupdate", true);
                        }
                    }
                });
                if (update) {
                    temp.setImageBitmap(bitmap);
                    path1.setText(path.substring(path.lastIndexOf("/") + 1));
                    update = false;
                    pos = 0;
                } else {
                    crAttachments.llDocuments.addView(image_cell);
                    MaxPos++;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }

    @Override
    public void onButtonYesClick(String from) {
        if (from.equalsIgnoreCase(IFormView.SUCCESS)) {
            finish();
        } else if (from.equalsIgnoreCase("delupdate")) {
            iFormPresenter.removeAttachment(s);
            ((ViewGroup) ll.getParent()).removeView(ll);
            update = false;
            if (crAttachments.llDocuments.getChildCount() == 0)
                tvAttachmentFileName.setVisibility(View.VISIBLE);
            MaxPos--;
        }
    }

    @Override
    public void onButtonNoClick(String from) { // update
        if (from.equalsIgnoreCase("delupdate")) {
            iFormPresenter.removeAttachment(s);
            update = true;
            CaptureImage();
        }
    }

    public void setStaffDetail() {

        tvUser_Name.setText("" + preference.getStringFromPreference(Preference.STAFF_NAME, "N/A"));
        tvUserDesignation.setText("" + preference.getStringFromPreference(Preference.STAFF_POSITION, "N/A"));
        tvUser_Id.setText("[" + preference.getStringFromPreference(Preference.STAFF_NUMBER, "N/A") + "]");
        tvMobileNo.setText("" + preference.getStringFromPreference(preference.STAFF_MOBILE, "N/A"));
        tvEmailId.setText("" + preference.getStringFromPreference(preference.STAFF_EMAIL, "N/A"));
        tv_functional_area.setText("" + preference.getStringFromPreference(preference.FUNCTIONAL_AREA, "N/A"));
        tvPosition.setText("" + preference.getStringFromPreference(preference.STAFF_POSITION, "N/A"));
        tv_Nationality.setText("" + preference.getStringFromPreference(preference.STAFF_NATIONALITY, "N/A"));
        tv_date_of_joining.setText("" + preference.getStringFromPreference(preference.HIRE_DATE, "N/A"));
        // tv_grade.setText("" + preference.getStringFromPreference(preference.FUNCTIONAL_AREA, "N/A") + ",");
        tv_band.setText("" + preference.getStringFromPreference(preference.BAND, "N/A"));
        tv_leave_balance.setText("" + preference.getIntFromPreference(preference.LEAVE_BALANCE, 0));
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void showLoder(String msg) {

    }

    @Override
    public void hideLoder() {

    }

    @Override
    public void gotFormData(HashMap<String, ServiceResponseData> hmFormDataDetail) {
//        iFormPresenter.loadData(hmFormDataDetail);
    }

}
