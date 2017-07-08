package com.yan.sh.sh_android.engine.managers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yan.sh.sh_android.engine.Engine;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import io.socket.emitter.Emitter;
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


    ScheduledExecutorService serialQueue;
    private Socket socketIO;
    private Context mContext;

    public SocketManager(Context context){
        this.startup();
        serialQueue = Executors.newSingleThreadScheduledExecutor();
        mContext = context;

        try{
            socketIO = IO.socket(domain);
        } catch (URISyntaxException ex) {
            Timber.e(ex, "Error initalizing socket");
        }
    }

    //send completed objective to
    public void sendSocketMessage(final String message, final JSONObject arguments){
        if(socketIO == null || !socketIO.connected()){
            openSocket();
        }

        socketIO.emit(message, arguments);
    }

    public void openSocket(){
        if(socketIO == null){
            try {
                socketIO = IO.socket(domain);
            } catch (URISyntaxException ex) {
                Timber.e(ex, "URI error");
            }
        }

        if(socketIO.connected()){
            return;
        }

        socketIO.connect();
        socketIO.on(Socket.EVENT_CONNECT, new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                Timber.i("Connected");
            }
        }).on("connection", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                Timber.i("on connection");
                try {
                    JSONObject json = (JSONObject) args[0];
                    if(json != null){
                        Timber.i(json.toString());
                        Engine.user().setUuid(json.getJSONObject("data").getString("id"));
                        Engine.user().setGameKey(Engine.game().getGameCode());
                    }
                } catch (JSONException ex){
                    Timber.e(ex, "JSON exception");
                }

            }
        });
    }

    public void closeSocket(){
        if(socketIO == null || !socketIO.connected()){
            return;
        }

        socketIO.off();
        socketIO.disconnect();
        socketIO.close();
        socketIO = null;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        closeSocket();
    }
}
