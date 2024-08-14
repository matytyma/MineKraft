package dev.matytyma.minekraft.inventory

import io.papermc.paper.persistence.PersistentDataContainerView
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

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
        get() = object : MutableMap<Enchantment, Int> {
            // region Enchantments
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

            override fun remove(key: Enchantment): Int? {
                val previous = stack.getEnchantmentLevel(key)
                stack.removeEnchantment(key)
                return if (previous == 0) null else previous
            }

            override fun putAll(from: Map<out Enchantment, Int>) =
                from.forEach { stack.addEnchantment(it.key, it.value) }

            override fun put(key: Enchantment, value: Int): Int? {
                val previous = stack.getEnchantmentLevel(key)
                stack.addEnchantment(key, value)
                return if (previous == 0) null else previous
            }

            override fun get(key: Enchantment): Int? = stack.getEnchantmentLevel(key).let { if (it == 0) null else it }

            override fun containsValue(value: Int): Boolean = throw UnsupportedOperationException("")

            override fun containsKey(key: Enchantment): Boolean = stack.containsEnchantment(key)
            // endregion+
        }
        set(value) {
            enchantments.clear()
            enchantments.putAll(value)
        }

    var unsafeEnchantments: MutableMap<Enchantment, Int>
        get() = object : MutableMap<Enchantment, Int> {
            // region UnsafeEnchantments
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

            override fun remove(key: Enchantment): Int? {
                val previous = stack.getEnchantmentLevel(key)
                stack.removeEnchantment(key)
                return if (previous == 0) null else previous
            }

            override fun putAll(from: Map<out Enchantment, Int>) =
                from.forEach { stack.addUnsafeEnchantment(it.key, it.value) }

            override fun put(key: Enchantment, value: Int): Int? {
                val previous = stack.getEnchantmentLevel(key)
                stack.addUnsafeEnchantment(key, value)
                return if (previous == 0) null else previous
            }

            override fun get(key: Enchantment): Int? = stack.getEnchantmentLevel(key).let { if (it == 0) null else it }

            override fun containsValue(value: Int): Boolean = throw UnsupportedOperationException("")

            override fun containsKey(key: Enchantment): Boolean = stack.containsEnchantment(key)
            // endregion
        }
        set(value) {
            unsafeEnchantments.clear()
            unsafeEnchantments.putAll(value)
        }

    var itemMeta: ItemMeta
        get() = stack.itemMeta
        set(value) {
            stack.itemMeta = value
        }

    val displayName: Component
        get() = stack.displayName()

    var lore: List<Component>
        get() = stack.lore() ?: emptyList()
        set(value) = stack.lore(value)

    var itemFlags: MutableSet<ItemFlag>
        get() = object : MutableSet<ItemFlag> {
            // region ItemFlags
            override fun add(element: ItemFlag): Boolean {
                val hasItemFlag = stack.hasItemFlag(element)
                if (!hasItemFlag) {
                    stack.addItemFlags(element)
                }
                return hasItemFlag
            }

            override val size: Int
                get() = stack.itemFlags.size

            override fun clear() = stack.removeItemFlags(*stack.itemFlags.toTypedArray())

            override fun isEmpty(): Boolean = stack.itemFlags.isEmpty()

            override fun iterator(): MutableIterator<ItemFlag> = stack.itemFlags.iterator()

            override fun retainAll(elements: Collection<ItemFlag>): Boolean {
                val forRemoval = stack.itemFlags.filter { it !in elements }
                stack.removeItemFlags(*forRemoval.toTypedArray())
                return forRemoval.isNotEmpty()
            }

            override fun removeAll(elements: Collection<ItemFlag>): Boolean {
                var removed = false
                elements.forEach { removed = removed || remove(it) }
                return removed
            }

            override fun remove(element: ItemFlag): Boolean {
                val removed = stack.hasItemFlag(element)
                stack.removeItemFlags(element)
                return removed
            }

            override fun containsAll(elements: Collection<ItemFlag>): Boolean {
                var contains = true
                elements.forEach { contains = contains && stack.hasItemFlag(it) }
                return contains
            }

            override fun contains(element: ItemFlag): Boolean = stack.hasItemFlag(element)

            override fun addAll(elements: Collection<ItemFlag>): Boolean {
                var added = false
                elements.forEach { added = added || add(it) }
                return added
            }
            // endregion
        }
        set(value) {
            itemFlags.clear()
            itemFlags.addAll(value)
        }

    val translationKey: String
        get() = stack.translationKey()
}

fun itemStack(
    material: Material,
    amount: Int = 1,
    builder: ItemStackBuilder.() -> Unit,
): ItemStack = ItemStack(material, amount).edit(builder)

fun ItemStack.edit(
    builder: ItemStackBuilder.() -> Unit,
): ItemStack = ItemStackBuilder(this).apply(builder).stack
