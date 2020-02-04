package pl.pb.pbtask.Room;

import androidx.lifecycle.LiveData;
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

    @Query("DELETE FROM task_table")
    void deleteTasks();

    @Query("SELECT * FROM task_table ORDER BY task_difficulty ASC")
    LiveData<List<Task>> getAllTasksByDifficulty();
    //MutableLiveData<List<Task>> getAllTasksByDifficulty();

    @Query("SELECT * FROM task_table WHERE task_title LIKE :title")
    List<Task> findTaskByTitle(String title);

    @Query("SELECT * FROM task_table WHERE task_finish LIKE 1")
    List<Task> getAllFinishTasks();
}


/*
 @Query("DELETE FROM word_table")
   void deleteAll();

   @Query("SELECT * from word_table ORDER BY word ASC")
   List<Word> getAlphabetizedWords();
}
 */