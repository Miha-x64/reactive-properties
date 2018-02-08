package net.aquadc.properties

import net.aquadc.properties.internal.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class OptimizationTest {

    @Test fun immutablePropMapReturnsImmutable() {
        val prop = immutablePropertyOf("yo")

        val mapped = prop.map { it + it }
        assertEquals("yoyo", mapped.value)
        assertTrue(mapped is ImmutableReferenceProperty)
    }

    @Test fun concImmutablePropMapWithReturnsMapped() =
            immutablePropMapWithReturnsMapped(true, ConcurrentMappedReferenceProperty::class.java)

    @Test fun unsImmutablePropMapWithReturnsMapped() =
            immutablePropMapWithReturnsMapped(false, UnsynchronizedMappedReferenceProperty::class.java)

    private fun immutablePropMapWithReturnsMapped(concurrent: Boolean, mapsTo: Class<*>) {
        val prop0 = immutablePropertyOf("yo")
        val prop1 = mutablePropertyOf("hey", concurrent)

        val mapped0 = prop0.mapWith(prop1) { a, b -> "$b $a" }
        assertEquals("hey yo", mapped0.value)
        assertTrue(mapsTo.isInstance(mapped0))

        val mapped1 = prop1.mapWith(prop0) { a, b -> "$a $b" }
        assertEquals("hey yo", mapped1.value)
        assertTrue("mapped1 is ${mapped1.javaClass}", mapsTo.isInstance(mapped1))
    }

    @Test fun immutablePropMapWithImmutableReturnsImmutable() {
        val prop0 = immutablePropertyOf("hey")
        val prop1 = immutablePropertyOf("yo")

        val mapped = prop0.mapWith(prop1) { a, b -> "$a $b" }
        assertEquals("hey yo", mapped.value)
        assertTrue(mapped is ImmutableReferenceProperty)
    }

    @Test fun concSimpleMap() = simpleMap(true, ConcurrentMappedReferenceProperty::class.java)
    @Test fun unsSimpleMap() = simpleMap(false, UnsynchronizedMappedReferenceProperty::class.java)
    fun simpleMap(concurrent: Boolean, mapsTo: Class<*>) {
        val prop = mutablePropertyOf("hey", concurrent)
        val mapped = prop.map { "$it!" }
        assertTrue("mapped is ${mapped.javaClass}", mapsTo.isInstance(mapped))
    }

    @Test fun concSimpleMapWith() = simpleMapWith(true, ConcurrentBiMappedCachedReferenceProperty::class.java)
    @Test fun unsSimpleMapWith() = simpleMapWith(false, UnsynchronizedBiMappedCachedReferenceProperty::class.java)
    fun simpleMapWith(concurrent: Boolean, mapsTo: Class<*>) {
        val prop0 = mutablePropertyOf("hey", concurrent)
        val prop1 = mutablePropertyOf("hey", concurrent)
        assertTrue(mapsTo.isInstance(prop0.mapWith(prop1) { a, b -> "$a $b" }))
    }

    @Test fun mapValueList() {
        val prop0 = unsynchronizedMutablePropertyOf("hey")
        val prop1 = concurrentMutablePropertyOf("yo")
        val joinedProp = listOf(prop0, prop1).mapValueList { it.joinToString(" ") }
        assertEquals("hey yo", joinedProp.value)
        assertTrue(joinedProp is UnsynchronizedMultiMappedCachedReferenceProperty<*, *>)
    }

    /*@Test fun memoryStressTest() {
        //  -Xmx10M

        // AtomicReference,    JDK 1.8, 42 000, OOM: GC overhead limit exceeded
        // AtomicReference,    JDK   9, 56 000, OOM: Java heap space

        // AtomicFieldUpdater, JDK 1.8, 51 000, OOM: GC overhead limit exceeded
        // AtomicFieldUpdater, JDK   9, 70 000, OOM: Java heap space
        val list = ArrayList<MutableProperty<Any?>>()
        while (true) {
            repeat(1_000) { list.add(concurrentMutablePropertyOf(null)) }
            println(list.size)
        }
    }*/

}
