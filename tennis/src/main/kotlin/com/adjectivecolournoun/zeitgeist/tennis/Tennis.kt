package com.adjectivecolournoun.zeitgeist.tennis

class Tennis {

    var serverPoints = 0
    var receiverPoints = 0

    fun serverScores() {
        serverPoints++
    }

    fun receiverScores() {
        receiverPoints++
    }

    private fun scoreFor(points: Int): String {
        return when (points) {
            0 -> "love"
            1 -> "15"
            2 -> "30"
            3 -> "40"
            else -> throw IllegalArgumentException()
        }
    }

    fun score(): String {
        if (serverPoints == 3 && receiverPoints == 3) {
            return "deuce"
        } else if (serverPoints >= 4) {
            if (serverPoints == receiverPoints) {
                return "deuce"
            } else if (serverPoints - receiverPoints > 1) {
                return "server wins"
            }
            return "advantage server"
        } else if (receiverPoints >= 4) {
            if (serverPoints == receiverPoints) {
                return "deuce"
            } else if (receiverPoints - serverPoints > 1) {
                return "receiver wins"
            }
            return "advantage receiver"
        }
        return scoreFor(serverPoints) + "-" + scoreFor(receiverPoints)
    }
}