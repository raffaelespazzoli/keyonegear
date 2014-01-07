package com.keybank.onetouch;

import java.text.DateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class IncomingSms extends BroadcastReceiver {
	
	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();
	
	public void onReceive(Context context, Intent intent) {
	
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try {
			
			if (bundle != null) {
				
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				
				for (int i = 0; i < pdusObj.length; i++) {
					
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					
			        String senderNum = phoneNumber;
			        String message = currentMessage.getDisplayMessageBody();
			        if(senderNum.equals("69539")) //hard coded key bank mobile banking number
			        {
				        Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
				        
				        String lines[] = message.split("\\r?\\n");
				        
				        String balance = "";
				        for (int j=0; j < lines.length;j=j+1)
				        {
				        	if(lines[j].contains("Avail") || lines[j].contains("Curr") )
				        	{
				        		String words[] = lines[j].split(" ");
				        		balance = balance + "Your available balance for " + words[0] + " is $" + words[2] + "\n";
				        	}
				        }
				        
				        balance = balance + "Last updated: " +  DateFormat.getDateTimeInstance().format(new Date());
				        
				        int duration = Toast.LENGTH_LONG;
						
				        //intent.putExtra("balance_message",balance);
				        //intent.setClass(context, OneTouchGear.class);
			            //context.startActivity(intent);
				        
				        if (OneTouchGear.mThis != null) {
				            ((TextView)OneTouchGear.mThis.findViewById(R.id.balance)).setText(balance);
				            OneTouchGear.mThis.sendMessage(balance);
				        }
				        else {
				        	//handle the lack activity thread here
				        }
				        
				        Toast toast = Toast.makeText(context, balance, duration);
						toast.show();
						//abortBroadcast(); //cancel the broadcast
			        }
				} // end for loop
              } // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);
			
		}
	}
	
	
}