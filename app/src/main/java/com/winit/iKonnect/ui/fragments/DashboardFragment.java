package com.winit.iKonnect.ui.fragments;

import android.support.v4.app.Fragment;


/**
 * Created by Rahul.Yadav on 5/12/2017.
 */

public class DashboardFragment extends Fragment{

    /*private IDashboardPresenter iDashboardPresenter;
    LinearLayoutManager feedsLayoutManager;

    @Nullable
    @Bind(R.id.rv_hrServices)
    RecyclerView rvServices;

    @Nullable
    @Bind(R.id.rvSelectedCategories)
    RecyclerView rvSelectedCategories;

    @Nullable
    @Bind(R.id.tvNoData)
    TextView tvNoData;

    private FeedsAdapter feedsAdapter;
    private ServicesAdapter servicesAdapter;
    private int position;
    private boolean loading = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.debug(LogUtils.LOG_TAG,"DashboardFragment - onCreate");
        iDashboardPresenter = ((DashboardActivity)getActivity()).iDashboardPresenter; //Bad practice need to find good Solution
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hr_services, container, false);
        ButterKnife.bind(this, view);
        LogUtils.debug(LogUtils.LOG_TAG,"DashboardFragment - onCreateView");
        position = getArguments().getInt(ConstantExtraKey.DASHBOARD_POSITION);
        if(position == 0) {
            populateFeeds();
            rvServices.addOnScrollListener(new RecyclerView.OnScrollListener()
            {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                {
                    int pastVisiblesItems, visibleItemCount, totalItemCount;
                    if(dy > 0) //check for scroll down
                    {
                        visibleItemCount = feedsLayoutManager.getChildCount();
                        totalItemCount = feedsLayoutManager.getItemCount();
                        pastVisiblesItems = feedsLayoutManager.findFirstVisibleItemPosition();
//                        feedsLayoutManager.getChildCount() + feedsLayoutManager.findFirstVisibleItemPosition() >= feedsLayoutManager.getItemCount()
                        if (loading)
                        {
                            if ( (visibleItemCount + pastVisiblesItems)+50 >= totalItemCount)
                            {
                                loading = false;
                                iDashboardPresenter.fetchInboxs(iDashboardPresenter.getNotificationDOs());
                            }
                        }
                    }
                }
            });
        }else
            populateServices();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void populateServices() {
        if(servicesAdapter == null){
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
            rvServices.setLayoutManager(mLayoutManager);
            rvServices.setItemAnimator(new DefaultItemAnimator());
            rvServices.setAdapter(servicesAdapter = new ServicesAdapter(getActivity(),iDashboardPresenter.getServiceDOs()));
        }else{
            servicesAdapter.refresh();
        }
    }

    private void populateFeeds() {
        ArrayList<CategoryDO> categoryDOs = iDashboardPresenter.getArrSelected();
        FeedBinder feedBinder = new FeedBinder(getActivity());
        feedBinder.setiBasePresenter(iDashboardPresenter);
        if(categoryDOs != null && categoryDOs.size()>0) {
            rvSelectedCategories.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvSelectedCategories.setLayoutManager(linearLayoutManager);
            rvSelectedCategories.setItemAnimator(new DefaultItemAnimator());
            rvSelectedCategories.setAdapter(new CategoryAdapter(getActivity(), categoryDOs, iDashboardPresenter));
        }else
            rvSelectedCategories.setVisibility(View.GONE);
        List<FeedsDO> feedsDOs = iDashboardPresenter.getNotificationDOs();
        if(feedsDOs!= null && feedsDOs.size()>0) {
            rvServices.setVisibility(View.VISIBLE);
            if (feedsAdapter == null) {
                feedsLayoutManager = new LinearLayoutManager(getActivity());
                rvServices.setLayoutManager(feedsLayoutManager);
                rvServices.setItemAnimator(new DefaultItemAnimator());
                rvServices.setAdapter(feedsAdapter = new FeedsAdapter(getActivity(), feedsDOs, feedBinder));
            } else {
                feedsAdapter.refresh(feedsDOs);
            }
        }else{
            rvServices.setVisibility(View.GONE);
            tvNoData.setText("No Feeds found.");
        }
    }*/


}
