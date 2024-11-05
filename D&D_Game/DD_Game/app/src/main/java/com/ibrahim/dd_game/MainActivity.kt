package com.ibrahim.dd_game

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ibrahim.dd_game.database.DatabaseHelper
import com.ibrahim.dd_game.shared.Personagem
import com.ibrahim.dd_game.services.GerenciadorAtributos

class MainActivity : AppCompatActivity() {

    private val gerenciadorAtributos = GerenciadorAtributos()
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var tvForca: TextView
    private lateinit var tvDestreza: TextView
    private lateinit var tvConstituicao: TextView
    private lateinit var tvSabedoria: TextView
    private lateinit var tvInteligencia: TextView
    private lateinit var tvCarisma: TextView
    private lateinit var tvPontosRestantes: TextView
    private lateinit var tvRacaSelecionada: TextView
    private lateinit var tvPontosVida: TextView
    private lateinit var tvBonusRacial: TextView
    private lateinit var tvPersonagemFinal: TextView
    private lateinit var etNomePersonagem: EditText
    private lateinit var tvResults: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar o DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Conectar os elementos da interface com os IDs do XML
        tvForca = findViewById(R.id.tv_forca)
        tvDestreza = findViewById(R.id.tv_destreza)
        tvConstituicao = findViewById(R.id.tv_constituicao)
        tvSabedoria = findViewById(R.id.tv_sabedoria)
        tvInteligencia = findViewById(R.id.tv_inteligencia)
        tvCarisma = findViewById(R.id.tv_carisma)
        tvPontosRestantes = findViewById(R.id.tv_pontos_restantes)
        tvRacaSelecionada = findViewById(R.id.tv_raca_selecionada)
        tvPontosVida = findViewById(R.id.tv_pontos_vida)
        tvPersonagemFinal = findViewById(R.id.tv_personagem_final)
        tvBonusRacial = findViewById(R.id.tv_bonus_racial)
        etNomePersonagem = findViewById(R.id.et_nome_personagem)
        tvResults = findViewById(R.id.tvResults)

        atualizarAtributos()
        atualizarPontosRestantes()
        atualizarPontosVida()

        // Configuração dos botões de seleção de raça e ajuste de atributos
        configurarSelecaoRaca()
        configurarAjustesAtributos()


        // Inicializando os botões e configurando as funcionalidades
        val btnInsert: Button = findViewById(R.id.btnInsert)
        val btnUpdate: Button = findViewById(R.id.btnUpdate)
        val btnDelete: Button = findViewById(R.id.btnDelete)
        val btnDisplay: Button = findViewById(R.id.btnDisplay)


        // Inserir Personagem
        btnInsert.setOnClickListener {
            val nome = etNomePersonagem.text.toString()
            if (nome.isNotEmpty()) {
                val personagem = Personagem(
                    nome = nome,
                    raca = tvRacaSelecionada.text.toString(),
                    forca = gerenciadorAtributos.forca,
                    destreza = gerenciadorAtributos.destreza,
                    constituicao = gerenciadorAtributos.constituicao,
                    sabedoria = gerenciadorAtributos.sabedoria,
                    inteligencia = gerenciadorAtributos.inteligencia,
                    carisma = gerenciadorAtributos.carisma,
                    pontosdeVida = gerenciadorAtributos.calcularPontosDeVida()
                )
                val result = databaseHelper.insertPersonagem(personagem)
                tvResults.text = if (result != -1L) "Personagem inserido com sucesso!" else "Erro ao inserir personagem."
            } else {
                tvResults.text = "Por favor, insira o nome do personagem."
            }
        }

// Atualizar Personagem
        btnUpdate.setOnClickListener {
            val nome = etNomePersonagem.text.toString()
            if (nome.isNotEmpty()) {
                val personagem = Personagem(
                  //  id = /* ID do personagem que deseja atualizar */,
                    nome = nome,
                    raca = tvRacaSelecionada.text.toString(),
                    forca = gerenciadorAtributos.forca,
                    destreza = gerenciadorAtributos.destreza,
                    constituicao = gerenciadorAtributos.constituicao,
                    sabedoria = gerenciadorAtributos.sabedoria,
                    inteligencia = gerenciadorAtributos.inteligencia,
                    carisma = gerenciadorAtributos.carisma,
                    pontosdeVida = gerenciadorAtributos.calcularPontosDeVida()
                )
                val rowsAffected = databaseHelper.updatePersonagem(personagem)
                tvResults.text = if (rowsAffected > 0) "Personagem atualizado com sucesso!" else "Erro ao atualizar personagem."
            } else {
                tvResults.text = "Por favor, insira o nome do personagem."
            }
        }

// Deletar Personagem
        btnDelete.setOnClickListener {
            val personagemId = 1/* ID do personagem que deseja deletar */
            val rowsDeleted = databaseHelper.deletePersonagem(personagemId)
            tvResults.text = if (rowsDeleted > 0) "Personagem deletado com sucesso!" else "Erro ao deletar personagem."
        }

// Exibir Personagens
        btnDisplay.setOnClickListener {
            val personagens = databaseHelper.getAllPersonagens()
            if (personagens.isNotEmpty()) {
                tvResults.text = personagens.joinToString(separator = "\n") { personagem ->
                    "ID: ${personagem.id}, Nome: ${personagem.nome}, Raça: ${personagem.raca}, Força: ${personagem.forca}, Destreza: ${personagem.destreza}, Constituição: ${personagem.constituicao}, Inteligência: ${personagem.inteligencia}, Sabedoria: ${personagem.sabedoria}, Carisma: ${personagem.carisma}, Pontos de Vida: ${personagem.pontosdeVida}"
                }
            } else {
                tvResults.text = "Nenhum personagem encontrado."
            }
        }

    }


    private fun configurarSelecaoRaca() {
        findViewById<Button>(R.id.btn_anao).setOnClickListener {
            gerenciadorAtributos.resetarAtributos()
            gerenciadorAtributos.aplicarBonusRacial("anao")
            atualizarInterface("Raça Selecionada: Anão", "Bônus Racial: +2 Constituição")
        }

        findViewById<Button>(R.id.btn_elfo).setOnClickListener {
            gerenciadorAtributos.resetarAtributos()
            gerenciadorAtributos.aplicarBonusRacial("elfo")
            atualizarInterface("Raça Selecionada: Elfo", "Bônus Racial: +2 Destreza")
        }

        findViewById<Button>(R.id.btn_gnomo).setOnClickListener {
            gerenciadorAtributos.resetarAtributos()
            gerenciadorAtributos.aplicarBonusRacial("gnomo")
            atualizarInterface("Raça Selecionada: Gnomo", "Bônus Racial: +2 Inteligência")
        }

        findViewById<Button>(R.id.btn_humano).setOnClickListener {
            gerenciadorAtributos.resetarAtributos()
            gerenciadorAtributos.aplicarBonusRacial("humano")
            atualizarInterface("Raça Selecionada: Humano", "Bônus Racial: +1 em todos os atributos")
        }
    }

    private fun configurarAjustesAtributos() {
        configurarAjusteAtributo(R.id.btn_forca_menos, R.id.btn_forca_mais, "forca")
        configurarAjusteAtributo(R.id.btn_destreza_menos, R.id.btn_destreza_mais, "destreza")
        configurarAjusteAtributo(R.id.btn_constituicao_menos, R.id.btn_constituicao_mais, "constituicao")
        configurarAjusteAtributo(R.id.btn_sabedoria_menos, R.id.btn_sabedoria_mais, "sabedoria")
        configurarAjusteAtributo(R.id.btn_inteligencia_menos, R.id.btn_inteligencia_mais, "inteligencia")
        configurarAjusteAtributo(R.id.btn_carisma_menos, R.id.btn_carisma_mais, "carisma")
    }

    private fun configurarAjusteAtributo(btnMenosId: Int, btnMaisId: Int, atributo: String) {
        findViewById<Button>(btnMenosId).setOnClickListener {
            if (gerenciadorAtributos.diminuirAtributo(atributo)) {
                atualizarInterface()
            }
        }

        findViewById<Button>(btnMaisId).setOnClickListener {
            if (gerenciadorAtributos.aumentarAtributo(atributo)) {
                atualizarInterface()
            }
        }
    }


    private fun atualizarInterface(racaSelecionada: String = "", bonusRacial: String = "") {
        if (racaSelecionada.isNotEmpty()) tvRacaSelecionada.text = racaSelecionada
        if (bonusRacial.isNotEmpty()) tvBonusRacial.text = bonusRacial
        atualizarAtributos()
        atualizarPontosRestantes()
        atualizarPontosVida()
        verificarPontosRestantes()
    }

    private fun atualizarAtributos() {
        tvForca.text = gerenciadorAtributos.forca.toString()
        tvDestreza.text = gerenciadorAtributos.destreza.toString()
        tvConstituicao.text = gerenciadorAtributos.constituicao.toString()
        tvSabedoria.text = gerenciadorAtributos.sabedoria.toString()
        tvInteligencia.text = gerenciadorAtributos.inteligencia.toString()
        tvCarisma.text = gerenciadorAtributos.carisma.toString()
    }

    private fun atualizarPontosRestantes() {
        tvPontosRestantes.text = "Pontos Restantes: ${gerenciadorAtributos.pontosRestantes}"
    }

    private fun atualizarPontosVida() {
        val pontosVida = gerenciadorAtributos.calcularPontosDeVida()
        tvPontosVida.text = "Pontos de Vida: $pontosVida"
    }

    private fun verificarPontosRestantes() {
        if (gerenciadorAtributos.pontosRestantes == 0) {
            exibirPersonagemFinal()
        }
    }

    private fun exibirPersonagemFinal() {
        val nomePersonagem = etNomePersonagem.text.toString()
        val personagem = Personagem(
            nome = nomePersonagem,
            raca = tvRacaSelecionada.text.toString(),
            forca = gerenciadorAtributos.forca,
            destreza = gerenciadorAtributos.destreza,
            constituicao = gerenciadorAtributos.constituicao,
            sabedoria = gerenciadorAtributos.sabedoria,
            inteligencia = gerenciadorAtributos.inteligencia,
            carisma = gerenciadorAtributos.carisma,
            pontosdeVida = gerenciadorAtributos.calcularPontosDeVida()
        )

        databaseHelper.insertPersonagem(personagem)

        val personagemDetalhes = """
            Personagem Criado!
            ${tvRacaSelecionada.text}
            ${tvBonusRacial.text}
            Força: ${gerenciadorAtributos.forca}
            Destreza: ${gerenciadorAtributos.destreza}
            Constituição: ${gerenciadorAtributos.constituicao}
            Sabedoria: ${gerenciadorAtributos.sabedoria}
            Inteligência: ${gerenciadorAtributos.inteligencia}
            Carisma: ${gerenciadorAtributos.carisma}
            Pontos de Vida: ${gerenciadorAtributos.calcularPontosDeVida()}
        """.trimIndent()

        tvPersonagemFinal.text = personagemDetalhes
    }
}