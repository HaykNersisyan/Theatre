package diploma.gyumri.theatre.view.service;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.Event;
import diploma.gyumri.theatre.view.activities.MainActivity;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MyService extends Service {
    private static String TAG = MyService.class.getSimpleName();
    private PendingIntent pendingIntent;
    NotificationManager notification;
    private Socket socket;
    private Event event;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            socket = IO.socket("ip");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        notification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(final Intent intent, int flags, final int startId) {
        if (intent != null) {
            socket.connect();
            socket.emit("notif");
            socket.on("notif", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    int time = intent.getIntExtra("time", 1);
                    String name = intent.getStringExtra("name");
                    pendingIntent = intent.getParcelableExtra("pending");
                    Log.i(TAG, "onStartCommand()  " + "   time  " + time + "   name  " + name + "   startId  " + startId);
                    intent.putExtra("key", 111);
                    sendNotification(event);
                }
            });

        }
        return START_STICKY_COMPATIBILITY;
    }

    private void sendNotification(Event event) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        //or ej@bacenq sxmenq vren kori notification;
        builder.setAutoCancel(true);
        builder.setTicker("my notification");
        builder.setContentTitle("fffff");
        builder.setSmallIcon(R.drawable.events);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setNumber(100);
        //nkari format@ kpoxe
//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.call_icon);
//        builder.setLargeIcon(icon);
        builder.build();
        Notification notif = builder.build();
        notification.notify(11, notif);

//        notif.flags |=  Notification.FLAG_AUTO_CANCEL;
//        notif.
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}
