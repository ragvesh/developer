package com.coign.cp.messages;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN) public class main extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
      
    }
    public void SendRequest_inbox_OnClick(View v)
    {
        
        Intent intent = new Intent(main.this, sms_inbox.class);
        startActivity(intent);
    } 
    
    public void SendRequest_settings_OnClick(View v)
    {
        
        Intent intent = new Intent(main.this, settings.class);
        startActivity(intent);
    } 
    
    
    public void AboutMe(View v)
    {
          
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this.getApplicationContext(), "  THANKS FOR INSTALLING  ", duration);
        toast.show();
        
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(null)
		.setCancelable(true);
		
		AlertDialog alertDialog = builder.create();
		String AboutMe="\n\n\n\nDear User\n\nI am Ragvesh Sharma. Thanks For Installing this App.\nKIndly write me for Review of this App At \nragvesh.bit@gmail.com\n\n\n\n";
		alertDialog.setMessage(AboutMe);
		alertDialog.show();
    }
} 