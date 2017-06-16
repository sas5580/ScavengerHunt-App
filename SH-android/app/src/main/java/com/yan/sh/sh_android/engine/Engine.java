package com.yan.sh.sh_android.engine;

import android.content.Context;

import com.yan.sh.sh_android.engine.managers.DataManager;
import com.yan.sh.sh_android.engine.managers.GameManager;
import com.yan.sh.sh_android.engine.managers.HardwareManager;
import com.yan.sh.sh_android.engine.managers.NetworkManager;
import com.yan.sh.sh_android.engine.managers.ObjectiveManager;
import com.yan.sh.sh_android.engine.managers.SocketManager;
import com.yan.sh.sh_android.engine.managers.UserManager;

import java.net.Socket;

/**
 * Created by yan on 6/11/17.
 */

public class Engine {
    private DataManager data;
    private GameManager game;
    private HardwareManager hardware;
    private NetworkManager network;
    private SocketManager socket;
    private UserManager user;
    private ObjectiveManager objective;

    private boolean started = false;

    protected static Engine sharedInstance = null;
    public static Engine instance(){
        if(sharedInstance == null){
            sharedInstance = new Engine();
        }
        return sharedInstance;
    }

    public static void startup(Context context){
        Engine.instance().instanceStartup(context);
    }

    public static void shutdown(){
        Engine.instance().instanceShutdown();
    }

    //Manager getters
    public static DataManager data(){
        return Engine.instance().data;
    }

    public static GameManager game(){
        return Engine.instance().game;
    }

    public static HardwareManager hardware(){
        return Engine.instance().hardware;
    }

    public static NetworkManager network(){
        return Engine.instance().network;
    }

    public static SocketManager socket(){
        return Engine.instance().socket;
    }

    public static UserManager user(){
        return Engine.instance().user;
    }

    public static ObjectiveManager objective() { return Engine.instance().objective; }

    private void instanceStartup(Context context){
        if(started){
            return;
        }
        started = true;

        if(data == null) {
            data = new DataManager();
        }
        if(game == null){
            game = new GameManager();
        }
        if(hardware == null) {
            hardware = new HardwareManager(context);
        }
        if(network == null) {
            network = new NetworkManager();
        }
        if(socket == null) {
            socket = new SocketManager();
        }
        if(user == null){
            user = new UserManager();
        }
        if(objective == null){
            objective = new ObjectiveManager();
        }
    }

    private void instanceShutdown(){
        if(!started){
            return;
        }
        started = false;
        //shutdown managers
        data.shutdown();
        data = null;

        game.shutdown();
        game = null;

        hardware.shutdown();
        hardware = null;

        network.shutdown();
        network = null;

        socket.shutdown();
        socket = null;

        user.shutdown();
        user = null;

        objective.shutdown();
        objective = null;
    }
}
