package pl.pb.pbtask.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-27
 */

@Database(entities = {Task.class}, version = 2) // VERSION DB 2
public abstract class TaskRoomDatabase extends RoomDatabase {
    private static volatile TaskRoomDatabase INSTANCE;

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    static synchronized TaskRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskRoomDatabase.class,
                    "task_database").fallbackToDestructiveMigration()
                    /*.addCallback(roomCallback)*/.build(); // INSERT DEFAULT ROWS TO DB
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;

        private PopulateDbAsyncTask(TaskRoomDatabase database) {
            taskDao = database.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.insertTask(new Task("Title 1", "easy", "12.12.12",
                    "11:40", false, "week", 1, false));
            taskDao.insertTask(new Task("Title 2", "hard", "12.12.12",
                    "12:50", true, "hour", 2, true));
            taskDao.insertTask(new Task("Title 3", "middle", "12.12.12",
                    "13:50", true, "day", 3, true));
            return null;
        }
    }
}