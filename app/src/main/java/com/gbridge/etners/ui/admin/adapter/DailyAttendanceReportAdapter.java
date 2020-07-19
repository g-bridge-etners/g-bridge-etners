package com.gbridge.etners.ui.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.gbridge.etners.R;
import com.gbridge.etners.data.DailyAttendanceReportItem;
import com.jaygoo.widget.RangeSeekBar;


import java.sql.Timestamp;
import java.util.List;


public class DailyAttendanceReportAdapter extends RecyclerView.Adapter<DailyAttendanceReportAdapter.ViewHolder> {


    private final List<DailyAttendanceReportItem> items;


    public DailyAttendanceReportAdapter(List<DailyAttendanceReportItem> items) {
        this.items = items;
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
        holder.rangeSeekBar.setProgress(0,80);
        holder.rangeSeekBar.getLeftSeekBar().setIndicatorText("09:00 출근");
        holder.rangeSeekBar.getLeftSeekBar().setIndicatorPaddingLeft(15);
        holder.rangeSeekBar.getRightSeekBar().setIndicatorText("17:00 퇴근");
        holder.rangeSeekBar.getRightSeekBar().setIndicatorPaddingRight(15);
        holder.rangeSeekBar.setEnabled(false);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        RangeSeekBar rangeSeekBar;
        public ViewHolder(View view) {
            super(view);
            rangeSeekBar = view.findViewById(R.id.seek_bar_item_daily_attendance_report);


        }
    }


    private class myRangeSeekbar extends RangeSeekBar{

        public myRangeSeekbar(Context context) {
            super(context);
        }


    }
}