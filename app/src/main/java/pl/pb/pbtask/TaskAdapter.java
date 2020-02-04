package pl.pb.pbtask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.pb.pbtask.Room.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private final LayoutInflater layoutInflater;
    private List<Task> tasks;

    TaskAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recyclerview_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (tasks != null) {
            Task current = tasks.get(position);
            holder.task_id.setText("id");
            holder.task_title.setText(current.getTitle());
        } else {
            holder.task_id.setText("Empty");
            holder.task_title.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        if (tasks != null) {
            return tasks.size();
        } else return 0;
    }

    void setTasks(List<Task> tasks_1) {
        tasks = tasks_1;
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView task_id, task_title, task_difficulty, task_date, task_time, task_repeat,
                task_repeat_type, task_repeat_number, task_finish;

        private TaskViewHolder(View itemView) {
            super(itemView);

            task_title = itemView.findViewById(R.id.task_title);
            task_id = itemView.findViewById(R.id.task_id);
            task_difficulty = null;
            task_date = null;
            task_time = null;
            task_repeat = null;
            task_repeat_type = null;
            task_repeat_number = null;
            task_finish = null;

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(view -> {
                tasks.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                Log.d(TAG, "delete long click" + getAdapterPosition());
                // dodać do usunięcia z bazy
                return true;
            });
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), tasks.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }

}
