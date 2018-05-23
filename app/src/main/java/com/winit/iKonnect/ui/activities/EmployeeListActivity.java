package com.winit.iKonnect.ui.activities;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.EmployeeListAdapter;
import com.winit.iKonnect.adapter.MyDocumentsAdapter;
import com.winit.iKonnect.dataobject.ChatMemberDO;
import com.winit.iKonnect.dataobject.response.EmpModelDO;
import com.winit.iKonnect.dataobject.response.EmpdetailsModelDO;
import com.winit.iKonnect.module.employeeList.EmployeeListPresenter;
import com.winit.iKonnect.module.employeeList.IEmployeeListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.editable;
import static com.winit.iKonnect.R.id.mSwipeRefreshLayout;
import static com.winit.iKonnect.R.id.rvFeeds;
import static com.winit.iKonnect.R.id.rvMyDocuments;

/**
 * Created by Sreekanth.P on 27-12-2017.
 */

public class EmployeeListActivity extends BaseActivity implements IEmployeeListView {

    @Nullable
    @Bind(R.id.ed_Search)
    EditText ed_Search;
    @Nullable
    @Bind(R.id.rv_employeeList)
    RecyclerView rv_employeeList;

    @Nullable
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private EmployeeListAdapter adapter;
    private EmployeeListPresenter listPresenter;

    private int startIndex=0;
    private int endIndex=20;
    private boolean loading = true;
    private boolean isSwipe=false;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void initialize() {

        inflater.inflate(R.layout.employee_list_activity,flBody);
        ButterKnife.bind(this);

        setToolbarTitle("Employee List");

        listPresenter=new EmployeeListPresenter(this);
        getEmployeelist();

        linearLayoutManager = new LinearLayoutManager(EmployeeListActivity.this);
        rv_employeeList.setLayoutManager(linearLayoutManager);
        rv_employeeList.setItemAnimator(new DefaultItemAnimator());

        rv_employeeList.setAdapter(adapter = new EmployeeListAdapter(EmployeeListActivity.this, new ArrayList<EmpdetailsModelDO>()));

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
//                    showLoader("");
                    listPresenter.getEmployeeList(startIndex,endIndex,editable.toString());
                }
                else
                    showCustomDialog(EmployeeListActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "gotoDashboard", false);
            }
        });

        rv_employeeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int pastVisiblesItems, visibleItemCount, totalItemCount;

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems)>= totalItemCount) {
                            loading = false;
                            showLoader("");
                            listPresenter.getEmployeeList(totalItemCount,totalItemCount+endIndex,"");
                        }
                    }else {
                    }
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isSwipe=true;
                listPresenter.getEmployeeList(startIndex, endIndex,"");
            }
        });

    }

    private void getEmployeelist() {
        showLoader("");
        listPresenter.getEmployeeList(startIndex,endIndex,"");
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void FetchedEmployeeData(final ArrayList<EmpdetailsModelDO> arrEmployee) {

        hideLoader();
        loading = true;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (isSwipe) {
                    isSwipe = false;
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                adapter.refresh(arrEmployee);
            }
        });
    }
}
