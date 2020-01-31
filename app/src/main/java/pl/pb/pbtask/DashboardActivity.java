package pl.pb.pbtask;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import pl.pb.pbtask.Room.Task;
import pl.pb.pbtask.Room.TaskRoomDatabase;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";

    List<String> tasks;

    TaskAdapter taskAdapter;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();
        initRecyclerView();
        insertTask();
        getAllTasks();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tasks.add("Black Widow (2020)");
                tasks.add("The Eternals (2020)");
                tasks.add("Shang-Chi and the Legend of the Ten Rings (2021)");
                tasks.add("Doctor Strange in the Multiverse of Madness (2021)");
                tasks.add("Thor: Love and Thunder (2021)");

                taskAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(getApplicationContext());
                View view = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.toast_layout, null);
                TextView toastTextView = view.findViewById(R.id.textViewToast);
                toastTextView.setText("Test");
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.END | Gravity.BOTTOM, 32, 179);
                toast.show();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dark:
                Log.d(TAG, "1");
                break;
            case R.id.action_another_1:
                Log.d(TAG, "2");
                break;
            case R.id.action_another_2:
                Log.d(TAG, "3");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ----------

    private void initData() {
        tasks = new ArrayList<>();

        tasks.add("Iron Man");
        tasks.add("The Incredible Hulk");
        tasks.add("Iron Man 2");
        tasks.add("Thor");
        tasks.add("Captain America: The First Avenger");
        tasks.add("The Avengers");
        tasks.add("Iron Man 3");
        tasks.add("Thor: The Dark World");
        tasks.add("Captain America: The Winter Soldier");
        tasks.add("Guardians of the Galaxy");
        tasks.add("Avengers: Age of Ultron");
        tasks.add("Ant-Man");
        tasks.add("Captain America: Civil War");
        tasks.add("Doctor Strange");
        tasks.add("Guardians of the Galaxy Vol. 2");
        tasks.add("Spider-Man: Homecoming");
        tasks.add("Thor: Ragnarok");
        tasks.add("Black Panther");
        tasks.add("Avengers: Infinity War");
        tasks.add("Ant-Man and the Wasp");
        tasks.add("Captain Marvel");
        tasks.add("Avengers: Endgame");
        tasks.add("Spider-Man: Far From Home");
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        taskAdapter = new TaskAdapter(tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);
    }

    // ----------

    public void insertTask() {
        Task task = new Task("asdasd_1", 1, "12.12.2017", "01:50", true, "day", 2, false);
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(task);
        Log.d(TAG, "przeszło" + task.toString());
    }

    public void insertTasks(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = new ArrayList<>();
                tasks.add(new Task("asdasd_x", 1, "12.12.2019", "11:50", true, "day", 2, true));
                tasks.add(new Task("asdasd_y", 2, "12.12.2015", "21:50", false, "month", 1, true));
                tasks.add(new Task("asdasd_z", 3, "12.12.2012", "10:50", true, "minute", 4, false));
                tasks.add(new Task("asdasd_h", 1, "12.12.2011", "01:50", false, "year", 5, false));

                TaskRoomDatabase.getInstance(getApplicationContext())
                        .taskDao()
                        .insertTasks(tasks);

                Log.d(TAG, "run: tasks has been inserted...");
            }
        }).start();

    }

    public void updateTask(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Task task = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
                        .findTaskById(1);

                if (task != null) {
                    task.setFinish(true);

                    TaskRoomDatabase.getInstance(getApplicationContext())
                            .taskDao()
                            .updateTask(task);

                    Log.d(TAG, "run: task has been updated...");
                }
            }
        }).start();

    }

    public void deleteTask(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Task task = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
                        .findTaskById(4);

                Log.d(TAG, "run: " + task.toString());

                if (task != null) {
                    TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
                            .deleteTask(task);

                    Log.d(TAG, "run: task has been deleted...");
                }

            }
        }).start();
    }

    public void getAllTasks() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
                        .getAllTasks();

                Log.d(TAG, "run: " + tasks.toString());
            }
        }).start();
    }

    public void findTaskById(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Task task = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
                        .findTaskById(1);

                Log.d(TAG, "run: " + task.toString());
            }
        });
    }

    public void findAllFinishTasks(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = TaskRoomDatabase.getInstance(getApplicationContext()).taskDao()
                        .getAllFinishTasks();

                Log.d(TAG, "run: " + tasks.toString());
            }
        }).start();

    }

    class InsertAsyncTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {

            TaskRoomDatabase.getInstance(getApplicationContext()).taskDao().insertTask(tasks[0]);
            return null;
        }
    }

}
