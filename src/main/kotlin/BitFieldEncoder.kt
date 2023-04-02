import java.util.*

class BitFieldEncoder (private val model: Model) {
    private val bitField = BitField(model)
    private val undefined: BitSet
        get() = FibonacciEncodedRanges(bitField.undefined).encoded
    val encodedString: String
        get() = "$encodedHeaderString~${bitField.encodedString}" +
                    if (encodedUndefined.isNotEmpty()) "~$encodedUndefined" else ""
    private val encodedHeaderString: String
        get() = bitSetToBase64(header)
    private val encodedUndefined: String
        get() {
            if (undefined.isEmpty) {
                return ""
            }
            return bitSetToBase64(undefined)
        }
    private val header: BitSet
        get() {
            val encodedHeader = BitSet()
            var pointer = 0
            if (bitField.shift == 1u) {
                encodedHeader.set(pointer++)
            } else {
                pointer++
                encodedHeader.or(shortEncoder(bitField.shift, pointer))
                pointer+=16
            }
            encodedHeader.or(shortEncoder(bitField.shiftedMaxId, pointer))
            return encodedHeader
        }
    val size: Int
        get() = headerSize + bodySize
    val headerSize: Int
        get() = header.size()
    val bodySize: Int
        get() = bitField.size + undefined.size()
}