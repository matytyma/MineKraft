package dev.matytyma.minekraft.inventory.meta

import dev.matytyma.minekraft.inventory.ItemStackBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.meta.ItemMeta

@JvmInline
value class ItemMetaBuilder(internal val meta: ItemMeta) {
    var displayName: Component?
        get() = meta.displayName()
        set(value) = meta.displayName(value)

    // TODO: Check if has item name
    var itemName: Component
        get() = meta.itemName()
        set(value) = meta.itemName(value)

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
