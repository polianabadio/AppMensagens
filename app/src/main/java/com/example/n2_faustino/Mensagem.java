package com.example.n2_faustino;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity(tableName = "mensagens")
public class Mensagem {
    @PrimaryKey
    @NotNull
    public String id;
    public String advice;

    @Override
    public String toString() {
        return this.advice;
    }
}
