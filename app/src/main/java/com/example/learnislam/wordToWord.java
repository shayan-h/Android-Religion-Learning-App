package com.example.learnislam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class wordToWord extends Activity {

    private Button button;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_wtw);

        /*
        TextView textview = new TextView(this);
        textview.setText("Welcome to Word to Word Translation!");
        setContentView(textview);
         */

        button = (Button) findViewById(R.id.buttonTest2);
        button.setActivated(true);

    }
}
