package com.winit.iKonnect.ui.activities;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.winit.common.constant.AppConstants;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.RadioBTNDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.dataobject.VisaSpouseChildrenDO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

;import static android.R.attr.id;

/**
 * Created by Sreekanth.P on 26-10-2017.
 */

public class VisitVisaForSpouseChildren extends FormActivity {


    @Nullable
    @Bind(R.id.tvDateOfBirth)
    TextView DateOfBirth;
    @Nullable
    @Bind(R.id.tvDate_of_issue)
    TextView DateOfIssue;
    @Nullable
    @Bind(R.id.tvDate_of_expiry)
    TextView DateOfExpiry;
    @Nullable
    @Bind(R.id.tvDate_of_last_entry)
    TextView DateOfLastEntry;
    @Nullable
    @Bind(R.id.etName)
    EditText name;
    @Nullable
    @Bind(R.id.etFatherName)
    EditText FatherName;
    @Nullable
    @Bind(R.id.etMotherName)
    EditText MotherName;
    @Nullable
    @Bind(R.id.etNationality)
    EditText Nationality;
    @Nullable
    @Bind(R.id.etPlaceOfBirth)
    EditText PlaceOfBirth;
    @Nullable
    @Bind(R.id.etPassportNo)
    EditText PassportNo;
    @Nullable
    @Bind(R.id.etPlace_of_issue)
    EditText PlaceOfIssue;
    @Nullable
    @Bind(R.id.etprofession)
    EditText Profession;
    @Nullable
    @Bind(R.id.etAddress_abroad)
    EditText AddrAbroad;

    @Nullable
    @Bind(R.id.tv_relationship)
    TextView tv_relationship;

    @Nullable
    @Bind(R.id.rbBankLoanYes)
    RadioButton rbBankLoanYes;
    @Nullable
    @Bind(R.id.rbBankLoanNo)
    RadioButton rbBankLoanNo;
    @Nullable
    @Bind(R.id.rbHRAdvanceYes)
    RadioButton rbHRAdvanceYes;
    @Nullable
    @Bind(R.id.rbHRAdvanceNo)
    RadioButton rbHRAdvanceNo;
    @Nullable
    @Bind(R.id.rbTransAdvanceYes)
    RadioButton rbTransAdvanceYes;
    @Nullable
    @Bind(R.id.rbTransAdvanceNo)
    RadioButton rbTransAdvanceNo;
    @Nullable
    @Bind(R.id.rbCreditCardYes)
    RadioButton rbCreditCardYes;
    @Nullable
    @Bind(R.id.rbCreditCardNo)
    RadioButton rbCreditCardNo;
    @Nullable
    @Bind(R.id.tvVisaCost)
    TextView tvVisaCost;
    @Nullable
    @Bind(R.id.tvLabourAppli_cost)
    TextView tvLabourAppli_cost;


    private String AnyBankLoan, AnyHRAdvance, AnyTransportAdvance, CreditCards;
    private VisaSpouseChildrenDO visaSpouseChildrenDO;


    @Override
    protected void initializeForm() {

        visaSpouseChildrenDO = (VisaSpouseChildrenDO) getIntent().getSerializableExtra(getResources().getString(R.string.Visit_Visa_for_Spouse_Children));

        ll_reson_for_request.setVisibility(View.GONE);
        inflater.inflate(R.layout.visit_visa_for_spouse_children, flFormBody, true);
        ButterKnife.bind(this);
        bindData();

        if (visaSpouseChildrenDO != null) {
            showAllContent();
            disableAllChilds(ll_form);
            btnSubmit.setVisibility(View.GONE);
        }

        tvVisaCost.setText("Visit visa(all categories) - 20 OMR");
        tvLabourAppli_cost.setText("Labour card application form charge- 2 OMR(age above 12 years)");
    }


    public void BankLoan(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbBankLoanYes:
                if (isChecked) {
                    AnyBankLoan = "Yes";
                }
                break;
            case R.id.rbBankLoanNo:
                if (isChecked) {
                    AnyBankLoan = "No";
                }
                break;
            default:
                break;
        }
    }

    public void HrAdance(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbHRAdvanceYes:
                if (isChecked) {
                    AnyHRAdvance = "Yes";
                }
                break;
            case R.id.rbHRAdvanceNo:
                if (isChecked) {
                    AnyHRAdvance = "No";
                }
                break;
            default:
                break;
        }
    }

    public void TransportAdance(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbTransAdvanceYes:
                if (isChecked) {
                    AnyTransportAdvance = "Yes";
                }
                break;
            case R.id.rbTransAdvanceNo:
                if (isChecked) {
                    AnyTransportAdvance = "No";
                }
                break;
            default:
                break;
        }
    }

    public void CreditCards(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbCreditCardYes:
                if (isChecked) {
                    CreditCards = "Yes";
                }
                break;
            case R.id.rbCreditCardNo:
                if (isChecked) {
                    CreditCards = "No";
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
            case NAME_AS_PASSPORT:
                message = getString(R.string.name_as_passport);
                break;
            case FATHER_NAME:
                message = "Please Enter Father's/Mother's Name";
                break;
            case MOTHER_NAME:
                message = "Please Enter Father's/Mother's Name";
                break;
            case NATIONALITY:
                message = getString(R.string.nationality_alert);
                break;
            case DATE_OF_BIRTH:
                message = getString(R.string.dob);
                break;
            case PLACE_OF_BIRTH:
                message = getString(R.string.place_of_birth_alert);
                break;
            case PASSPORT_NO:
                message = getString(R.string.passport_no);
                break;
            case PLACE_OF_ISSUE:
                message = getString(R.string.place_of_issue_alert);
                break;
            case DATE_OF_ISSUE:
                message = getString(R.string.date_of_issue_alert);
                break;
            case DATE_OF_EXPIRY:
                message = getString(R.string.date_of_expiry_alert);
                break;
            /*case PROFESSION:
                message = getString(R.string.profession);
                break;*/
            case DATE_OF_LAST_ENTRY:
                message = getString(R.string.date_of_last_entry);
                break;
            case ADDR_ABROAD:
                message = getString(R.string.addr_abroad);
                break;
            case R_SHIP_WITH_APPLI:
                message = getString(R.string.r_ship_with_appli);
                break;
            case ANY_BANK_LOAN:
                message = getString(R.string.any_bank_loan);
                break;
            case ANY_HR_ADVANCE:
                message = getString(R.string.any_hr_advance);
                break;
            case ANY_TRANS_ADVANCE:
                message = getString(R.string.any_trans_advance);
                break;
            case CREDIT_CARDS:
                message = getString(R.string.credit_cards_alert);
                break;
            default:
                message = type;
                break;
        }
        if (message.equalsIgnoreCase(AppConstants.Logout))
            showCustomDialog(VisitVisaForSpouseChildren.this, getString(R.string.alert), getString(R.string.force_logout), getString(R.string.OK), "", "forcelogout", false);
        else
            showCustomDialog(VisitVisaForSpouseChildren.this, getString(R.string.alert), message, getString(R.string.OK), "", "");
    }

    @Override
    public void setRadioBTN(ArrayList<RadioBTNDO> radioBTNDOs) {

    }

    @Override
    public void postData(View view) {
        showLoader(getResources().getString(R.string.pleaseWait));
        iFormPresenter.VisaSpouseChildren(
                name.getText().toString()
                , FatherName.getText().toString()
                , MotherName.getText().toString()
                , Nationality.getText().toString()
                , DateOfBirth.getText().toString()
                , PlaceOfBirth.getText().toString()
                , PassportNo.getText().toString()
                , PlaceOfIssue.getText().toString()
                , DateOfIssue.getText().toString()
                , DateOfExpiry.getText().toString()
                , Profession.getText().toString()
                , DateOfLastEntry.getText().toString()
                , AddrAbroad.getText().toString()
                , tv_relationship.getText().toString()
                , AnyBankLoan
                , AnyHRAdvance
                , AnyTransportAdvance
                , CreditCards
        );

    }


    private void bindData() {

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedDateFromDatePicker(VisitVisaForSpouseChildren.this, DateOfBirth);
            }
        });
        DateOfIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedDateFromDatePicker(VisitVisaForSpouseChildren.this, DateOfIssue);
            }
        });
        DateOfExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDatePickerDialog(VisitVisaForSpouseChildren.this, DateOfExpiry);
                getSelectedDateFromDatePicker(VisitVisaForSpouseChildren.this, DateOfIssue.getText().toString(), DateOfExpiry);
            }
        });
        DateOfLastEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(VisitVisaForSpouseChildren.this, DateOfLastEntry);
            }
        });
        tv_relationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerShow(tv_relationship);
            }
        });

    }


    public void spinnerShow(final View view) {
        AlertDialog dialog;
        final CharSequence str[] = {"Husband", "Wife", "Son","Daughter", "Father", "Mother"};


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Relationship");
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

    private void showAllContent() {

        name.setText(visaSpouseChildrenDO.getPassportName());
        FatherName.setText(visaSpouseChildrenDO.getFathername());
        MotherName.setText(visaSpouseChildrenDO.getMothername());
        Nationality.setText(visaSpouseChildrenDO.getNationality());
        DateOfBirth.setText(visaSpouseChildrenDO.getDOB().replace("/","-"));
        PlaceOfBirth.setText(visaSpouseChildrenDO.getPlaceofBirth());
        PassportNo.setText(visaSpouseChildrenDO.getPPNumber());
        PlaceOfIssue.setText(visaSpouseChildrenDO.getPlaceofIssue());
        DateOfIssue.setText(visaSpouseChildrenDO.getDateofIssue().replace("/","-"));
        DateOfExpiry.setText(visaSpouseChildrenDO.getDateofExpiry().replace("/","-"));
        Profession.setText(visaSpouseChildrenDO.getProfessionpassport());
        DateOfLastEntry.setText(visaSpouseChildrenDO.getDatelastentryOman().replace("/","-"));
        AddrAbroad.setText(visaSpouseChildrenDO.getAddrAbroad());
        tv_relationship.setText(visaSpouseChildrenDO.getR_shipwithapplicant());

        AnyBankLoan = visaSpouseChildrenDO.getAnyBankLoan();
        AnyHRAdvance = visaSpouseChildrenDO.getAnyHRAdvance();
        AnyTransportAdvance = visaSpouseChildrenDO.getAnyTransportAdvance();
        CreditCards = visaSpouseChildrenDO.getCreditCards();

        if (AnyBankLoan.equalsIgnoreCase("Yes")) {
            rbBankLoanYes.setChecked(true);
        } else {
            rbBankLoanNo.setChecked(true);
        }
        if (AnyHRAdvance.equalsIgnoreCase("Yes")) {
            rbHRAdvanceYes.setChecked(true);
        } else {
            rbHRAdvanceNo.setChecked(true);
        }
        if (AnyTransportAdvance.equalsIgnoreCase("Yes")) {
            rbTransAdvanceYes.setChecked(true);
        } else {
            rbTransAdvanceNo.setChecked(true);
        }
        if (CreditCards.equalsIgnoreCase("Yes")) {
            rbCreditCardYes.setChecked(true);
        } else {
            rbCreditCardNo.setChecked(true);
        }
    }
}
