package pl.pb.pbtask;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";

    List<String> tasks;

    public TaskAdapter(List<String> moviesList) {
        this.tasks = moviesList;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.task_title.setText(String.valueOf(position));
        holder.task_row.setText(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView task_title, task_row;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);

            task_title = itemview.findViewById(R.id.task_title);
            task_row = itemview.findViewById(R.id.task_row);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    tasks.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    Log.d(TAG, "delete long click");
                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), tasks.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "to edit activity");
        }
    }
}
