package com.example.folha_de_pagamento.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.folha_de_pagamento.R;
import com.google.android.material.snackbar.Snackbar;

public class PrimeiraActivity extends AppCompatActivity {

    private EditText nome;
    private Button btnComecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira);

        btnComecar = findViewById(R.id.btnComecar);
        nome = findViewById(R.id.etxNome);

        btnComecar.setOnClickListener(v ->{
            if (nome.getText().toString().isEmpty() || nome.getText().toString().length() > 15) {
                nome.setError("Nome Inválido");
                Snackbar.make(nome, "Nome Inválido", Snackbar.LENGTH_SHORT).show();
            } else {
                Intent intencao = new Intent(this, FormActivity.class);
                intencao.putExtra("nome", nome.getText().toString());
                startActivity(intencao);
                finish();
            }
        });
    }
}