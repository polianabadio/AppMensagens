package com.example.n2_faustino;

import android.content.Context;

import androidx.room.Room;

public class Conexao {

    private static AppDatabase appDatabase;

    private Conexao() {
    }

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "N2FAUSTINO"
            ).build();

        }
        return appDatabase;
    }
}