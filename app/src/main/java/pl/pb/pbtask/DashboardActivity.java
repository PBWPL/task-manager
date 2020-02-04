package pl.pb.pbtask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import pl.pb.pbtask.Room.Task;
import pl.pb.pbtask.Room.TaskViewModel;

public class DashboardActivity extends AppCompatActivity {
    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "DashboardActivity";
    TaskAdapter taskAdapter;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SwipeRefreshLayout swipeRefreshLayout;
    TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        taskAdapter = new TaskAdapter(this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        taskViewModel.getAllTasksByDifficulty().observe(this, tasks -> taskAdapter.setTasks(tasks));

//        getAllTasks();
//        initRecyclerView();
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                tasks.add(new Task("asdasd_x", 1, "12.12.2019", "11:50", true, "day", 2, true));
//                tasks.add(new Task("asdasd_y", 2, "12.12.2015", "21:50", false, "month", 1, true));
//                tasks.add(new Task("asdasd_z", 3, "12.12.2012", "10:50", true, "minute", 4, false));
//                tasks.add(new Task("asdasd_h", 1, "12.12.2011", "01:50", false, "year", 5, false));
                taskViewModel.insertTask(new Task("wstawka", 9, "11.12.2019", "12:50", true, "minute", 1, true));
                LiveData<List<Task>> x = taskViewModel.getAllTasksByDifficulty();
                Log.d(TAG, x.toString());

                taskAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                insertTask();
//                insertTask();
//                insertTask();
//                insertTask();
//                insertTask();
//                getAllTasks();

                Intent intent = new Intent(DashboardActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);

                Toast toast = new Toast(getApplicationContext());
                View view = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.toast_layout, null);
                TextView toastTextView = view.findViewById(R.id.textViewToast);
                toastTextView.setText("FAB");
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.END | Gravity.BOTTOM, 32, 179);
                toast.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Task test = new Task(data.getStringExtra(AddTaskActivity.EXTRA_REPLY), 1, "11.12.2019", "12:50", true, "minute", 1, true);
            taskViewModel.insertTask(test);
        } else {
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dark:
                Log.d(TAG, "dark");
                break;
            case R.id.action_grid:
                Log.d(TAG, "grid");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ----------

//    private void initData(List<Task> data) {
//        this.tasks = data;
//    }
//
//    private void initRecyclerView() {
//        recyclerView = findViewById(R.id.recyclerView);
//        taskAdapter = new TaskAdapter(tasks);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(taskAdapter);
//    }

//    // ----------
//
//    public void insertTask() {
//        Task task = new Task("asdasd_1", 1, "12.12.2017", "01:50", true, "day", 2, false);
//        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
//        insertAsyncTask.execute(task);
//        Log.d(TAG, "przeszło" + task.toString());
//    }
//
//    public void insertTasks(View view) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Task> tasks = new ArrayList<>();
//                tasks.add(new Task("asdasd_x", 1, "12.12.2019", "11:50", true, "day", 2, true));
//                tasks.add(new Task("asdasd_y", 2, "12.12.2015", "21:50", false, "month", 1, true));
//                tasks.add(new Task("asdasd_z", 3, "12.12.2012", "10:50", true, "minute", 4, false));
//                tasks.add(new Task("asdasd_h", 1, "12.12.2011", "01:50", false, "year", 5, false));
//
//                TaskRoomDatabase.getInstance(getApplicationContext())
//                        .taskDao()
//                        .insertTasks(tasks);
//
//                Log.d(TAG, "run: tasks has been inserted...");
//            }
//        }).start();
//
//    }
//
//    public void updateTask(View view) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Task task = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
//                        .findTaskById(1);
//
//                if (task != null) {
//                    task.setFinish(true);
//
//                    TaskRoomDatabase.getInstance(getApplicationContext())
//                            .taskDao()
//                            .updateTask(task);
//
//                    Log.d(TAG, "run: task has been updated...");
//                }
//            }
//        }).start();
//
//    }
//
//    public void deleteTask(View view) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Task task = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
//                        .findTaskById(4);
//
//                Log.d(TAG, "run: " + task.toString());
//
//                TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
//                        .deleteTask(task);
//
//                Log.d(TAG, "run: task has been deleted...");
//
//            }
//        }).start();
//    }
//
//    public void getAllTasks() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                tasks = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
//                        .getAllTasks();
//
//                //initData(tasks);
//
//                Log.d("XXX", tasks.toString());
//            }
//        }).start();
//
//    }
//
//    public void findTaskById() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Task task = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
//                        .findTaskById(1);
//
//                Log.d(TAG, "run: " + task.toString());
//            }
//        });
//    }
//
//    public void findAllFinishTasks(View view) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Task> tasks = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
//                        .getAllFinishTasks();
//
//                Log.d(TAG, "run: " + tasks.toString());
//            }
//        }).start();
//
//    }

    @Override
    public void onBackPressed() {
        // block button back on this activity
    }

//    class InsertAsyncTask extends AsyncTask<Task, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Task... tasks) {
//
//            TaskRoomDatabase.getInstance(getApplicationContext()).taskDao().insertTask(tasks[0]);
//            return null;
//        }
//    }

}
