package com.example.n2_faustino;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MensagemService {
    @GET("advice")
    Call<MensagemSlip> buscarMensagem();
}
