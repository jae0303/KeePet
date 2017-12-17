package com.example.song.capstone_1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by song on 2017-12-11.
 */

public class carTest {
    private car CarTest;
    private car.ControlRequset req;

    @Before
    public void setup(){
        CarTest = new car();
        req = new car().new ControlRequset("Control","carNum" ,"x", "y", "cmd");
    }

    @Test
    public  void CarAwsTest(){
        req.setReceiver(new car.IReceived() {
            public void getResponseBody(final String msg) {
                CarTest.handler.post(new Runnable() {
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
