package com.winit.iKonnect.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winit.common.util.CalendarUtil;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.CalenderDetailDo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rahul.Yadav on 7/4/2017.
 */

public class CalenderViewBottomsheet extends BottomSheetDialogFragment {
    private TextView tv_subject, tv_desc, tv_startdate, tv_enddate, tvavailablity, tv_organiser, tv_loc, tv_attendance;
    private CalenderDetailDo aDo = null;
    public Context context;

    public CalenderViewBottomsheet() {
    }

    /*public CalenderViewBottomsheet(CalenderDetailDo aDo) {
        this.aDo = aDo;
    }*/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calenderbottomsheet, container, false);
        tv_subject = (TextView) view.findViewById(R.id.tv_subject);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        tv_enddate = (TextView) view.findViewById(R.id.tv_enddate);
        tvavailablity = (TextView) view.findViewById(R.id.tvavailablity);
        tv_organiser = (TextView) view.findViewById(R.id.tv_organiser);
        tv_attendance = (TextView) view.findViewById(R.id.tv_attendance);
        tv_loc = (TextView) view.findViewById(R.id.tv_loc);
        tv_startdate = (TextView) view.findViewById(R.id.tv_startdate);
        try {
            initializeControls(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initializeControls(final View view) throws ParseException {
        context = getActivity();
        tv_subject.setText("" + aDo.getSubject());
        tv_desc.setText("" + aDo.getDescription());
        tvavailablity.setText("" + aDo.getAvailability());
        tv_organiser.setText("" + aDo.getOrganizer());
        tv_loc.setText("" + aDo.getLocation());
        if (aDo.getStatus().equalsIgnoreCase("1"))
            tv_attendance.setText("" + getString(R.string.planned));
        else if (aDo.getStatus().equalsIgnoreCase("2"))
            tv_attendance.setText("" + getString(R.string.approved));
        else if (aDo.getStatus().equalsIgnoreCase("3"))
            tv_attendance.setText("" + getString(R.string.Cancelled));
        else if (aDo.getStatus().equalsIgnoreCase("4"))
            tv_attendance.setText("" + getString(R.string.Tentative));
        else if (aDo.getStatus().equalsIgnoreCase("5"))
            tv_attendance.setText("" + getString(R.string.Confirmed));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(CalendarUtil.DATE_PATTERN);
        Date parsedDate = simpleDateFormat2.parse(aDo.getEndDate());
        simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        String newFormatttedDate = simpleDateFormat2.format(parsedDate);
        tv_enddate.setText("" + newFormatttedDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtil.DATE_PATTERN);
        Date parsedDate1 = simpleDateFormat.parse(aDo.getStartDate());
        String newFormatttedDate1 = simpleDateFormat2.format(parsedDate1);
        tv_startdate.setText("" + newFormatttedDate1);
    }
}
