package ivan.studentlist.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HeadsetReceiver extends BroadcastReceiver {

    private boolean firstReceiving = true;
    private boolean connected;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!firstReceiving) {
            int state = intent.getIntExtra("state", -1);
            if (state == 1 && !connected) {
                Toast.makeText(context, "headset connected", Toast.LENGTH_SHORT).show();
                connected = true;
            } else if (state == 0 && connected) {
                Toast.makeText(context, "headset disconnected", Toast.LENGTH_SHORT).show();
                connected = false;
            }
        } else {
            firstReceiving = false;
        }
    }
}
