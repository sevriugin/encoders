import java.util.*
abstract class Encoder<T: EncodedRanges> (private val model: Model) {
    abstract val enabled:  T; abstract val disabled: T; abstract val undefined: T

    private val header = BitSet(); private val firstPart = BitSet(); private val secondPart = BitSet()

    fun encode() : Encoder<T> {
        val stateSizes = listOf<StateSize>(
                StateSize(State.ENABLED, enabled.size),
                StateSize(State.DISABLED, disabled.size),
                StateSize(State.UNDEFINED, undefined.size),
            ).toSortedSet { s1, s2 -> if (s1.size == s2.size) 1 else s1.size - s2.size }

        when (stateSizes.first().state) {
            State.ENABLED -> firstPart.or(enabled.encoded)
            State.DISABLED -> {
                header.set(1)
                firstPart.or(disabled.encoded)
            }
            else -> {
                header.set(0, 2)
                firstPart.or(undefined.encoded)
            }
        }
        when (stateSizes.elementAt(1).state) {
            State.ENABLED -> secondPart.or(enabled.encoded)
            State.DISABLED -> {
                header.set(3)
                secondPart.or(disabled.encoded)
            }
            else -> {
                header.set(2, 4)
                secondPart.or(undefined.encoded)
            }
        }
        return this
    }
    private val encodedBodyString: String
        get() = "${bitSetToBase64(firstPart)}~${bitSetToBase64(secondPart)}"
    private val encodedHeaderString: String
        get() = bitSetToBase64(header)
    val encodedString: String
        get() = "$encodedHeaderString~$encodedBodyString"
    val headerSize: Int
        get() = header.size()
    val bodySize: Int
        get() = firstPart.size() + secondPart.size()
    val size: Int
        get() = headerSize + bodySize
}