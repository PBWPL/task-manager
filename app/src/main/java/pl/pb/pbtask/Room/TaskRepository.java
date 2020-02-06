package pl.pb.pbtask.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-26
 */

class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> tasks;

    TaskRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getInstance(application);
        taskDao = database.taskDao();
        tasks = taskDao.getAllTasksByDifficulty();
    }

    void insertTask(Task task) {
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    void updateTask(Task task) {
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }

    void deleteTask(Task task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    void deleteTasks() {
        new DeleteTasksAsyncTask(taskDao).execute();
    }

    LiveData<List<Task>> getAllTasksByDifficulty() {
        return tasks;
    }

    LiveData<List<Task>> getAllTasksByDifficultyWithoutFinishTasks() {
        return taskDao.getAllTasksByDifficultyWithoutFinishTasks();
    }

    LiveData<List<Task>> findTaskByTitle(String title_like) {
        return taskDao.findTaskByTitle(title_like);
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insertTask(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.updateTask(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.deleteTask(tasks[0]);
            return null;
        }
    }

    private static class DeleteTasksAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;

        private DeleteTasksAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteTasks();
            return null;
        }
    }
}