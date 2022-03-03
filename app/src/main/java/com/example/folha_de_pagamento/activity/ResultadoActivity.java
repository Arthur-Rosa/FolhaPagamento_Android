package com.example.folha_de_pagamento.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.folha_de_pagamento.R;
import com.example.folha_de_pagamento.model.User;

public class ResultadoActivity extends AppCompatActivity {

    //Criando as variaveis
    private TextView salarioB, inss, irrf, valeRefeicao, valeTransporte, valeAlimentacao, planoSaude, salarioLiquido, porcetagem ;
    private Button btnNovaConsul, btnAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Referenciado as variaveis
        salarioB = findViewById(R.id.tvSalarioBruto);
        inss = findViewById(R.id.tvInss);
        irrf = findViewById(R.id.tvIrss);
        valeRefeicao = findViewById(R.id.tvValeRef);
        valeAlimentacao = findViewById(R.id.tvValeAlimen);
        valeTransporte = findViewById(R.id.tvValeTransp);
        planoSaude = findViewById(R.id.tvPlanoSaude);
        salarioLiquido = findViewById(R.id.tvSalarioLiquido);
        btnNovaConsul = findViewById(R.id.btnNovaConsulta);
        btnAlterar = findViewById(R.id.btnAlterar);
        porcetagem = findViewById(R.id.tvPorcetagem);

        // Pegando os dados da MainActivity
        User u = getIntent().getExtras().getParcelable("user");

        // Setando os valores nas TextViews
        salarioB.setText(u.getSalarioBase());
        inss.setText(u.getInss());
        irrf.setText(u.getIrss());
        valeRefeicao.setText(u.getValeRefei());
        valeAlimentacao.setText(u.getValeAlimen());
        valeTransporte.setText(u.getValeTransp());
        planoSaude.setText(u.getPlanoS());
        salarioLiquido.setText(u.getSalarioLiquido());
        porcetagem.setText(u.getPorcetagem());
        btnAlterar.setOnClickListener(V -> finish());

        btnNovaConsul.setOnClickListener(M -> {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();

        });

    }
}



