package com.eduardocaio.controle_contatos.controladores;

import com.eduardocaio.controle_contatos.entidades.Contato;

import java.util.ArrayList;

public class CtrContato {

    private static ArrayList<Contato> contatos = new ArrayList<>();

    public static int getContatoId(int posicao){
        return contatos.get(posicao).getId();
    }

    public static Contato getContato(int posicao){
        return contatos.get(posicao);
    }

    public static ArrayList<Contato> getContatos() {
        return contatos;
    }

    public static void adicionarListaContatos(ArrayList<Contato> listaContatos){
        contatos.clear();
        contatos.addAll(listaContatos);
    }



}
