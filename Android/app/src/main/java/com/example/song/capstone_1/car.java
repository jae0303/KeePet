package com.example.song.capstone_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class car extends Activity implements Button.OnClickListener{

   //private static final String MOVIE_URL = "https://192.168.43.103:8080/?action=stream";

    public  String value;
    public static int macroStart = 0;
    public static int macroCounter = 0;

    ControlRequset req;

    public Handler handler;

    public car() {
        handler = new Handler();
    }

    public static int x,y = 0;
    public static int cmd = 0;

    Button cw,ccw, micBotton;
    ToggleButton servoButton, laiserBotton;

    RelativeLayout layout_joystick;
    ImageView image_joystick, image_border;

    JoyStickClass js;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        cw = (Button)findViewById(R.id.cw);
        ccw = (Button)findViewById(R.id.ccw);
        servoButton = (ToggleButton) findViewById(R.id.servoButton);
        laiserBotton = (ToggleButton) findViewById(R.id.laiserBotton);
        micBotton = (Button) findViewById(R.id.micBotton);

        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.console);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        value =  intent.getStringExtra("value");

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.setPadding(0,0,0,0);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        String url ="http://192.168.43.103:8080/javascript_simple.html";
        webView.loadUrl(url);

        layout_joystick.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    x = js.getX();
                    y = js.getY();
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    x = js.getX();
                    y = js.getY();
                }
                return true;
            }
        });

        TimerTask sendJoy = new TimerTask() {
            public void run() {
                try {
                    req = new ControlRequset("Control", value, String.valueOf(x), String.valueOf(y), "0");
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(final String msg) {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {

                                    } catch (Exception ex) {

                                    }
                                }
                            });
                        }
                    });
                    req.start();

                    if (macroStart == 1) {
                        req= new ControlRequset("Macro", String.valueOf(macroCounter), String.valueOf(x), String.valueOf(y), "0");
                        req.setReceiver(new IReceived() {
                            public void getResponseBody(final String msg) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            macroCounter++;

                                            if (macroCounter  > 20) {
                                                macroStart = 0;
                                                macroCounter = 0;

                                                Toast.makeText(getApplicationContext(), "매크로 종료", Toast.LENGTH_LONG).show();

                                            }

                                        } catch (Exception ex) {

                                        }
                                    }
                                });
                            }
                        });
                        req.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer sendTimer = new Timer();
        sendTimer.schedule(sendJoy, 500, 2000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.servoButton: {
                if (servoButton.isChecked()) {
                    servoButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.servostopb)
                    );

                    req = new ControlRequset("Control",value, "0", "0", "1");
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(final String msg) {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {

                                    } catch (Exception ex) {

                                    }
                                }
                            });
                        }
                    });
                    req.start();

                    if (macroStart == 1) {
                        req= new ControlRequset("Macro", String.valueOf(macroCounter),"0", "0", "1");
                        req.setReceiver(new IReceived() {
                            public void getResponseBody(final String msg) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            macroCounter++;

                                            if (macroCounter > 20) {
                                                macroStart = 0;
                                                macroCounter = 0;
                                            }

                                        } catch (Exception ex) {

                                        }
                                    }
                                });
                            }
                        });
                        req.start();
                    }
                    break;
                } else {
                    servoButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.servostartb)
                    );

                    req = new ControlRequset("Control",value, "0", "0", "2");
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(final String msg) {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {

                                    } catch (Exception ex) {

                                    }
                                }
                            });
                        }
                    });
                    req.start();

                    if (macroStart == 1) {
                        req= new ControlRequset("Macro", String.valueOf(macroCounter),"0", "0", "2");
                        req.setReceiver(new IReceived() {
                            public void getResponseBody(final String msg) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            macroCounter++;

                                            if (macroCounter > 20) {
                                                macroStart = 0;
                                                macroCounter = 0;
                                            }

                                        } catch (Exception ex) {

                                        }
                                    }
                                });
                            }
                        });
                        req.start();
                    }
                }
                break;
            }
            case R.id.laiserBotton: {
                if (laiserBotton.isChecked()) {
                    laiserBotton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.lasieroffb)
                    );

                    req = new ControlRequset("Control",value ,"0", "0", "3");
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(final String msg) {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {

                                    } catch (Exception ex) {

                                    }
                                }
                            });
                        }
                    });
                    req.start();

                    if (macroStart == 1) {
                        req= new ControlRequset("Macro", String.valueOf(macroCounter),"0", "0", "3");
                        req.setReceiver(new IReceived() {
                            public void getResponseBody(final String msg) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            macroCounter++;

                                            if (macroCounter > 20) {
                                                macroStart = 0;
                                                macroCounter = 0;
                                            }

                                        } catch (Exception ex) {

                                        }
                                    }
                                });
                            }
                        });
                        req.start();
                    }
                    break;
                } else {
                    laiserBotton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.laseronb)
                    );


                    req = new ControlRequset("Control",value,"0", "0", "4");
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(final String msg) {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {

                                    } catch (Exception ex) {

                                    }
                                }
                            });
                        }
                    });
                    req.start();

                    if (macroStart == 1) {
                        req= new ControlRequset("Macro", String.valueOf(macroCounter),"0", "0", "4");
                        req.setReceiver(new IReceived() {
                            public void getResponseBody(final String msg) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            macroCounter++;

                                            if (macroCounter > 20) {
                                                macroStart = 0;
                                                macroCounter = 0;
                                            }

                                        } catch (Exception ex) {

                                        }
                                    }
                                });
                            }
                        });
                        req.start();
                    }
                }
                break;
            }
            case R.id.micBotton: {
                req = new ControlRequset("Control",value,"0", "0", "7");
                req.setReceiver(new IReceived() {
                    public void getResponseBody(final String msg) {
                        handler.post(new Runnable() {
                            public void run() {
                                try {

                                } catch (Exception ex) {

                                }
                            }
                        });
                    }
                });
                req.start();

                if (macroStart == 1) {
                    req= new ControlRequset("Macro", String.valueOf(macroCounter),"0", "0", "7");
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(final String msg) {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {
                                        macroCounter++;

                                        if (macroCounter > 20) {
                                            macroStart = 0;
                                            macroCounter = 0;
                                        }

                                    } catch (Exception ex) {

                                    }
                                }
                            });
                        }
                    });
                    req.start();
                }
                break;
            }
            case R.id.cw: {
                for (int i = 0; i < 4; i++) {
                    req = new ControlRequset("Control", value , "0", "0", "5");
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(final String msg) {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {

                                    } catch (Exception ex) {

                                    }
                                }
                            });
                        }
                    });
                    req.start();

                    if (macroStart == 1) {
                        req = new ControlRequset("Macro", String.valueOf(macroCounter), "0", "0", "5");
                        req.setReceiver(new IReceived() {
                            public void getResponseBody(final String msg) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            macroCounter++;

                                            if (macroCounter > 20) {
                                                macroStart = 0;
                                                macroCounter = 0;
                                            }

                                        } catch (Exception ex) {

                                        }
                                    }
                                });
                            }
                        });
                        req.start();
                    }
                }
                break;
            }
            case R.id.ccw: {
                for (int j = 0; j < 4; j++) {
                    req = new ControlRequset("Control", value , "0", "0", "6");
                    req.setReceiver(new IReceived() {
                        public void getResponseBody(final String msg) {
                            handler.post(new Runnable() {
                                public void run() {
                                    try {

                                    } catch (Exception ex) {

                                    }
                                }
                            });
                        }
                    });
                    req.start();

                    if (macroStart == 1) {
                        req = new ControlRequset("Macro", String.valueOf(macroCounter), "0", "0", "6");
                        req.setReceiver(new IReceived() {
                            public void getResponseBody(final String msg) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            macroCounter++;

                                            if (macroCounter > 20) {
                                                macroStart = 0;
                                                macroCounter = 0;
                                            }

                                        } catch (Exception ex) {

                                        }
                                    }
                                });
                            }
                        });
                        req.start();
                    }
                }
                break;
            }
            case R.id.macroSet: {
                macroStart = 1;
                break;
            }
        }
    }

    class ContentInstanceObject {

        private String sendTable = "Control";
        private String sendCarNum= "0";
        private String sendX = "0";
        private String sendY = "0";
        private String sendCmd= "0";

        public void setSendTable(String value) {
            this.sendTable = value;
        }
        public void setSendCarNum(String value) {
            this.sendCarNum = value;
        }
        public void setSendX(String value) {
            this.sendX = value;
        }
        public void setSendY(String value) {
            this.sendY = value;
        }
        public void setSendCmd(String value) {
            this.sendCmd = value;
        }


        public String makeBodyJson() {
            String json = "";

            json += "{\"table\" : \"";
            json += sendTable;
            json += "\", ";
            json += "\"CarNum\" : \"";
            json += sendCarNum;
            json += "\", ";
            json += "\"x\" : \"";
            json += sendX;
            json += "\",  ";
            json += "\"y\" : \"";
            json += sendY;
            json += "\",  ";
            json += "\"cmd\" : \"";
            json += sendCmd;
            json += "\" ";
            json += "}";

            return json;
        }
    }

    class ControlRequset extends Thread {
        private final Logger LOG = Logger.getLogger(ControlRequset.class.getName());

        private IReceived receiver;

        private String sendTable = "Control";
        private String sendCarNum = "0";
        private String sendX = "0";
        private String sendY = "0";
        private String sendCmd= "0";

        public ContentInstanceObject instance;

        public ControlRequset(String sendTable, String sendCarNum ,String sendX, String sendY, String sendCmd ) {
            instance = new ContentInstanceObject();
            instance.setSendTable(sendTable);
            instance.setSendCarNum(sendCarNum);
            instance.setSendX(sendX);
            instance.setSendY(sendY);
            instance.setSendCmd(sendCmd);
        }

        public void setReceiver(IReceived handler) {
            this.receiver = handler;
        }

        public void run() {
            try {
                String sb;

                sb = awsConfig.aws_URL;

                URL mUrl = new URL(sb);

                HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setInstanceFollowRedirects(false);

                conn.setRequestProperty("option", "text/plain");
                conn.setRequestProperty("content-type", "application/json");
                conn.setRequestProperty("host", "jceiryvfi2.execute-api.us-west-2.amazonaws.com");
                conn.setRequestProperty("cache-control", "no-cache");
                String reqContent = instance.makeBodyJson();
                conn.setRequestProperty("Content-Length", String.valueOf(reqContent.length()));

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.write(reqContent.getBytes());
                dos.flush();
                dos.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String resp = "";
                String strLine;
                while ((strLine = in.readLine()) != null) {
                    resp += strLine;
                }

                if (receiver != null) {
                    receiver.getResponseBody(resp);
                }

                conn.disconnect();
            } catch (Exception exp) {
                LOG.log(Level.SEVERE, exp.getMessage());
            }
        }
    }

    public interface IReceived{
        void getResponseBody(String msg);
    }

    public class awsConfig{
        public final static String aws_URL = "https://jceiryvfi2.execute-api.us-west-2.amazonaws.com/beta4/Application-GET";
    }
}