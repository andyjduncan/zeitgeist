package com.adjectivecolournoun.diamond

fun buildLine(letter: Char, pivotLetter: Char): String {
    val between = ((letter - 'A') * 2) - 1
    val before = pivotLetter - letter

    return " ".repeat(before) + letter +
            if (between > 0) " ".repeat(between) + letter else ""
}

fun generate(letter: Char): String {
    val buffer = StringBuilder()

    ('A'..letter)
            .map { buildLine(it, letter) }
            .joinTo(buffer = buffer, separator = "\n", postfix = "\n")

    (letter - 1 downTo 'A')
            .map { buildLine(it, letter) }
            .joinTo(buffer = buffer, separator = "\n")

    return buffer.toString()
}

fun main(args: Array<String>) {
    println(generate(args.first().first()))
}