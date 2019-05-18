package com.app.mg.pruebadewebsocket.WebSocket;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.app.mg.connectionlibraryandroid.Implementations.MessageMethods;
import com.app.mg.pruebadewebsocket.Entities.MessageBody;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class WebsocketServer extends WebSocketServer {

    MessageMethods<MessageBody,WebsocketClient,WebSocket> messageMethods;
    public WebsocketServer(InetSocketAddress address) {
        super(address);
        messageMethods = new MessageMethods<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

        InetSocketAddress clientIP = conn.getRemoteSocketAddress();
        MessageBody messageBody = new MessageBody()
                .setToTV(false)
                .setSender(conn.getLocalSocketAddress().getAddress().toString())
                .setMessage("Bienvenido al servidor usuario: " + clientIP.getAddress().toString().replace("/", ""));
        conn.send(messageMethods.ConstructMessageBodyJSON(conn.getLocalSocketAddress().getAddress().toString(),messageBody));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        broadcast(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {
    }

}
