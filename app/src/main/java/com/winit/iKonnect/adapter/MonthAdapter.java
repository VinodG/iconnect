package com.winit.iKonnect.adapter;

/**
 * Created by Ashoka.Reddy on 5/29/2017.
 */

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.CalenderDetailDo;
import com.winit.iKonnect.ui.activities.CalenderViewDetailActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Abhijit.Paul on 5/15/2017.
 */

public class MonthAdapter extends BaseAdapter {
    private GregorianCalendar mCalendar;
    private Calendar mCalendarToday;
    private Context mContext;
    private DisplayMetrics mDisplayMetrics;
    private List<String> mItems;
    private int mMonth;
    private int mYear;
    private int mDaysShown;
    private int mDaysLastMonth;
    private int mDaysNextMonth;
    private int mTitleHeight, mDayHeight;
    private final String[] mDays = { "M", "T", "W", "T", "F", "S", "S" };
    private final int[] mDaysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private LayoutInflater inflater;
    private HashMap<String,CalenderDetailDo> hashMap;
    NumberFormat nf;
    private String mm="";
    private String dd="";
    private String yyyy="";
    public MonthAdapter(Context c, int month, int year, HashMap<String,CalenderDetailDo> hashMap, DisplayMetrics metrics,String mm, String dd, String yyyy) {
        mContext = c;
        mMonth = month;
        mYear = year;
        mCalendar = new GregorianCalendar(mYear, mMonth, 1);
        mCalendarToday = Calendar.getInstance();
        mDisplayMetrics = metrics;
        populateMonth();
        this.hashMap= hashMap;
        nf = NumberFormat.getInstance();
        inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        this.dd=dd;
        this.mm=mm;
        this.yyyy=yyyy;
    }

    private void populateMonth() {
        mItems = new ArrayList<String>();
        for (String day : mDays) {
            mItems.add(day);
            mDaysShown++;
        }

        int firstDay = getDay(mCalendar.get(Calendar.DAY_OF_WEEK));
        int prevDay;
        if (mMonth == 0)
            prevDay = daysInMonth(11) - firstDay + 1;
        else
            prevDay = daysInMonth(mMonth - 1) - firstDay + 1;
        for (int i = 0; i < firstDay; i++) {
            mItems.add(String.valueOf(prevDay + i));
            mDaysLastMonth++;
            mDaysShown++;
        }

        int daysInMonth = daysInMonth(mMonth);
        for (int i = 1; i <= daysInMonth; i++) {
            mItems.add(String.valueOf(i));
            mDaysShown++;
        }

        mDaysNextMonth = 1;
        while (mDaysShown % 7 != 0) {
            mItems.add(String.valueOf(mDaysNextMonth));
            mDaysShown++;
            mDaysNextMonth++;
        }

        mTitleHeight = 110;
        int rows = (mDaysShown / 7);
        mDayHeight = (mDisplayMetrics.heightPixels - mTitleHeight
                - (rows * 8) - getBarHeight()) / (rows - 1);
        mDayHeight=250;
    }

    private int daysInMonth(int month) {
        int daysInMonth = mDaysInMonth[month];
        if (month == 1 && mCalendar.isLeapYear(mYear))
            daysInMonth++;
        return daysInMonth;
    }

    private int getBarHeight() {
        switch (mDisplayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_HIGH:
                return 48;
            case DisplayMetrics.DENSITY_MEDIUM:
                return 32;
            case DisplayMetrics.DENSITY_LOW:
                return 24;
            default:
                return 48;
        }
    }

    private int getDay(int day) {
        switch (day) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return 0;
        }
    }

    private boolean isToday(int day, int month, int year) {
        if (mCalendarToday.get(Calendar.MONTH) == month
                && mCalendarToday.get(Calendar.YEAR) == year
                && mCalendarToday.get(Calendar.DAY_OF_MONTH) == day) {
            return true;
        }
        return false;
    }

    private int[] getDate(int position) {
        int date[] = new int[3];
        if (position <= 6) {
            return null; // day names
        } else if (position <= mDaysLastMonth + 6) {
            // previous month
            date[0] = Integer.parseInt(mItems.get(position));
            if (mMonth == 0) {
                date[1] = 11;
                date[2] = mYear - 1;
            } else {
                date[1] = mMonth - 1;
                date[2] = mYear;
            }
        } else if (position <= mDaysShown - mDaysNextMonth  ) {
            // current month
            date[0] = position - (mDaysLastMonth + 6);
            date[1] = mMonth;
            date[2] = mYear;
        } else {
            // next month
            date[0] = Integer.parseInt(mItems.get(position));
            if (mMonth == 11) {
                date[1] = 0;
                date[2] = mYear + 1;
            } else {
                date[1] = mMonth + 1;
                date[2] = mYear;
            }
        }
        return date;
    }
    int[] date;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.calendarcell, null);
            viewHolder = new MonthAdapter.ViewHolder();
            viewHolder.TextView = (TextView) convertView.findViewById(R.id.objName);
            viewHolder.ll_backgrnd = (LinearLayout) convertView.findViewById(R.id.ll_backgrnd);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            if(position<7)
                viewHolder.TextView.setText(mItems.get(position));
            else
                viewHolder.TextView.setText(nf.format(StringUtils.getInt(mItems.get(position))));
            date = getDate(position);
            viewHolder.img.setVisibility(View.INVISIBLE);

            if (date != null) {
                if (date[1] != mMonth) {
                    viewHolder.TextView.setTextColor(mContext.getResources().getColor(R.color.gray_light));
                    viewHolder.img.setVisibility(View.INVISIBLE);
                } else {
                    if(((date[1]+1)+"").length()==1)
                        mm1=0+""+(date[1]+1);
                    else
                        mm1=""+(date[1]+1);
                    if(((date[0])+"").length()==1)
                        mm2=0+""+(date[0]);
                    else
                        mm2=""+(date[0]);

                    if(mm.equalsIgnoreCase(mm1) && dd.equalsIgnoreCase(""+mm2) && yyyy.equalsIgnoreCase(date[2]+""))
                    {
                            viewHolder.ll_backgrnd.setBackgroundColor(mContext.getResources().getColor(R.color.dot_light_screen3));
                    }

                    if(hashMap!=null && hashMap.containsKey(date[2]+"-"+mm1+"-"+mm2))
                    {
                        viewHolder.img.setImageResource(R.drawable.star);
                        viewHolder.img.setVisibility(View.VISIBLE);

                        convertView.setTag(R.string.like,hashMap.get(date[2]+"-"+mm1+"-"+mm2));
                    }
                    else
                        viewHolder.img.setVisibility(View.INVISIBLE);
                }
            } else {
//            view.setBackgroundColor(Color.argb(100, 10, 80, 255));
                viewHolder.img.setVisibility(View.GONE);
                viewHolder.TextView.setHeight(mTitleHeight);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CalenderDetailDo DO = (CalenderDetailDo) v.getTag(R.string.like);
                    if(DO!=null)
                    {
                        ((CalenderViewDetailActivity)mContext).setData(DO);
                    }
                    else
                        ((CalenderViewDetailActivity)mContext).initializeText();
                }
            });
            convertView.setTag(R.string.key_get_data,date);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (MonthAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;

    }
    boolean flag=false;
    String mm1="";
    String mm2="";
    String dd1="";
//    private int checkFordate(int dd, int mm, int yyyy) {
//        if((mm+"").length()==1)
//            mm1=0+""+mm;
//        else
//            mm1=mm+"";
//        if((dd+"").length()==1)
//            dd1=0+""+dd;
//        else
//            dd1=dd+"";
//        for(PlanningDO po:arrPlanning)
//        {
//            if(po.DateSchedule.split("T")[0].split("-")[0].equalsIgnoreCase(yyyy+"") && po.DateSchedule.split("T")[0].split("-")[1].equalsIgnoreCase(mm1+"") &&  po.DateSchedule.split("T")[0].split("-")[2].equalsIgnoreCase(dd1+""))
//            {
//                flag=true;
//                break;
//            }
//            else
//                flag=false;
//
//        }
//        if(flag)
//            return 1;
//        else
//            return 0;
//    }



    static class ViewHolder {
        private TextView TextView;
        private ImageView img;
        private LinearLayout ll_backgrnd;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}