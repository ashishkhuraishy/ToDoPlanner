package com.blogspot.codecampanion.todoplanner.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.codecampanion.todoplanner.R;
import com.blogspot.codecampanion.todoplanner.Room.SubTask.SubTask;


public class SubTaskAdapter extends ListAdapter<SubTask, SubTaskAdapter.SubTaskViewHolder> {

    private setOnItemClickListner listner;

    public SubTaskAdapter() {
        super(DIFF_UTIL);
    }

    private static DiffUtil.ItemCallback<SubTask> DIFF_UTIL = new DiffUtil.ItemCallback<SubTask>() {
        @Override
        public boolean areItemsTheSame(@NonNull SubTask oldItem, @NonNull SubTask newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SubTask oldItem, @NonNull SubTask newItem) {
            return oldItem.getSubTask().equals(newItem.getSubTask()) && (oldItem.getTaskId() == newItem.getTaskId());
        }
    };

    class SubTaskViewHolder extends RecyclerView.ViewHolder{

        private TextView subTaskText;

        SubTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            subTaskText = itemView.findViewById(R.id.subTaskListText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listner != null && position != RecyclerView.NO_POSITION){
                        listner.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.subtask_list_activity, parent, false
        );

        return new SubTaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) {

        SubTask currentSubTask = getItem(position);

        holder.subTaskText.setText(currentSubTask.getSubTask());
    }



    public interface setOnItemClickListner{
        void onItemClick(SubTask subTask);
    }


    public void setListner(setOnItemClickListner listner) {
        this.listner = listner;
    }

    public SubTask getSubTask(int position){
        return getItem(position);
    }
}
