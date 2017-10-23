package com.example.song.capstone_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by song on 2017-10-23.
 */

public class setting extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView ipView = (TextView)findViewById(R.id.ipView);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
    } // end of onCreate
}
