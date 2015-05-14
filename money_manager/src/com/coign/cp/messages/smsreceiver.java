package com.coign.cp.messages;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class smsreceiver extends BroadcastReceiver {
	
	@SuppressLint("DefaultLocale")
	@SuppressWarnings({ "unused", "deprecation" })
	public void onReceive(Context context, Intent intent) {
		
		int duration = Toast.LENGTH_LONG;
        String colName="";
        intent.setClass(context, sms_inbox.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = intent.getExtras();
		
		 if (bundle != null) {
            Object[] pdus = (Object[])bundle.get("pdus");
            SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[0]);
            Toast toast1 = Toast.makeText(context, sms.getOriginatingAddress().toUpperCase(), duration);
            toast1.show();
            if (sms.getOriginatingAddress().toUpperCase().contains("CITI") || sms.getOriginatingAddress().toUpperCase().contains("ICICI") || sms.getOriginatingAddress().toUpperCase().contains("SBI") || sms.getOriginatingAddress().toUpperCase().contains("CENT") || sms.getOriginatingAddress().toUpperCase().contains("PNB") || sms.getOriginatingAddress().toUpperCase().contains("HDFC") || sms.getOriginatingAddress().toUpperCase().contains("HSBC")  || sms.getOriginatingAddress().toUpperCase().contains("AXIS"))
            {
	            colName="";
		        colName = colName + sms.getOriginatingAddress() + "\n";
		        colName = colName + sms.getMessageBody() + "\n";
		        
		        Toast toast = Toast.makeText(context, "Bank Transaction : \n"+colName, duration);
	            toast.show();
	            
	            Notification.Builder builder = new Notification.Builder(context);
	            //Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.notifysnd);
	            //mBuilder.setSound(sound);
	            //builder.setSound(sound);
	            Notification noti = new Notification(R.drawable.notif_icon, null, System.currentTimeMillis());  
	            
	            PendingIntent contentIntent = PendingIntent.getActivity(context, duration, intent, 0);
	            noti.setLatestEventInfo(context, "Money Manager", "Bank Transaction : \n"+colName, contentIntent);
	            
	            noti.flags =    Notification.FLAG_AUTO_CANCEL;
	            noti.defaults = Notification.DEFAULT_ALL;
	     
	            final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	            //noti.notify();
	            nm.notify(1,noti);
	            global.update_required = true;
	            global.total_update_required += 1;
            }
        }
	}
}