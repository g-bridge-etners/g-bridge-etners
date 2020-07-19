package com.gbridge.etners.ui.admin.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gbridge.etners.R;
import com.gbridge.etners.data.EmployeeAttendanceItem;

import java.util.List;


public abstract class EmployeeAttendanceManagementAdapter extends RecyclerView.Adapter<EmployeeAttendanceManagementAdapter.ViewHolder> {


    private final List<EmployeeAttendanceItem> items;


    public EmployeeAttendanceManagementAdapter(List<EmployeeAttendanceItem> items) {
        this.items = items;
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

        String startTime = mItem.getStartTime();
        String endTime = mItem.getEndTime();
        String startDate = mItem.getStartDate();
        String endDate = mItem.getEndDate();

        if(startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()){
            holder.tvTimeDivider.setText("미등록");
        } else {
            holder.tvStartTime.setText(mItem.getStartTime());
            holder.tvEndTime.setText(mItem.getEndTime());
        }

        if(startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()){
            holder.tvDateDivider.setText("미등록");
        } else {
            holder.tvStartDate.setText(mItem.getStartDate());
            holder.tvEndDate.setText(mItem.getEndDate());
        }


        String department = mItem.getDepartment();

        // TODO: 부서 목록 정해지면 목록에 맞게수정 필요
        switch (department) {
            case "개발팀":
                holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_a39ef1_view);
                break;
            case "기획팀":
                holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_00c8ff_view);
                break;
            case "경영팀":
                holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_8dc100_view);
                break;
            default:
                holder.tvDepartment.setBackgroundResource(R.drawable.bg_round_primary_view);
        }
        holder.tvDepartment.setText(department);

        holder.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionClick(holder);
            }
        });
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

    protected abstract void onOptionClick(ViewHolder holder);
}