package pl.pb.pbtask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.pb.pbtask.Room.Task;
import pl.pb.pbtask.Room.TaskViewModel;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-30
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private static final String TAG = "RecyclerAdapter";

    private List<Task> tasks = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = tasks.get(position);
        holder.task_id.setText(current.getTitle());
        holder.task_title.setText(current.getTitle());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    Task getTaskAt(int position) {
        return tasks.get(position);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView task_id, task_title, task_difficulty, task_date, task_time, task_repeat,
                task_repeat_type, task_repeat_number, task_finish;

        private TaskViewHolder(View itemView) {
            super(itemView);
            task_id = itemView.findViewById(R.id.task_id);
            task_title = itemView.findViewById(R.id.task_title);
            task_difficulty = null;
            task_date = null;
            task_time = null;
            task_repeat = null;
            task_repeat_type = null;
            task_repeat_number = null;
            task_finish = null;

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(tasks.get(position));
                }
            });

            itemView.setOnLongClickListener(view -> {
                Toast.makeText(view.getContext(),"delete long click" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                return true;
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
