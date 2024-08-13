package dev.matytyma.minekraft.inventory

import dev.matytyma.minekraft.enchantments.Enchantments
import dev.matytyma.minekraft.enchantments.UnsafeEnchantments
import io.papermc.paper.persistence.PersistentDataContainerView
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

@JvmInline
value class ItemStackBuilder(internal val stack: ItemStack) {
    val persistentDataContainer: PersistentDataContainerView
        get() = stack.persistentDataContainer

    val type: Material
        get() = stack.type

    var amount: Int
        get() = stack.amount
        set(value) {
            stack.amount = value
        }

    val maxStackSize: Int
        get() = stack.maxStackSize

    var enchantments: MutableMap<Enchantment, Int>
        get() = Enchantments(stack)
        set(value) {
            enchantments.clear()
            enchantments.putAll(value)
        }

    var unsafeEnchantments: MutableMap<Enchantment, Int>
        get() = UnsafeEnchantments(stack)
        set(value) {
            unsafeEnchantments.clear()
            unsafeEnchantments.putAll(value)
        }
}

fun itemStack(
    material: Material,
    amount: Int = 1,
    builder: ItemStackBuilder.() -> Unit,
): ItemStack = ItemStack(material, amount).edit(builder)

fun ItemStack.edit(
    builder: ItemStackBuilder.() -> Unit,
): ItemStack = ItemStackBuilder(this).apply(builder).stack
