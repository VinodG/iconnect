package com.winit.iKonnect.ui.activities;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winit.common.Preference;
import com.winit.common.util.CalendarUtil;
import com.winit.iKonnect.R;
import com.winit.iKonnect.adapter.MonthAdapter;
import com.winit.iKonnect.dataobject.CalenderDetailDo;
import com.winit.iKonnect.dataobject.CalenderListDO;
import com.winit.iKonnect.dataobject.PlanningDO;
import com.winit.iKonnect.dataobject.response.CalenderListResponse;
import com.winit.iKonnect.module.CalenderViewDetailModule.CalenderViewDetailPresenter;
import com.winit.iKonnect.module.CalenderViewDetailModule.ICalenderViewDetailPresenter;
import com.winit.iKonnect.module.CalenderViewDetailModule.ICalenderViewDetailView;
import com.winit.iKonnect.ui.fragments.CalenderViewBottomsheet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

/**
 * Created by Ashoka.Reddy on 5/29/2017.
 */

public class CalenderViewDetailActivity extends BaseActivity implements ICalenderViewDetailView {
    private GridView gvPlanning;
    private ArrayList<CalenderListDO> arrlist;
    private Calendar calendar, toDateCalender, fromDateCalender;
    private ICalenderViewDetailPresenter iCalenderviewPresenter;
    int calMonthCount;
    private String fromDate, toDate, selDate, today;
    private int mToday[] = new int[3];
    private ArrayList<PlanningDO> arrPlanningdetail = new ArrayList<>();
    private MonthAdapter mn;
    private DisplayMetrics metrics;
    private HashMap<String, CalenderDetailDo> hashMap = new HashMap<>();
    private TextView tvMonth, tvsubject, tvdesc, tvstartdate, tvenddate, tvavailabilty, tvorganiser, tvlocation;
    private ImageView img_back, img_front;
    private int count = 0;
    public Bundle bundle;
    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private String mm = "", dd = "", yyyy = "";

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.calenderview, flBody, true);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gvPlanning = (GridView) findViewById(R.id.gridview);
        iCalenderviewPresenter = new CalenderViewDetailPresenter(this);
        toDateCalender = Calendar.getInstance();
        fromDateCalender = Calendar.getInstance();
        setToolbarTitle("" + getString(R.string.mycalender));
        initializeControls();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, count);
        cal.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = cal.getTime();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        Date lastDateOfPreviousMonth = cal.getTime();
        mToday[0] = cal.get(Calendar.DAY_OF_MONTH);
        mToday[1] = cal.get(Calendar.MONTH); // zero based
        mToday[2] = cal.get(Calendar.YEAR);
        metrics = new DisplayMetrics();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Calendar.getInstance().getTime());
        mm = timeStamp.split("-")[1];
        dd = timeStamp.split("-")[2].length() == 1 ? "0" + timeStamp.split("-")[2] : timeStamp.split("-")[2];
        yyyy = timeStamp.split("-")[0];

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        tvMonth.setText(CalendarUtil.getMonthFromNumber_lang(mToday[1] + 1).toUpperCase());
        mn = new MonthAdapter(this, mToday[1], mToday[2], null, metrics, mm, dd, yyyy);
        gvPlanning.setAdapter(mn);
        showLoader("");
        iCalenderviewPresenter.LoadData("" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), "2017-01-01", "2017-12-31");

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                --count;
                cal.add(Calendar.MONTH, 0);
                cal.set(Calendar.DATE, 1);
                cal.add(Calendar.MONTH, count);
                mToday[0] = cal.get(Calendar.DAY_OF_MONTH);
                mToday[1] = cal.get(Calendar.MONTH); // zero based
                mToday[2] = cal.get(Calendar.YEAR);
                tvMonth.setText(CalendarUtil.getMonthFromNumber_lang(mToday[1] + 1).toUpperCase());
                mn = new MonthAdapter(CalenderViewDetailActivity.this, mToday[1], mToday[2], null, metrics, mm, dd, yyyy);
                gvPlanning.setAdapter(mn);
                showLoader("");
                iCalenderviewPresenter.LoadData("" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), "2017-01-01", "2017-12-31");
            }
        });
        img_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                ++count;
                cal.add(Calendar.MONTH, 0);
                cal.set(Calendar.DATE, 1);
                cal.add(Calendar.MONTH, count);

                mToday[0] = cal.get(Calendar.DAY_OF_MONTH);
                mToday[1] = cal.get(Calendar.MONTH); // zero based
                mToday[2] = cal.get(Calendar.YEAR);
                tvMonth.setText(CalendarUtil.getMonthFromNumber_lang(mToday[1] + 1).toUpperCase());
                mn = new MonthAdapter(CalenderViewDetailActivity.this, mToday[1], mToday[2], null, metrics, mm, dd, yyyy);
                gvPlanning.setAdapter(mn);
                showLoader("");
                iCalenderviewPresenter.LoadData("" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), "2017-01-01", "2017-12-31");
                showLoader("");
                iCalenderviewPresenter.LoadData("" + preference.getStringFromPreference(Preference.STAFF_NUMBER, ""), "2017-01-01", "2017-12-31");
            }
        });

    }

    private void initializeControls() {
        tvsubject = (TextView) findViewById(R.id.tvsubject);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvdesc = (TextView) findViewById(R.id.tvdesc);
        tvstartdate = (TextView) findViewById(R.id.tvstartdate);
        tvenddate = (TextView) findViewById(R.id.tvenddate);
        tvavailabilty = (TextView) findViewById(R.id.tvavailabilty);
        tvorganiser = (TextView) findViewById(R.id.tvorganiser);
        tvlocation = (TextView) findViewById(R.id.tvlocation);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_front = (ImageView) findViewById(R.id.img_front);
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
    public void LoadData(CalenderListResponse cal) {

        PlanningDO pl = null;
        if (cal.getEventModels() != null) {
            for (CalenderDetailDo DO : cal.getEventModels()) {
                if (DO.getStartDate().equalsIgnoreCase(DO.getEndDate()))
                    hashMap.put(DO.getStartDate().split("T")[0], DO);
                else {
                    String string = DO.getStartDate().split("T")[0];
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = format.parse(string);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String string1 = DO.getEndDate().split("T")[0];
                    Date date1 = null;
                    try {
                        date1 = format.parse(string1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Date> arrDates = (ArrayList<Date>) getDaysBetweenDates(date, date1);
                    for (Date d : arrDates) {
                        SimpleDateFormat outputFormat = new SimpleDateFormat(CalendarUtil.DATE_STD_PATTERN, Locale.ENGLISH);
                        hashMap.put(outputFormat.format(d), DO);
                    }
                }
            }

            mn = new MonthAdapter(CalenderViewDetailActivity.this, mToday[1], mToday[2], hashMap, metrics, mm, dd, yyyy);
            gvPlanning.setAdapter(mn);
            hideLoader();
        } else {
            hideLoader();
        }


    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate) || calendar.getTime().equals(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public void setData(CalenderDetailDo aDo) {
       /* tvsubject.setText("Subject : "+aDo.getSubject());
        tvdesc.setText("Description : "+aDo.getDescription());
        tvstartdate.setText("Start date : "+aDo.getStartDate().split("T")[0]);
        tvenddate.setText("End Date : "+aDo.getEndDate().split("T")[0]);
        tvavailabilty.setText("Availabilty : "+aDo.getAvailability());
        tvorganiser.setText("Organiser : "+aDo.getOrganizer());
        tvlocation.setText("Location : "+aDo.getLocation());*/

        bottomSheetDialogFragment = new CalenderViewBottomsheet();
        bottomSheetDialogFragment.setArguments(bundle);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

    }

    public void initializeText() {
        if(bottomSheetDialogFragment!=null)
        bottomSheetDialogFragment.dismiss();
        Toast.makeText(CalenderViewDetailActivity.this,""+getString(R.string.warning1),Toast.LENGTH_SHORT).show();
    }
}
