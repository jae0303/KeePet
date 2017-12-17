package com.example.song.capstone_1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by song on 2017-12-11.
 */

public class settingTest {

    private setting Setting;
    private setting.ControlRequset req;

    @Before
    public void setup(){
        Setting = new setting();
        req =  new setting().new ControlRequset("value","soundSensing","switchSensing");
    }

    @Test
    public void setSettingAwsTest(){
        req.setReceiver(new setting.IReceived() {
            public void getResponseBody(final String msg) {
                Setting.handler.post(new Runnable() {
                    public void run() {
                        try {
                            assertEquals("\"success post\"", msg);
                        } catch (Exception ex) {

                        }
                    }
                });
            }
        });
        req.start();
    }
}
