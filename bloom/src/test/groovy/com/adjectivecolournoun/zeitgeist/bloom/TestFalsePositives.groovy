package com.adjectivecolournoun.zeitgeist.bloom

import spock.lang.Specification
import spock.lang.Unroll

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

class TestFalsePositives extends Specification {

    @Unroll
    void 'test #algorithm false positives'() {
        given:
        Set<String> words = null
        new File('/usr/share/dict/words').withReader {
            words = it.readLines() as Set
        }

        def spellchecker = new Spellchecker(words, algorithm)

        int falsePositives = 0

        when:
        10_000_000.times {
            def fakeWord = randomAlphanumeric(5)
            if (spellchecker.correct(fakeWord) && !words.contains(fakeWord)) {
                falsePositives++
            }
        }

        then:
        println falsePositives
        falsePositives <= maxFalsePositives

        where:
        algorithm | maxFalsePositives
        'MD2'     | 10
        'MD5'     | 10
        'SHA-1'   | 10
        'SHA-256' | 5

    }
}