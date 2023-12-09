package com.example.n2_faustino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class IncluirMensagem extends AppCompatActivity implements View.OnClickListener{

    private AppDatabase db;
    private Mensagem mensagem;
    private MensagemDao mensagemDao;
    private MainActivityViewModel viewModel;
    private TextView respostaMensagem;
    private Button btnAdicionarMensagem;
    private Button btnBuscarMensagem;
    private Boolean adicionar;
    private int idAleatorio;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_mensagem);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


        respostaMensagem = findViewById(R.id.respostaMensagem);
        btnBuscarMensagem = findViewById(R.id.button_buscar_mensagem);
        btnAdicionarMensagem = findViewById(R.id.button_adicionar_mensagem);
        btnVoltar = findViewById(R.id.button_voltar);

        btnVoltar.setOnClickListener(this);
        btnAdicionarMensagem.setOnClickListener(this);
        btnBuscarMensagem.setOnClickListener(this);

        AppDatabase db = Conexao.getInstance(this);
        viewModel.init(db);

        viewModel.getFimBusca().observe(this, sucesso -> {
            if (sucesso) {
                String mensagem = "Mensagem: " + viewModel.getRespostaMensagem().getValue().advice;
                respostaMensagem.setVisibility(View.VISIBLE);
                respostaMensagem.setText(mensagem);
            }
        });

            viewModel.adicionarMensagemAoBanco(viewModel.getRespostaMensagem().getValue());
            viewModel.getFimInsertMensagem().observe(this, fim -> {
                if(fim){
                    Toast.makeText(getApplicationContext(), "Mensagem inserido com sucesso!"
                            , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IncluirMensagem.this, MainActivity.class);
                    startActivity(intent);
                }
                if(!fim){
                    Toast.makeText(getApplicationContext(), "Mensagem já cadastrada!"
                            , Toast.LENGTH_SHORT).show();
                }
            });
    }


    @Override
    public void onClick(View v) {
        if(v==btnVoltar){
            Intent intent = new Intent(IncluirMensagem.this, MainActivity.class);
            startActivity(intent);
        }

        if(v==btnBuscarMensagem){
            adicionar=false;
            viewModel.buscarMensagem(adicionar);
        }

        if(v==btnAdicionarMensagem){
            adicionar=true;
            Log.i("",""+String.valueOf(idAleatorio));
            viewModel.buscarMensagem(adicionar);
        }

    }
}
