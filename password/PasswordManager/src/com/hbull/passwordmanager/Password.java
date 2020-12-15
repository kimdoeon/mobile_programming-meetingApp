/*
 * Copyright (C) 2011 Donghyun, Hwang (hbull@hanmail.net)
 */

package com.hbull.passwordmanager;

import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ViewFlipper;

public class Password extends Activity {
	public static final String NEXT_ACTIVITY = "nextActivity";
	public static final String PASSWORD = "password";
	public static final String RESULT_PASSWORD = "resultPassword";
	public static final String MODE = "mode";
	
	public static final int MODE_CHANGE_PASSWORD = 0;
	public static final int MODE_INIT_PASSWORD = 1;
	public static final int MODE_CHECK_PASSWORD = 2;
	
	public static final int PHASE_INIT_PASSWORD = 0;
	public static final int PHASE_INPUT_PASSWORD = 1;
	public static final int PHASE_CONFIRM_PASSWORD = 2;
	
	private int currentMode = PHASE_CONFIRM_PASSWORD;
	private int initMode = PHASE_CONFIRM_PASSWORD;
	private String currentPassword;
	private int passwordLength = 9999;
	private Intent nextActivity;
	private EditText passwordForm, passwordConfirmForm;
	private String passwordString;
	private ViewFlipper passwordFlipper;
	private TranslateAnimation pushLeftIn, pushLeftOut, shakeAni;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        init();
        initAnimation();
    }
    
    private Runnable passwordRunnable = new Runnable(){
        @Override
        public void run() {
        	checkPassword();
        }
    };
     
    private void checkPassword(){  	
    	switch(currentMode){
    	case PHASE_INIT_PASSWORD:
    		if( passwordString.equals(passwordForm.getText().toString()) ){
    			goToNextPhase();
    		}else{
                passwordForm.startAnimation(shakeAni);
    		}
    		break;
    		
    	case PHASE_INPUT_PASSWORD:
    		goToNextPhase();
    		break;
    		
    	case PHASE_CONFIRM_PASSWORD:
    		EditText currentForm = passwordConfirmForm; 
    		if(initMode == PHASE_CONFIRM_PASSWORD) currentForm = passwordForm;
    		
    		if( currentPassword.equals(currentForm.getText().toString()) )
    			goToNextPhase();
    		else{
                passwordForm.startAnimation(shakeAni);
    		}
    	}
    	
    	passwordForm.setText("");
    	passwordConfirmForm.setText("");
    }
    
    private void goToNextPhase(){
    	switch(currentMode){
    	case PHASE_INIT_PASSWORD:
    		currentMode = PHASE_INPUT_PASSWORD;
    		break;
    		
    	case PHASE_INPUT_PASSWORD:
    		currentPassword = passwordForm.getText().toString();
    		currentMode = PHASE_CONFIRM_PASSWORD;
    	             
    		passwordFlipper.setInAnimation(pushLeftIn);
    		passwordFlipper.setOutAnimation(pushLeftOut);
    		passwordFlipper.showPrevious();
    		break;
    		
    	case PHASE_CONFIRM_PASSWORD:
    		finish();
    		nextActivity.putExtra(RESULT_PASSWORD, currentPassword);
        	startActivity(nextActivity);    		
        	break;
    	}
    }
    
    private void init(){
    	Intent intent = getIntent();
        String nextActivityClassString = intent.getStringExtra(NEXT_ACTIVITY);
        nextActivity = new Intent();
        nextActivity.setClassName(Password.this, nextActivityClassString);
        
        initMode = intent.getIntExtra(MODE, MODE_CHECK_PASSWORD);
        passwordString = intent.getStringExtra(PASSWORD);
        
        currentMode = initMode;
        currentPassword = passwordString;
        passwordLength = passwordString.length();
        
        passwordFlipper = (ViewFlipper)findViewById(R.id.password_flipper);
        passwordForm = (EditText)findViewById(R.id.password);       
        passwordForm.addTextChangedListener(new TextWatcher() {
        	public void  afterTextChanged (Editable s){
        	}
            public void  beforeTextChanged  (CharSequence s, int start, int count, int after){
            }
            public void  onTextChanged  (CharSequence s, int start, int before, int count) {
            	if(passwordForm.getText().toString().length() == passwordLength){
            		Handler passwordHandler = new Handler();
            		passwordHandler.postDelayed(passwordRunnable, 200);
        		}
            } 
        });
        
        passwordConfirmForm = (EditText)findViewById(R.id.password_confirm);       
        passwordConfirmForm.addTextChangedListener(new TextWatcher() {
        	public void  afterTextChanged (Editable s){
        	}
            public void  beforeTextChanged  (CharSequence s, int start, int count, int after){
            }
            public void  onTextChanged  (CharSequence s, int start, int before, int count) {
            	if(passwordConfirmForm.getText().toString().length() == passwordLength){
            		Handler passwordHandler = new Handler();
            		passwordHandler.postDelayed(passwordRunnable, 200);
        		}
            } 
        });
    }
    
    private void initAnimation(){
    	pushLeftIn = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 1.0f,   
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
    	pushLeftIn.setDuration(200);
    	pushLeftIn.setFillAfter(true);
    	
    	pushLeftOut = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,   
                TranslateAnimation.RELATIVE_TO_SELF, -1.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
    	pushLeftOut.setDuration(200);
    	pushLeftOut.setFillAfter(true);
    	
    	shakeAni = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,   
                TranslateAnimation.RELATIVE_TO_SELF, 0.05f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
    	shakeAni.setDuration(300);
    	shakeAni.setInterpolator(new CycleInterpolator(2.0f));
    }
}