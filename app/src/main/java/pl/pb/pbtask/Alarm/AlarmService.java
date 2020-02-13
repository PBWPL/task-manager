package pl.pb.pbtask.Alarm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import pl.pb.pbtask.AddEditTaskActivity;
import pl.pb.pbtask.R;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-02-06
 */

public class AlarmService extends IntentService {
    private static final String DEBUG_TAG = "AlarmService";

    private static final int NOTIFICATION_ID = 50;

    public AlarmService() {
        super(DEBUG_TAG);
    }

    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setData(uri);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();

        Intent action = new Intent(this, AddEditTaskActivity.class);
        action.setData(uri);
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarm);
        Notification note = new NotificationCompat.Builder(getApplicationContext(), "PBTask")
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.description))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(NOTIFICATION_ID, note);
        ringtone.play();
    }
}