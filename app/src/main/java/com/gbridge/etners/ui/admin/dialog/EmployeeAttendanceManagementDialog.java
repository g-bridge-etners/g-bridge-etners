package com.gbridge.etners.ui.admin.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gbridge.etners.R;
import com.gbridge.etners.data.EmployeeAttendanceManagementItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import cn.qqtheme.framework.picker.TimePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;


public abstract class EmployeeAttendanceManagementDialog extends Dialog implements View.OnClickListener {
    private EditText edtTitle;
    private EditText edtDescription;
    private EditText edtStartDate;
    private EditText edtEndDate;
    private EditText edtStartTime;
    private EditText edtEndTime;
    private Button btnSubmit;
    private Button btnCancle;

    private EmployeeAttendanceManagementItem item;
    private Activity activity;


    public EmployeeAttendanceManagementDialog(@NonNull Activity activity, EmployeeAttendanceManagementItem item) {
        super(activity);
        this.item = item;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_attendance_management);

        edtTitle = (EditText) findViewById(R.id.edt_dialog_attendance_management_title);
        edtDescription = (EditText) findViewById(R.id.edt_dialog_attendance_management_description);
        edtStartDate = (EditText) findViewById(R.id.edt_dialog_attendance_management_start_date);
        edtEndDate = (EditText) findViewById(R.id.edt_dialog_attendance_management_end_date);
        edtStartTime = (EditText) findViewById(R.id.edt_dialog_attendance_management_start_time);
        edtEndTime = (EditText) findViewById(R.id.edt_dialog_attendance_management_end_time);
        btnSubmit = (Button) findViewById(R.id.btn_dialog_attendance_management_submit);
        btnCancle = (Button) findViewById(R.id.btn_dialog_attendance_management_cancle);

        edtStartDate.setOnClickListener(this);
        edtEndDate.setOnClickListener(this);
        edtStartTime.setOnClickListener(this);
        edtEndTime.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

        if(item != null){
            edtTitle.setText(item.getTitle());
            edtDescription.setText(item.getDescription());
            edtStartDate.setText(item.getStartDate());
            edtEndDate.setText(item.getEndDate());
            edtStartTime.setText(item.getStartTime());
            edtEndTime.setText(item.getEndTime());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_dialog_attendance_management_start_date:
                new myDatePicker() {
                    @Override
                    protected void onDatePickedExternal(String year, String month, String day) {
                        edtStartDate.setText(year.substring(2) + "-" + month + "-" + day);
                    }
                };
                break;
            case R.id.edt_dialog_attendance_management_end_date:
                new myDatePicker() {
                    @Override
                    protected void onDatePickedExternal(String year, String month, String day) {
                        edtEndDate.setText(year.substring(2) + "-" + month + "-" + day);
                    }
                };
                break;
            case R.id.edt_dialog_attendance_management_start_time:
                new myTimePicker() {
                    @Override
                    protected void onTimePickedExternal(String hour, String minute) {
                        edtStartTime.setText(hour + ":" + minute);
                    }
                };
                break;
            case R.id.edt_dialog_attendance_management_end_time:
                new myTimePicker() {
                    @Override
                    protected void onTimePickedExternal(String hour, String minute) {
                        edtEndTime.setText(hour + ":" + minute);
                    }
                };
                break;
            case R.id.btn_dialog_attendance_management_submit:
                String title = edtTitle.getText().toString();
                String description = edtDescription.getText().toString();
                String startDate = edtStartDate.getText().toString();
                String endDate = edtEndDate.getText().toString();
                String startTime = edtStartTime.getText().toString();
                String endTime = edtEndTime.getText().toString();

                if(title.isEmpty()||description.isEmpty()||startDate.isEmpty()||endDate.isEmpty()||startTime.isEmpty()||endTime.isEmpty()){
                    Toast.makeText(activity, "모든 항목을 빠짐없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    item = new EmployeeAttendanceManagementItem(

                            edtTitle.getText().toString(),
                            edtDescription.getText().toString(),
                            edtStartDate.getText().toString(),
                            edtEndDate.getText().toString(),
                            edtStartTime.getText().toString(),
                            edtEndTime.getText().toString()
                    );
                    onPositiveClicked(item);
                    dismiss();
                }
                break;
            case R.id.btn_dialog_attendance_management_cancle:
                onNegativeClicked();
                cancel();
                break;
        }
    }


    public abstract void onPositiveClicked(EmployeeAttendanceManagementItem item);

    public abstract void onNegativeClicked();

    private abstract class myDatePicker {
        public myDatePicker() {
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.KOREA);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.KOREA);
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

            String year = yearFormat.format(currentTime);
            String month = monthFormat.format(currentTime);
            String day = dayFormat.format(currentTime);

            final cn.qqtheme.framework.picker.DatePicker picker = new cn.qqtheme.framework.picker.DatePicker(activity);
            picker.setTopLineHeight(0);
            picker.setDividerColor(activity.getResources().getColor(R.color.colorGainsboro));
            picker.setTextColor(activity.getResources().getColor(R.color.colorDarkGray));
            picker.setLabelTextColor(activity.getResources().getColor(R.color.colorDarkGray));
            picker.setSubmitTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            picker.setCancelTextColor(activity.getResources().getColor(R.color.colorGray));
            picker.setLabel("년", "월", "일");
            picker.setCanceledOnTouchOutside(true);
            picker.setUseWeight(true);
            picker.setTopPadding(ConvertUtils.toPx(getContext(), 10));
            picker.setRangeEnd(2040, 12, 31);
            picker.setRangeStart(2000, 1, 1);
            picker.setSelectedItem(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            picker.setResetWhileWheel(false);
            picker.setOnDatePickListener(new cn.qqtheme.framework.picker.DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    onDatePickedExternal(year, month, day);
                }
            });
            picker.setOnWheelListener(new cn.qqtheme.framework.picker.DatePicker.OnWheelListener() {
                @Override
                public void onYearWheeled(int index, String year) {
                }

                @Override
                public void onMonthWheeled(int index, String month) {
                }

                @Override
                public void onDayWheeled(int index, String day) {
                }
            });
            picker.show();
        }

        protected abstract void onDatePickedExternal(String year, String month, String day);
    }

    private abstract class myTimePicker {

        public myTimePicker() {
            TimePicker picker = new TimePicker(activity, TimePicker.HOUR_24);
            picker.setDividerColor(activity.getResources().getColor(R.color.colorGainsboro));
            picker.setTextColor(activity.getResources().getColor(R.color.colorDarkGray));
            picker.setLabelTextColor(activity.getResources().getColor(R.color.colorDarkGray));
            picker.setSubmitTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            picker.setCancelTextColor(activity.getResources().getColor(R.color.colorGray));
            picker.setLabel("시", "분");
            picker.setUseWeight(false);
            picker.setCycleDisable(false);
            picker.setRangeStart(0, 0);//00:00
            picker.setRangeEnd(23, 59);//23:59
            Calendar calendar = Calendar.getInstance(Locale.KOREA);
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);
            picker.setSelectedItem(currentHour, currentMinute);
            picker.setTopLineVisible(false);
            picker.setTextPadding(ConvertUtils.toPx(activity, 15));
            picker.setOnTimePickListener(new cn.qqtheme.framework.picker.TimePicker.OnTimePickListener() {
                @Override
                public void onTimePicked(String hour, String minute) {
                    onTimePickedExternal(hour, minute);
                }
            });
            picker.show();
        }

        protected abstract void onTimePickedExternal(String hour, String minute);
    }
}
