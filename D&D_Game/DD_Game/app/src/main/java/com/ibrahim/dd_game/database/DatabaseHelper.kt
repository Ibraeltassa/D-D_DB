package com.ibrahim.dd_game.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.util.Log
import com.ibrahim.dd_game.shared.Personagem

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_PERSONAGEM (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT NOT NULL,
                $COLUMN_RACA TEXT NOT NULL,
                $COLUMN_FORCA INTEGER NOT NULL,
                $COLUMN_DESTREZA INTEGER NOT NULL,
                $COLUMN_CONSTITUICAO INTEGER NOT NULL,
                $COLUMN_INTELIGENCIA INTEGER NOT NULL,
                $COLUMN_SABEDORIA INTEGER NOT NULL,
                $COLUMN_CARISMA INTEGER NOT NULL,
                $COLUMN_PONTOS_DE_VIDA INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("DatabaseHelper", "Atualizando banco de dados da versão $oldVersion para $newVersion.")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONAGEM")
        onCreate(db)
    }

    // Inserir um novo personagem
    fun insertPersonagem(personagem: Personagem): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, personagem.nome)
            put(COLUMN_RACA, personagem.raca)
            put(COLUMN_FORCA, personagem.forca)
            put(COLUMN_DESTREZA, personagem.destreza)
            put(COLUMN_CONSTITUICAO, personagem.constituicao)
            put(COLUMN_INTELIGENCIA, personagem.inteligencia)
            put(COLUMN_SABEDORIA, personagem.sabedoria)
            put(COLUMN_CARISMA, personagem.carisma)
            put(COLUMN_PONTOS_DE_VIDA, personagem.pontosdeVida)
        }
        val result = db.insert(TABLE_PERSONAGEM, null, values)
        db.close()

        if (result == -1L) Log.e("DatabaseHelper", "Erro ao inserir personagem.")
        return result
    }

    // Atualizar um personagem
    fun updatePersonagem(personagem: Personagem): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, personagem.nome)
            put(COLUMN_RACA, personagem.raca)
            put(COLUMN_FORCA, personagem.forca)
            put(COLUMN_DESTREZA, personagem.destreza)
            put(COLUMN_CONSTITUICAO, personagem.constituicao)
            put(COLUMN_INTELIGENCIA, personagem.inteligencia)
            put(COLUMN_SABEDORIA, personagem.sabedoria)
            put(COLUMN_CARISMA, personagem.carisma)
            put(COLUMN_PONTOS_DE_VIDA, personagem.pontosdeVida)
        }
        val rowsAffected = db.update(TABLE_PERSONAGEM, values, "$COLUMN_ID = ?", arrayOf(personagem.id.toString()))
        db.close()

        if (rowsAffected == 0) Log.e("DatabaseHelper", "Erro ao atualizar personagem.")
        return rowsAffected
    }

    // Deletar um personagem pelo ID
    fun deletePersonagem(personagemId: Int): Int {
        val db = writableDatabase
        val rowsDeleted = db.delete(TABLE_PERSONAGEM, "$COLUMN_ID = ?", arrayOf(personagemId.toString()))
        db.close()

        if (rowsDeleted == 0) Log.e("DatabaseHelper", "Erro ao deletar personagem.")
        return rowsDeleted
    }

    // Obter todos os personagens
    fun getAllPersonagens(): List<Personagem> {
        val personagens = mutableListOf<Personagem>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM personagens", null)
        if (cursor.moveToFirst()) {
            do {
                val personagem = Personagem(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                    raca = cursor.getString(cursor.getColumnIndexOrThrow("raca")),
                    forca = cursor.getInt(cursor.getColumnIndexOrThrow("forca")),
                    destreza = cursor.getInt(cursor.getColumnIndexOrThrow("destreza")),
                    constituicao = cursor.getInt(cursor.getColumnIndexOrThrow("constituicao")),
                    sabedoria = cursor.getInt(cursor.getColumnIndexOrThrow("sabedoria")),
                    inteligencia = cursor.getInt(cursor.getColumnIndexOrThrow("inteligencia")),
                    carisma = cursor.getInt(cursor.getColumnIndexOrThrow("carisma")),
                    pontosdeVida = cursor.getInt(cursor.getColumnIndexOrThrow("pontosdeVida"))
                )
                personagens.add(personagem)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return personagens
    }


    companion object {
        private const val DATABASE_NAME = "Personagens.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_PERSONAGEM = "personagem"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_RACA = "raca"
        const val COLUMN_FORCA = "forca"
        const val COLUMN_DESTREZA = "destreza"
        const val COLUMN_CONSTITUICAO = "constituicao"
        const val COLUMN_INTELIGENCIA = "inteligencia"
        const val COLUMN_SABEDORIA = "sabedoria"
        const val COLUMN_CARISMA = "carisma"
        const val COLUMN_PONTOS_DE_VIDA = "pontosdeVida"
    }
}
