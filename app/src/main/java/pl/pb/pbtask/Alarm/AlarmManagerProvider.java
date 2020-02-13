package pl.pb.pbtask.Alarm;

import android.app.AlarmManager;
import android.content.Context;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-02-06
 */

class AlarmManagerProvider {
    private static final String DEBUG_TAG = "AlarmManagerProvider";

    private static AlarmManager alarmManager;

    static synchronized AlarmManager getAlarmManager(Context context) {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return alarmManager;
    }
}