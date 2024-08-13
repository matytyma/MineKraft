package dev.matytyma.minekraft.inventory

import io.papermc.paper.persistence.PersistentDataContainerView
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@JvmInline
value class ItemStackBuilder(internal val stack: ItemStack) {
    val persistentDataContainer: PersistentDataContainerView
        get() = stack.persistentDataContainer

    val type: Material
        get() = stack.type
}

fun itemStack(
    material: Material,
    amount: Int = 1,
    builder: ItemStackBuilder.() -> Unit
): ItemStack = ItemStack(material, amount).edit(builder)

fun ItemStack.edit(
    builder: ItemStackBuilder.() -> Unit
): ItemStack = ItemStackBuilder(this).apply(builder).stack
