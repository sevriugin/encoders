class Range (val start: UInt, val end: UInt) {
    val numberOfIds: UInt
        get() = end - start + 1u
}