package pl.pb.pbtask.Room;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> tasks;

    TaskRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getInstance(application);
        taskDao = database.taskDao();
        tasks = taskDao.getAllTasksByDifficulty();
    }

    LiveData<List<Task>> getAllTasksByDifficulty() {
        return tasks;
    }

//    @SuppressLint("Assert")
//    List<Task> findTaskByTitle(String title) {
//        AtomicReference<List<Task>> t = null;
//        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
//            assert false;
//            t.set(taskDao.findTaskByTitle(title));
//        });
//        assert false;
//        return t.get();
//    }


    void insertTask(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(task);
        });
    }

    void insertTasks(List<Task> tasks) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTasks(tasks);
        });
    }

    void updateTask(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.updateTask(task);
        });
    }

    void deleteTask(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.deleteTask(task);
        });
    }

    void deleteTasks() {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {

        });
    }

}
