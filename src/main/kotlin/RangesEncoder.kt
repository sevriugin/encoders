class RangesEncoder (private val model: Model) : Encoder<SimpleEncodedRanges>(model) {
    override val enabled = SimpleEncodedRanges(model.enabled)
    override val disabled = SimpleEncodedRanges(model.disabled)
    override val undefined = SimpleEncodedRanges(model.undefined)
}