// Caminho: com.ibrahim.dd_game.shared.Personagem.kt

package com.ibrahim.dd_game.shared

data class Personagem(
    val id: Int? = null,              // ID para o banco de dados
    val nome: String,
    var raca: String,
    var forca: Int,
    var destreza: Int,
    var constituicao: Int,
    var inteligencia: Int,
    var sabedoria: Int,
    var carisma: Int,
    val pontosdeVida: Int
) {
    // Modificador de Constituição
    private val modificadorConstituicao: Int
        get() = (constituicao - 10) / 2

    // Cálculo dos pontos de vida
    val pontosDeVida: Int
        get() = 10 + modificadorConstituicao
}
