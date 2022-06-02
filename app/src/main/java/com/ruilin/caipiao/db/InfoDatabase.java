package com.ruilin.caipiao.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDate;

@Database(entities = {Info.class, Password.class}, version = 2)
public abstract class InfoDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "info.db";

    @SuppressWarnings("WeakerAccess")
    public abstract InfoDao infoDao();

    @SuppressWarnings("WeakerAccess")
    public abstract PasswordDao pwdDao();


    private static InfoDatabase sInstance;

    public static InfoDatabase getInstance() {
        return sInstance;
    }

    public static synchronized void init(Context context) {
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
                    })
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
    }

    public void savePassword(Password pwd) {
        int n = pwdDao().hasKey(pwd.getId());
        pwd.setDate(LocalDate.now().toString());
        if (n > 0){
            pwdDao().update(pwd);
        } else {
            pwdDao().insert(pwd);
        }
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("wgg", "InfoDatabase Migration migrate()#  1--->2  now=" + database.getVersion());
            database.execSQL("CREATE TABLE IF NOT EXISTS `" + Password.TABLE_NAME + "` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `type` INTEGER NOT NULL, `folder` TEXT, `account` TEXT, `password` TEXT, `bank_number` TEXT, `bank_date` TEXT, `date` TEXT)");
        }
    };
}
