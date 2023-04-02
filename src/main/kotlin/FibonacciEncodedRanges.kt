import java.util.*

class FibonacciEncodedRanges (private val ids: Set<UInt>) : EncodedRanges {
    private val fib = mutableSetOf<UInt>()
    private val numberOfFibonacciNumbers = 23               // fib(23) = 28_657
    private val rangeField = SimpleEncodedRanges(ids)
    override val ranges: Set<Range>
        get() = rangeField.ranges
    init {
        fib.add(1u); fib.add(2u)

        for (i in 2..numberOfFibonacciNumbers) {
            fib.add(fib.elementAt(i - 1 ) + fib.elementAt( i - 2))
        }
    }
    private fun largestFibonacciLessOrEqual(id: UInt): Int {
        var i: Int = 2
        while (fib.elementAt(i - 1) <= id) {
            i++
        }
        return i - 2
    }
    private fun fibonacciEncoding(id: UInt, shift: Int = 0): BitSet {
        var n = id
        val index: Int = largestFibonacciLessOrEqual(n)
        val codeword = BitSet(index + shift + 2)

        var i = index
        while (n > 0u) {
            codeword.set(shift + i)
            n -= fib.elementAt(i)
            i -= 1

            while (i >= 0 && fib.elementAt(i) > n) {
                i -= 1
            }
        }
        codeword.set(index + shift + 1)

        return codeword
    }
    override val size: Int
        get() = encoded.size()
    override val encoded: BitSet
        get() {
            val result = BitSet()
            for (range in ranges) {
                result.or(fibonacciEncoding(range.start, getLastSetBit(result)))
                result.or(fibonacciEncoding(range.numberOfIds, getLastSetBit(result)))
            }
            return result
        }
    override fun toString(): String {
        var result = ""
        for (i in 0 until getLastSetBit(encoded)) {
            result += if (encoded.get(i)) "1" else "0"
        }
        return result
    }
}