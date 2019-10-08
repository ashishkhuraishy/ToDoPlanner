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
import com.blogspot.codecampanion.todoplanner.Room.Task.Task;


public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {

    private setOnItemClickListener listener;

    public void setListener(setOnItemClickListener listener) {
        this.listener = listener;
    }

    public TaskAdapter(){
        super(DIFF_UTIL);
    }

    private static DiffUtil.ItemCallback<Task> DIFF_UTIL = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getTask().equals(newItem.getTask());
        }
    };


    public Task getTask(int position){
        return getItem(position);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView taskText;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.taskText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.OnItemClick(getItem(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.task_list_activity, parent, false
        );

        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentPosition = getItem(position);

        holder.taskText.setText(currentPosition.getTask());
    }


    public interface setOnItemClickListener{
        void OnItemClick(Task task);
    }


}
