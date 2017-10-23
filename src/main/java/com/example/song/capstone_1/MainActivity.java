package com.example.song.capstone_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements Button.OnClickListener{

    public String value;
    public Button send;
    public TextView Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Text = (TextView) findViewById(R.id.textView);
        setContentView(R.layout.activity_main);
        send = (Button) findViewById(R.id.send);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send: {
                EditText commentOutput = (EditText) findViewById(R.id.ip_address);
                value = commentOutput.getText().toString();

                Intent myintent;
                myintent = new Intent(getApplicationContext(), menu.class);
                myintent.putExtra("value", value);
                startActivity(myintent);
                break;
            }
        }
    }
}
