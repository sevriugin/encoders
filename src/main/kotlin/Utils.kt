import java.util.*

fun shortEncoder(value: UInt, shift: Int = 0): BitSet {
    val encoded = BitSet()
    var mask: UInt = 0x8000u
    for (i in 0..65535) {
        encoded.set(shift + i, (value and mask) != 0u)
        mask = mask shr 1
    }
    return encoded
}

fun getLastSetBit(bitSet: BitSet): Int {
    var index = bitSet.size()
    if (index == 0) return 0
    while (--index >= 0) {
        if (bitSet.get(index)) return index + 1
    }
    return 0;
}

fun bitSetToBase64(bitSet: BitSet): String = String(Base64.getEncoder().encode(bitSet.toByteArray()))

fun getMinId(enabled: Set<UInt>, disabled: Set<UInt>): UInt {
    if (enabled.isEmpty() || disabled.isEmpty()) return 0u
    return if (enabled.min() > disabled.min()) disabled.min() else enabled.min()
}
fun getMaxId(enabled: Set<UInt>, disabled: Set<UInt>): UInt {
    if (enabled.isEmpty() && disabled.isEmpty()) {
        return 0u
    } else if (enabled.isEmpty()) {
        return disabled.max()
    } else if (disabled.isEmpty()) {
        return enabled.max()
    }
    return if (enabled.max() < disabled.max()) disabled.max() else enabled.max()
}