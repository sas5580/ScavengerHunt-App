package com.yan.sh.sh_android.engine.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by yan on 6/11/17.
 * Handles data storage and persistance
 */

public class DataManager extends Manager{

    //Store current game data incase user becomes offline and data not synced
    public static final class GameData implements BaseColumns{
        public static final String TABLE_NAME = "gameData";
        public static final String COLUMN_OBJECTIVE_ID = "objectiveId";
        public static final String COLUMN_OBJECTIVE_COMPLETED_TIMESTAMP = "completedTime";
        public static final String COLUMN_OBJECTIVE_PICTURE_URL = "objectiveUrl";
        public static final String COLUMN_GAME_ID = "gameId";
        public static final String COLUMN_USER_ID = "userId";
        public static final String COLUMN_SYNCED = "dataSynced";
    }

    public static class GameDataDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "gamedata.db";
        private static final int DATABASE_VERSION = 1;

        public GameDataDBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String SQL_CREATE_GAMEDATA_TABLE = "CREATE TABLE " + GameData.TABLE_NAME + " (" +
                    GameData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GameData.COLUMN_OBJECTIVE_ID + " INTEGER NOT NULL, " +
                    GameData.COLUMN_OBJECTIVE_COMPLETED_TIMESTAMP + " TEXT NOT NULL, " +
                    GameData.COLUMN_OBJECTIVE_PICTURE_URL + " TEXT, " +
                    GameData.COLUMN_GAME_ID + " INTEGER NOT NULL, " +
                    GameData.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                    GameData.COLUMN_SYNCED + " BOOLEAN NOT NULL " +
                    "); ";

            db.execSQL(SQL_CREATE_GAMEDATA_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + GameData.TABLE_NAME);
            onCreate(db);
        }
    }
    private Context mContext;
    private SQLiteDatabase mDb;

    public DataManager(Context context){
        this.startup();
        mContext = context;
        GameDataDBHelper gameDataDBHelper = new GameDataDBHelper(mContext);
        mDb = gameDataDBHelper.getWritableDatabase();
    }


    //TODO Reading, writing and dropping table

}
