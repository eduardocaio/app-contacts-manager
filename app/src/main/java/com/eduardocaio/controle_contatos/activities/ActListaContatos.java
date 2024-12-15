package com.eduardocaio.controle_contatos.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eduardocaio.controle_contatos.adapters.AdpContato;
import com.eduardocaio.controle_contatos.R;
import com.eduardocaio.controle_contatos.controladores.CtrContato;
import com.eduardocaio.controle_contatos.entidades.Contato;
import com.eduardocaio.controle_contatos.servicos.DbHelper;

import java.util.ArrayList;
import java.util.Objects;

public class ActListaContatos extends AppCompatActivity {
    private RecyclerView rvItens;
    private Button btAdicionar;
    private EditText edNome;
    private DbHelper dbHelper;
    private AdpContato adpContato;
    private ArrayList<Contato> listaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lista_contatos);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Lista de Contatos");
        }

        dbHelper = new DbHelper(this);
        adpContato = new AdpContato();
        listaContatos = new ArrayList<>();

        rvItens = findViewById(R.id.act_lista_contatos_rv_itens);
        btAdicionar = findViewById(R.id.act_lista_contatos_bt_adicionar);
        edNome = findViewById(R.id.act_lista_contatos_ed_nome);

        configurarRecyclerView(adpContato);
        configurarBotao();
        configurarCampoPesquisa();
        listarDados();
    }

    private void listarDados(){
        listaContatos = dbHelper.listarContatos();
        CtrContato.adicionarListaContatos(listaContatos);
        adpContato.atualizar(listaContatos);
    }

    private void configurarRecyclerView(AdpContato adpContato){
        rvItens.setLayoutManager(new LinearLayoutManager(this));
        rvItens.setHasFixedSize(true);
        rvItens.setAdapter(adpContato);
        registerForContextMenu(rvItens);
    }

    private void configurarBotao(){
        btAdicionar.setOnClickListener(view -> {
            ActAdicionarContato.abrirTela(this);
        });
    }

    private void configurarCampoPesquisa() {
        edNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filtrarContatos(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filtrarContatos(String query) {
        ArrayList<Contato> contatosFiltrados = new ArrayList<>();

        if (query.isEmpty()) {
            contatosFiltrados = listaContatos;
        } else {
            for (Contato contato : listaContatos) {
                if (contato.getNome().toLowerCase().contains(query.toLowerCase())) {
                    contatosFiltrados.add(contato);
                }
            }
        }

        adpContato.atualizar(contatosFiltrados);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.cm_contato, menu);
        menu.findItem(R.id.cm_contato_excluir);
        menu.findItem(R.id.cm_contato_editar);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int posicao = ((AdpContato) Objects.requireNonNull(rvItens.getAdapter())).getPosicao();

        if (item.getItemId() == R.id.cm_contato_excluir){
            int id = CtrContato.getContatoId(posicao);
            dbHelper.removerContato(id);
            listarDados();
            Toast.makeText(this, "Contato excluído com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            ActEditarContato.abrirTela(this, CtrContato.getContato(posicao));
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarDados();
    }
}
