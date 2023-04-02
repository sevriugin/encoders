enum class Case {
    RH, RM, CL
}

class ModelFactory (private val numberOfEnabled: UInt, private val numberOfDisabled: UInt ) {
    private val rhMax = 15_000u
    private val rmMax = 1_024u
    private val clMax = 128u

    private val rhIds = (1u..rhMax).toMutableList()
    private val rmIds = (1u..rmMax).toMutableList()
    private val clIds = (1u..clMax).toMutableList()

    private fun getIds(list: List<UInt>, num: UInt, shift: Int = 0): Set<UInt> {
        val result = mutableSetOf<UInt>()
        for ((i, _) in (1u..num).withIndex()) {
            result.add(list[i + shift])
        }
        return result.toSortedSet()
    }
    private val total: UInt
        get() = numberOfDisabled + numberOfEnabled
    private fun validTotal(case: Case): Boolean =
        when (case) {
            Case.RM -> total <= rmMax
            Case.RH -> total <= rhMax
            Case.CL -> total <= clMax
        }
    fun getModel(case: Case): Model {
        if (!validTotal(case)) {
            println("Invalid total $total for $case")
            return Model(setOf(), setOf())
        }
        when (case) {
            Case.RM -> {
                rmIds.shuffle()
                val enabled = getIds(rmIds, numberOfEnabled)
                val disabled = getIds(rmIds, numberOfDisabled, enabled.size)
                return Model(enabled, disabled)
            }
            Case.RH -> {
                rhIds.shuffle()
                val enabled = getIds(rhIds, numberOfEnabled)
                val disabled = getIds(rhIds, numberOfDisabled, enabled.size)
                return Model(enabled, disabled)
            }
            Case.CL -> {
                clIds.shuffle()
                val enabled = getIds(clIds, numberOfEnabled)
                val disabled = getIds(clIds, clMax - numberOfEnabled, enabled.size)
                return Model(enabled, disabled)
            }
        }
    }
}