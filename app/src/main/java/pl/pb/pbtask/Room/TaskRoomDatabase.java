package pl.pb.pbtask.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1, exportSchema = false) // exportSchema = false ??
public abstract class TaskRoomDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    private static volatile TaskRoomDatabase INSTANCE;

    public static TaskRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, "Task_Database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
