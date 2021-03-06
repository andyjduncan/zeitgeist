package com.adjectivecolournoun.zeitgeist.bloom

import java.nio.ByteBuffer
import java.security.MessageDigest

import static java.lang.Math.abs

class Spellchecker {

    private final bits = new BitSet()

    private final MessageDigest hasher

    private final int digestLength

    private final int maxHashes

    Spellchecker(Iterable<String> words, String hashAlgorithm = 'SHA-256', int maxHashes = 1024) {
        hasher = MessageDigest.getInstance(hashAlgorithm)
        digestLength = hasher.digestLength
        this.maxHashes = maxHashes

        words.each {
            bitsForWord(it).each { bits.set(it) }
        }
    }

    static void main(String[] args) {
        Spellchecker spellchecker = null

        new File(args[0]).withReader { reader ->
            spellchecker = new Spellchecker(
                    reader.readLines(),
                    args.size() > 1 ? args[1] : 'SHA-256',
                    args.size() > 2 ? args[2] as int : 256)
        }

        println spellchecker.hasherInfo

        println spellchecker.loading

        while (true) {
            print '> '
            def word = System.in.newReader().readLine()
            if (!word) break
            def correct = spellchecker.correct(word)

            println "$word is${correct ? "" : "n't"} correct"
        }
    }

    boolean correct(String word) {
        bitsForWord(word).every { bits.get(it) }
    }

    private bitsForWord(String word) {
        def hash = hasher.digest(word.getBytes('UTF-8'))

        offsets().collect {
            abs(ByteBuffer.wrap(hash[it..it + 3] as byte[]).int)
        }
    }

    private offsets() {
        (0..<digestLength).step(4).take(maxHashes)
    }

    String getLoading() {
        """Set size: ${bits.length()}
Bits set: ${bits.cardinality()}"""
    }

    String getHasherInfo() {
        """Algorithm: ${hasher.getAlgorithm()}
Offsets: ${offsets().join(' ')}"""
    }
}
