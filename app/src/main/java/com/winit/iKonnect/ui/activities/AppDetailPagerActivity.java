package com.winit.iKonnect.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.Preference;
import com.winit.iKonnect.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Girish Velivela on 11/4/2016.
 */

public class AppDetailPagerActivity extends BaseActivity{

    @Nullable
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Nullable
    @Bind(R.id.ll_dots)
    LinearLayout llDots;

    @Nullable
    @Bind(R.id.btn_skip)
    Button btnSkip;
    @Nullable
    @Bind(R.id.btn_next)
    Button btnNext;
    TextView tvheader;

    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView[] dots;
    private int[] layouts;
    private int mCurrentState = -1;
    private Handler mHandler = new Handler();
    private static int INTERVAL = ViewConfiguration.getLongPressTimeout();

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.activity_app_detail, flBody, true);
        ButterKnife.bind(this);

        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
              };

        addBottomDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    mHandler.postDelayed(mRun, INTERVAL);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoggedIn = preference.getbooleanFromPreference(Preference.IS_LOGGED_IN,false);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        llDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[2]);
            llDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[2]);

        for (int i=0;i<currentPage;i++){
            dots[i].setTextColor(colorsActive[1]);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
//                tvheader.setText(getString(R.string.Track_service_req));
            } else {

//                if(position == 0)
//                {
//                    tvheader.setText(getString(R.string.stay_updates));
//                }else if(position ==1)
//                    tvheader.setText(getString(R.string.req_service));
//                else if(position ==2)
//                    tvheader.setText(getString(R.string.Track_service_req));
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mCurrentState = state;
            // if we are at the second page and the user touched the
            // ViewPager post a Runnable with a decent time to schedule our
            // Intent
            if (viewPager.getCurrentItem() == layouts.length-1) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    mHandler.postDelayed(mRun, INTERVAL);
                }
            }
        }
    };


    @Override
    protected void setTypeFace() {

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            tvheader = (TextView) view.findViewById(R.id.tvheader);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private Runnable mRun = new Runnable() {

        @Override
        public void run() {
            // we got the Runnable to be executed. If we are on the second page
            // and the user let go of the ViewPager in our time frame then start
            // the Activity(also cancel the dozen Runnables that were posted)
            if (mCurrentState == ViewPager.SCROLL_STATE_IDLE
                    && viewPager.getCurrentItem() == layouts.length-1) {
                mHandler.removeCallbacks(mRun);// or always set it to run
                Intent intent = new Intent(AppDetailPagerActivity.this, LoginActivity.class);
                startActivity(intent);
                if(preference.getStringFromPreference(preference.LANGUAGE,"").equalsIgnoreCase("english")){

                    overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
                }
                else{
                    overridePendingTransition(R.anim.slide_right1,R.anim.slide_left1);
                }
                preference.saveBooleanInPreference(Preference.IS_FISRT_TIME_LAUNCH,true);
                finish();
            }
        }
    };
}
