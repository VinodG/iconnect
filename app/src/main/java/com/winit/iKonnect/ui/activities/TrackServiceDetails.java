package com.winit.iKonnect.ui.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.CalendarUtil;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.TrackServiceDetailsAdapter;
import com.winit.iKonnect.dataobject.NotificationDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.ServiceHistoryDO;
import com.winit.iKonnect.dataobject.ServiceRequestListDO;
import com.winit.iKonnect.dataobject.ServiceRequestDO;
import com.winit.iKonnect.dataobject.ServicerequestassignmentModelsDO;
import com.winit.iKonnect.module.serviceHistory.IServiceHistoryPresenter;
import com.winit.iKonnect.module.serviceHistory.IServiceHistoryView;
import com.winit.iKonnect.module.serviceHistory.ServiceRequestHistroyPresenter;
import com.winit.iKonnect.module.trackService.ITrackServicePresenter;
import com.winit.iKonnect.module.trackService.ITrackServiceView;
import com.winit.iKonnect.module.trackService.TrackServicePresenter;
import com.winit.iKonnect.parser.NotificationParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.winit.iKonnect.R.id.viewpager_trackService;

public class TrackServiceDetails extends BaseActivity implements IServiceHistoryView,ITrackServiceView {

    @Nullable
    @Bind(R.id.rvTackDetails)
    RecyclerView rvTackDetails;
    @Nullable
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String FORM_NAME_TAG = "FORM_NAME_TAG";

    private IServiceHistoryPresenter iServiceHistoryPresenter;

    private ServiceRequestDO serviceRequestDO;
    private TrackServiceDetailsAdapter trackServicesAdapter;
    private ServicerequestassignmentModelsDO modelsDO;

    public ITrackServicePresenter iTrackServicePresenter;
    private boolean isfromServiceReq=false;
    private boolean isfromNotification=false;
    private int serviceId=-1;
    private NotificationDO notificationDO;

    @Override
    protected void initialize() {

        inflater.inflate(R.layout.track_service_request, flBody, true);
        ButterKnife.bind(this);
        setToolbarTitle("Track Service Request");
        ivHistory.setVisibility(View.VISIBLE);

        iServiceHistoryPresenter = new ServiceRequestHistroyPresenter(this);

        iTrackServicePresenter = new TrackServicePresenter(this);

        /************ when we are directly coming from service requests ******************************/
        if (getIntent().hasExtra("FORMS")) {
            showLoader("Loading...");
            isfromServiceReq=true;
            iTrackServicePresenter.fetchTrackServices();

            serviceId=AppConstants.SERVICE_ID;
        }
        //***********************************************************************************************

        if (getIntent().hasExtra("FROM_TRACK")) {
            serviceRequestDO = (ServiceRequestDO) getIntent().getSerializableExtra("FROM_TRACK");
        }

        ///**************************Directly we are coming from notification****************************

        if (getIntent().hasExtra("message")) {
            String message = getIntent().getExtras().getString("message");
            NotificationParser notificationParser = new NotificationParser();
            notificationParser.parse(new StringBuilder(message));
            notificationDO = (NotificationDO) notificationParser.getData();
        } else if (getIntent().hasExtra(ConstantExtraKey.NOTIFICATION_OBJECT)) {
            notificationDO = (NotificationDO) getIntent().getExtras().get(ConstantExtraKey.NOTIFICATION_OBJECT);
        }

        if (notificationDO != null) {
            isfromNotification=true;
            showLoader("Loading...");
            serviceId=notificationDO.getId();
            iTrackServicePresenter.fetchTrackServices();
        }
        //***********************************************************************************************


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(TrackServiceDetails.this, 1);
        rvTackDetails.setLayoutManager(mLayoutManager);
        rvTackDetails.setItemAnimator(new DefaultItemAnimator());

        if (serviceRequestDO != null) {

            bindControls(serviceRequestDO);
            rvTackDetails.setAdapter(trackServicesAdapter = new TrackServiceDetailsAdapter(TrackServiceDetails.this, serviceRequestDO.getServicerequestassignmentModels(), serviceRequestDO));
        }
        else {
            rvTackDetails.setAdapter(trackServicesAdapter = new TrackServiceDetailsAdapter(TrackServiceDetails.this, new ArrayList<ServicerequestassignmentModelsDO>(), new ServiceRequestDO()));
        }

        ivHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoader("Loading...");
                moveToActivity(serviceRequestDO.getId());
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isfromServiceReq=false;
                isfromNotification=false;
                iTrackServicePresenter.fetchTrackServices();
            }
        });
    }


    private void moveToActivity(int id) {
        iServiceHistoryPresenter.submitFormId(id);
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void getServiceHistoryData(ServiceHistoryDO serviceHistoryDO) {

        hideLoader();
        Intent intent = null;
        ServiceDO serviceDO = null;
        if (serviceHistoryDO != null) {

            //ServiceDo binds Here
            serviceDO = new ServiceDO();
            serviceDO.serviceName = ServiceDO.getFormName(serviceHistoryDO.getService_Request().getServicerequest().getFormid());
            serviceDO.serviceLogo = getMainFormImages(serviceHistoryDO.getService_Request().getServicerequest().getFormid());


            switch (serviceHistoryDO.getService_Request().getServicerequest().getFormid()) {
                case 16:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Leave_Application));
                    serviceDO.serviceType = ServiceDO.ServiceType.LEAVE_APPLICATION;
                    intent = new Intent(TrackServiceDetails.this, LeaveApplicationActivtiy.class);
                    intent.putExtra(this.getResources().getString(R.string.Leave_Application), serviceHistoryDO.getService_Request().getAnnualLeave());
                    break;

                case 18:

                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Salary_Transfer_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, SalaryTransferRequestBankAccount.class);
                    intent.putExtra(this.getResources().getString(R.string.Salary_Transfer_Request), serviceHistoryDO.getService_Request().getSalaryTransferRequest());

                    break;

                case 19:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.C3_Card_ATM_witch_Claim_Service_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_ATM_WITCH_CLAIM_SERVICE_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, AtmSwitchClaimActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.C3_Card_ATM_witch_Claim_Service_Request), serviceHistoryDO.getService_Request().getC3CardATMSwitchClaim());
                    break;

                case 20:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.C3_Card_Replacement_Service_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_REPLACEMENT_SERVICE_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, CardReplacementActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.C3_Card_Replacement_Service_Request), serviceHistoryDO.getService_Request().getC3CardReplacement());
                    break;

                case 21:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.C3_Card_Statement_Service_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_STATEMENT_SERVICE_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, CardStatementActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.C3_Card_Statement_Service_Request), serviceHistoryDO.getService_Request().getC3CardStatement());
                    break;
                case 22:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.C3_Card_Cancellation_Service_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.C3_CARD_CANCELLATION_SERVICE_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, CardCancellationActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.C3_Card_Cancellation_Service_Request), serviceHistoryDO.getService_Request().getC3CardCancelation());
                    break;

                case 23:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Business_Card_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.BUSINESS_CARD_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, BusinessCardActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.Business_Card_Request), serviceHistoryDO.getService_Request().getBusinessCard());
                    break;


                case 24:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Business_Travel_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.BUSINESS_TRAVEL_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, BusinessTravelAdviceActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.Business_Travel_Request), serviceHistoryDO.getService_Request().getBusinessTravel());
                    break;
                case 25:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Commitment_Form));
                    serviceDO.serviceType = ServiceDO.ServiceType.COMMITMENT_FORM;
                    intent = new Intent(TrackServiceDetails.this, CommitmentFormActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.Commitment_Form), serviceHistoryDO.getService_Request().getCommitmentForm());
                    break;
                case 26:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Housing_Allowance));
                    serviceDO.serviceType = ServiceDO.ServiceType.HOUSING_ALLOWANCE;
                    intent = new Intent(TrackServiceDetails.this, HouseAllowanceActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.Housing_Allowance), serviceHistoryDO.getService_Request().getHousingAllowance());
                    break;

                case 27:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Leave_Joining));
                    serviceDO.serviceType = ServiceDO.ServiceType.LEAVE_JOINING;
                    intent = new Intent(TrackServiceDetails.this, LeaveJoining.class);
                    intent.putExtra(this.getResources().getString(R.string.Leave_Joining), serviceHistoryDO.getService_Request().getLeaveJoining());
                    break;
                case 28:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Passport_Release_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.PASSPORT_RELEASE_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, PassportReleaseActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.Passport_Release_Request), serviceHistoryDO.getService_Request().getPassportRelease());
                    break;
                case 29:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Salary_Certificate_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.SALARY_CERTIFICATE_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, SalaryCertificateRequestForm.class);
                    intent.putExtra(this.getResources().getString(R.string.Salary_Certificate_Request), serviceHistoryDO.getService_Request().getSalaryCertificateRequest());
                    break;
                case 30:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Salary_Transfer_Letter_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.SALARY_TRANSFER_LETTER_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, SalaryTransferLetterRequest.class);
                    intent.putExtra(this.getResources().getString(R.string.Salary_Transfer_Letter_Request), serviceHistoryDO.getService_Request().getSalaryTransferLetterRequest());
                    break;

                case 31:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.System_Access_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.SYSTEM_ACCESS_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, SystemAccessRequestActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.System_Access_Request), serviceHistoryDO.getService_Request().getSystemaccess());
                    break;
                case 32:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Transport_Loan_Request));
                    serviceDO.serviceType = ServiceDO.ServiceType.TRANSPORT_LOAN_REQUEST;
                    intent = new Intent(TrackServiceDetails.this, TransportLoanRequestActivity.class);
                    intent.putExtra(this.getResources().getString(R.string.Transport_Loan_Request), serviceHistoryDO.getService_Request().getTransportLoan());
                    break;
                case 33:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Visit_Visa_for_Spouse_Children));
                    serviceDO.serviceType = ServiceDO.ServiceType.VISIT_VISA_FOR_SPOUSE_CHILDREN;
                    intent = new Intent(TrackServiceDetails.this, VisitVisaForSpouseChildren.class);
                    intent.putExtra(this.getResources().getString(R.string.Visit_Visa_for_Spouse_Children), serviceHistoryDO.getService_Request().getVisaSpouseChildren());
                    break;
                case 34:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Visit_Visa_for_In_laws_children));
                    serviceDO.serviceType = ServiceDO.ServiceType.VISIT_VISA_FOR_IN_LAWS_CHILDREN;
                    intent = new Intent(TrackServiceDetails.this, VisitVisaForinLawsChildren.class);
                    intent.putExtra(this.getResources().getString(R.string.Visit_Visa_for_In_laws_children), serviceHistoryDO.getService_Request().getVisaInLawsChildren());
                    break;
                case 35:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Family_Joining_Visa_for_Spouse_children));
                    serviceDO.serviceType = ServiceDO.ServiceType.FAMILY_JOINING_VISA_FOR_SPOUSE_CHILDREN;
                    intent = new Intent(TrackServiceDetails.this, FamilyVisaJoiningForSpouseChildren.class);
                    intent.putExtra(this.getResources().getString(R.string.Family_Joining_Visa_for_Spouse_children), serviceHistoryDO.getService_Request().getFamilyVisaSpouseChildren());
                    break;
                case 36:
                    Log.d(FORM_NAME_TAG, this.getResources().getString(R.string.Family_Joining_Visa_for_Parents));
                    serviceDO.serviceType = ServiceDO.ServiceType.FAMILY_JOINING_VISA_FOR_PARENTS;
                    intent = new Intent(TrackServiceDetails.this, FamilyVisaJoiningForParents.class);
                    intent.putExtra(this.getResources().getString(R.string.Family_Joining_Visa_for_Parents), serviceHistoryDO.getService_Request().getFamilyVisaParent());
                    break;
            }

            if (intent != null) {
                intent.putExtra(ConstantExtraKey.SERVICE_OBJECT, serviceDO);
                intent.putExtra(ConstantExtraKey.ATTACHMENTS, serviceHistoryDO.getService_Request().getAttachments());
                startActivity(intent);
            }

        }
    }

    @Override
    public void showAlert(String message) {

    }

    public static int getMainFormImages(int formId) {

        switch (formId) {

            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return R.drawable.form_one;

            case 23:
                return R.drawable.form_two;

            case 24:
                return R.drawable.form_fourteen_new;

            case 25:
                return R.drawable.form_four;

            case 26:
                return (R.drawable.form_five);

            case 27:
                return (R.drawable.form_six);

            case 16:
                return (R.drawable.form_seven);

            case 28:
                return (R.drawable.form_eight);

            case 29:
                return (R.drawable.form_nine);

            case 30:
                return (R.drawable.form_nine);

            case 31:
                return (R.drawable.form_ten);

            case 32:
                return (R.drawable.form_eleven);

            case 33:
            case 34:
            case 35:
            case 36:
                return (R.drawable.form_twelve);
        }
        return (R.drawable.track_default_icon);
    }

    private void bindControls(ServiceRequestDO serviceRequestDO) {

        serviceId=serviceRequestDO.getId();

        tvFormName_track.setText(ServiceDO.getFormName(serviceRequestDO.getFormid()));
        ivFormIcon_track.setImageDrawable(ServiceDO.getFormImages(serviceRequestDO.getFormid()));

        if (serviceRequestDO.getRequestTime().contains("-"))
            serviceRequestDO.setRequestTime(serviceRequestDO.getRequestTime().replaceAll("-", "/"));

        tvReq_Date.setText(CalendarUtil.convertUTCtoDateFormat(serviceRequestDO.getRequestTime(),"dd/MM/yyyy HH:mm"));
        // CalendarUtil.getDate(serviceRequestDO.getRequestTime(),CalendarUtil.SEC_DATE_PATTERN,CalendarUtil.YYYY_MM_DD_FULL_PATTERN,Locale.getDefault())
        Collections.reverse(serviceRequestDO.getServicerequestassignmentModels());

        if (serviceRequestDO.getFinalLevel().equalsIgnoreCase("true")) {
            serviceRequestDO.getServicerequestassignmentModels().get(0).setAliasStatus(serviceRequestDO.getAliasStatus());
        }
    }

    @Override
    public void onTrackServices(List<ServiceRequestDO> trackServices) {

        hideLoader();

        if (trackServices!=null && trackServices.size()>0){

            if (isfromServiceReq) {
//                serviceRequestDO = trackServices.get(0);

                for (int i=0;i<trackServices.size();i++){
                    serviceRequestDO=trackServices.get(i);
                    if (serviceId==serviceRequestDO.getId()){
                        serviceId=-1;
                        AppConstants.SERVICE_ID=-1;
                        bindControls(serviceRequestDO);
                        trackServicesAdapter.refresh(serviceRequestDO.getServicerequestassignmentModels(), serviceRequestDO);
                        break;
                    }
                }

//                bindControls(serviceRequestDO);
//                trackServicesAdapter.refresh(serviceRequestDO.getServicerequestassignmentModels(), serviceRequestDO);
            } else if (isfromNotification){
                for (int i=0;i<trackServices.size();i++){
                    serviceRequestDO=trackServices.get(i);
                    if (serviceId==serviceRequestDO.getId()){
                        serviceId=-1;
                        bindControls(serviceRequestDO);
                        trackServicesAdapter.refresh(serviceRequestDO.getServicerequestassignmentModels(), serviceRequestDO);
                        break;
                    }
                }
            } else {
                isfromServiceReq=true;
                isfromNotification=true;
                mSwipeRefreshLayout.setRefreshing(false);
                for (int i=0;i<trackServices.size();i++){
                    serviceRequestDO=trackServices.get(i);
                    if (serviceId==serviceRequestDO.getId()){
                        serviceId=-1;
                        bindControls(serviceRequestDO);
                        trackServicesAdapter.refresh(serviceRequestDO.getServicerequestassignmentModels(), serviceRequestDO);
                        break;
                    }
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //listener for home
        if (item.getItemId() == android.R.id.home) {
            if (isBackAllowed)
            {
                if(isfromServiceReq || isfromNotification)
                {
                    Intent in = new Intent(TrackServiceDetails.this,DashboardActivity.class);
                    in.putExtra("FROM_DETAILS","FROM_DETAILS");
                    startActivity(in);
                    finish();
                }else
                    finish();
            }
            else
                drawerLayout.openDrawer(navigationView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(isfromServiceReq || isfromNotification)
        {
            Intent in = new Intent(TrackServiceDetails.this,DashboardActivity.class);
            in.putExtra("FROM_DETAILS","FROM_DETAILS");
            startActivity(in);
            finish();
        }else
            super.onBackPressed();
    }
}
