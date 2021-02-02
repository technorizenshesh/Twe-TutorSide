package com.tech.twe_tutorside.fcm;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tech.twe_tutorside.activity.HomeActvity;
import com.tech.twe_tutorside.fragments.NotificationFragment;

import org.json.JSONException;
import org.json.JSONObject;




public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        System.out.println("------------------------In MyFirebaseMessagingService---------------------------------");
        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }


    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        String hostname, drivername = "driver", status;
        double driver_lat, driver_lng;
        Intent resultIntent = new Intent(getApplicationContext(), HomeActvity.class);
        Intent NotiIntent = new Intent(getApplicationContext(), NotificationFragment.class);
        try {
            JSONObject data = json.getJSONObject("message");
            String keyMessage = data.getString("key");
            String notifi_msg = data.getString("notifi_msg");


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", data.toString());
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();

                if (keyMessage.equals("You Have a new jams")) {
                    String book_id = data.getString("book_id");
                    resultIntent.putExtra("message", "" + data);
                    resultIntent.putExtra("book_id", book_id);
                    showNotificationMessage(getApplicationContext(), "JamsTime", "" + notifi_msg, "Timestamp", resultIntent);


                } else if (keyMessage.equals("You Have a new request")) {
                    String book_id = data.getString("book_id");
                    resultIntent.putExtra("message", "" + data);
                    resultIntent.putExtra("book_id", book_id);
                    showNotificationMessage(getApplicationContext(), "JamsTime", "" + notifi_msg, "Timestamp", NotiIntent);


                } else {

                    String book_id = data.getString("book_id");
                    resultIntent.putExtra("message", "" + data);
                    resultIntent.putExtra("book_id", book_id);
                    showNotificationMessage(getApplicationContext(), "JamsTime", "" + keyMessage, "Timestamp", NotiIntent);
                }


            } else {

                if (keyMessage.equals("You Have a new jams")) {
                    String book_id = data.getString("book_id");
                    resultIntent.putExtra("message", "" + data);
                    resultIntent.putExtra("book_id", book_id);
                    showNotificationMessage(getApplicationContext(), "JamsTime", "" + keyMessage, "Timestamp", resultIntent);

                } else if (keyMessage.equals("You Have a new request")) {
                    String book_id = data.getString("book_id");
                    resultIntent.putExtra("message", "" + data);
                    resultIntent.putExtra("book_id", book_id);
                    showNotificationMessage(getApplicationContext(), "JamsTime", "" + keyMessage, "Timestamp", NotiIntent);

                } else {

                    String book_id = data.getString("book_id");
                    resultIntent.putExtra("message", "" + data);
                    resultIntent.putExtra("book_id", book_id);
                    showNotificationMessage(getApplicationContext(), "JamsTime", "" + keyMessage, "Timestamp", NotiIntent);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}

