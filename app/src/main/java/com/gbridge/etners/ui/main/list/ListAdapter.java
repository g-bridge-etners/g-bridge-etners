package com.gbridge.etners.ui.main.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gbridge.etners.R;
import com.gbridge.etners.ui.main.list.models.ListResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.gbridge.etners.ApplicationClass.DATE_FORMAT;
import static com.gbridge.etners.ApplicationClass.DATE_FORMAT2;
import static com.gbridge.etners.ApplicationClass.TIME_FORMAT;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<ListResponse.ListHistory> mListHistory;
    private LayoutInflater mInflate;
    private Context mContext;

    public ListAdapter(ArrayList<ListResponse.ListHistory> mListHistory, Context mContext) {
        this.mListHistory = mListHistory;
        this.mInflate = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = mInflate.inflate(R.layout.recyclerview_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);

        return viewHolder;

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        final ListResponse.ListHistory listHistory = mListHistory.get(position);

        String year, month, dayNum, dayText = null, inTime = null, outTime = null, schedule = null, rTHour = null, rTMinute = null;
        int inHour, inMinute, outHour, outMinute, tHour, tMinute;
        Date iTime = null, oTime = null;

        if(listHistory.getClockOutTime() != null) {
            //날짜 쪼개서 넣어주기
            String[] date = listHistory.getDate().split("-");
            year = date[0];
            month = date[1];
            dayNum = date[2];

            //시간 쪼개서 넣어주기
            String[] arrInTime = listHistory.getClockInTime().split(":");
            String[] arrOutTime = listHistory.getClockOutTime().split(":");

            inHour = Integer.parseInt(arrInTime[0]);
            inMinute = Integer.parseInt(arrInTime[1]);
            outHour = Integer.parseInt(arrOutTime[0]);
            outMinute = Integer.parseInt(arrOutTime[1]);

            //총 근무 시간 계산
            tHour = outHour - inHour;
            if (outMinute < inMinute) {
                tHour -= 1;
                tMinute = outMinute - inMinute + 60;
            } else {
                tMinute = outMinute - inMinute;
            }

            if(tHour < 10){
                rTHour = "0"+ tHour;
            }else{
                rTHour = String.valueOf(tHour);
            }
            if(tMinute < 10){
                rTMinute = "0"+ tMinute;
            }else{
                rTMinute = String.valueOf(tMinute);
            }

            try {
                //요일 구하기
                dayText = getDateDay(listHistory.getDate());
                //시간 형식 변경
                iTime = TIME_FORMAT.parse(listHistory.getClockInTime());
                oTime = TIME_FORMAT.parse(listHistory.getClockOutTime());
                inTime = String.valueOf(iTime);
                outTime = String.valueOf(oTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //시간 별로 근무일정 다르게 표시
            try {
                if (iTime.after(TIME_FORMAT.parse("08:00")) && !oTime.before(TIME_FORMAT.parse("17:00"))) {
                    schedule = "지각";
                    holder.mListSchedule.setTextColor(Color.RED);
                } else if (oTime.before(TIME_FORMAT.parse("17:00")) && !iTime.after(TIME_FORMAT.parse("08:00"))) {
                    schedule = "오후반차";
                    holder.mListSchedule.setTextColor(Color.RED);

                }else if(iTime.after(TIME_FORMAT.parse("08:00")) && oTime.before(TIME_FORMAT.parse("17:00"))){
                    schedule = "지각, 오후반차";
                    holder.mListSchedule.setTextColor(Color.RED);
                } else {
                    schedule = "정상근무";
                    holder.mListSchedule.setTextColor(R.color.colorLittleBlack);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date currentTime = Calendar.getInstance().getTime();
            String today = DATE_FORMAT2.format(currentTime);

            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            int top = Math.round(20 * dm.density);
            int start = Math.round(20 * dm.density);
            int end = Math.round(20 * dm.density);
            int bottom = Math.round(20 * dm.density);

            if(today.equals(listHistory.getDate())){
                holder.mListAll.setBackgroundResource(R.drawable.border_orange);
                holder.mListDivider.setVisibility(View.GONE);
                holder.mListAll.setPadding(start, top,end, bottom);
            }else {
                holder.mListAll.setBackground(null);
                holder.mListAll.setPadding(start, top,end,0);
                if (position == 0) {
                    holder.mListDivider.setVisibility(View.GONE);
                    holder.mListAll.setPadding(start, top,end, bottom);
                } else {
                    holder.mListDivider.setVisibility(View.VISIBLE);
                }
            }

            holder.mListYearMonth.setText(year + "년 " + month + "월");
            holder.mListDayNum.setText(dayNum);
            holder.mListDayText.setText(dayText);
            holder.mListInOutTime.setText(arrInTime[0] + ":"+ arrInTime[1] + " ~ " + arrOutTime[0] + ":" + arrOutTime[1]);
            holder.mListTotalTime.setText(rTHour + "시간 " + rTMinute + "분");
            holder.mListSchedule.setText(schedule);
        }
    }


    @Override
    public int getItemCount() {
        return mListHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mListAll;
        private TextView mListYearMonth, mListDayNum, mListDayText, mListInOutTime, mListTotalTime, mListSchedule;
        private View mListDivider;

        public ViewHolder(View convertView) {
            super(convertView);

            mListAll = convertView.findViewById(R.id.list_rv_all);
            mListYearMonth = convertView.findViewById(R.id.list_rv_year_month);
            mListDayNum = convertView.findViewById(R.id.list_rv_day_number);
            mListDayText = convertView.findViewById(R.id.list_rv_day_char);
            mListInOutTime = convertView.findViewById(R.id.list_rv_in_out_time);
            mListTotalTime = convertView.findViewById(R.id.list_rv_total_time);
            mListSchedule = convertView.findViewById(R.id.list_rv_schedule);
            mListDivider = convertView.findViewById(R.id.list_rv_divider);
        }
    }

    public static String getDateDay(String date) throws ParseException {
        //받아온 날짜에 따른 요일 구하기
        String day = null;

        Date nDate = DATE_FORMAT.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        int changeDay = cal.get(Calendar.DAY_OF_WEEK);

        switch (changeDay) {
            case 1:
                day = "화";
                break;
            case 2:
                day = "수";
                break;
            case 3:
                day = "목";
                break;
            case 4:
                day = "금";
                break;
            case 5:
                day = "토";
                break;
            case 6:
                day = "일";
                break;
            case 7:
                day = "월";
                break;
        }
        return day;
    }
}
