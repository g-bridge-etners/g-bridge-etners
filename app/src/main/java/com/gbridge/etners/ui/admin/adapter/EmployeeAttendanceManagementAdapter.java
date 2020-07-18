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
        holder.tvClockInTime.setText(mItem.getClockInTime());
        holder.tvClockOutTime.setText(mItem.getClockOutTime());
        holder.tvDateStart.setText(mItem.getStartDate());
        holder.tvDateEnd.setText(mItem.getEndDate());

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


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvName;
        public final TextView tvDepartment;
        public final TextView tvEmployeeNumber;
        public final TextView tvClockInTime;
        public final TextView tvClockOutTime;
        public final TextView tvDateStart;
        public final TextView tvDateEnd;
        public final Button btnOptions;


        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.item_employee_attendance_management_name);
            tvDepartment = view.findViewById(R.id.item_employee_attendance_management_department);
            tvEmployeeNumber = view.findViewById(R.id.item_employee_attendance_management_employee_number);
            tvClockInTime = view.findViewById(R.id.item_employee_attendance_management_clock_in_time);
            tvClockOutTime = view.findViewById(R.id.item_employee_attendance_management_clock_out_time);
            tvDateStart = view.findViewById(R.id.item_employee_attendance_management_date_start);
            tvDateEnd = view.findViewById(R.id.item_employee_attendance_management_date_end);
            btnOptions = view.findViewById(R.id.btn_employee_attendance_management_options);
        }
    }

    protected abstract void onOptionClick(ViewHolder holder);
}