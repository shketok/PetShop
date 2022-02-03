package utils

object StringGenerationUtils {
    private const val ALPHABET_AND_NUMBERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz"

    fun getRandomStringOfLength(n: Int): String {
        val sb = StringBuilder(n)
        for (i in 0 until n) {
            val index = (ALPHABET_AND_NUMBERS.length * Math.random()).toInt()
            sb.append(ALPHABET_AND_NUMBERS[index])
        }
        return sb.toString()
    }
}