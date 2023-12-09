package com.example.n2_faustino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AppDatabase db;
    private Mensagem mensagem;
    private MensagemDao mensagemDao;
    private MainActivityViewModel viewModel;
    private Button btnIncluirMensagem;
    private ListView listaMensagens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIncluirMensagem = findViewById(R.id.button_incluir_mensagem);
        listaMensagens = findViewById(R.id.listaMensagens);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        AppDatabase db = Conexao.getInstance(this);
        viewModel.init(db);

        viewModel.listarMensagens();

        viewModel.getListaMensagens().observe(this, new Observer<List<Mensagem>>() {
            @Override
            public void onChanged(List<Mensagem> mensagens) {
                ArrayAdapter<Mensagem> adapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, mensagens);
                listaMensagens.setAdapter(adapter);
            }
        });


        btnIncluirMensagem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==btnIncluirMensagem){
            Intent intent = new Intent(MainActivity.this, IncluirMensagem.class);
            startActivity(intent);
        }

    }
}