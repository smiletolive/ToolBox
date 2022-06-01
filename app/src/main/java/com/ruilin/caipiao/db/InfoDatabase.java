package com.ruilin.caipiao.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Info.class}, version = 1)
public abstract class InfoDatabase extends RoomDatabase{

    public static final String DATABASE_NAME = "info.db";

    @SuppressWarnings("WeakerAccess")
    public abstract InfoDao infoDao();

    private static InfoDatabase sInstance;

    public static InfoDatabase getInstance() {
        return sInstance;
    }

    public static synchronized void init(Context context){
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), InfoDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.d("wgg", "InfoDatabase onCreate()#  Version=" + db.getVersion());
                        }

                        @Override
                        public void onOpen(SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            Log.d("wgg", "InfoDatabase onOpen()#  Version=" + db.getVersion());
                        }

                        @Override
                        public void onDestructiveMigration(SupportSQLiteDatabase db) {
                            super.onDestructiveMigration(db);
                            Log.d("wgg", "InfoDatabase onDestructiveMigration()#  Version=" + db.getVersion());
                        }
                    }).build();
        }
    }
}
