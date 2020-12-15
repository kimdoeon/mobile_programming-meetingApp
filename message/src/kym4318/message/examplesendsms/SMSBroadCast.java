package whdghks913.tistory.examplesendsms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context mContext, Intent intent) {
    	String action =  intent.getAction();
    	
    	if("android.provider.Telephony.SMS_RECEIVED".equals(action)){
    		/**
    		 * SMS�޼��� �Ľ�
    		 */
    		Bundle bundle = intent.getExtras();
    		Object messages[] = (Object[])bundle.get("pdus");
    		SmsMessage smsMessage[] = new SmsMessage[messages.length];

    		for(int i = 0; i < messages.length; i++) {
    		    /**
    		     * PDU������ SMS�� ��ȯ�մϴ�
    		     */
    		    smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
    		}
    		
    		/**
    		 * ��¥ ������ �츮���� �µ��� ��ȯ�մϴ�
    		 */
    		Date curDate = new Date(smsMessage[0].getTimestampMillis());
    		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� HH�� mm�� ss��", Locale.KOREA);
    		
    		String originDate = mDateFormat.format(curDate);
    		String origNumber = smsMessage[0].getOriginatingAddress();
    		String Message = smsMessage[0].getMessageBody().toString();
    		
    		Intent showSMSIntent = new Intent(mContext, ShowSMSActivity.class);
    		showSMSIntent.putExtra("originNum", origNumber);
    		showSMSIntent.putExtra("smsDate", originDate);
    		showSMSIntent.putExtra("originText", Message);
    		
    		showSMSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		
    		mContext.startActivity(showSMSIntent);
    	}
    }
}