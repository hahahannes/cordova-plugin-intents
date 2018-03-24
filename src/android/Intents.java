package org.apache.cordova;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;


public class Intents extends CordovaPlugin {
    private CallbackContext callback;
    
    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        callback = callbackContext;
        
        JSONObject data = args.getJSONObject(0);
        
        if (action.equals("createEvent")) {
            final Intent calIntent = new Intent(Intent.ACTION_INSERT);      
            
            try {
                // TODO check if attributes exists 
                calIntent.putExtra(CalendarContract.Events.TITLE, data.getString("title"));
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, data.getLong("startDateMilli"));
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, data.getLong("endDateMilli"));
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, data.optString("location"));
            } catch (JSONException e) {
                 System.err.println("Exception: " + e.getMessage());
                 callback.error(e.getMessage());
            }    
             
            start(calIntent);
        

            return true;

        } else if(action.equals("sendMail")) {
            Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
            mailIntent.setData(Uri.parse("mailto:")); 
            mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {data.optString("address")});
            /*mailIntent.putExtra(Intent.EXTRA_TEXT, new String[] {data.optString("infos")});*/
            
            start(mailIntent);
            return true;
        } else {
            return false;

        }
    }
    
    public void start(final Intent intent) {
        cordova.getActivity().runOnUiThread(new Runnable() {
                  @Override
                  public void run() {      
                    cordova.getActivity().startActivity(intent);
                    callback.success("Success"); // Thread-safe.   
                  } 
        });
    }
}

