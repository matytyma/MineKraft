package dev.matytyma.minekraft.inventory.meta

import dev.matytyma.minekraft.inventory.ItemStackBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

@JvmInline
value class ItemMetaBuilder(internal val meta: ItemMeta) {
    var displayName: Component?
        get() = meta.displayName()
        set(value) = meta.displayName(value)

    var itemName: Component?
        get() = if (meta.hasItemName()) meta.itemName() else null
        set(value) = meta.itemName(value)

    var lore: List<Component?>?
        get() = meta.lore()
        set(value) = meta.lore(value)

    var customModelData: Int?
        get() = if (meta.hasCustomModelData()) meta.customModelData else null
        set(value) = meta.setCustomModelData(value)

    var enchantments: MutableMap<Enchantment, Int>
        get() = object : MutableMap<Enchantment, Int> {
            // region Enchantments
            override val entries: MutableSet<MutableMap.MutableEntry<Enchantment, Int>>
                get() = meta.enchants.entries

            override val keys: MutableSet<Enchantment>
                get() = meta.enchants.keys

            override val size: Int
                get() = meta.enchants.size

            override val values: MutableCollection<Int>
                get() = meta.enchants.values

            override fun clear() = meta.removeEnchantments()

            override fun isEmpty(): Boolean = meta.hasEnchants()

            override fun remove(key: Enchantment): Int? {
                val previous = meta.getEnchantLevel(key)
                meta.removeEnchant(key)
                return if (previous == 0) null else previous
            }

            override fun putAll(from: Map<out Enchantment, Int>) =
                from.forEach { meta.addEnchant(it.key, it.value, false) }

            override fun put(key: Enchantment, value: Int): Int? {
                val previous = meta.getEnchantLevel(key)
                meta.addEnchant(key, value, false)
                return if (previous == 0) null else previous
            }

            override fun get(key: Enchantment): Int? = meta.getEnchantLevel(key).let { if (it == 0) null else it }

            override fun containsValue(value: Int): Boolean = throw UnsupportedOperationException("")

            override fun containsKey(key: Enchantment): Boolean = meta.hasEnchant(key)
            // endregion
        }
        set(value) {
            enchantments.clear()
            enchantments.putAll(value)
        }

    var unsafeEnchantments: MutableMap<Enchantment, Int>
        get() = object : MutableMap<Enchantment, Int> {
            // region UnsafeEnchantments
            override val entries: MutableSet<MutableMap.MutableEntry<Enchantment, Int>>
                get() = meta.enchants.entries

            override val keys: MutableSet<Enchantment>
                get() = meta.enchants.keys

            override val size: Int
                get() = meta.enchants.size

            override val values: MutableCollection<Int>
                get() = meta.enchants.values

            override fun clear() = meta.removeEnchantments()

            override fun isEmpty(): Boolean = meta.hasEnchants()

            override fun remove(key: Enchantment): Int? {
                val previous = meta.getEnchantLevel(key)
                meta.removeEnchant(key)
                return if (previous == 0) null else previous
            }

            override fun putAll(from: Map<out Enchantment, Int>) =
                from.forEach { meta.addEnchant(it.key, it.value, true) }

            override fun put(key: Enchantment, value: Int): Int? {
                val previous = meta.getEnchantLevel(key)
                meta.addEnchant(key, value, true)
                return if (previous == 0) null else previous
            }

            override fun get(key: Enchantment): Int? = meta.getEnchantLevel(key).let { if (it == 0) null else it }

            override fun containsValue(value: Int): Boolean = throw UnsupportedOperationException("")

            override fun containsKey(key: Enchantment): Boolean = meta.hasEnchant(key)
            // endregion
        }
        set(value) {
            enchantments.clear()
            enchantments.putAll(value)
        }

    var itemFlags: MutableSet<ItemFlag>
        get() = object : MutableSet<ItemFlag> {
            // region ItemFlags
            override fun add(element: ItemFlag): Boolean {
                val hasItemFlag = meta.hasItemFlag(element)
                if (!hasItemFlag) {
                    meta.addItemFlags(element)
                }
                return !hasItemFlag
            }

            override val size: Int
                get() = meta.itemFlags.size

            override fun clear() = meta.removeItemFlags(*meta.itemFlags.toTypedArray())

            override fun isEmpty(): Boolean = meta.itemFlags.isEmpty()

            override fun iterator(): MutableIterator<ItemFlag> = meta.itemFlags.iterator()

            override fun retainAll(elements: Collection<ItemFlag>): Boolean {
                val forRemoval = meta.itemFlags.filter { it !in elements }
                meta.removeItemFlags(*forRemoval.toTypedArray())
                return forRemoval.isNotEmpty()
            }

            override fun removeAll(elements: Collection<ItemFlag>): Boolean {
                var removed = false
                elements.forEach { removed = removed || remove(it) }
                return removed
            }

            override fun remove(element: ItemFlag): Boolean {
                val removed = meta.hasItemFlag(element)
                meta.removeItemFlags(element)
                return removed
            }

            override fun containsAll(elements: Collection<ItemFlag>): Boolean {
                var contains = true
                elements.forEach { contains = contains && contains(it) }
                return contains
            }

            override fun contains(element: ItemFlag): Boolean = meta.hasItemFlag(element)

            override fun addAll(elements: Collection<ItemFlag>): Boolean {
                var added = false
                elements.forEach { added = added || add(it) }
                return added
            }
            // endregion
        }
        set(value) {
            val removed = meta.itemFlags.filter { it !in value }
            val added = value.filter { it !in meta.itemFlags }
            meta.removeItemFlags(*removed.toTypedArray())
            meta.addItemFlags(*added.toTypedArray())
        }

    var hideTooltip: Boolean
        get() = meta.isHideTooltip
        set(value) {
            meta.isHideTooltip = value
        }

    var unbreakable: Boolean
        get() = meta.isUnbreakable
        set(value) {
            meta.isUnbreakable = value
        }
}

fun itemMeta(
    material: Material,
    builder: ItemMetaBuilder.() -> Unit,
): ItemMeta = Bukkit.getItemFactory().getItemMeta(material).edit(builder)

fun ItemMeta.edit(
    builder: ItemMetaBuilder.() -> Unit,
): ItemMeta = ItemMetaBuilder(this).apply(builder).meta

fun ItemStackBuilder.itemMeta(builder: ItemMetaBuilder.() -> Unit) {
    itemMeta = itemMeta.edit(builder)
}
