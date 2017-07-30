package iskconbangalore.org.hkmsadhana;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

public class RemainderService extends Service {
    public RemainderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Query the database and show alarm if it applies

        // I don't want this service to stay in memory, so I stop it
        // immediately after doing what I wanted it to do.

//        Toast.makeText(getApplicationContext(), " Remainder Service Running.",
//                            Toast.LENGTH_LONG).show();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Intent myIntent = new Intent(this , RemainderService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);

//        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
//        alarm.set(
//                // This alarm will wake up the device when System.currentTimeMillis()
//                // equals the second argument value
//                alarm.RTC_WAKEUP,
//                System.currentTimeMillis() + (1000 * 60 * 60), // One hour from now
//                // PendingIntent.getService creates an Intent that will start a service
//                // when it is called. The first argument is the Context that will be used
//                // when delivering this intent. Using this has worked for me. The second
//                // argument is a request code. You can use this code to cancel the
//                // pending intent if you need to. Third is the intent you want to
//                // trigger. In this case I want to create an intent that will start my
//                // service. Lastly you can optionally pass flags.
//                PendingIntent.getService(this, 0, new Intent(this, RemainderService.class), 0));

//                Intent myIntent = new Intent(ThisApp.this , myService.class);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        pendingIntent = PendingIntent.getService(ThisApp.this, 0, myIntent, 0);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 12);
//        calendar.set(Calendar.MINUTE, 00);
//        calendar.set(Calendar.SECOND, 00);

   //     alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);  //set repeating every 24 hours


//       // PendingIntent startActivty
//        Notification n  = new Notification.Builder(this)
//                .setContentTitle("Update Today Sadhana")
//                .setContentText("MA update Pending")
//                .setSmallIcon(R.drawable.ic_menu_camera)
//                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0))
//                .setAutoCancel(true).build();
//
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, n);
        stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // I want to restart this service again in one hour
//        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
//        alarm.set(
//                alarm.RTC_WAKEUP,
//                System.currentTimeMillis() + (1000 * 60 * 60),
//                PendingIntent.getService(this, 0, new Intent(this, RemainderService.class), 0)
//        );
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Intent myIntent = new Intent(this , RemainderService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
