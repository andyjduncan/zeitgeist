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

        def spellchecker = new Spellchecker(words, algorithm, 1)

        int falsePositives = 0

        when:
        100_000.times {
            def fakeWord = randomAlphanumeric(5)
            if (spellchecker.correct(fakeWord) && !words.contains(fakeWord)) {
                falsePositives++
            }
        }

        then:
        falsePositives > minFalsePositive && falsePositives < maxFalsePositives

        where:
        algorithm | minFalsePositive | maxFalsePositives
        'MD2'     | 5                | 15
        'MD5'     | 5                | 15
        'SHA-1'   | 5                | 15
        'SHA-256' | 5                | 15
        'SHA-512' | 5                | 15

    }
}