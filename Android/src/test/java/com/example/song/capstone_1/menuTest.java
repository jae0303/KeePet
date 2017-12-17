package com.example.song.capstone_1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by song on 2017-12-11.
 */

public class menuTest {
    private menu MenuTest;
    private menu.ControlRequset req;

    @Before
    public void setup() {
        MenuTest = new menu();
        req = new menu().new ControlRequset("5");
    }

    @Test
    public void MenuAwsTest() {
        req.setReceiver(new menu.IReceived() {
            public void getResponseBody(final String msg) {
                MenuTest.handler.post(new Runnable() {
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
