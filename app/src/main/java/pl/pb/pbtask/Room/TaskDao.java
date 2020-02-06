package pl.pb.pbtask.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-26
 */

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM task_table")
    void deleteTasks();

    @Query("SELECT * FROM task_table ORDER BY task_difficulty ASC")
    LiveData<List<Task>> getAllTasksByDifficulty();

    @Query("SELECT * FROM task_table WHERE task_finish=0 ORDER BY task_difficulty ASC")
    LiveData<List<Task>> getAllTasksByDifficultyWithoutFinishTasks();

    @Query("SELECT * FROM task_table WHERE task_title LIKE (:title_like)")
    LiveData<List<Task>> findTaskByTitle(String title_like);
}