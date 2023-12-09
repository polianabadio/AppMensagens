package com.example.n2_faustino;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {

    private MensagemDao mensagemDao;

    private MutableLiveData<Boolean> fimBusca = new MutableLiveData<>();
    public LiveData<Boolean> getFimBusca() {
        return fimBusca;
    }

    private MutableLiveData<Boolean> fimInsertMensagem = new MutableLiveData<>();
    public LiveData<Boolean> getFimInsertMensagem() {
        return fimInsertMensagem;
    }

    private MutableLiveData<Mensagem> respostaMensagem = new MutableLiveData<>();
    public LiveData<Mensagem> getRespostaMensagem()  {
        return respostaMensagem;
    }
    private MutableLiveData<List<Mensagem>> listaMensagens = new MutableLiveData<>();
    public LiveData<List<Mensagem>> getListaMensagens()  {
        return listaMensagens;
    }

    public void init(AppDatabase db) {
        mensagemDao = db.mensagemDao();
    }

    public void buscarMensagem(Boolean adicionar){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.adviceslip.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MensagemService service = retrofit.create(MensagemService.class);
        Call<MensagemSlip> call = service.buscarMensagem();

        call.enqueue(new Callback<MensagemSlip>() {
            @Override
            public void onResponse(Call<MensagemSlip> call, Response<MensagemSlip> response) {
                if(response.isSuccessful()) {
                    MensagemSlip mensagem = response.body();
                    Mensagem mensagem1 = new Mensagem();
                    mensagem1.advice = mensagem.getSlip().getAdvice();
                    mensagem1.id = mensagem.getSlip().getId();
                        respostaMensagem.postValue(mensagem1);
                        fimBusca.postValue(true);
                        if(adicionar){
                            adicionarMensagemAoBanco(mensagem1);
                        }
                    } else {
                        fimBusca.postValue(false);
                    }
            }

            @Override
            public void onFailure(Call<MensagemSlip> call, Throwable t) {
                    fimBusca.postValue(false);
            }
        });
    }
    public void adicionarMensagemAoBanco(Mensagem mensagem){
        new Thread(() -> {
            try {

                if(mensagem!=null) {
                    int existe = mensagemDao.verificarMensagemPorIdCadastrado(Integer.valueOf(mensagem.id));

                    if (existe==0) {
                        mensagemDao.inserirMensagem(mensagem);
                        fimInsertMensagem.postValue(true);
                    } else {
                        fimInsertMensagem.postValue(false);
                    }
                }
            } catch (android.database.sqlite.SQLiteConstraintException e) {

            }
        }).start();
    }

    public void listarMensagens(){
        new Thread(() -> {
            try {
            List<Mensagem> listaAtualizada= mensagemDao.obterTodasMensagens();
            listaMensagens.postValue(listaAtualizada);
            } catch (android.database.sqlite.SQLiteConstraintException e) {

            }
        }).start();
    }
}
