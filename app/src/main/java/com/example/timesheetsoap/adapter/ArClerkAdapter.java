package com.example.timesheetsoap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timesheetsoap.R;
import com.example.timesheetsoap.models.InvoiceModel;

import java.util.List;

public class ArClerkAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<InvoiceModel> invoiceModels;

    public ArClerkAdapter(Context context, int layout, List<InvoiceModel> invoiceModels) {
        this.context = context;
        this.layout = layout;
        this.invoiceModels = invoiceModels;
    }

    @Override
    public int getCount() {
        return invoiceModels.size();
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
        TextView start, end, time, idnv, tennv, task, project, client;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ArClerkAdapter.ViewHolder holder;

        if (view == null) {
            holder = new ArClerkAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
            holder.start = view.findViewById(R.id.textView11);
            holder.end = view.findViewById(R.id.textView12);
            holder.time = view.findViewById(R.id.textView13);
            holder.idnv = view.findViewById(R.id.textView14);
            holder.tennv = view.findViewById(R.id.textView18);
            holder.task = view.findViewById(R.id.textView15);
            holder.project = view.findViewById(R.id.textView16);
            holder.client = view.findViewById(R.id.textView17);
            view.setTag(holder);
        } else {
            holder = (ArClerkAdapter.ViewHolder) view.getTag();
        }

        InvoiceModel quanLy = invoiceModels.get(i);
        holder.start.setText(quanLy.getStartDate());
        holder.end.setText(quanLy.getEndDate());
        holder.time.setText(quanLy.getInvoiceTime());
        holder.idnv.setText(quanLy.getWorkerId().getId());
        holder.tennv.setText(quanLy.getWorkerId().getName());
        holder.task.setText(quanLy.getTaskIV());
        holder.project.setText(quanLy.getProjectIV());
        holder.client.setText(quanLy.getClientName());

        return view;
    }
}
