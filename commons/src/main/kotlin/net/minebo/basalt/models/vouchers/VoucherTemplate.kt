package net.minebo.basalt.models.vouchers

data class VoucherTemplate(
    var id: String,
    var whatFor: String,
    var commandToExecute: String,
    var lore: MutableList<String>
)