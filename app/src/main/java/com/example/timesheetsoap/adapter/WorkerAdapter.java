package com.example.timesheetsoap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timesheetsoap.R;
import com.example.timesheetsoap.models.HistoryModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class WorkerAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<HistoryModel> historyModelList;

    public WorkerAdapter(Context context, int layout, List<HistoryModel> historyModelList) {
        this.context = context;
        this.layout = layout;
        this.historyModelList = historyModelList;
    }

    @Override
    public int getCount() {
        return historyModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView start, end, time, task, project, state;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
            holder.start = view.findViewById(R.id.tvStartTimeH);
            holder.end = view.findViewById(R.id.tvEndTimeH);
            holder.time = view.findViewById(R.id.tvTotalTime);
            holder.task = view.findViewById(R.id.tvTaskH);
            holder.project = view.findViewById(R.id.tvProjectH);
            holder.state = view.findViewById(R.id.tvAccept);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        HistoryModel lichSu = historyModelList.get(i);

        String dateStart = lichSu.getStartTimeH();
        String dateEnd = lichSu.getEndTimeH();

        holder.start.setText(dateformat(dateStart));
        holder.end.setText(dateformat(dateEnd));
        holder.time.setText(lichSu.getTotalTime()+"");
        holder.task.setText(lichSu.getTaskH());
        holder.project.setText(lichSu.getProjectH());
        String op = "";
        if(lichSu.isAccept()) op = "Chấp nhận";
        else op = "Từ chối";
        holder.state.setText(op);

        return view;
    }

    public String dateformat(String date){
        return date.replace("T", " ");
    }
}
