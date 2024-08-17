package dev.matytyma.minekraft.plugin

import org.bukkit.event.*
import org.bukkit.plugin.Plugin

inline fun <reified T : Event> Plugin.on(
    priority: EventPriority = EventPriority.NORMAL,
    noinline handler: T.() -> Unit
) {
    val listener: Listener = object : Listener {}
    val executor: (Listener, Event) -> Unit = { _, event -> (event as T).handler() }
    server.pluginManager.registerEvent(T::class.java, listener, priority, executor, this)
}
