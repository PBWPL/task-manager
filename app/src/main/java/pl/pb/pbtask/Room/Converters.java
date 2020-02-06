package pl.pb.pbtask.Room;

import androidx.room.TypeConverter;

import java.util.Date;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-26
 */

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}