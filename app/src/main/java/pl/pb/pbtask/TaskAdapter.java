package pl.pb.pbtask;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import pl.pb.pbtask.Room.Task;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-30
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private static final String DEBUG_TAG = "TaskAdapter";

    private List<Task> tasks = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = tasks.get(position);
        holder.task_thumbnail_image.setImageDrawable(drawThumbnailImage(current.getTitle()));
        holder.task_title.setText(current.getTitle());
        holder.task_datetime.setText(current.getDate() + " " + current.getTime());

        if (current.isActive()) {
            holder.task_active.setChecked(true);
        } else {
            holder.task_active.setChecked(false);
        }

        switch (current.getDifficulty()) {
            case "easy":
                holder.task_difficulty.setBackgroundColor(Color.rgb(0, 200, 0));
                break;
            case "middle":
                holder.task_difficulty.setBackgroundColor(Color.rgb(230, 130, 30));
                break;
            case "hard":
                holder.task_difficulty.setBackgroundColor(Color.rgb(200, 0, 0));

                break;
        }
    }

    private TextDrawable drawThumbnailImage(String title) {
        String letter = "";
        if (title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }
        int color = ColorGenerator.MATERIAL.getRandomColor();
        return TextDrawable.builder().beginConfig().withBorder(4).endConfig()
                .buildRoundRect(letter, color, 20);
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

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView task_title, task_datetime;
        private View task_difficulty;
        private ImageView task_thumbnail_image;
        private SwitchMaterial task_active;

        private TaskViewHolder(View itemView) {
            super(itemView);
            task_thumbnail_image = itemView.findViewById(R.id.thumbnail_image);
            task_title = itemView.findViewById(R.id.task_title);
            task_datetime = itemView.findViewById(R.id.task_datetime);
            task_difficulty = itemView.findViewById(R.id.task_difficulty);
            task_active = itemView.findViewById(R.id.switch_active);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(tasks.get(position));
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}