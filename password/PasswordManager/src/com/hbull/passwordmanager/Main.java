/*
 * Copyright (C) 2011 Donghyun, Hwang (hbull@hanmail.net)
 */
package com.hbull.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Main extends Activity {
	EditText editText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText)findViewById(R.id.editText1);
        
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Main.this, Password.class);
                intent.putExtra(Password.NEXT_ACTIVITY, "com.hbull.passwordmanager.Profile");
                intent.putExtra(Password.PASSWORD, editText.getText().toString());
                intent.putExtra(Password.MODE, Password.MODE_CHANGE_PASSWORD);
                startActivity(intent);   
            }
		});
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Main.this, Password.class);
            	intent.putExtra(Password.NEXT_ACTIVITY, "com.hbull.passwordmanager.Profile");
                intent.putExtra(Password.PASSWORD, editText.getText().toString());
                intent.putExtra(Password.MODE, Password.MODE_INIT_PASSWORD);
                startActivity(intent);   
            }
		});
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Main.this, Password.class);
            	intent.putExtra(Password.NEXT_ACTIVITY, "com.hbull.passwordmanager.Main");
                intent.putExtra(Password.PASSWORD, editText.getText().toString());
                intent.putExtra(Password.MODE, Password.MODE_CHECK_PASSWORD);
                startActivity(intent);   
            }
		});
    }
    
}