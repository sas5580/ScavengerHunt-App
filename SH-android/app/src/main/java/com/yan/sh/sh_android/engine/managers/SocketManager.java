package com.yan.sh.sh_android.engine.managers;

import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import timber.log.Timber;

import io.socket.client.IO;
import io.socket.client.Socket;

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
    private Socket socketIO;

    public SocketManager(){
        this.startup();
        serialQueue = Executors.newSingleThreadScheduledExecutor();
        //openSocket();
        try{
            socketIO = IO.socket(domain);
            socketIO.connect();
        } catch (URISyntaxException ex) {
            Timber.e(ex, "Error initalizing socket");
        }
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

    public void openSocket(){
        Timber.i("on open socket");
        if(serialQueue != null){
            serialQueue.execute(new Runnable() {
                @Override
                public void run() {
                    Timber.i("Attempting to connect to socket");
                    OkHttpClient client = new OkHttpClient();
                    SocketListener listener = new SocketListener();
                    Request request = new Request.Builder().url("https://glacial-garden-48114.herokuapp.com/").build();
                    webSocket = client.newWebSocket(request, listener);
                    client.dispatcher().executorService().shutdown();
                }
            });

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
