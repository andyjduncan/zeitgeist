package com.adjectivecolournoun.zeitgeist.tennis

import spock.lang.Specification


class TestTennis extends Specification {

    private final tennis = new Tennis()

    void 'if the server scores first, the score is 15-love'() {
        when:
        tennis.serverScores()

        then:
        tennis.score() == '15-love'
    }

    void 'if the receiver scores first, the score is love-15'() {
        when:
        tennis.receiverScores()

        then:
        tennis.score() == 'love-15'
    }

    void 'when the server scores twice, the score is 30-love'() {
        given:
        tennis.serverScores()

        when:
        tennis.serverScores()

        then:
        tennis.score() == '30-love'
    }

    void 'when the receiver scores twice, the score is love-30'() {
        given:
        tennis.receiverScores()

        when:
        tennis.receiverScores()

        then:
        tennis.score() == 'love-30'
    }

    void 'when the server scores thrice, the score is 40-love'() {
        given:
        tennis.serverScores()
        tennis.serverScores()

        when:
        tennis.serverScores()

        then:
        tennis.score() == '40-love'
    }

    void 'when the receiver scores thrice, the score is love-40'() {
        given:
        tennis.receiverScores()
        tennis.receiverScores()

        when:
        tennis.receiverScores()

        then:
        tennis.score() == 'love-40'
    }

    void 'when the server scores four times, they win the game'() {
        given:
        tennis.serverScores()
        tennis.serverScores()
        tennis.serverScores()

        when:
        tennis.serverScores()

        then:
        tennis.score() == 'server wins'
    }

    void 'when the receiver scores four times, they win the game'() {
        given:
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()

        when:
        tennis.receiverScores()

        then:
        tennis.score() == 'receiver wins'
    }

    void 'when the server is 40-30 up and the receiver scores, the score is deuce'() {
        given:
        tennis.serverScores()
        tennis.serverScores()
        tennis.serverScores()
        tennis.receiverScores()
        tennis.receiverScores()

        when:
        tennis.receiverScores()

        then:
        tennis.score() == 'deuce'
    }

    void 'when the score is deuce and the server scores, the score is advantage server'() {
        given:
        tennis.serverScores()
        tennis.serverScores()
        tennis.serverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()

        when:
        tennis.serverScores()

        then:
        tennis.score() == 'advantage server'
    }

    void 'when the score is deuce and the receiver scores, the score is advantage receiver'() {
        given:
        tennis.serverScores()
        tennis.serverScores()
        tennis.serverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()

        when:
        tennis.receiverScores()

        then:
        tennis.score() == 'advantage receiver'
    }

    void 'when the score is advantage server and the receiver scores, the score is deuce'() {
        given:
        tennis.serverScores()
        tennis.serverScores()
        tennis.serverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.serverScores()
        assert tennis.score() == 'advantage server'

        when:
        tennis.receiverScores()

        then:
        tennis.score() == 'deuce'
    }

    void 'when the score is advantage receiver and the server scores, the score is deuce'() {
        given:
        tennis.serverScores()
        tennis.serverScores()
        tennis.serverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        assert tennis.score() == 'advantage receiver'

        when:
        tennis.serverScores()

        then:
        tennis.score() == 'deuce'
    }

    void 'when the score is advantage server and they score, they win the game'() {
        given:
        tennis.serverScores()
        tennis.serverScores()
        tennis.serverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.serverScores()
        assert tennis.score() == 'advantage server'

        when:
        tennis.serverScores()

        then:
        tennis.score() == 'server wins'
    }

    void 'when the score is advantage receiver and they score, they win the game'() {
        given:
        tennis.serverScores()
        tennis.serverScores()
        tennis.serverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        tennis.receiverScores()
        assert tennis.score() == 'advantage receiver'

        when:
        tennis.receiverScores()

        then:
        tennis.score() == 'receiver wins'
    }
}