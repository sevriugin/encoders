import java.util.*

interface EncodedRanges  {
    val ranges: Set<Range>
    val size: Int
    val encoded: BitSet
}