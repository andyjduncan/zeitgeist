package com.adjectivecolournoun.zeitgeist.tennis

class Tennis(var state: String = "start") {

    private val SERVER = "server"

    private val RECEIVER = "receiver"

    private val transitions = mapOf(
            "start" to mapOf(SERVER to "15-love", RECEIVER to "love-15"),
            "love-15" to mapOf(SERVER to "15 all", RECEIVER to "love-30"),
            "love-30" to mapOf(SERVER to "15-30", RECEIVER to "love-40"),
            "love-40" to mapOf(SERVER to "15-40", RECEIVER to "game receiver"),
            "15-love" to mapOf(SERVER to "30-love", RECEIVER to "15 all"),
            "15 all" to mapOf(SERVER to "30-15", RECEIVER to "15-30"),
            "15-30" to mapOf(SERVER to "30 all", RECEIVER to "15-40"),
            "15-40" to mapOf(SERVER to "30-40", RECEIVER to "game receiver"),
            "30-love" to mapOf(SERVER to "40-love", RECEIVER to "30-15"),
            "30-15" to mapOf(SERVER to "40-15", RECEIVER to "30 all"),
            "30 all" to mapOf(SERVER to "40-30", RECEIVER to "30-40"),
            "30-40" to mapOf(SERVER to "deuce", RECEIVER to "game receiver"),
            "40-love" to mapOf(SERVER to "game server", RECEIVER to "40-15"),
            "40-15" to mapOf(SERVER to "game server", RECEIVER to "40-30"),
            "40-30" to mapOf(SERVER to "game server", RECEIVER to "deuce"),
            "deuce" to mapOf(SERVER to "advantage server", RECEIVER to "advantage receiver"),
            "advantage server" to mapOf(SERVER to "game server", RECEIVER to "deuce"),
            "advantage receiver" to mapOf(SERVER to "deuce", RECEIVER to "game receiver")
    )

    fun serverScores() {
        state = transitions[state]?.get(SERVER) ?: throw IllegalStateException(state + "->" + SERVER)
    }

    fun receiverScores() {
        state = transitions[state]?.get(RECEIVER) ?: throw IllegalStateException(state + "->" + RECEIVER)
    }

    fun score(): String {
        return state
    }
}