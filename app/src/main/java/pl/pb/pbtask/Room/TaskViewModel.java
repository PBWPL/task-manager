package pl.pb.pbtask.Room;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TaskViewModel extends AndroidViewModel {
    public TaskRepository repository;
    private LiveData<List<Task>> tasks;

    public TaskViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
        tasks = repository.getAllTasksByDifficulty();
    }

    public LiveData<List<Task>> getAllTasksByDifficulty() {
        return tasks;
    }

    public void insertTask(Task task) {
        repository.insertTask(task);
    }

    void insertTasks(List<Task> tasks) {
        repository.insertTasks(tasks);
    }

    void updateTask(Task task) {
        repository.updateTask(task);
    }

    void deleteTask(Task task) {
        repository.deleteTask(task);
    }

}
