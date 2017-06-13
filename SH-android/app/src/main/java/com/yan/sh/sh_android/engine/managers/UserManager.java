package com.yan.sh.sh_android.engine.managers;

import java.util.UUID;

/**
 * Created by yan on 6/11/17.
 */

public class UserManager extends Manager {
    private boolean connectedToGame;
    private boolean userInitialized;

    private String nickName;
    private UUID uuid;
    private UUID currentSession;

    public UserManager(){
        this.startup();
        connectedToGame = false;
        userInitialized = false;
    }


}
