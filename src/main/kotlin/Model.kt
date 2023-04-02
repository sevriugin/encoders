enum class State {
    ENABLED, DISABLED, UNDEFINED
}
class StateSize (val state: State, val size: Int) {
    override fun toString(): String {
        return "$state: $size"
    }
}
class Model(val enabled: Set<UInt>, val disabled: Set<UInt>) {
    val undefined: Set<UInt>
        get() {
            val result = mutableSetOf<UInt>()
            for (id in minId..maxId) {
                if (getStateFor(id) == State.UNDEFINED) {
                    result.add(id)
                }
            }
            return result
        }
    val maxId: UInt
        get() = getMaxId(enabled, disabled)
    val minId: UInt
        get() = getMinId(enabled, disabled)
    fun getStateFor(id: UInt): State {
        if (enabled.contains(id)) return State.ENABLED
        if (disabled.contains(id)) return State.DISABLED
        return State.UNDEFINED
    }
}