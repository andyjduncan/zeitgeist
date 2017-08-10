package com.adjectivecolournoun.zeitgeist.bloom

import spock.lang.Specification

import java.security.Security

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

class TestSpellchecking extends Specification {

    void 'indicates real words are correctly spelt'() {
        when:
        def words = (1..5).collect { randomAlphanumeric(8) }
        def spellchecker = new Spellchecker(words)

        then:
        words.every { spellchecker.correct(it) }
    }

    void 'indicates fake words are incorrectly spelt'() {
        when:
        def words = (1..5).collect { randomAlphanumeric(8) }
        def spellchecker = new Spellchecker(words)

        then:
        !(1..5).collect { randomAlphanumeric(8) }.any { spellchecker.correct(it) }
    }

    void 'defaults to sha-256 hashing'() {
        when:
        def spellchecker = new Spellchecker([])

        then:
        spellchecker.hasherInfo.contains('Algorithm: SHA-256')
        spellchecker.hasherInfo.readLines().any { it == 'Offsets: 0 4 8 12 16 20 24 28' }
    }

    void 'supports other hashing algorithms'() {
        when:
        def spellchecker = new Spellchecker([], 'MD5')

        then:
        spellchecker.hasherInfo.contains('Algorithm: MD5')
        spellchecker.hasherInfo.readLines().any { it == 'Offsets: 0 4 8 12' }
    }

    void 'allows the number of hashes to be limited'() {
        when:
        def spellchecker = new Spellchecker([], 'SHA-256', 2)

        then:
        spellchecker.hasherInfo.readLines().any { it == 'Offsets: 0 4' }
    }
}
