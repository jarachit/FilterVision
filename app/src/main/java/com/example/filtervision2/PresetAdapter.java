package com.example.filtervision2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class PresetAdapter extends RecyclerView.Adapter<PresetAdapter.Holder> {
    private Context context;
    private List<PresetModel> taskModelList;

    public PresetAdapter(Context context, List<PresetModel> taskModelList) {
        this.context = context;
        this.taskModelList = taskModelList;
        Collections.reverse(taskModelList);
    }

    public void setTaskModelList(List<PresetModel> taskModelList) {
        this.taskModelList = taskModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context)
                .inflate(R.layout.layout_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.d("Daily", "taskModelList: " + taskModelList);
        holder.txtTaskName.setText(taskModelList.get(position).getMatrixValue());
        holder.txtTaskAddTime.setText(String.valueOf(taskModelList.get(position).getpresetNumber()));
    }

    @Override
    public int getItemCount() {
        return taskModelList != null ? taskModelList.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtTaskName, txtTaskAddTime;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtTaskName = itemView.findViewById(R.id.txt_task_name);
            txtTaskAddTime = itemView.findViewById(R.id.txt_date);
        }
    }

}
