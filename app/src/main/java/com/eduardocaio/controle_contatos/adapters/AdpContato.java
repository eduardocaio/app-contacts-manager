package com.eduardocaio.controle_contatos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eduardocaio.controle_contatos.R;
import com.eduardocaio.controle_contatos.entidades.Contato;

import java.util.ArrayList;

public class AdpContato extends RecyclerView.Adapter<AdpContato.ContatoViewHolder> {

    private ArrayList<Contato> contatos = new ArrayList<>();
    private int posicao;


    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_contato, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        Contato contato = contatos.get(position);

        holder.tvNome.setText(contato.getNome());
        holder.tvTelefone.setText("Telefone: " + contato.getTelefone());
        holder.tvEmail.setText("E-mail: " + contato.getEmail());

        holder.itemView.setOnLongClickListener(
                v -> {
                    setPosicao(position);
                    v.showContextMenu();
                    return true;
                }
        );
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao){
        this.posicao = posicao;
    }

    @Override
    public int getItemCount() {
        return contatos.size();  // Retorna o tamanho correto da lista
    }

    public void atualizar(ArrayList<Contato> contatos){
        this.contatos.clear();
        this.contatos.addAll(contatos);
        notifyDataSetChanged();
    }

    class ContatoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNome;
        private TextView tvTelefone;
        private TextView tvEmail;

        public ContatoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNome = itemView.findViewById(R.id.adp_contato_tv_nome);
            tvEmail = itemView.findViewById(R.id.adp_contato_tv_email);
            tvTelefone = itemView.findViewById(R.id.adp_contato_tv_telefone);
        }
    }
}

