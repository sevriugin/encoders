fun main(args: Array<String>) {
    println("Encoding Algorithms Test!\n")
    val model = ModelFactory(200u, 0u).getModel(Case.RM)
    val bitField = BitFieldEncoder(model)
    val range = RangesEncoder(model).encode()
    val fibonacci = FibonacciRangesEncoder(model).encode()

    println("Max Id ${model.maxId}\t\tLength\t\t\t\tSize\t\t\t\t\tHeader\t\t\t\t\tBody")
    println("BitField\t\t${bitField.encodedString.length}\t\t\t\t\t${bitField.size}\t\t\t\t\t\t${bitField.headerSize}\t\t\t\t\t\t${bitField.bodySize}")
    println("Range\t\t\t${range.encodedString.length}\t\t\t\t\t${range.size}\t\t\t\t\t\t${range.headerSize}\t\t\t\t\t\t${range.bodySize}")
    println("Fibonacci\t\t${fibonacci.encodedString.length}\t\t\t\t\t${fibonacci.size}\t\t\t\t\t\t${fibonacci.headerSize}\t\t\t\t\t\t${fibonacci.bodySize}")

}