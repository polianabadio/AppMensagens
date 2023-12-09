package com.example.n2_faustino;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Mensagem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MensagemDao mensagemDao();
}
