package dev.matytyma.minekraft.enchantments

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

@JvmInline
internal value class Enchantments(private val stack: ItemStack) : MutableMap<Enchantment, Int> {
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

    override fun putAll(from: Map<out Enchantment, Int>) = stack.addEnchantments(from.mapKeys { it as Enchantment })

    override fun put(key: Enchantment, value: Int): Int? {
        stack.addEnchantment(key, value)
        return null
    }

    override fun get(key: Enchantment): Int = stack.getEnchantmentLevel(key)

    override fun containsValue(value: Int): Boolean = throw UnsupportedOperationException("")

    override fun containsKey(key: Enchantment): Boolean = stack.containsEnchantment(key)
}

@JvmInline
internal value class UnsafeEnchantments(private val stack: ItemStack) : MutableMap<Enchantment, Int> {
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
        stack.addUnsafeEnchantments(from.mapKeys { it as Enchantment })

    override fun put(key: Enchantment, value: Int): Int? {
        stack.addUnsafeEnchantment(key, value)
        return null
    }

    override fun get(key: Enchantment): Int = stack.getEnchantmentLevel(key)

    override fun containsValue(value: Int): Boolean = throw UnsupportedOperationException("")

    override fun containsKey(key: Enchantment): Boolean = stack.containsEnchantment(key)
}
