package pl.pb.pbtask.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-27
 */

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        tasks = repository.getAllTasksByDifficulty();
    }

    public void insertTask(Task task) {
        repository.insertTask(task);
    }

    public void updateTask(Task task) {
        repository.updateTask(task);
    }

    public void deleteTask(Task task) {
        repository.deleteTask(task);
    }

    public void deleteTasks() {
        repository.deleteTasks();
    }

    public LiveData<List<Task>> getAllTasksByDifficulty() {
        return tasks;
    }

    public LiveData<List<Task>> getAllTasksByDifficultyWithoutActiveTasks() {
        return repository.getAllTasksByDifficultyWithoutActiveTasks();
    }

    public LiveData<List<Task>> findTaskByTitle(String title_like) {
        return repository.findTaskByTitle(title_like);
    }
}