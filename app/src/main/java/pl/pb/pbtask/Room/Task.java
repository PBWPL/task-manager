package pl.pb.pbtask.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-26
 */

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int id;

    @ColumnInfo(name = "task_title")
    private String title;

    @ColumnInfo(name = "task_difficulty")
    private String difficulty;

    @ColumnInfo(name = "task_date")
    private String date;

    @ColumnInfo(name = "task_time")
    private String time;

    @ColumnInfo(name = "task_repeat")
    private boolean repeat;

    @ColumnInfo(name = "task_repeat_type")
    private String repeat_type;

    @ColumnInfo(name = "task_repeat_number")
    private int repeat_number;

    @ColumnInfo(name = "task_active")
    private boolean active;

    public Task(String title, String difficulty, String date, String time, boolean repeat,
                String repeat_type, int repeat_number, boolean active) {
        this.title = title;
        this.difficulty = difficulty;
        this.date = date;
        this.time = time;
        this.repeat = repeat;
        this.repeat_type = repeat_type;
        this.repeat_number = repeat_number;
        this.active = active;
    }

    @NonNull
    @Override
    public String toString() {
        return "\nTask{" + "id='" + id + "', title='" + title + "', difficulty=" + difficulty
                + "', date=" + date + "', time=" + time + "', repeat=" + repeat
                + "', repeat_type=" + repeat_type + "', repeat_number=" + repeat_number
                + "', active=" + active + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public String getRepeat_type() {
        return repeat_type;
    }

    public void setRepeat_type(String repeat_type) {
        this.repeat_type = repeat_type;
    }

    public int getRepeat_number() {
        return repeat_number;
    }

    public void setRepeat_number(int repeat_number) {
        this.repeat_number = repeat_number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}