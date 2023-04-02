import java.util.*

class SimpleEncodedRanges (private val ids: Set<UInt>) : EncodedRanges {
    override var ranges = mutableSetOf<Range>()
    init {
        var start: UInt = 0u
        var end: UInt = 0u
        for (id in ids) {
            if (start == 0u) {
                start = id
                end = id
                continue
            }
            if (id == end + 1u) {
                end = id
            } else {
                ranges.add(Range(start, end))
                start = id
                end = id
            }
        }
        if (end != 0u) {
            ranges.add(Range(start, end))
        }
    }
    override val size: Int
        get() = ranges.size
    override fun toString(): String {
        var result = ""
        for (range in ranges) {
            if (result != "") {
                result += ", "
            }
            result += "[${range.start}..${range.end}]"
        }
        return result
    }
    override val encoded: BitSet
        get() {
            val result = BitSet()
            var pointer = 0
            for (range in ranges) {
                if (range.end == range.start) {
                    result.set(pointer++)
                    result.or(shortEncoder(range.start, pointer))
                    pointer+= 16
                } else {
                    pointer++
                    result.or(shortEncoder(range.start, pointer))
                    pointer+= 16
                    result.or(shortEncoder(range.end, pointer))
                    pointer+= 16
                }
            }
            return result
        }
}