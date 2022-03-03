package com.example.folha_de_pagamento.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.folha_de_pagamento.R;
import com.example.folha_de_pagamento.model.User;
import com.google.android.material.snackbar.Snackbar;

public class FormActivity extends AppCompatActivity {

    private EditText etxSalarioB, etxDependentes;
    private Button btnEnviar;
    private Spinner spinPlanoSaude;
    private double salarioBase, porcentagem, salarioLiqui;
    private TextView tvnome;
    private RadioGroup rbtnValeRefeicao, rbtnValeAli, rbtnValeTrans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //Pegando o nome inserindo na PrimeiraActivity
        Bundle pacotedados = getIntent().getExtras();
        String nome = pacotedados.getString("nome");

        // Atribuindo os valores no Spinner
        spinPlanoSaude = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPlanoSaude.setAdapter(adapter);

        // Referenciando o botão
        tvnome = findViewById(R.id.tvTitulo);
        btnEnviar = findViewById(R.id.button2);
        spinPlanoSaude = findViewById(R.id.spinner);
        etxDependentes = findViewById(R.id.etDependentes);
        etxSalarioB = findViewById(R.id.etxSalario);
        rbtnValeRefeicao = findViewById(R.id.radGpValeRefeicao);
        rbtnValeAli = findViewById(R.id.radGpValeAli);
        rbtnValeTrans = findViewById(R.id.radGpValeTransp);

        tvnome.setText(nome);

        // Onclick do Botão
        btnEnviar.setOnClickListener(v -> {

            //Validação dos inputs
            if (etxSalarioB.getText().toString().isEmpty() || etxSalarioB.getText().toString().length() > 10) {
                etxSalarioB.setError("Valor Inválido");
                Snackbar.make(btnEnviar, "Sálario Inválido", Snackbar.LENGTH_SHORT).show();
            } else if (etxDependentes.getText().toString().isEmpty() || etxDependentes.getText().toString().length() > 2) {
                etxDependentes.setError("Valor Invalido");
                Snackbar.make(btnEnviar, "Num. Dependentes Inválido ", Snackbar.LENGTH_SHORT).show();
            } else if (rbtnValeRefeicao.getCheckedRadioButtonId() == -1) {
                Snackbar.make(btnEnviar, "Selecione uma opção de Vale Refeição", Snackbar.LENGTH_SHORT).show();
            } else if (rbtnValeAli.getCheckedRadioButtonId() == -1) {
                Snackbar.make(btnEnviar, "Selecione uma opção de Vale Alimentação", Snackbar.LENGTH_SHORT).show();
            } else if (rbtnValeTrans.getCheckedRadioButtonId() == -1) {
                Snackbar.make(btnEnviar, "Selecione uma opção de Vale Transporte", Snackbar.LENGTH_SHORT).show();
            } else {

                // Convertendo o salario
                salarioBase = Double.parseDouble(etxSalarioB.getText().toString());

                // Setando os valores no objeto
                User usuario = new User();
                usuario.setInss(String.format("%.2f", CalcINSS()));
                usuario.setSalarioBase("R$ " + etxSalarioB.getText().toString());
                usuario.setIrss(String.format("%.2f", CalcIRRF()));
                usuario.setPlanoS(String.format("%.2f", CalcSaude()));

                salarioLiqui = salarioBase - CalcINSS() - CalcIRRF() - CalcSaude();

                if (rbtnValeRefeicao.getCheckedRadioButtonId() == R.id.simValeRefe) {
                    usuario.setValeRefei(String.format("%.2f", CalcVR()));
                    salarioLiqui = salarioLiqui - CalcVR();
                } else {
                    usuario.setValeRefei("0.00");
                }

                if (rbtnValeTrans.getCheckedRadioButtonId() == R.id.simValeTransp) {
                    usuario.setValeTransp(String.format("%.2f", CalcVT()));
                    salarioLiqui = salarioLiqui - CalcVT();
                } else {
                    usuario.setValeTransp("0.00");
                }

                if (rbtnValeAli.getCheckedRadioButtonId() == R.id.simValeAlimen) {
                    usuario.setValeAlimen(String.format("%.2f", CalcVA()));
                    salarioLiqui = salarioLiqui - CalcVA();
                } else {
                    usuario.setValeAlimen("0.00");
                }

                usuario.setSalarioLiquido(String.format("R$ %.2f", salarioLiqui));

                porcentagem = ( 1 - salarioLiqui / salarioBase) * 100;
                usuario.setPorcetagem(String.format("%.0f", porcentagem));

                // Passando os valores para outra activity
                Intent intencao = new Intent(this, ResultadoActivity.class);
                intencao.putExtra("user", usuario);
                startForResult.launch(intencao);

            }
        });
    }

    /***
     * Métedo que substituiu o startActivityForResult que estava deprecated
     */
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                etxSalarioB.setText("");
                etxDependentes.setText("");
                rbtnValeRefeicao.clearCheck();
                rbtnValeTrans.clearCheck();
                rbtnValeAli.clearCheck();
                spinPlanoSaude.setSelection(0);
            }
        }

    });

    private double CalcIRRF() {
        double dependentes = Double.parseDouble(etxDependentes.getText().toString());
        double baseIRRF = salarioBase - CalcINSS() - (189.59 * dependentes);

        if (baseIRRF <= 1903.98) {
            return  0;

        } else if (baseIRRF <= 2826.65) {
            return  baseIRRF * 0.075 - 142.80;

        } else if (baseIRRF <= 3751.05) {
            return  baseIRRF * 0.15 - 354.80;

        } else if (baseIRRF <= 4664.68) {
            return baseIRRF * 0.225 - 636.13;

        } else
            return  baseIRRF * 0.275 - 869.36;
    }

    private double CalcINSS() {
        if (salarioBase <= 1212.00) {
            return salarioBase * (7.5 / 100);

        } else if (salarioBase <= 2427.35) {
            return salarioBase * 9 / 100 - 18.18;

        } else if (salarioBase <= 3641.03) {
            return salarioBase * 12 / 100 - 91.00;

        } else if (salarioBase <= 7087.22) {
            return salarioBase * 14 / 100 - 163.82;

        } else {
            return 828.39;
        }
    }

    private double CalcVR() {
        if (salarioBase <= 3000) {
            return 2.60 * 22;

        } else if (salarioBase <= 5000) {
            return 3.65 * 22;

        } else {
            return 6.50 * 22;
        }
    }

    private double CalcVT() {
        return salarioBase * 6 / 100;
    }

    private double CalcVA() {
        if (salarioBase <= 3000) {
            return 15.00;

        } else if (salarioBase <= 5000) {
            return 25.00;

        } else {
            return 35.00;

        }
    }

    private double CalcSaude() {

        switch (spinPlanoSaude.getSelectedItemPosition()) {

            case 0:
                if (salarioBase <= 3000) {
                    return 60.00;

                } else
                    return 80.00;

            case 1:
                if (salarioBase <= 3000) {
                    return 80.00;

                } else
                    return 110.00;

            case 2:
                if (salarioBase <= 3000) {

                    return 95.00;

                } else {

                    return 135.00;
                }

            case 3:
                if (salarioBase <= 3000) {
                    return 130.00;

                } else {
                    return 180.00;
                }

            default:
                return 0;
        }
    }
}
