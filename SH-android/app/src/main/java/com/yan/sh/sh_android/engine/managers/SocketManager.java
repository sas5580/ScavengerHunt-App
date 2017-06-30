package com.yan.sh.sh_android.engine.managers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import timber.log.Timber;

/**
 * Created by yan on 6/11/17.
 * Handles socket request to the server
 */

public class SocketManager extends Manager {

    final private String domain = "https://glacial-garden-48114.herokuapp.com/";

    public final class SocketListener extends WebSocketListener{
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            Timber.i("SOCKET MESSAGE:" + "CONNECTED");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Timber.i("SOCKET MESSAGE:" + text);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
        }
    }

    WebSocket webSocket;
    ScheduledExecutorService serialQueue;

    public SocketManager(){
        this.startup();
        serialQueue = Executors.newSingleThreadScheduledExecutor();
        serialQueue.execute(new Runnable() {
            @Override
            public void run() {
                openSocket();
            }
        });
    }

    //send completed objective to
    public boolean sendSocketMessage(final String message){
        if(webSocket == null || message == null)
            return false;
        if(serialQueue != null){
            serialQueue.execute(new Runnable() {
                @Override
                public void run() {
                    webSocket.send(message);
                }
            });
        }
        return true;
    }

    private void openSocket(){
        if(webSocket != null){
            OkHttpClient client = new OkHttpClient();
            SocketListener listener = new SocketListener();
            Request request = new Request.Builder().url("ws://echo.websocket.org").build();
            webSocket = client.newWebSocket(request, listener);
            client.dispatcher().executorService().shutdown();
        }
    }

    private void closeSocket(){
        if(webSocket == null)
            return;
        webSocket.close(1000, "Expected Shutdown");
        webSocket = null;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        closeSocket();
    }
}
