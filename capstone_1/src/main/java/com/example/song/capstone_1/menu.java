package com.example.song.capstone_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by song on 2017-10-23.
 */

public class menu extends Activity implements Button.OnClickListener {

    public String ipA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView ipView = (TextView)findViewById(R.id.ipView);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        ipA =  intent.getStringExtra("value");
        ipView.setText(ipA);
    } // end of onCreate

    @Override
    public void onClick(View v) {

    }
}
