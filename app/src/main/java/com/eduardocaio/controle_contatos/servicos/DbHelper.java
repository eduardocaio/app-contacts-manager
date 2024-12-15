package com.eduardocaio.controle_contatos.servicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.eduardocaio.controle_contatos.entidades.Contato;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contatosdb";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS contatos(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR," +
                "telefone VARCHAR," +
                "email VARCHAR)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contatos");
        onCreate(db);
    }

    public void adicionarContato(Contato contato) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", contato.getNome());
        valores.put("telefone", contato.getTelefone());
        valores.put("email", contato.getEmail());

        db.insert("contatos", null, valores);
        db.close();
    }

    public void editarContato(Contato contato) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", contato.getNome());
        valores.put("telefone", contato.getTelefone());
        valores.put("email", contato.getEmail());

        db.update("contatos", valores, "id = ?", new String[]{String.valueOf(contato.getId())});
        db.close();
    }

    public void removerContato(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("contatos", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Contato> listarContatos() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contato> contatos = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT id, nome, telefone, email FROM contatos", null);

        if (cursor != null && cursor.getCount() > 0) {
            int idIndex = cursor.getColumnIndex("id");
            int nomeIndex = cursor.getColumnIndex("nome");
            int telefoneIndex = cursor.getColumnIndex("telefone");
            int emailIndex = cursor.getColumnIndex("email");

            if (idIndex >= 0 && nomeIndex >= 0 && telefoneIndex >= 0 && emailIndex >= 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idIndex);
                    String nome = cursor.getString(nomeIndex);
                    String telefone = cursor.getString(telefoneIndex);
                    String email = cursor.getString(emailIndex);

                    Contato contato = new Contato(id, email, telefone, nome);
                    contatos.add(contato);
                }
            } else {
                Log.e("DbHelper", "Índices de colunas inválidos.");
            }

            cursor.close();
        } else {
            Log.e("DbHelper", "Nenhum contato encontrado no banco de dados.");
        }

        db.close();
        return contatos;
    }
}
