package com.example.app1;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {UserInfo.class},exportSchema = false, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "users";
    private static AppDatabase instance = null;
    public static synchronized  AppDatabase getDB(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract UserDao userDao();
}