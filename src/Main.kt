import kotlin.random.Random

fun main() {
    println("Шифровка и расшифровка сообщений")
    println("1. Использовать типовую таблицу")
    println("2. Генерировать случайную таблицу")
    print("Выберите опцию (1/2): ")

    val option = readLine()?.toIntOrNull()

    when (option) {
        1 -> processWithDefaultTable()
        2 -> processWithRandomTable()
        else -> println("Некорректный ввод.")
    }
}

fun processWithDefaultTable() {
    val message = enterMessage()
    val auxiliarySymbol = enterAuxiliarySymbol()
    val defaultTable = getDefaultTable()

    val encryptedMessage = encryptMessage(message, defaultTable)
    println("Исходное сообщение: $message")
    println("Шифрованное сообщение: $encryptedMessage")
    println("Шифровальная таблица:")
    printTable(defaultTable)
}

fun processWithRandomTable() {
    val message = enterMessage()
    val auxiliarySymbol = enterAuxiliarySymbol()
    val randomTable = generateRandomTable()

    val encryptedMessage = encryptMessage(message, randomTable)
    println("Исходное сообщение: $message")
    println("Шифрованное сообщение: $encryptedMessage")
    println("Шифровальная таблица:")
    printTable(randomTable)
}

fun enterMessage(): String {
    print("Введите исходное сообщение: ")
    return readLine().orEmpty().toUpperCase().filter { it.isLetter() }
}

fun enterAuxiliarySymbol(): Char {
    print("Введите вспомогательный символ: ")
    return readLine().orEmpty().firstOrNull() ?: ' '
}

fun getDefaultTable(): Map<String, String> {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val table = mutableMapOf<String, String>()
    for (i in 0 until alphabet.length - 1) {
        val pair = alphabet[i].toString() + alphabet[i + 1]
        table[pair] = (i + 1).toString().padStart(3, '0')
    }
    return table
}

fun generateRandomTable(): Map<String, String> {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val pairs = mutableListOf<String>()
    for (i in 0 until alphabet.length - 1) {
        val pair = alphabet[i].toString() + alphabet[i + 1]
        pairs.add(pair)
    }
    pairs.shuffle()

    val table = mutableMapOf<String, String>()
    for (i in pairs.indices) {
        table[pairs[i]] = (i + 1).toString().padStart(3, '0')
    }
    return table
}

fun encryptMessage(message: String, table: Map<String, String>): String {
    val encryptedPairs = mutableListOf<String>()
    for (i in 0 until message.length step 2) {
        val pair = if (i + 1 < message.length) message.substring(i, i + 2) else message[i].toString() + 'X'
        encryptedPairs.add(table[pair] ?: "000")
    }
    return encryptedPairs.joinToString(" ")
}

fun printTable(table: Map<String, String>) {
    table.forEach { (pair, code) ->
        println("$pair -> $code")
    }
}
