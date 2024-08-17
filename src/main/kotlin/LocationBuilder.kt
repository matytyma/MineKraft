package dev.matytyma.minekraft

import org.bukkit.*
import org.bukkit.block.Block

@JvmInline
value class LocationBuilder(internal val location: Location) {
    var x: Double
        get() = location.x
        set(value) {
            location.x = value
        }

    var y: Double
        get() = location.y
        set(value) {
            location.y = value
        }

    var z: Double
        get() = location.z
        set(value) {
            location.z = value
        }

    var yaw: Float
        get() = location.yaw
        set(value) {
            location.yaw = value
        }

    var pitch: Float
        get() = location.pitch
        set(value) {
            location.pitch = value
        }

    var world: World?
        get() = location.world
        set(value) {
            location.world = value
        }

    val worldLoaded: Boolean
        get() = location.isWorldLoaded

    val chunk: Chunk
        get() = location.chunk

    val block: Block
        get() = location.block
}

fun location(
    world: World?,
    x: Double,
    y: Double,
    z: Double,
    builder: LocationBuilder.() -> Unit,
): Location = Location(world, x, y, z).edit(builder)

fun Location.edit(
    builder: LocationBuilder.() -> Unit,
): Location = LocationBuilder(this).apply(builder).location
