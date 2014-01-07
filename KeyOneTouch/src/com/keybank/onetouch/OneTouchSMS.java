package com.keybank.onetouch;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;


public class OneTouchSMS extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.androidexample_broadcast_newsms);
	}
	
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		SmsManager sms=SmsManager.getDefault();
		String phone="69539";
		String message="bal";
		PendingIntent piSent=PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
		PendingIntent piDelivered=PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
		sms.sendTextMessage(phone, null, message, piSent, piDelivered);
	}
}
