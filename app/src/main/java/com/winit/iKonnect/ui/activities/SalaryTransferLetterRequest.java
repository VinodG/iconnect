package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.SalaryTransferLetterRequestDO;
import com.winit.iKonnect.dataobject.ServiceDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.winit.iKonnect.R.id.etCc_outStandingAmt;
import static com.winit.iKonnect.R.id.rbCreditCardYes;
import static com.winit.iKonnect.R.id.tvCurrency;

/**
 * Created by Sreekanth.P on 25-10-2017.
 */

public class SalaryTransferLetterRequest extends FormActivity {


    @Nullable
    @Bind(R.id.rbSalaryTransYes)
    RadioButton rbSalaryTransYes;
    @Nullable
    @Bind(R.id.rbSalaryTransNo)
    RadioButton rbSalaryTransNo;
    @Nullable
    @Bind(R.id.rbBankLoanYes)
    RadioButton rbBankLoanYes;
    @Nullable
    @Bind(R.id.rbBankLoanNo)
    RadioButton rbBankLoanNo;
    @Nullable
    @Bind(R.id.rbCreditCardYes)
    RadioButton rbCreditCardYes;
    @Nullable
    @Bind(R.id.rbCreditCardNo)
    RadioButton rbCreditCardNo;

    @Nullable
    @Bind(R.id.tvTotalLoantaken)
    TextView tvTotalLoantaken;
    @Nullable
    @Bind(R.id.tvOutstandingAmt)
    TextView tvOutstandingAmt;
    @Nullable
    @Bind(R.id.tvMonthlyInstallment)
    TextView tvMonthlyInstallment;
    @Nullable
    @Bind(R.id.tvCc_outStandingAmt)
    TextView tvCc_outStandingAmt;
    @Nullable
    @Bind(R.id.tvTotalLoanAmt)
    TextView tvTotalLoanAmt;

    @Nullable
    @Bind(R.id.et_account_number)
    EditText et_account_number;

    @Nullable
    @Bind(R.id.llConfirm)
    LinearLayout llConfirm;
    @Nullable
    @Bind(R.id.llApproval)
    LinearLayout llApproval;
    @Nullable
    @Bind(R.id.llRequest)
    LinearLayout llRequest;

    private EditText currentBankName, currentIBANno, bankName, IBANno, loanBankName, totalLoanTaken, outStanding_asOfday, monthlyInstallment, ccBankName, ccOutStandingAmt, purposeOfLoan, bankDetailsName, totalLoanAmt, monthlyInstal_50;
    private TextView tvLoanAppliedOn, lastInstal_due;
    private LinearLayout llBank, llBankLoans, llUndertaking, llCreditCard, llDetails;
    private String salaryTransferToSameBank;
    private String haveExistingLoans;
    private String haveCreditCard;
    private CheckBox decla_Confirm, decla_Approval, decla_Request;
    private String Confirm="", Approval="", Request="";

    SalaryTransferLetterRequestDO salaryTransferLetterRequestDO;

    @Override
    protected void initializeForm() {


        salaryTransferLetterRequestDO = (SalaryTransferLetterRequestDO) getIntent().getSerializableExtra(this.getResources().getString(R.string.Salary_Transfer_Letter_Request));

        inflater.inflate(R.layout.salary_transfer_letter_request, flFormBody, true);
        ButterKnife.bind(this);

        initializeControls();

        ll_reson_for_request.setVisibility(View.GONE);


        llConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!decla_Confirm.isChecked()) {
                    decla_Confirm.setChecked(true);
                }else {
                    decla_Confirm.setChecked(false);
                }
            }
        });
        llApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!decla_Approval.isChecked()) {
                    decla_Approval.setChecked(true);
                }else {
                    decla_Approval.setChecked(false);
                }
            }
        });
        llRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!decla_Request.isChecked()) {
                    decla_Request.setChecked(true);
                }else {
                    decla_Request.setChecked(false);
                }
            }
        });



        if (salaryTransferLetterRequestDO != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }

        if (preference.getStringFromPreference(Preference.STAFF_WORK_COUNTRY, "").equalsIgnoreCase(AppConstants.STAFF_WORK_COUNTRY)) {
            tvTotalLoantaken.setText(getResources().getString(R.string.total_loan_taken) + " (" + AppConstants.OMR + ")");
            tvOutstandingAmt.setText(getResources().getString(R.string.outstanding_date) + " (" + AppConstants.OMR + ")");
            outStanding_asOfday.setHint(getResources().getString(R.string.outstanding_date) + " (" + AppConstants.OMR + ")");
            tvMonthlyInstallment.setText(getResources().getString(R.string.monthly_instlment) + " (" + AppConstants.OMR + ")");
            tvCc_outStandingAmt.setText(getResources().getString(R.string.credit_limit) + " (" + AppConstants.OMR + ")");
            ccOutStandingAmt.setHint(getResources().getString(R.string.credit_limit) + " (" + AppConstants.OMR + ")");
            tvTotalLoanAmt.setText(getResources().getString(R.string.total_loan_Amt_req) + " (" + AppConstants.OMR + ")");
            totalLoanAmt.setHint(getResources().getString(R.string.total_loan_Amt_req) + " (" + AppConstants.OMR + ")");
        } else {
            tvTotalLoantaken.setText(getResources().getString(R.string.total_loan_taken) + " (" + AppConstants.AED + ")");
            tvOutstandingAmt.setText(getResources().getString(R.string.outstanding_date) + " (" + AppConstants.AED + ")");
            outStanding_asOfday.setHint(getResources().getString(R.string.outstanding_date) + " (" + AppConstants.AED + ")");
            tvMonthlyInstallment.setText(getResources().getString(R.string.monthly_instlment) + " (" + AppConstants.AED + ")");
            tvCc_outStandingAmt.setText(getResources().getString(R.string.credit_limit) + " (" + AppConstants.AED + ")");
            ccOutStandingAmt.setHint(getResources().getString(R.string.credit_limit) + " (" + AppConstants.AED + ")");
            tvTotalLoanAmt.setText(getResources().getString(R.string.total_loan_Amt_req) + " (" + AppConstants.AED + ")");
            totalLoanAmt.setHint(getResources().getString(R.string.total_loan_Amt_req) + " (" + AppConstants.AED + ")");
        }
    }


    public void initializeControls() {

//        currentBankName = (EditText) findViewById(R.id.etCurrentBankName);
//        currentIBANno = (EditText) findViewById(R.id.etCurrentIban_no);
        bankName = (EditText) findViewById(R.id.etBankName);
        IBANno = (EditText) findViewById(R.id.etIban_no);
        loanBankName = (EditText) findViewById(R.id.etLoanBankName);
        totalLoanTaken = (EditText) findViewById(R.id.etTotalLoanTaken);
        outStanding_asOfday = (EditText) findViewById(R.id.etOutstanding);
        monthlyInstallment = (EditText) findViewById(R.id.etMonthlyInstallment);
        ccBankName = (EditText) findViewById(R.id.etCc_BankName);
        ccOutStandingAmt = (EditText) findViewById(etCc_outStandingAmt);
        purposeOfLoan = (EditText) findViewById(R.id.etPurposeOfLoan);
        bankDetailsName = (EditText) findViewById(R.id.etBankDetailsName);
        totalLoanAmt = (EditText) findViewById(R.id.etTotalLoanAmt);
        monthlyInstal_50 = (EditText) findViewById(R.id.etMonthlyInstal_50);

        tvLoanAppliedOn = (TextView) findViewById(R.id.tvLoanAppliedOn);
        lastInstal_due = (TextView) findViewById(R.id.tvLastInstallment);

        llBank = (LinearLayout) findViewById(R.id.llBank);
        llBankLoans = (LinearLayout) findViewById(R.id.llBankLoans);
        llUndertaking = (LinearLayout) findViewById(R.id.llUndertaking);
        llCreditCard = (LinearLayout) findViewById(R.id.llCreditCard);
        llDetails = (LinearLayout) findViewById(R.id.llDetails);

        decla_Confirm = (CheckBox) findViewById(R.id.cb_confirm);
        decla_Approval = (CheckBox) findViewById(R.id.cb_Approval);
        decla_Request = (CheckBox) findViewById(R.id.cb_Request);

        tvLoanAppliedOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectedDateFromDatePicker(SalaryTransferLetterRequest.this, tvLoanAppliedOn);
            }
        });
        lastInstal_due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectedDateFromDatePicker(SalaryTransferLetterRequest.this, lastInstal_due);
            }
        });

    }

    public void onClickSalaryTransfer(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbSalaryTransYes:
                if (isChecked) {
                    salaryTransferToSameBank = "Yes";
                    llBank.setVisibility(View.GONE);
                    llCreditCard.setVisibility(View.GONE);
                }
                break;
            case R.id.rbSalaryTransNo:
                if (isChecked) {
                    salaryTransferToSameBank = "No";
                    llBank.setVisibility(View.VISIBLE);
                    showAlert(" Kindly Submit \"Salary transfer Request\", to update your bank account");
                } else {
                }
                break;
            default:
                break;
        }
    }

    public void onClickBankLoans(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbBankLoanYes:
                if (isChecked) {
                    haveExistingLoans = "Yes";
                    llBankLoans.setVisibility(View.VISIBLE);
                } else {
                }
                break;
            case R.id.rbBankLoanNo:
                if (isChecked) {
                    haveExistingLoans = "No";
                    llBankLoans.setVisibility(View.GONE);

                } else {

                }
                break;
            default:
                break;
        }
    }

    public void onClickCreditCard(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbCreditCardYes:
                if (isChecked) {
                    haveCreditCard = "Yes";
                    llCreditCard.setVisibility(View.VISIBLE);
                } else {
                }
                break;
            case R.id.rbCreditCardNo:
                if (isChecked) {
                    haveCreditCard = "No";
                    llCreditCard.setVisibility(View.GONE);
                } else {
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
            case CURRENT_BANK_NAME:
                message = getString(R.string.current_bank_name);
                break;
            case CURRENT_IBAN_NO:
                message = getString(R.string.current_iban_no);
                break;
            case ENTER_23DIGITS_IBAN:
                message = "IBAN Number should be 23 alphanumeric characters";
                break;
            case SALARY_TRANS_TO_SAME_BANK:
                message = "Please mention do you want the Salary Transfer Letter addressed to the same bank?";
                break;
            case BANK_NAME:
                message = getString(R.string.enter_bank_name);
                break;
            case LOAN_BANK_NAME:
                message = "Please enter your Bank Name for which loan has been applied";
                break;
            case CC_BANK_NAME:
                message = "Please enter Credit Card Bank Name";
                break;
            case IBAN_NO:
                message = "Please enter IBAN Number";
                break;
            case TOTAL_LOAN_TAKEN:
                message = getString(R.string.total_loan_taken_amt);
                break;
            case LOAN_APPLIED_ON:
                message = " Please select date for loan applied";
                break;
            case OUTSTANDING_ASOFDAY:
                message = getString(R.string.outstanding_asofday);
                break;
            case LAST_INSTAL_DUE:
                message = getString(R.string.last_instal_due);
                break;
            case MONTHLY_INSTAL:
                message = getString(R.string.monthly_instal);
                break;
            case PURPOSE_OF_LOAN:
                message = getString(R.string.purpose_of_loan_alert);
                break;
            case TOTAL_LOAN_AMT_REQ:
                message = getString(R.string.total_loan_amt_req);
                break;
            case MONTHLY_INSTAL_50:
                message = getString(R.string.monthly_instal);
                break;
            case MONTHLY_INSTAL_CONDITION:
                message = "Monthly installment amount should not exist more than 50% of Basic Salary";
                break;
            case OUTSTANDING_AMT:
                message = getString(R.string.outstanding_amt_alert);
                break;
            case CREDIT_LIMIT:
                message = "Please enter Credit Card Credit Limit";
                break;
            case HAVE_EXISTING_LOANS:
                message = "Please mention do you have Existing Bank Loans";
                break;
            case HAVE_CREDIT_CARD:
                message = "Do you have any Credit card?";
                break;
            case DECLA_CONFIRM:
//                message = getString(R.string.decla_confirm);
                message = "Please Read and Agree to all the Undertaking/s";
                break;
            case DECLA_APPROVAL:
//                message = getString(R.string.decla_approval);
                message = "Please Read and Agree to all the Undertaking/s";
                break;
            case DECLA_REQUEST:
                message = getString(R.string.decla_request);
                message = "Please Read and Agree to all the Undertaking/s";
                break;
            case ENTER_ACCOUNT_NO:
                message = getString(R.string.AccountNumberMsg);
                break;
            default:
                message = type;
                break;
        }

        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(SalaryTransferLetterRequest.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(SalaryTransferLetterRequest.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        checkDeclarations(view);
        iFormPresenter.SalaryTransferLetterRequest(
                ""
                , ""
                , salaryTransferToSameBank
                , bankName.getText().toString()
                , IBANno.getText().toString()
                , haveExistingLoans
                , loanBankName.getText().toString()
                , totalLoanTaken.getText().toString()
                , tvLoanAppliedOn.getText().toString()
                , outStanding_asOfday.getText().toString()
                , lastInstal_due.getText().toString()
                , monthlyInstallment.getText().toString()
                , purposeOfLoan.getText().toString()
                , bankDetailsName.getText().toString()
                , totalLoanAmt.getText().toString()
                , monthlyInstal_50.getText().toString()
                , haveCreditCard
                , ccBankName.getText().toString()
                , ccOutStandingAmt.getText().toString()
                , Confirm
                , Approval
                , Request
                , et_account_number.getText().toString()
        );

    }

    private void checkDeclarations(View view) {
        if (decla_Confirm.isChecked()) {
            Confirm = "Yes";
        } else {
            Confirm = "";
        }
        if (decla_Approval.isChecked()) {
            Approval = "Yes";
        } else {
            Approval = "";
        }
        if (decla_Request.isChecked()) {
            Request = "Yes";
        } else {
            Request = "";
        }
    }

    @Override
    public void showAlert(String message, ServiceDO serviceRequestDO) {
        hideLoader();
        if (serviceRequestDO != null)
//            showFormSubmitPopup(serviceRequestDO.serviceName, message);
            showFormSubmitPopup(serviceRequestDO.serviceName, "Submitted to the Reporting Manager for approval");
        else {
//            showFormSubmitPopup("Salary Transfer Letter Request", message);
            showFormSubmitPopup("Salary Transfer Letter Request", "Submitted to the Reporting Manager for approval");
        }
    }

    private void showAllContent() {

//        currentBankName.setText(salaryTransferLetterRequestDO.getCurrentsalaryAccountBankName());
//        currentIBANno.setText(salaryTransferLetterRequestDO.getCurrentsalaryIBAN());

        salaryTransferToSameBank = salaryTransferLetterRequestDO.getSalaryTransfersameBank();

        if (salaryTransferToSameBank.equalsIgnoreCase("Yes")) {
            rbSalaryTransYes.setChecked(true);
            llBank.setVisibility(View.GONE);
            llCreditCard.setVisibility(View.GONE);
        } else {
            rbSalaryTransNo.setChecked(true);
            llBank.setVisibility(View.VISIBLE);
        }

        bankName.setText(salaryTransferLetterRequestDO.getBankName());
        et_account_number.setText(salaryTransferLetterRequestDO.getAccountNumber());
        IBANno.setText(salaryTransferLetterRequestDO.getIBANNumber());

        haveExistingLoans = salaryTransferLetterRequestDO.getDoyouhaveanyexistingBankloans();
        if (haveExistingLoans.equalsIgnoreCase("Yes")) {
            rbBankLoanYes.setChecked(true);
            llBankLoans.setVisibility(View.VISIBLE);
        } else {
            rbBankLoanNo.setChecked(true);
            llBankLoans.setVisibility(View.GONE);
        }

        loanBankName.setText(salaryTransferLetterRequestDO.getExistingBankName());
        totalLoanTaken.setText(salaryTransferLetterRequestDO.getTotalLoanTaken());
        tvLoanAppliedOn.setText(salaryTransferLetterRequestDO.getLoanAppliedon().replace("/","-"));
        outStanding_asOfday.setText(salaryTransferLetterRequestDO.getOutstandingasofDate());
        lastInstal_due.setText(salaryTransferLetterRequestDO.getLastinstallmentdue().replace("/","-"));
        monthlyInstallment.setText(salaryTransferLetterRequestDO.getMonthlyinstallment());
        purposeOfLoan.setText(salaryTransferLetterRequestDO.getPurposeofLoan());
        bankDetailsName.setText(salaryTransferLetterRequestDO.getProposedBankName());
        totalLoanAmt.setText(salaryTransferLetterRequestDO.getTotalLoanAmreq());
        monthlyInstal_50.setText(salaryTransferLetterRequestDO.getMonthlyInst_50());

        if (!(StringUtils.isEmpty(salaryTransferLetterRequestDO.getCreditBankName()) &&
                StringUtils.isEmpty(salaryTransferLetterRequestDO.getOutstandingAmount()))) {
            rbCreditCardYes.setChecked(true);
            llCreditCard.setVisibility(View.VISIBLE);
            ccBankName.setText(salaryTransferLetterRequestDO.getCreditBankName());
            ccOutStandingAmt.setText(salaryTransferLetterRequestDO.getOutstandingAmount());
        } else {
            rbCreditCardNo.setChecked(true);
            llCreditCard.setVisibility(View.GONE);
        }

        Confirm = salaryTransferLetterRequestDO.getDec_confirm();
        if (Confirm.equalsIgnoreCase("Yes")) {
            decla_Confirm.setChecked(true);
        } else {
            decla_Confirm.setChecked(false);
        }
        Approval = salaryTransferLetterRequestDO.getDec_approval();
        if (Approval.equalsIgnoreCase("Yes")) {
            decla_Approval.setChecked(true);
        } else {
            decla_Approval.setChecked(false);
        }
        Request = salaryTransferLetterRequestDO.getDec_request();
        if (Request.equalsIgnoreCase("Yes")) {
            decla_Request.setChecked(true);
        } else {
            decla_Request.setChecked(false);
        }
    }
}
