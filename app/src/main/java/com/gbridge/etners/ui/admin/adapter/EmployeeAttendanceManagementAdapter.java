package com.gbridge.etners.ui.admin.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gbridge.etners.R;
import com.gbridge.etners.data.DailyAttendanceReportItem;
import com.gbridge.etners.data.EmployeeAttendanceItem;

import java.util.ArrayList;
import java.util.List;


public abstract class EmployeeAttendanceManagementAdapter extends RecyclerView.Adapter<EmployeeAttendanceManagementAdapter.ViewHolder> {


    private List<EmployeeAttendanceItem> items;


    public EmployeeAttendanceManagementAdapter() {
        items = new ArrayList<>();
    }

    public EmployeeAttendanceManagementAdapter(List<EmployeeAttendanceItem> items) {
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee_attendance_management, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        EmployeeAttendanceItem mItem = items.get(position);
        holder.tvName.setText(mItem.getName());
        holder.tvEmployeeNumber.setText(mItem.getEmployeeNumber());



        String department = mItem.getDepartment();

       if(department == null){
           holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_darkgray_view);
           department = "미등록";
       } else if (department.equals("개발팀")){
           holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_primary_view);
       } else if (department.equals("기획팀")){
           holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_8dc100_view);
       } else if (department.equals("경영팀")){
           holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_00c8ff_view);
       } else if (department.equals("디자인팀")){
           holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_a39ef1_view);
       } else {
           holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_ed2939_view);
       }

        holder.tvDepartment.setText(department);

        holder.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionClick(holder, items.get(position));
            }
        });

        String startTime = mItem.getStartTime();
        String endTime = mItem.getEndTime();
        String startDate = mItem.getStartDate();
        String endDate = mItem.getEndDate();

        if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
            holder.tvTimeDivider.setText("미등록");
            holder.tvStartTime.setText("");
            holder.tvEndTime.setText("");
        } else {
            holder.tvTimeDivider.setText("~");
            holder.tvStartTime.setText(startTime);
            holder.tvEndTime.setText(endTime);
        }

        if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
            holder.tvDateDivider.setText("미등록");
            holder.tvStartDate.setText("");
            holder.tvEndDate.setText("");
        } else {
            holder.tvDateDivider.setText("~");
            holder.tvStartDate.setText(startDate);
            holder.tvEndDate.setText(endDate);
        }

    }

    public void changeItems(List<EmployeeAttendanceItem> items) {
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

        public final TextView tvName;
        public final TextView tvDepartment;
        public final TextView tvEmployeeNumber;
        public final TextView tvStartTime;
        public final TextView tvEndTime;
        public final TextView tvStartDate;
        public final TextView tvEndDate;
        public final Button btnOptions;

        public final TextView tvTimeDivider;
        public final TextView tvDateDivider;


        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.item_employee_attendance_management_name);
            tvDepartment = view.findViewById(R.id.item_employee_attendance_management_department);
            tvEmployeeNumber = view.findViewById(R.id.item_employee_attendance_management_employee_number);
            tvStartTime = view.findViewById(R.id.item_employee_attendance_management_start_time);
            tvEndTime = view.findViewById(R.id.item_employee_attendance_management_end_time);
            tvStartDate = view.findViewById(R.id.item_employee_attendance_management_start_date);
            tvEndDate = view.findViewById(R.id.item_employee_attendance_management_end_date);
            btnOptions = view.findViewById(R.id.btn_employee_attendance_management_options);

            tvTimeDivider = view.findViewById(R.id.item_employee_attendance_management_time_divider);
            tvDateDivider = view.findViewById(R.id.item_employee_attendance_management_date_divider);
        }
    }

    protected abstract void onOptionClick(ViewHolder holder, EmployeeAttendanceItem item);
}