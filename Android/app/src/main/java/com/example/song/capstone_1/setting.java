package com.example.song.capstone_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by song on 2017-10-23.
 */

public class setting extends Activity implements  Button.OnClickListener{

    public Handler handler;

    public setting() {
        handler = new Handler();
    }

    public String cmd;
    public String value;
    public String soundSensing = "0";
    public String switchSensing = "0";
    ControlRequset req;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView ipView = (TextView)findViewById(R.id.ipView);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        value = intent.getStringExtra("value");
    } // end of onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.macroOn: {
                soundSensing = "1";
                req = new ControlRequset(value,soundSensing,switchSensing);
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
                break;
            }
            case R.id.macroOff: {
                soundSensing = "2";
                req = new ControlRequset(value,soundSensing,switchSensing);
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
                break;
            }
            case R.id.SmacroOn: {
                switchSensing = "1";
                req = new ControlRequset(value,soundSensing,switchSensing);
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
                break;
            }
            case R.id.SmacroOff: {
                switchSensing = "2";

                req = new ControlRequset(value,soundSensing,switchSensing);
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
                break;
            }
        }
    }

    class ContentInstanceObject {
        private String option1 = "0";
        private String option2 = "0";
        private String carNum = "0";

        public void setOption1(String value) {
            this.option1 = value;
        }
        public void setOption2(String value) {
            this.option2 = value;
        }

        public void setSendCarNum(String value) {
            this.carNum = value;
        }

        public String makeBodyJson() {
            String json = "";

            json += "{\"table\" : \"Option\", ";
            json += "\"CarNum\" : \"";
            json += carNum;
            json +="\",  ";
            json += "\"option1\" : \"";
            json += option1;
            json +="\",  ";
            json += "\"option2\" : \"";
            json += option2;
            json +="\",  ";
            json += "\"option3\" : \"";
            json += "0";
            json +="\" ";
            json += "}";

            return json;
        }
    }

    class ControlRequset extends Thread {
        private final Logger LOG = Logger.getLogger(ControlRequset.class.getName());

        private IReceived receiver;

        private String option1 = "";
        private String option2 = "";
        private String carNum = "";

        public ContentInstanceObject instance;

        public ControlRequset( String carNum, String option1, String option2) {
            instance = new ContentInstanceObject();
            instance.setSendCarNum(carNum);
            instance.setOption1(option1);
            instance.setOption2(option2);
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
