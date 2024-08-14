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

            override fun remove(key: Enchantment): Int = stack.removeEnchantment(key)

            override fun putAll(from: Map<out Enchantment, Int>) =
                stack.addEnchantments(from.mapKeys { it as Enchantment })

            override fun put(key: Enchantment, value: Int): Int? {
                val previous = stack.getEnchantmentLevel(key)
                stack.addEnchantment(key, value)
                return if (previous == 0) null else previous
            }

            override fun get(key: Enchantment): Int = stack.getEnchantmentLevel(key)

            override fun containsValue(value: Int): Boolean = throw UnsupportedOperationException("")

            override fun containsKey(key: Enchantment): Boolean = stack.containsEnchantment(key)
            // endregion
        }
        set(value) {
            enchantments.clear()
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

            override fun remove(key: Enchantment): Int = stack.removeEnchantment(key)

            override fun putAll(from: Map<out Enchantment, Int>) =
                stack.addUnsafeEnchantments(from.mapKeys { it as Enchantment })

            override fun put(key: Enchantment, value: Int): Int? {
                val previous = stack.getEnchantmentLevel(key)
                stack.addUnsafeEnchantment(key, value)
                return if (previous == 0) null else previous
            }

            override fun get(key: Enchantment): Int = stack.getEnchantmentLevel(key)

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

    // TODO: Add ItemMeta builder support

    val displayName: Component
        get() = stack.displayName()

    var lore: List<Component>
        get() = stack.lore() ?: emptyList()
        set(value) = stack.lore(value)

    val itemFlags: MutableSet<ItemFlag>
        get() = object : MutableSet<ItemFlag> {
            // region ItemFlags
            override fun add(element: ItemFlag): Boolean {
                stack.addItemFlags(element)
                return true
            }

            override val size: Int
                get() = stack.itemFlags.size

            override fun clear() = stack.removeItemFlags(*stack.itemFlags.toTypedArray())

            override fun isEmpty(): Boolean = stack.itemFlags.isEmpty()

            override fun iterator(): MutableIterator<ItemFlag> = stack.itemFlags.iterator()

            override fun retainAll(elements: Collection<ItemFlag>): Boolean {
                val flags = stack.itemFlags.toMutableSet()
                val result = flags.retainAll(elements.toSet())
                stack.removeItemFlags(*stack.itemFlags.toTypedArray())
                stack.addItemFlags(*flags.toTypedArray())
                return result
            }

            override fun removeAll(elements: Collection<ItemFlag>): Boolean {
                val flags = stack.itemFlags.toMutableSet()
                val result = flags.removeAll(elements.toSet())
                stack.removeItemFlags(*stack.itemFlags.toTypedArray())
                stack.addItemFlags(*flags.toTypedArray())
                return result
            }

            override fun remove(element: ItemFlag): Boolean {
                val result = stack.hasItemFlag(element)
                stack.removeItemFlags(element)
                return result
            }

            override fun containsAll(elements: Collection<ItemFlag>): Boolean = stack.itemFlags.containsAll(elements)

            override fun contains(element: ItemFlag): Boolean = stack.itemFlags.contains(element)

            override fun addAll(elements: Collection<ItemFlag>): Boolean {
                val previous = stack.itemFlags
                stack.addItemFlags(*elements.toTypedArray())
                return previous != stack.itemFlags
            }
            // endregion
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
