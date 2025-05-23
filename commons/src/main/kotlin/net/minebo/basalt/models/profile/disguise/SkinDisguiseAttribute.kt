package net.minebo.basalt.models.profile.disguise

data class SkinDisguiseAttribute(
    var customName: String,
    var disguisedAt: Long = System.currentTimeMillis(),
    var skinName: String,
    var texture: String,
    var signature: String
)