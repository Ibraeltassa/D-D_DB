package com.ibrahim.dd_game.services

class GerenciadorAtributos {

    var forca = 8
    var destreza = 8
    var constituicao = 8
    var sabedoria = 8
    var inteligencia = 8
    var carisma = 8
    var pontosRestantes = 27
    var racaSelecionada: String = ""

    fun aplicarBonusRacial(raca: String) {
        resetarBonusRacial()

        when (raca) {
            "anao" -> {
                forca += 2
                constituicao += 2
            }
            "elfo" -> {
                destreza += 2
            }
            "gnomo" -> {
                inteligencia += 2
            }
            "humano" -> {
                forca += 1
                destreza += 1
                constituicao += 1
                sabedoria += 1
                inteligencia += 1
                carisma += 1
            }
        }
    }


    fun resetarAtributos() {
        forca = 8
        destreza = 8
        constituicao = 8
        sabedoria = 8
        inteligencia = 8
        carisma = 8
        pontosRestantes = 27
    }



    fun resetarBonusRacial() {
        forca = 8
        destreza = 8
        constituicao = 8
        sabedoria = 8
        inteligencia = 8
        carisma = 8
    }

    fun aumentarAtributo(atributo: String): Boolean {
        if (pontosRestantes > 0) {
            when (atributo) {
                "forca" -> if (forca < 15) { forca++; pontosRestantes-- }
                "destreza" -> if (destreza < 15) { destreza++; pontosRestantes-- }
                "constituicao" -> if (constituicao < 15) { constituicao++; pontosRestantes-- }
                "sabedoria" -> if (sabedoria < 15) { sabedoria++; pontosRestantes-- }
                "inteligencia" -> if (inteligencia < 15) { inteligencia++; pontosRestantes-- }
                "carisma" -> if (carisma < 15) { carisma++; pontosRestantes-- }
                else -> return false
            }
            return true
        }
        return false
    }

    fun diminuirAtributo(atributo: String): Boolean {
        if (pontosRestantes < 27) {
            when (atributo) {
                "forca" -> if (forca > 8) { forca--; pontosRestantes++ }
                "destreza" -> if (destreza > 8) { destreza--; pontosRestantes++ }
                "constituicao" -> if (constituicao > 8) { constituicao--; pontosRestantes++ }
                "sabedoria" -> if (sabedoria > 8) { sabedoria--; pontosRestantes++ }
                "inteligencia" -> if (inteligencia > 8) { inteligencia--; pontosRestantes++ }
                "carisma" -> if (carisma > 8) { carisma--; pontosRestantes++ }
                else -> return false
            }
            return true
        }
        return false
    }

    fun calcularPontosDeVida(): Int {
        return 10 + (constituicao - 10) / 2
    }
}
