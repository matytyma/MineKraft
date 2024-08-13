package dev.matytyma.minekraft.inventory

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

    val enchantments: MutableMap<Enchantment, Int>
        get() = object : MutableMap<Enchantment, Int> {
            override val entries: MutableSet<MutableMap.MutableEntry<Enchantment, Int>>
                get() = stack.enchantments.entries

            override val keys: MutableSet<Enchantment>
                get() = stack.enchantments.keys

            override val size: Int
                get() = stack.enchantments.size

            override val values: MutableCollection<Int>
                get() = stack.enchantments.values

            override fun clear() = stack.removeEnchantments()

            override fun isEmpty(): Boolean = stack.enchantments.isEmpty()

            override fun remove(key: Enchantment): Int = stack.removeEnchantment(key)

            override fun putAll(from: Map<out Enchantment, Int>) =
                stack.addEnchantments(from.mapKeys { it as Enchantment })

            override fun put(key: Enchantment, value: Int): Int? {
                stack.addEnchantment(key, value)
                return null
            }

            override fun get(key: Enchantment): Int = stack.getEnchantmentLevel(key)

            override fun containsValue(value: Int): Boolean = throw UnsupportedOperationException("")

            override fun containsKey(key: Enchantment): Boolean = stack.containsEnchantment(key)
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
