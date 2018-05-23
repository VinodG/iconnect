package com.winit.common.util;

import com.winit.common.application.IKonnectApplication;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.ServiceRequestDO;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Girish Velivela on 08-07-2016.
 */
public class CalendarUtil {

    public static final String DATE_STD_PATTERN = "yyyy-MM-dd";
    public static final String DD_MM_YYYY_PATTERN = "dd-MM-yyyy";
    public static final String DD_MM_YYYY_DATE_PATTERN = "dd/MM/yyyy";
    public static final String DD_MMM_YYYY_COMMA_PATTERN = "dd MMM, yyyy";
    public static final String DD_MMM_YYYY_PATTERN = "dd-MMM-yyyy";
    public static final String DD_MMM_YYYY_HHMM_PATTERN = "dd/MM/yyyy hh:mm";
    public static final String DD_MM_YYY_IFEN_PATTERN = "dd-MM-yyyy HH:mm";
    public static final String DD_MMM_YYYY_Comma_PATTERN = "dd MMM yyyy, HH:mm";
    public static final String DD_MMM_YYYY = "dd MMM yyyy";
    public static final String MM_DD_YYYY_PATTERN = "MM-dd-yyyy";
    public static final String MM_DD_YYYYPATTERN = "dd/MM/yyyy HH:mm";
    public static final String DATE_STD_PATTERN_MONTH = "yyyy-MM";
    public static final String MM_YYYY_PATTERN = "MM-yyyy";
    public static final String MMM_YYYY_PATTERN = "MMM-yyyy";
    public static final String MMM_PATTERN = "MMM";
    public static final String EEE = "EEE";
    public static final String DD_PATTERN = "dd";
    public static final String YY_PATTERN = "yyyy";
    public static final String DATE_STD_PATTERN_FULLDATE = "MMM dd,yyyy";
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SEC_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String yesterday_SEC_DATE_PATTERN = "'yesterday at ' HH:mm";
    public static final String DATE_PATTERN_dd_MMM_YYYY = "dd-MMM-yyyy HH:mm:ss";
    public static final String DATE_PATTERN_dd_MM_YYYY = "dd-MM-yyyy HH:mm:ss";
    public static final String YYYY_MM_DD_FULL_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_PATTERN_MMM_dd = "MMM dd";
    public static final String dd_MMMM_PATTERN = "dd MMMM";
    public static final String EEEE_PATTERN = "EEEE";
    public static final String dd_MM_yyyy_EEEE_PATTERN = "dd/MM/yyyy, EEEE";
    public static final String yyyy_HH_mm_PATTERN = "yyyy, hh:mm";
    public static final String yyyy_HH_mm_PATTERN1 = "yyyy, hh:mm";
    public static final String dd_MM_yyyy_SEC_PATTERN = "dd/MM/yyyy 'at' hh:mm";
    public final static String DATE_PATTERN_SERVICE = "yyyy-MM-dd";
    public final static String FULL_DATE_FORMATE = "EEEE, dd MMM yyyy";


    public static String getDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public static String getDate(Date date, String pattern, Locale locale, String timezone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        if (!StringUtils.isEmpty(timezone))
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return simpleDateFormat.format(date);
    }

    public static String getDate(String date, String parsePattern, String pattern, Locale locale, String timeZone) {


        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern, locale);
            if (!StringUtils.isEmpty(timeZone))
                parseDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            if (date != null)
                return simpleDateFormat.format(parseDateFormat.parse(date));
            else
                return "";
        } catch (ParseException e) {
            e.printStackTrace();
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern, locale);

        }
        return "";
    }

    public static String getYear(String date, String parsePattern, String pattern, Locale locale, String timeZone) {


        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern, locale);
            if (!StringUtils.isEmpty(timeZone))
                parseDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            if (date != null)
                return simpleDateFormat.format(parseDateFormat.parse(date));
            else
                return "";
        } catch (ParseException e) {
            e.printStackTrace();
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern, locale);

        }
        return "";
    }

    public static String getDate(String strDate, String parsePattern, String pattern, long delay, Locale locale) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern, locale);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
            Date date = parseDateFormat.parse(strDate);
            return simpleDateFormat.format(date.getTime() - delay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getEpochTime() {
        return (new Date().getTime()) / 1000;
    }

    public static int[] getCurrentDayMonthYear(String date, String parsePattern) {
        int[] dayMonth = new int[4];
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern, Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parseDateFormat.parse(date));
            dayMonth[0] = calendar.get(Calendar.DAY_OF_MONTH);
            dayMonth[1] = calendar.get(Calendar.MONTH);
            dayMonth[2] = calendar.get(Calendar.YEAR);
            dayMonth[3] = calendar.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayMonth;
    }

    public static boolean compareDate(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(DATE_PATTERN_dd_MM_YYYY);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            if (startDate < endDate)
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean compareDates(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DD_MMM_YYYY_PATTERN);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(startDate);
            date2 = sdf.parse(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date2.before(date1)) {
            return true;
        }

        return false;
    }

    public static boolean compareYear(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(YY_PATTERN);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(startDate);
            date2 = sdf.parse(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1.before(date2)) {
            return true;
        }

        return false;
    }


    public static long getDifferenceTimezone(String startDateStr, String endDateStr, String pattern, String timeZone) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(pattern);
            if (!StringUtils.isEmpty(timeZone))
                parseDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (endDate - startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifferenceTimezone1(String startDateStr, String endDateStr, String pattern, String timeZone) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(pattern);
            if (!StringUtils.isEmpty(timeZone))
                parseDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (startDate - endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifference(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (endDate - (startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifference(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern, int delay) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (endDate + delay - (startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        return simpleDateFormat.format(new Date());
    }

    public static String getCurrentYear() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YY_PATTERN);
        return simpleDateFormat.format(new Date());
    }

    public static int getDifferenceOfWeek(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            Date startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr);
            else
                startDate = new Date();
            Date endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr);
            else
                endDate = new Date();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            return endCalendar.get(Calendar.WEEK_OF_YEAR) - startCalendar.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getDifferenceOfMonth(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            Date startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr);
            else
                startDate = new Date();
            Date endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr);
            else
                endDate = new Date();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            int endYear = endCalendar.get(Calendar.YEAR);
            return (((endYear - startCalendar.get(Calendar.YEAR)) * 12) + (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDay(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
        if (month != 0)
            calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDate(int day, int month, int year, String pattern, Locale locale) {
        Calendar calendar = Calendar.getInstance();
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
        if (month >= 0)
            calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        return simpleDateFormat.format(calendar.getTime());
    }
    public static String getFullDate(int dayOfWeek,int day, int month, int year, String pattern, Locale locale) {
        Calendar calendar = Calendar.getInstance();
        if (dayOfWeek >0)
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
        if (month >= 0)
            calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getValidDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
        if (month != 0)
            calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
       /* if(calendar.getTimeInMillis() > new Date().getTime())
            return "";*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DD_MMM_YYYY_PATTERN, Locale.ENGLISH);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDay(String date, String pattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(pattern);
            Date startDate;
            if (!StringUtils.isEmpty(date))
                startDate = parseDateFormat.parse(date);
            else
                startDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
            return simpleDateFormat.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /*public static String getDayName() {
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = inFormat.parse(input);
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String goal = outFormat.format(date);
        return goal;
    }
*/

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        String d = null;
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                d = "Sunday";
                break;
            case Calendar.MONDAY:
                d = "Monday";
                break;

            case Calendar.TUESDAY:
                d = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                d = "Wednesday";
                break;
            case Calendar.THURSDAY:
                d = "Thursday";
                break;
            case Calendar.FRIDAY:
                d = "Friday";
                break;
            case Calendar.SATURDAY:
                d = "Saturday";
                break;
        }
        String month = String.format(Locale.US, "%tb", calendar);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return (sb.append(d).append(",").append(" ").append(month).append(" ").append(date).append(",").append(" ").append(year).toString());

    }

    public static String getTodayDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        StringBuilder sb = new StringBuilder();
        String d = null;
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                d = "Sunday";
                break;
            case Calendar.MONDAY:
                d = "Monday";
                break;

            case Calendar.TUESDAY:
                d = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                d = "Wednesday";
                break;
            case Calendar.THURSDAY:
                d = "Thursday";
                break;
            case Calendar.FRIDAY:
                d = "Friday";
                break;
            case Calendar.SATURDAY:
                d = "Saturday";
                break;
        }
        String month = String.format(Locale.US, "%tb", calendar);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return (sb.append(d).append(",").append(" ").append(month).append(" ").append(date).append(",").append(" ").append(year).toString());

    }

    public static String getCurrentDate() {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }

    public static String getCurrentDateTimeInTFormate() {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }

    public static long getCurrentTimeInMilli() {
        return System.currentTimeMillis();
    }

    public static String getTimeFromTimestamp(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getTimeFromDate(String date) {
        String[] strDate = date.split("T");
        String[] strTime = strDate[1].split(":");

        return strTime[0] + ":" + strTime[1];

    }

    public static String getFormatedDatefromStringWithTime(String strDate) {
        String formatedDate = null;
        try {
            formatedDate = strDate;
            LogUtils.errorLog(strDate, strDate);
            if (strDate.contains("T")) {
                String dateTime[] = strDate.split("T");
                String arrDate[] = dateTime[0].split("-");
                formatedDate = arrDate[2] + " " + getMonthFromNumber(StringUtils.getInt(arrDate[1])) + ", " + arrDate[0];

                if (dateTime[1].contains(":")) {
                    String arrTime[] = dateTime[1].split(":");
                    formatedDate = formatedDate + " at " + arrTime[0] + ":" + arrTime[1];

                }

            } else {
                String arrDate[] = strDate.split("-");
                formatedDate = arrDate[2] + " " + getMonthFromNumber(StringUtils.getInt(arrDate[1])) + ", " + arrDate[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
            formatedDate = null;
        }
        return formatedDate;
    }

    public static String getDetailTime(String time) {
        NumberFormat nf = NumberFormat.getInstance();
        long millsSec = getDifferenceTimezone(time, "", CalendarUtil.SEC_DATE_PATTERN, "UTC");
        int days = (int) (millsSec / (1000 * 60 * 60 * 24));
        if (days >= 0 && days <= 2) {
            if (days <= 1) {
                int mins = (int) (millsSec / (1000 * 60));
                if (mins >= 0 && (mins / 60) < 24) {

                    if (mins < 60)
                        return nf.format(mins) + " " + IKonnectApplication.mContext.getString(R.string.mins_ago);
                    else
                        return nf.format((mins / 60)) + " " + IKonnectApplication.mContext.getString(R.string.hour_ago);
                } else
                    return getDate(time, CalendarUtil.SEC_DATE_PATTERN, CalendarUtil.yesterday_SEC_DATE_PATTERN, Locale.getDefault(), "UTC");
            } else {
                return getDate(time, CalendarUtil.SEC_DATE_PATTERN, CalendarUtil.yesterday_SEC_DATE_PATTERN, Locale.getDefault(), "UTC");
            }
        } else
            return getDate(time, CalendarUtil.SEC_DATE_PATTERN, CalendarUtil.dd_MM_yyyy_SEC_PATTERN, Locale.getDefault(), "UTC");
    }

    /**
     * this method returns Month name from the int value of month
     *
     * @param intMonth
     **/
    public static String getMonthFromNumber(int intMonth) {
        String strMonth = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        return dfs.getMonths()[intMonth - 1];
       /* switch(intMonth)
        {

            case 1:
                strMonth = "January";break;
            case 2:
                strMonth = "February";break;
            case 3:
                strMonth = "March";break;
            case 4:
                strMonth = "April";break;
            case 5:
                strMonth = "May";break;
            case 6:
                strMonth = "June";break;
            case 7:
                strMonth = "July";break;
            case 8:
                strMonth = "August";break;
            case 9:
                strMonth = "September";break;
            case 10:
                strMonth = "October";break;
            case 11:
                strMonth = "November";break;
            case 12:
                strMonth = "December";break;
        }

        return strMonth.toUpperCase();*/
    }

    /**
     * this method returns Month name from the int value of month
     *
     * @param intMonth
     **/
    public static String getMonthFromNumber_lang(int intMonth) {
        String strMonth = "";

        switch (intMonth) {
            case 1:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.January);
                break;
            case 2:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.February);
                break;
            case 3:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.March);
                break;
            case 4:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.April);
                break;
            case 5:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.May);
                break;
            case 6:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.June);
                break;
            case 7:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.July);
                break;
            case 8:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.August);
                break;
            case 9:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.September);
                break;
            case 10:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.October);
                break;
            case 11:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.November);
                break;
            case 12:
                strMonth = "" + IKonnectApplication.mContext.getString(R.string.December);
                ;
                break;
        }

        return strMonth.toUpperCase();
    }

    public static int getNumberFromMonth(String month) {
        int strMonth = 0;

        switch (month) {
            case "Jan":
                strMonth = 0;
                break;
            case "Feb":
                strMonth = 1;
                break;
            case "Mar":
                strMonth = 2;
                break;
            case "Apr":
                strMonth = 3;
                break;
            case "May":
                strMonth = 4;
                break;
            case "Jun":
                strMonth = 5;
                break;
            case "Jul":
                strMonth = 6;
                break;
            case "Aug":
                strMonth = 7;
                break;
            case "Sep":
                strMonth = 8;
                break;
            case "Oct":
                strMonth = 9;
                break;
            case "Nov":
                strMonth = 10;
                break;
            case "Dec":
                strMonth = 11;
                break;
        }

        return strMonth;
    }

    public static long validateDateOfBrith(int year, int month, int day) {
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, day);
        thatDay.set(Calendar.MONTH, month); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, year);

        Calendar today = Calendar.getInstance();

        long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);
        return days;
    }

    public static String getDate(String strDate, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DD_MMM_YYYY_PATTERN, locale);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(DD_MMM_YYYY_PATTERN, Locale.ENGLISH);
        try {
            Date date = simpleDateFormat1.parse(strDate);
            strDate = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//
        return strDate;
    }


    public static int getDifferenceOfYear(String startDate) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(YY_PATTERN);
            calendar.setTime(sdf.parse(startDate));
            Calendar calendarNow = Calendar.getInstance();
            int yearNow = calendarNow.get(Calendar.YEAR);
            int yearStart = calendar.get(Calendar.YEAR);
            int years = yearNow - yearStart;
            return years;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //added by sandeep form finding No of Days(Mondays or tuesdays)

    public static int findTwoDaysCount(String startDate, String endDate, int DayName1, int DayName2) {
        Date startDATE = null, endDATE = null;
        SimpleDateFormat formatter = new SimpleDateFormat(CalendarUtil.FULL_DATE_FORMATE);
        int count = 0;
        try {
            startDATE = formatter.parse(startDate);
            endDATE = formatter.parse(endDate);

        } catch (ParseException e) {

            e.printStackTrace();
        }


        Calendar c1 = Calendar.getInstance();
        c1.setTime(startDATE);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(endDATE);

        int sundays = 0;
        int saturday = 0;

        while (!c1.after(c2)) {
            if (c1.get(Calendar.DAY_OF_WEEK) == DayName1) {
                saturday++;
            }
            if (c1.get(Calendar.DAY_OF_WEEK) == DayName2) {
                sundays++;
            }

            c1.add(Calendar.DATE, 1);
        }

        // System.out.println("Saturday Count = " + saturday);
        //System.out.println("Sunday Count = " + sundays);
        return saturday + sundays;
    }

    public static String convertUTCtoDateFormat(String strDate,String format){

//        String dateStr = "16/12/2017 07:41";
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df2.format(date);

        return formattedDate;
    }
    public static String dd_MMM_yyyy_to_dd_MM_yyyy(String strDate,String formformat,String toformat){

        String formattedDate="";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formformat);
        try {
            Date varDate=dateFormat.parse(strDate);
            dateFormat=new SimpleDateFormat(toformat);
            formattedDate=dateFormat.format(varDate);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static int CompareDates(String startDate,String endDate){

        int compareResult = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat(CalendarUtil.DD_MMM_YYYY_PATTERN);

        Date arg0Date =null;
        Date arg1Date = null;
        try {
            arg0Date = dateFormat.parse(startDate);
            arg1Date = dateFormat.parse(endDate);

            if ( arg0Date.compareTo(arg1Date)>=1)
                return 1;
            else if ( arg0Date.compareTo(arg1Date)<=-1)
                return -1;
            else
                return 0;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
