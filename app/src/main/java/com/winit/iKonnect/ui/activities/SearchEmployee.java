package com.winit.iKonnect.ui.activities;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.winit.common.Preference;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.SearchEmployeeAdapter;
import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.module.searchemployee.SearchPresenter;
import com.winit.iKonnect.module.searchemployee.iSearchView;

import java.util.ArrayList;

/**
 * Created by Ashoka.Reddy on 7/12/2017.
 */

public class SearchEmployee extends BaseActivity implements iSearchView
{
    private EditText ed_Search;
    private RecyclerView rv_employeeList;
    private SearchPresenter presentor;
    private SearchEmployeeAdapter adapter;
    @Override
    protected void initialize()
    {
        inflater.inflate(R.layout.search_employee_view,flBody);
        presentor = new SearchPresenter(this);
        initialisControl();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SearchEmployee.this);
        rv_employeeList.setLayoutManager(mLayoutManager);
        rv_employeeList.setItemAnimator(new DefaultItemAnimator());
        rv_employeeList.setAdapter(adapter = new SearchEmployeeAdapter(SearchEmployee.this,new ArrayList<ChatMemberDO>()));
        setToolbarTitle("Search");
        ed_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(isNetworkConnectionAvailable()) {
                    if (!TextUtils.isEmpty(editable.toString())) {
                        presentor.loadEmployees(editable.toString());
                    } else {
                        hideLoader();
                        adapter.refresh(new ArrayList<ChatMemberDO>());
                    }
                }
                else
                    showCustomDialog(SearchEmployee.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);

            }
        });

//        ed_Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_SEARCH){
//                    if(!TextUtils.isEmpty(v.getText().toString()))
//                    {
//                        presentor.loadEmployees(v.getText().toString());
//                    }else{
//                        hideLoader();
//                        adapter.refresh(new ArrayList<ChatMemberDO>());
//                    }
//                }
//                return true;
//            }
//    });

    }
    public void initialisControl()
    {
        ed_Search       = (EditText) findViewById(R.id.ed_Search);
        rv_employeeList = (RecyclerView) findViewById(R.id.rv_employeeList);
    }

    @Override
    protected void setTypeFace()
    {

    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed()
    {

    }

    @Override
    public void FetchedEmployeeData(final ArrayList<ChatMemberDO> arrEmployee)
    {

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                Predicate<ChatMemberDO> seacrhItem = new Predicate<ChatMemberDO>() {
                    @Override
                    public boolean apply(ChatMemberDO chatMemberDO)
                    {
                        return !chatMemberDO.getStaffNumber().trim().equalsIgnoreCase(preference.getStringFromPreference(Preference.STAFF_NUMBER,"").trim());
                    }
                };

                final ArrayList arrTmp = (ArrayList) filter(arrEmployee,seacrhItem);
                if(arrTmp!=null && arrTmp.size()>0)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.refresh(arrTmp);
                        }
                    });
                }else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.refresh(new ArrayList<ChatMemberDO>());
                            ed_Search.setFocusable(true);
                        }
                    });
                }
            }
        }).start();


    }
}
