package dev.matytyma.minekraft.collection

interface MultiMap<K, V> {
    fun isEmpty(): Boolean

    operator fun get(key: K): List<V>?

    fun toJavaMultiMap(): com.google.common.collect.Multimap<K, V>
}

interface MutableMultiMap<K, V> : MultiMap<K, V> {
    operator fun set(key: K, value: V)

    operator fun set(key: K, values: Collection<V>)

    fun putAll(from: Map<out K, Collection<V>>)

    fun add(key: K, value: V): Boolean

    fun add(key: K, values: Collection<V>): Boolean

    fun addAll(from: Map<out K, Collection<V>>): Boolean

    fun remove(key: K): Boolean

    operator fun plusAssign(pair: Pair<K, Collection<V>>) {
        add(pair.first, pair.second)
    }

    operator fun minusAssign(key: K) {
        remove(key)
    }

    operator fun minusAssign(keys: Collection<K>) = keys.forEach { remove(it) }
}

@JvmName("plusAssignSingle")
operator fun <K, V> MutableMultiMap<K, V>.plusAssign(pair: Pair<K, V>) {
    add(pair.first, pair.second)
}
