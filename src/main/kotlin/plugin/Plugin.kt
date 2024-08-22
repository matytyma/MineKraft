package dev.matytyma.minekraft.plugin

import org.bukkit.event.*
import org.bukkit.plugin.Plugin

inline fun <reified T : Event> Plugin.on(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline handler: T.() -> Unit,
) = on(T::class.java, priority, ignoreCancelled, handler)

inline fun <reified T : Event> Plugin.on(
    eventClass: Class<out T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline handler: T.() -> Unit,
) {
    val listener: Listener = object : Listener {}
    val executor: (Listener, Event) -> Unit = { _, event -> (event as T).handler() }
    server.pluginManager.registerEvent(eventClass, listener, priority, executor, this, ignoreCancelled)
}
