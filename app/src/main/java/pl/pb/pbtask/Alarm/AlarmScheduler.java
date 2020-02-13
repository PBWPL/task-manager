package pl.pb.pbtask.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-02-06
 */

public class AlarmScheduler {
    private static final String DEBUG_TAG = "AlarmScheduler";

    public void setAlarm(Context context, long alarmTime, Uri uriTask) {
        AlarmManager alarmManager = AlarmManagerProvider.getAlarmManager(context);
        PendingIntent pendingIntent = AlarmService.getReminderPendingIntent(context, uriTask);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    public void setRepeatAlarm(Context context, long alarmTime, Uri uriTask, long RepeatTime) {
        AlarmManager alarmManager = AlarmManagerProvider.getAlarmManager(context);
        PendingIntent pendingIntent = AlarmService.getReminderPendingIntent(context, uriTask);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, RepeatTime, pendingIntent);
    }

    public void cancelAlarm(Context context, Uri uriTask) {
        AlarmManager alarmManager = AlarmManagerProvider.getAlarmManager(context);
        PendingIntent pendingIntent = AlarmService.getReminderPendingIntent(context, uriTask);
        alarmManager.cancel(pendingIntent);
    }
}