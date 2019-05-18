package com.app.mg.pruebadewebsocket.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.mg.connectionlibraryandroid.Implementations.ConnectMethods;
import com.app.mg.connectionlibraryandroid.Implementations.MessageMethods;
import com.app.mg.pruebadewebsocket.Entities.MessageBody;
import com.app.mg.pruebadewebsocket.Entities.RepeatListener;
import com.app.mg.pruebadewebsocket.R;
import com.app.mg.pruebadewebsocket.WebSocket.WebsocketClient;
import com.app.mg.pruebadewebsocket.WebSocket.WebsocketServer;
import com.google.gson.Gson;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

public class WSAndControlActivity extends AppCompatActivity {

    String port = "8080";
    String ipAddress;

    ImageButton btnUp, btnLeft, btnDown, btnRight, btnA,btnB,btnX,btnY;

    ConnectMethods connectMethods = new ConnectMethods();
    MessageMethods<MessageBody,WebsocketClient,WebSocket> messageMethods = new MessageMethods<>();

    WebsocketClient wsClient;
    InetSocketAddress inetSockAddress;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsand_control);

        ipAddress = connectMethods.FindMyIpAddress(this);



        btnUp = findViewById(R.id.ib_up);
        btnLeft = findViewById(R.id.ib_left);
        btnDown = findViewById(R.id.ib_down);
        btnRight = findViewById(R.id.ib_right);
        btnA = findViewById(R.id.ib_a);
        btnB = findViewById(R.id.ib_b);
        btnX = findViewById(R.id.ib_x);
        btnY = findViewById(R.id.ib_y);


        btnUp.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SendMessageBody("UP");
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                SendMessageBody("STOPUP");
            }
            return false;
        });

        btnLeft.setOnTouchListener((v, event) -> {

            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SendMessageBody("LEFT");
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                SendMessageBody("STOPLEFT");
            }
            return false;
        });

        btnDown.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SendMessageBody("DOWN");
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                SendMessageBody("STOPDOWN");
            }
            return false;
        });
        btnRight.setOnTouchListener((v, event) -> {

            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SendMessageBody("RIGHT");
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                SendMessageBody("STOPRIGHT");
            }

            return false;
        });

        btnA.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SendMessageBody("A");
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                SendMessageBody("STOPA");
            }
            return false;
        });

        btnB.setOnTouchListener((v, event) -> {

            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SendMessageBody("B");
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                SendMessageBody("STOPB");
            }
            return false;
        });

        btnY.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SendMessageBody("Y");
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                SendMessageBody("STOPY");
            }
            return false;
        });
        btnX.setOnTouchListener((v, event) -> {

            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SendMessageBody("X");
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                SendMessageBody("STOPX");
            }

            return false;
        });

        SetWServerAndStart();

        Handler handler = new Handler();
        handler.postDelayed(this::connectWebSocket, 2500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wsClient != null) wsClient.close();
    }

    private void connectWebSocket() {
        wsClient = new WebsocketClient(connectMethods.GetUriServer(ipAddress, port));
        wsClient.connect();
    }

    private void SendMessageBody(String message) {
        MessageBody messageBody = new MessageBody()
                .setMessage(message)
                .setSender(ipAddress)
                .setToTV(true);

        messageMethods.SendMessageBody(messageBody, wsClient, ipAddress);
    }

    private void SetWServerAndStart() {
        inetSockAddress = connectMethods.GetISocketAddres(this, port);
        WebsocketServer wsServer = new WebsocketServer(inetSockAddress);
        wsServer.start();

        Toast.makeText(getApplicationContext(),"Server Abierto",Toast.LENGTH_SHORT).show();
    }
}