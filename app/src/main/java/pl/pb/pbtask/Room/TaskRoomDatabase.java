package pl.pb.pbtask.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, /*delete*/ exportSchema = false /*exportSchema*/)
public abstract class TaskRoomDatabase extends RoomDatabase {
    private static volatile TaskRoomDatabase INSTANCE;


    // delete
    private static final int NUM_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_OF_THREADS);
    // delete

    static TaskRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, "task_database.db")/*.addCallback(sRoomDatabaseCallback)*/.build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();

//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            databaseWriteExecutor.execute(() -> {
//                TaskDao taskDao = INSTANCE.taskDao();
//                taskDao.deleteTasks();
//
//                taskDao.insertTask(new Task("Test1", 1,"11.12.2019", "12:50", true, "minute", 1, true));
//                taskDao.insertTask(new Task("Test2", 2,"12.12.2019", "13:50", false, "hour", 2, false));
//                taskDao.insertTask(new Task("Test3", 3,"13.12.2019", "14:50", true, "day", 3, true));
//            });
//        }
//    };

}
