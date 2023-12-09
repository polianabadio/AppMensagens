package com.example.n2_faustino;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MensagemDao {

    @Query("SELECT COUNT(*) FROM mensagens WHERE id = :id")
    int verificarMensagemPorIdCadastrado(int id);

    @Insert
    void inserirMensagem(Mensagem mensagem);

    @Query("SELECT * FROM mensagens")
    List<Mensagem> obterTodasMensagens();
}
