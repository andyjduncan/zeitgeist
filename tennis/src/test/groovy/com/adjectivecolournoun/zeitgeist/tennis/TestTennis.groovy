package com.adjectivecolournoun.zeitgeist.tennis

import spock.lang.Specification
import spock.lang.Unroll


class TestTennis extends Specification {

    @Unroll
    void 'when the score is #score and the #scorer scores, the score is #newScore'() {
        given:
        def tennis = new Tennis(score)

        when:
        tennis."${scorer}Scores"()

        then:
        tennis.score() == newScore

        where:
        score                | scorer     | newScore
        'start'              | 'server'   | '15-love'
        'start'              | 'receiver' | 'love-15'
        'love-15'            | 'server'   | '15 all'
        'love-15'            | 'receiver' | 'love-30'
        'love-30'            | 'server'   | '15-30'
        'love-30'            | 'receiver' | 'love-40'
        'love-40'            | 'server'   | '15-40'
        'love-40'            | 'receiver' | 'game receiver'
        '15-love'            | 'server'   | '30-love'
        '15-love'            | 'receiver' | '15 all'
        '15 all'             | 'server'   | '30-15'
        '15 all'             | 'receiver' | '15-30'
        '15-30'              | 'server'   | '30 all'
        '15-30'              | 'receiver' | '15-40'
        '15-40'              | 'server'   | '30-40'
        '15-40'              | 'receiver' | 'game receiver'
        '30-love'            | 'server'   | '40-love'
        '30-love'            | 'receiver' | '30-15'
        '30-15'              | 'server'   | '40-15'
        '30-15'              | 'receiver' | '30 all'
        '30 all'             | 'server'   | '40-30'
        '30 all'             | 'receiver' | '30-40'
        '30-40'              | 'server'   | 'deuce'
        '30-40'              | 'receiver' | 'game receiver'
        '40-love'            | 'server'   | 'game server'
        '40-love'            | 'receiver' | '40-15'
        '40-15'              | 'server'   | 'game server'
        '40-15'              | 'receiver' | '40-30'
        '40-30'              | 'server'   | 'game server'
        '40-30'              | 'receiver' | 'deuce'
        'deuce'              | 'server'   | 'advantage server'
        'deuce'              | 'receiver' | 'advantage receiver'
        'advantage server'   | 'server'   | 'game server'
        'advantage server'   | 'receiver' | 'deuce'
        'advantage receiver' | 'server'   | 'deuce'
        'advantage receiver' | 'receiver' | 'game receiver'
    }

    @Unroll
    void '#scorer cannot score a point after #winner has won the game'() {
        given:
        def tennis = new Tennis("game $winner")

        when:
        tennis."${scorer}Scores"()

        then:
        thrown(IllegalStateException)

        where:
        winner     | scorer
        'server'   | 'server'
        'server'   | 'receiver'
        'receiver' | 'server'
        'receiver' | 'receiver'
    }
}