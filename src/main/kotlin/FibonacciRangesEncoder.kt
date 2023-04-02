class FibonacciRangesEncoder (private val model: Model) : Encoder<FibonacciEncodedRanges>(model) {
    override val enabled = FibonacciEncodedRanges(model.enabled)
    override val disabled = FibonacciEncodedRanges(model.disabled)
    override val undefined = FibonacciEncodedRanges(model.undefined)
}