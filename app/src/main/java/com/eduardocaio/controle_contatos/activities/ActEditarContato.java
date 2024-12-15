package com.eduardocaio.controle_contatos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eduardocaio.controle_contatos.R;
import com.eduardocaio.controle_contatos.entidades.Contato;
import com.eduardocaio.controle_contatos.servicos.DbHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActEditarContato extends AppCompatActivity {

    private static final String CONTATO = "contato";

    int id;

    private DbHelper db;

    private EditText edNome;
    private EditText edTelefone;
    private EditText edEmail;
    private Button btConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editar_contato);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Editar Contato");
        }

        db = new DbHelper(this);

        btConfirmar = findViewById(R.id.act_editar_contato_bt_confirmar);
        edNome = findViewById(R.id.act_editar_contato_ed_nome);
        edTelefone = findViewById(R.id.act_editar_contato_ed_telefone);
        edEmail = findViewById(R.id.act_editar_contato_ed_email);

        preencherCampos();
        configurarBotao();
        configurarCampoTelefone();
    }

    private void preencherCampos() {
        Contato contato = (Contato) getIntent().getSerializableExtra(CONTATO);
        edNome.setText(contato.getNome());
        edTelefone.setText(contato.getTelefone());
        edEmail.setText(contato.getEmail());
        id = contato.getId();
    }

    private void configurarBotao() {
        btConfirmar.setOnClickListener(view -> {
            String nome = edNome.getText().toString().trim();
            String email = edEmail.getText().toString().trim();
            String telefone = edTelefone.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(this, "É necessário preencher o campo de nome!", Toast.LENGTH_SHORT).show();
                edNome.requestFocus();
                return;
            }

            if (telefone.length() < 13) {
                Toast.makeText(this, "O número de telefone precisa ter pelo menos 10 dígitos!", Toast.LENGTH_SHORT).show();
                edTelefone.requestFocus();
                return;
            }

            if (!validarEmail(email)) {
                Toast.makeText(this, "É necessário preencher o campo de e-mail corretamente!", Toast.LENGTH_SHORT).show();
                edEmail.requestFocus();
                return;
            }

            Contato contato = new Contato(id, email, telefone, nome);

            db.editarContato(contato);

            Toast.makeText(this, "Contato editado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Função para validar o e-mail usando regex
    private boolean validarEmail(String email) {
        // Regex mais robusto para validar e-mails.
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void configurarCampoTelefone() {
        edTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {}

            @Override
            public void afterTextChanged(Editable editable) {
                edTelefone.removeTextChangedListener(this);

                String telefone = editable.toString().replaceAll("[^0-9]", "");

                if (telefone.length() > 11) {
                    telefone = telefone.substring(0, 11);
                }

                if (telefone.length() > 0) {
                    if (telefone.length() > 6) {
                        telefone = "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7, Math.min(telefone.length(), 11));
                    } else if (telefone.length() > 2) {
                        telefone = "(" + telefone.substring(0, 2) + ") " + telefone.substring(2);
                    } else {
                        telefone = "(" + telefone;
                    }
                }

                edTelefone.setText(telefone);

                edTelefone.setSelection(telefone.length());

                edTelefone.addTextChangedListener(this);
            }
        });
    }

    public static void abrirTela(Activity activity, Contato contato) {
        Intent intent = new Intent(activity, ActEditarContato.class);
        intent.putExtra(CONTATO, contato);
        activity.startActivity(intent);
    }
}
