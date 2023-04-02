import java.util.*
class BitField (private val model: Model) {
    private var encoded: BitSet = BitSet()
    init {
        for ((pointer, id: UInt) in (model.minId..model.maxId).withIndex()) {
            if (model.getStateFor(id) == State.ENABLED) {
                encoded.set(pointer)
            }
        }
    }
    val undefined: Set<UInt>
        get() = model.undefined
    val shift: UInt
        get() = model.minId
    val shiftedMaxId: UInt
        get() = model.maxId - model.minId
    val encodedString: String
        get() = bitSetToBase64(encoded)
    val size: Int
        get() = encoded.size()
}