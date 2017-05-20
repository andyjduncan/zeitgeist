package com.adjectivecolournoun.zeitgeist.tennis

class Tennis {

    var state: String = "love-love"

    private val SERVER = "server"

    private val RECEIVER = "receiver"

    private val transitions = mapOf(
            "love-love" to mapOf(SERVER to "15-love", RECEIVER to "love-15"),
            "15-love" to mapOf(SERVER to "30-love"),
            "love-15" to mapOf(RECEIVER to "love-30"),
            "30-love" to mapOf(SERVER to "40-love"),
            "love-30" to mapOf(RECEIVER to "love-40"),
            "40-love" to mapOf(SERVER to "server wins", RECEIVER to "40-15"),
            "40-15" to mapOf(SERVER to "server wins", RECEIVER to "40-30"),
            "love-40" to mapOf(RECEIVER to "receiver wins"),
            "40-30" to mapOf(RECEIVER to "deuce"),
            "deuce" to mapOf(SERVER to "advantage server", RECEIVER to "advantage receiver"),
            "advantage server" to mapOf(SERVER to "server wins", RECEIVER to "deuce"),
            "advantage receiver" to mapOf(SERVER to "deuce", RECEIVER to "receiver wins")
    )

    fun serverScores() {
        state = transitions[state]?.get(SERVER) ?: throw IllegalStateException(state)
    }

    fun receiverScores() {
        state = transitions[state]?.get(RECEIVER) ?: throw IllegalStateException(state)
    }

    fun score(): String {
        return state
    }
}