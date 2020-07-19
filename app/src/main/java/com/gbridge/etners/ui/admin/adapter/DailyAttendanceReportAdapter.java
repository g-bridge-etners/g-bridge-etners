package com.gbridge.etners.ui.admin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gbridge.etners.R;
import com.gbridge.etners.data.DailyAttendanceReportItem;
import com.jaygoo.widget.RangeSeekBar;
import com.jaygoo.widget.SeekBar;


import java.sql.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class DailyAttendanceReportAdapter extends RecyclerView.Adapter<DailyAttendanceReportAdapter.ViewHolder> {


    private List<DailyAttendanceReportItem> items;

    public DailyAttendanceReportAdapter() {
        this.items = new ArrayList<>();
    }

    public DailyAttendanceReportAdapter(List<DailyAttendanceReportItem> items) {
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_attendance_report, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DailyAttendanceReportItem mItem = items.get(position);

        holder.tvName.setText(mItem.getName());
        holder.tvDepartment.setText(mItem.getDepartment());
        holder.tvEmployeeNumber.setText(mItem.getEmployeeNumber());


        /*******************************************************************************

         TODO: 작업2 : 근무상태 목록 정하고 변경 필요

         ******************************************************************************/
        String status = mItem.getStatus();
        switch (status) {
            case "출근":
                holder.tvStatus.setBackgroundResource(R.drawable.bg_round_a39ef1_view);
                break;
            case "퇴근":
                holder.tvStatus.setBackgroundResource(R.drawable.bg_round_00c8ff_view);
                break;
            case "근무":
                holder.tvStatus.setBackgroundResource(R.drawable.bg_round_8dc100_view);
                break;
            default:
                holder.tvStatus.setBackgroundResource(R.drawable.bg_round_000181_view);
        }
        holder.tvStatus.setText(status);



        RangeSeekBar rangeSeekBar = holder.rangeSeekBar;
        rangeSeekBar.setPadding(20, 0, 20, 0);
        rangeSeekBar.getLeftSeekBar().setIndicatorPaddingLeft(15);
        rangeSeekBar.getRightSeekBar().setIndicatorPaddingRight(15);
        rangeSeekBar.setEnabled(false);


        String startTime = mItem.getStartTime();
        String endTime = mItem.getEndTime();

        if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
            String[] tickArray = new String[5];
            Arrays.fill(tickArray, "");
            tickArray[2] = "등록된 근무가 없습니다.";
            rangeSeekBar.setTickMarkTextArray(tickArray);
            SeekBar leftSeekBar = rangeSeekBar.getLeftSeekBar();
            SeekBar rightSeekbar = rangeSeekBar.getRightSeekBar();
            leftSeekBar.setThumbDrawableId(R.drawable.ic_circle_invisible);
            rightSeekbar.setThumbDrawableId(R.drawable.ic_circle_invisible);
            leftSeekBar.setIndicatorTextColor(Color.TRANSPARENT);
            rightSeekbar.setIndicatorTextColor(Color.TRANSPARENT);
            return;
        } else {
            String[] tickArray = new String[2];
            tickArray[0] = startTime;
            tickArray[1] = endTime;
            rangeSeekBar.setTickMarkTextArray(tickArray);
        }


        String clockInTime = mItem.getClockInTime();
        String clockOutTIme = mItem.getClockOutTime();

        if (clockInTime == null || clockInTime.isEmpty()) {
            SeekBar leftSeekBar = rangeSeekBar.getLeftSeekBar();
            SeekBar rightSeekbar = rangeSeekBar.getRightSeekBar();
            leftSeekBar.setThumbDrawableId(R.drawable.ic_circle_invisible);
            rightSeekbar.setThumbDrawableId(R.drawable.ic_circle_invisible);
            leftSeekBar.setIndicatorTextColor(Color.TRANSPARENT);
            rightSeekbar.setIndicatorTextColor(Color.TRANSPARENT);

        } else if (clockOutTIme == null || clockOutTIme.isEmpty()) {
            SeekBar rightSeekbar = rangeSeekBar.getRightSeekBar();
            rightSeekbar.setThumbDrawableId(R.drawable.ic_circle_invisible);
            rightSeekbar.setIndicatorTextColor(Color.TRANSPARENT);
            rangeSeekBar.setProgress(0,0);
            rangeSeekBar.getLeftSeekBar().setIndicatorText(mItem.getClockInTime() + " 출근");
        } else {
            float leftSeekBarPercent = 0;
            float rightSeekBarPercent = 100;
            try {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.KOREA);
                Long longStartTime = timeFormat.parse(mItem.getStartTime()).getTime();
                Long longEndTime = timeFormat.parse(mItem.getEndTime()).getTime();
                Long longClockInTime = timeFormat.parse(mItem.getClockInTime()).getTime();
                Long longClockOutTime = timeFormat.parse(mItem.getClockOutTime()).getTime();
                long total = longEndTime - longStartTime;

                if (longClockInTime > longStartTime) {
                    long left = longClockInTime - longStartTime;
                    leftSeekBarPercent = left * 100f / total;
                }

                if (longClockOutTime < longEndTime) {
                    long right = longClockOutTime - longStartTime;
                    rightSeekBarPercent = right * 100f / total;
                }


            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }

            rangeSeekBar.setProgress(leftSeekBarPercent, rightSeekBarPercent);
            rangeSeekBar.getLeftSeekBar().setIndicatorText(mItem.getClockInTime() + " 출근");
            rangeSeekBar.getRightSeekBar().setIndicatorText(mItem.getClockOutTime() + " 퇴근");
        }


    }

    public void changeItems(List<DailyAttendanceReportItem> items) {
        if (this.items != null) {
            this.items.clear();
        }
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvName;
        final TextView tvDepartment;
        final TextView tvEmployeeNumber;
        final TextView tvStatus;
        final RangeSeekBar rangeSeekBar;

        public ViewHolder(View view) {
            super(view);
            rangeSeekBar = view.findViewById(R.id.seek_bar_item_daily_attendance_report);
            tvName = view.findViewById(R.id.tv_daily_attendance_report_name);
            tvDepartment = view.findViewById(R.id.tv_daily_attendance_report_department);
            tvEmployeeNumber = view.findViewById(R.id.tv_daily_attendance_report_employee_number);
            tvStatus = view.findViewById(R.id.tv_daily_attendance_report_status);

        }
    }


}