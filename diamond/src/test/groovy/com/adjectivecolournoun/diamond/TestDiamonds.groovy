package com.adjectivecolournoun.diamond

import spock.lang.Specification

import static java.lang.Math.random


class TestDiamonds extends Specification {

    def letter = ('C'..'Z')[((23 * random()) + 1).toInteger()].toCharacter()
    def letterIndex = (letter - 'A'.toCharacter()) + 1
    def lineWidth = letterIndex + 2

    void 'prints enough lines'() {
        given:
        def expectedLines = (2 * letterIndex) - 1

        when:
        def output = DiamondKt.generate(letter)

        then:
        output.readLines().size() == expectedLines
    }

    void 'prints a line of the width of the highest letter'() {
        when:
        def output = DiamondKt.generate(letter)

        then:
        output.readLines().any { it.size() == lineWidth }
    }

    void 'prints single A lines at top and bottom'() {
        when:
        def output = DiamondKt.generate(letter).readLines()

        then:
        output.first() ==~ /\s{${letter - 'A'.toCharacter()}}A$/
        output.last() ==~ /\s{${letter - 'A'.toCharacter()}}A$/
    }

    void 'prints two B lines'() {
        when:
        def output = DiamondKt.generate(letter).readLines()

        then:
        output.count { it.contains('B') } == 2
        output.findAll { it.contains('B') }.every { it ==~ /\s{${letter - 'B'.toCharacter()}}B B$/}
    }

    void 'prints the target letter line once'() {
        when:
        def output = DiamondKt.generate(letter).readLines()

        then:
        output.count { it.contains(letter.toString()) } == 1
        output.find { it.contains(letter.toString()) } ==~ /^${letter}\s{${2 * (letter - 'A'.toCharacter())- 1}}${letter}$/
    }
}