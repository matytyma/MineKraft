package dev.matytyma.minekraft

import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.util.Vector

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

    val blockX: Int
        get() = location.blockX

    val blockY: Int
        get() = location.blockY

    val blockZ: Int
        get() = location.blockZ

    var direction: Vector
        get() = location.direction
        set(value) {
            location.direction = value
        }

    operator fun plusAssign(other: Location) {
        location.add(other)
    }

    operator fun plusAssign(vector: Vector) {
        location.add(vector)
    }

    operator fun plusAssign(vector: Triple<Double, Double, Double>) {
        location.add(vector.first, vector.second, vector.third)
    }

    operator fun minusAssign(other: Location) {
        location.subtract(other)
    }

    operator fun minusAssign(vector: Vector) {
        location.subtract(vector)
    }

    operator fun minusAssign(vector: Triple<Double, Double, Double>) {
        location.subtract(vector.first, vector.second, vector.third)
    }

    val length: Double
        get() = location.length()

    val lengthSquared: Double
        get() = location.lengthSquared()
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
