package com.tongzhang.androidsocket;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button01 = null;
    private Button button02 = null;
    private EditText editText = null;
    private TextView textView = null;

    private static Socket ClientSocket = null;
    private byte[] msgBuffer = null;
    Handler handler = new Handler();

    private void initView() {
        button01 = (Button) findViewById(R.id.button01);
        button02 = (Button) findViewById(R.id.button02);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);

        button01.setOnClickListener(this);
        button02.setOnClickListener(this);

        button01.setEnabled(true);
        button02.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button01:
                // TODO: 15-9-4 socket连接线程
                connectThread();
                break;
            case R.id.button02:
                // TODO: 15-9-4 发送数据线程
                sendMsgThread();
                break;
        }
    }

    private void sendMsgThread() {

        final String text = editText.getText().toString();
        try {
            msgBuffer = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream outputStream;
                    //Socket输出流
                    outputStream = ClientSocket.getOutputStream();

                    outputStream.write(msgBuffer);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.append("发送成功：" + text + "\n");
                    }
                });
            }
        }).start();
    }

    private void connectThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ClientSocket = new Socket("192.168.1.196", 9001);
                    if (ClientSocket.isConnected()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.append("连接成功！" + "\n");
                                button01.setEnabled(false);
                                button02.setEnabled(true);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.append("连接失败！" + "\n");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}