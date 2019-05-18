package com.app.mg.pruebadewebsocket.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
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
import com.google.gson.Gson;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


public class ControlWsActivity extends AppCompatActivity {
    String ipAddress = "";
    String myIpAddress = "";
    String port = "8080";

    WebsocketClient wsClient;

    ConnectMethods connectMethods = new ConnectMethods();
    MessageMethods<MessageBody,WebsocketClient,WebSocket> messageMethods = new MessageMethods<>();

    Button btnSendMessage;
    EditText etMessage;

    ImageButton btnUp, btnLeft, btnDown, btnRight;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_ws);
        btnSendMessage = findViewById(R.id.btn_send_message);
        etMessage = findViewById(R.id.et_message);

        btnUp = findViewById(R.id.ib_up);
        btnLeft = findViewById(R.id.ib_left);
        btnDown = findViewById(R.id.ib_down);
        btnRight = findViewById(R.id.ib_right);

        ipAddress = getIntent().getStringExtra("networkIp");

        myIpAddress = connectMethods.FindMyIpAddress(this);

        connectWebSocket();

        btnSendMessage.setOnClickListener(v -> {
            SendMessageBody(etMessage.getText().toString());
        });

        btnUp.setOnTouchListener(new RepeatListener(50, 50, v -> SendMessageBody("UP")));

        btnLeft.setOnTouchListener(new RepeatListener(50, 50, v -> SendMessageBody("LEFT")));

        btnDown.setOnTouchListener(new RepeatListener(50, 50, v -> SendMessageBody("DOWN")));

        btnRight.setOnTouchListener(new RepeatListener(50, 50, v -> SendMessageBody("RIGHT")));
    }

    private void connectWebSocket() {
        wsClient = new WebsocketClient(connectMethods.GetUriServer(ipAddress,port));
        wsClient.connect();
    }

    private void SendMessageBody(String message) {
        MessageBody messageBody = new MessageBody()
                .setMessage(message)
                .setSender(myIpAddress)
                .setToTV(true);
        messageMethods.SendMessageBody(messageBody,wsClient,myIpAddress);
    }

}
