package pl.pb.pbtask.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Insert
    void insertTasks(List<Task> tasks);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM task_table")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task_table WHERE task_id LIKE :id")
    Task findTaskById(int id);

    @Query("SELECT * FROM task_table WHERE task_finish LIKE 1")
    List<Task> getAllFinishTasks();
}
