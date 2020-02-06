package pl.pb.pbtask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import maes.tech.intentanim.CustomIntent;
import pl.pb.pbtask.Room.Task;
import pl.pb.pbtask.Room.TaskViewModel;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-25
 */

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private TaskAdapter taskAdapter;
    private TaskViewModel taskViewModel;

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        try {
            AppCompatDelegate.setDefaultNightMode(getDark());
        } catch (NullPointerException e) {
            setDark(false);
            AppCompatDelegate.setDefaultNightMode(getDark());
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TaskAdapter taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasksByDifficulty().observe(this, taskAdapter::setTasks);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AddEditTaskActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
//            Toast toast = new Toast(getApplicationContext());
//            View view = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.toast_layout, null);
//            TextView toastTextView = view.findViewById(R.id.textViewToast);
//            toastTextView.setText("FAB");
//            toast.setView(view);
//            toast.setDuration(Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.END | Gravity.BOTTOM, 32, 179);
//            toast.show();
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.deleteTask(taskAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(DashboardActivity.this, "delete" + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        taskAdapter.setOnItemClickListener(task -> {
            Intent intent = new Intent(DashboardActivity.this, AddEditTaskActivity.class);
            intent.putExtra(AddEditTaskActivity.EXTRA_ID, task.getId());
            intent.putExtra(AddEditTaskActivity.EXTRA_TITLE, task.getTitle());
            intent.putExtra(AddEditTaskActivity.EXTRA_DIFFICULTY, task.getDifficulty());
            intent.putExtra(AddEditTaskActivity.EXTRA_DATE, task.getDate());
            intent.putExtra(AddEditTaskActivity.EXTRA_TIME, task.getTime());
            intent.putExtra(AddEditTaskActivity.EXTRA_REPEAT, task.isRepeat());
            intent.putExtra(AddEditTaskActivity.EXTRA_REPEAT_TYPE, task.getRepeat_type());
            intent.putExtra(AddEditTaskActivity.EXTRA_REPEAT_NUMBER, task.getRepeat_number());
            intent.putExtra(AddEditTaskActivity.EXTRA_FINISH, task.isFinish());
            CustomIntent.customType(this, "fadein-to-fadeout");
            startActivityForResult(intent, EDIT_NOTE_REQUEST);

        });

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String task_id = data.getStringExtra(AddEditTaskActivity.EXTRA_ID);
            String task_title = data.getStringExtra(AddEditTaskActivity.EXTRA_TITLE);
            int task_difficulty = Integer.parseInt(data.getStringExtra(AddEditTaskActivity.EXTRA_DIFFICULTY));
            String task_date = data.getStringExtra(AddEditTaskActivity.EXTRA_DATE);
            String task_time = data.getStringExtra(AddEditTaskActivity.EXTRA_TIME);
            boolean task_repeat = Boolean.parseBoolean(data.getStringExtra(AddEditTaskActivity.EXTRA_REPEAT));
            String task_repeat_type = data.getStringExtra(AddEditTaskActivity.EXTRA_REPEAT_TYPE);
            int task_repeat_number = Integer.parseInt(data.getStringExtra(AddEditTaskActivity.EXTRA_REPEAT_NUMBER));
            boolean task_finish = Boolean.parseBoolean(data.getStringExtra(AddEditTaskActivity.EXTRA_FINISH));
            Task task = new Task(task_title, task_difficulty, task_date, task_time, task_repeat, task_repeat_type, task_repeat_number, task_finish);
            taskViewModel.insertTask(task);
            Toast.makeText(getApplicationContext(), "Task added", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int task_id = data.getIntExtra(AddEditTaskActivity.EXTRA_ID, -1);
            if (task_id != -1) {
                Toast.makeText(getApplicationContext(), "Task can`t be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String task_title = data.getStringExtra(AddEditTaskActivity.EXTRA_TITLE);
            int task_difficulty = Integer.parseInt(data.getStringExtra(AddEditTaskActivity.EXTRA_DIFFICULTY));
            String task_date = data.getStringExtra(AddEditTaskActivity.EXTRA_DATE);
            String task_time = data.getStringExtra(AddEditTaskActivity.EXTRA_TIME);
            boolean task_repeat = Boolean.parseBoolean(data.getStringExtra(AddEditTaskActivity.EXTRA_REPEAT));
            String task_repeat_type = data.getStringExtra(AddEditTaskActivity.EXTRA_REPEAT_TYPE);
            int task_repeat_number = Integer.parseInt(data.getStringExtra(AddEditTaskActivity.EXTRA_REPEAT_NUMBER));
            boolean task_finish = Boolean.parseBoolean(data.getStringExtra(AddEditTaskActivity.EXTRA_FINISH));
            Task task = new Task(task_title, task_difficulty, task_date, task_time, task_repeat, task_repeat_type, task_repeat_number, task_finish);
            task.setId(task_id);
            taskViewModel.updateTask(task);
            Toast.makeText(getApplicationContext(), "Task updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Task not saved", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (isDark()) {
            menu.getItem(0).setChecked(true);
        }
        return true;
    }

    public int getDark() {
        return isDark() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
    }

    public boolean isDark() {
        return this.sharedPreferences.getInt("dark", AppCompatDelegate.MODE_NIGHT_NO) == 2;
    }

    public void setDark(boolean dark) {
        if (dark) {
            this.sharedPreferences.edit().putInt("dark", AppCompatDelegate.MODE_NIGHT_YES).apply();
        } else {
            this.sharedPreferences.edit().putInt("dark", AppCompatDelegate.MODE_NIGHT_NO).apply();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dark:
                if (item.isChecked() && isDark()) {
                    item.setChecked(false);
                    setDark(false);
                    AppCompatDelegate.setDefaultNightMode(getDark());
                } else {
                    item.setChecked(true);
                    setDark(true);
                    AppCompatDelegate.setDefaultNightMode(getDark());
                }
                break;
            case R.id.action_grid:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                break;
            case R.id.action_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ----------

    private void init() {
    }

    @Override
    public void onBackPressed() {
    }
}