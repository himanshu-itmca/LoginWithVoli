package com.rgnik.loginwithvoli;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    TextView txtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txtemail = (TextView)findViewById(R.id.temail);
        txtemail.setText(getIntent().getExtras().getString("temail"));

    }
}
