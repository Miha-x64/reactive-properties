package net.aquadc.properties.function

import net.aquadc.properties.ChangeListener
import net.aquadc.properties.executor.PlatformExecutors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Contains several general-purpose functions to pass into [net.aquadc.properties.map],
 * [net.aquadc.properties.mapWith], [net.aquadc.properties.distinct] etc.
 *
 * Name 'Objectz' was chosen to avoid conflicting with [java.util.Objects].
 */
class Objectz private constructor(
        private val mode: Int
): (Any?, Any?) -> Any {

    override fun invoke(p1: Any?, p2: Any?): Any = when (mode) {
        1 -> p1 == p2
        2 -> p1 != p2
        3 -> p1 === p2
        4 -> p1 !== p2
        else -> throw AssertionError()
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        /**
         * A function which compares the given arguments using [Object.equals] operator function.
         */
        @JvmField val Equal: (Any?, Any?) -> Boolean =
                Objectz(1) as (Any?, Any?) -> Boolean

        /**
         * A function which compares the given arguments using inverted return value of [Object.equals] operator function.
         */
        @JvmField val NotEqual: (Any?, Any?) -> Boolean =
                Objectz(2) as (Any?, Any?) -> Boolean

        /**
         * A function which compares the given arguments by identity.
         */
        @JvmField val Same: (Any?, Any?) -> Boolean =
                Objectz(3) as (Any?, Any?) -> Boolean

        /**
         * A function which compares given arguments by identity and inverts this value.
         */
        @JvmField val NotSame: (Any?, Any?) -> Boolean =
                Objectz(4) as (Any?, Any?) -> Boolean

        /**
         * A function which checks whether its argument is equal to `null`,
         * i. e. it's a partially-applied version of [isSameAs].
         */
        @JvmField val IsNull: (Any?) -> Boolean =
                `AppliedFunc1-`(null, 4) as (Any?) -> Boolean

        /**
         * A function which checks whether its argument is not equal to `null`,
         * i. e. it's a partially-applied version of [notSameAs].
         */
        @JvmField val IsNotNull: (Any?) -> Boolean =
                `AppliedFunc1-`(null, 6) as (Any?) -> Boolean

        /**
         * A function which ignores its argument and always returns true.
         */
        @JvmField val Any: (Any?) -> Boolean =
                `AppliedFunc1-`(true, 7) as (Any?) -> Boolean

        /**
         * A function which ignores its argument and always returns false.
         */
        @JvmField val None: (Any?) -> Boolean =
                `AppliedFunc1-`(false, 7) as (Any?) -> Boolean

    }

}

/**
 * Contains several general-purpose array-related functions to pass into [net.aquadc.properties.map],
 * [net.aquadc.properties.mapWith] etc.
 *
 * Name 'Arrayz' was chosen to avoid conflicting with [java.util.Arrays].
 */
class Arrayz private constructor(
        private val mode: Int
): (Any, Any) -> Any {

    override fun invoke(p1: Any, p2: Any): Any = when (mode) {
        0 -> (p1 as BooleanArray).contentEquals(p2 as BooleanArray)
        1 -> (p1 as ByteArray).contentEquals(p2 as ByteArray)
        2 -> (p1 as ShortArray).contentEquals(p2 as ShortArray)
        3 -> (p1 as CharArray).contentEquals(p2 as CharArray)
        4 -> (p1 as IntArray).contentEquals(p2 as IntArray)
        5 -> (p1 as LongArray).contentEquals(p2 as LongArray)
        6 -> (p1 as FloatArray).contentEquals(p2 as FloatArray)
        7 -> (p1 as DoubleArray).contentEquals(p2 as DoubleArray)
        8 -> (p1 as Array<out Any?>).contentEquals(p2 as Array<out Any?>)
        9 -> (p1 as Array<out Any?>).contentDeepEquals(p2 as Array<out Any?>)
        else -> throw AssertionError()
    }

    @Suppress("UNCHECKED_CAST") companion object {

        /**
         * A function which [Boolean] arrays with [java.util.Arrays.equals].
         */
        @JvmField val BooleansEqual: (BooleanArray, BooleanArray) -> Boolean =
                Arrayz(0) as (BooleanArray, BooleanArray) -> Boolean

        /**
         * A function which [Byte] arrays with [java.util.Arrays.equals].
         */
        @JvmField val BytesEqual: (ByteArray, ByteArray) -> Boolean =
                Arrayz(1) as (ByteArray, ByteArray) -> Boolean

        /**
         * A function which [Short] arrays with [java.util.Arrays.equals].
         */
        @JvmField val ShortsEqual: (ShortArray, ShortArray) -> Boolean =
                Arrayz(2) as (ShortArray, ShortArray) -> Boolean

        /**
         * A function which [Char] arrays with [java.util.Arrays.equals].
         */
        @JvmField val CharsEqual: (CharArray, CharArray) -> Boolean =
                Arrayz(3) as (CharArray, CharArray) -> Boolean

        /**
         * A function which [Int] arrays with [java.util.Arrays.equals].
         */
        @JvmField val IntsEqual: (IntArray, IntArray) -> Boolean =
                Arrayz(4) as (IntArray, IntArray) -> Boolean

        /**
         * A function which [Long] arrays with [java.util.Arrays.equals].
         */
        @JvmField val LongsEqual: (LongArray, LongArray) -> Boolean =
                Arrayz(5) as (LongArray, LongArray) -> Boolean

        /**
         * A function which [Float] arrays with [java.util.Arrays.equals].
         */
        @JvmField val FloatsEqual: (FloatArray, FloatArray) -> Boolean =
                Arrayz(6) as (FloatArray, FloatArray) -> Boolean

        /**
         * A function which [Double] arrays with [java.util.Arrays.equals].
         */
        @JvmField val DoublesEqual: (DoubleArray, DoubleArray) -> Boolean =
                Arrayz(7) as (DoubleArray, DoubleArray) -> Boolean

        /**
         * A function which reference arrays with [java.util.Arrays.equals].
         */
        @JvmField val Equal: (Array<out Any?>, Array<out Any?>) -> Boolean =
                Arrayz(8) as (Array<out Any?>, Array<out Any?>) -> Boolean

        /**
         * A function which reference arrays with [java.util.Arrays.deepEquals].
         */
        @JvmField val DeeplyEqual: (Array<out Any?>, Array<out Any?>) -> Boolean =
                Arrayz(9) as (Array<out Any?>, Array<out Any?>) -> Boolean

    }

}

/**
 * Contains several general-purpose [CharSequence]-related functions to pass into [net.aquadc.properties.map],
 * [net.aquadc.properties.mapWith] etc.
 *
 * The name 'CharSequencez' was chosen to be consistent with [Objectz] and [Arrayz]
 * and avoid potential conflict with an arbitrary utility-class.
 */
class CharSequencez private constructor(private val mode: Int) : (Any?) -> Any? {

    override fun invoke(p1: Any?): Any? = when (mode) {
        0 -> (p1 as CharSequence).isEmpty()
        1 -> (p1 as CharSequence).isNotEmpty()
        2 -> (p1 as CharSequence).isBlank()
        3 -> (p1 as CharSequence).isNotBlank()
        4 -> (p1 as CharSequence).length
        5 -> (p1 as CharSequence).trim()
        6 -> p1!!.toString()
        7 -> p1?.toString()
        8 -> if (p1 === null) "" else p1.toString()
        else -> throw AssertionError()
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        /**
         * A function which returns the result of invocation [CharSequence.isEmpty] on its argument.
         */
        @JvmField val Empty: (CharSequence) -> Boolean =
                CharSequencez(0) as (CharSequence) -> Boolean

        /**
         * A function which returns the result of invocation [CharSequence.isNotEmpty] on its argument.
         */
        @JvmField val NotEmpty: (CharSequence) -> Boolean =
                CharSequencez(1) as (CharSequence) -> Boolean

        /**
         * A function which returns the result of invocation [CharSequence.isBlank] on its argument.
         */
        @JvmField val Blank: (CharSequence) -> Boolean =
                CharSequencez(2) as (CharSequence) -> Boolean

        /**
         * A function which returns the result of invocation [CharSequence.isNotBlank] on its argument.
         */
        @JvmField val NotBlank: (CharSequence) -> Boolean =
                CharSequencez(3) as (CharSequence) -> Boolean

        /**
         * A function which returns the result of invocation [CharSequence.length] on its argument.
         */
        @JvmField val Length: (CharSequence) -> Int =
                CharSequencez(4) as (CharSequence) -> Int

        /**
         * A function which returns the result of invocation [CharSequence.trim] on its argument.
         */
        @JvmField val Trim: (CharSequence) -> CharSequence =
                CharSequencez(5) as (CharSequence) -> CharSequence

        /**
         * A function which returns the result of invocation [toString] on its argument.
         */
        @JvmField val ValueOf: (Any) -> String =
                CharSequencez(6) as (Any) -> String

        /**
         * A function which returns the result of invocation [toString] on its argument,
         * or `null`, if argument is `null`.
         */
        @JvmField val ValueOfOrNull: (Any?) -> String? =
                CharSequencez(7) as (Any?) -> String?

        /**
         * A function which returns the result of invocation [toString] on its argument,
         * or an empty string, if argument is `null`.
         */
        @JvmField val ValueOfOrBlank: (Any?) -> String =
                CharSequencez(8) as (Any?) -> String

    }
}

/**
 * Contains several general-purpose [Enum]-related functions to pass into [net.aquadc.properties.map],
 * [net.aquadc.properties.mapWith], [net.aquadc.persistence.type.enum] etc.
 *
 * The name 'Enumz' was chosen to be consistent with [Objectz] and [Arrayz]
 * and avoid potential conflict with an arbitrary utility-class.
 */
class Enumz private constructor(private val mode: Int) : (Any?) -> Any? {

    override fun invoke(p1: Any?): Any? {
        p1 as Enum<*>
        return when (mode) {
            0 -> p1.name
            1 -> p1.ordinal
            else -> throw AssertionError()
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        /**
         * A function which returns [Enum.name] of a passed [Enum] instance.
         */
        @JvmField val Name: (Enum<*>) -> String =
                Enumz(0) as (Enum<*>) -> String

        /**
         * A function which returns [Enum.ordinal] of a passed [Enum] instance.
         */
        @JvmField val Ordinal: (Enum<*>) -> Int =
                Enumz(1) as (Enum<*>) -> Int

    }

}

//
// Boolean
//

// I can't use erased types here,
// because checkcast to (Boolean, Boolean) -> Boolean
// would fail: https://youtrack.jetbrains.com/issue/KT-24067
@PublishedApi internal class `BoolFunc-`(
        private val mode: Int
) :
        /* not: */ (Boolean) -> Boolean,
 /* and|or|xor: */ (Boolean, Boolean) -> Boolean {

    // When used as unary function, acts like 'not'.
    override fun invoke(p1: Boolean): Boolean = !p1

    override fun invoke(p1: Boolean, p2: Boolean): Boolean = when (mode) {
        1 -> p1 && p2
        2 -> p1 || p2
        3 -> p1 xor p2
        else -> throw AssertionError()
    }

    @Suppress("UNCHECKED_CAST")
    @PublishedApi
    internal companion object {
        @JvmField val And = `BoolFunc-`(1)
        @JvmField val Or = `BoolFunc-`(2)
        @JvmField val Xor = `BoolFunc-`(3)
    }
}

/**
 * Contains several general-purpose [Collection]-related functions to pass into [net.aquadc.properties.map],
 * [net.aquadc.properties.mapWith] etc.
 *
 * Name 'Collectionz' was chosen to avoid conflicting with [java.util.Collections].
 */
class Collectionz private constructor(
        private val mode: Int
): (Any?) -> Any {

    override fun invoke(p1: Any?): Any = when (mode) {
        1 -> (p1 as Collection<*>?)?.isEmpty() ?: true
        2 -> (p1 as Collection<*>?)?.isNotEmpty() ?: false
        3 -> (p1 as Collection<*>?)?.size ?: 0
        else -> throw AssertionError()
    }

    @Suppress("UNCHECKED_CAST")
    companion object {

        /**
         * A function which returns the result of invocation [Collection.isEmpty] on its argument.
         */
        @JvmField val Empty: (Collection<*>?) -> Boolean =
                Collectionz(1) as (Collection<*>?) -> Boolean

        /**
         * A function which returns the result of invocation [Collection.isNotEmpty] on its argument.
         */
        @JvmField val NotEmpty: (Collection<*>?) -> Boolean =
                Collectionz(2) as (Collection<*>?) -> Boolean

        /**
         * A function which returns the result of invocation [Collection.size] on its argument.
         */
        @JvmField val Size: (Collection<*>?) -> Int =
                Collectionz(3) as (Collection<*>?) -> Int

    }

}


//
// contains
//

@Suppress("UNCHECKED_CAST") @PublishedApi
internal class `AppliedFunc1-`(
        private val value: Any?,
        private val mode: Int
) : (Any?) -> Any? {

    override fun invoke(p1: Any?): Any? = when (mode) {
        1 -> (p1 as Collection<*>).containsAll(value as Collection<*>)
        2 -> (p1 as Collection<*>).contains(value)
        3 -> p1 == value
        4 -> p1 === value
        5 -> p1 != value
        6 -> p1 !== value
        7 -> value
        else -> throw AssertionError()
    }

}

//
// common
//

@PublishedApi
internal object Identity : (Any?) -> Any? {
    override fun invoke(p1: Any?): Any? = p1
}

@PublishedApi
internal object ToPair : (Any?, Any?) -> Any? {

    // will be merged with any other class after Kotlin bug fix

    override fun invoke(p1: Any?, p2: Any?): Any? =
            Pair(p1, p2)
}

@PublishedApi
internal abstract class OnEach<T> : AtomicBoolean(), ChangeListener<T>, (T) -> Unit {

    override fun invoke(old: T, new: T) {
        set(true)
        invoke(new)
    }

}

@PublishedApi internal class Schedule(
        private val period: Long,
        private val unit: TimeUnit
) : (ScheduledExecutorService, Any?, Runnable) -> ScheduledFuture<*> {
    override fun invoke(p1: ScheduledExecutorService, p2: Any?, p3: Runnable): ScheduledFuture<*> {
        val exec = PlatformExecutors.requireCurrent()
        return p1.scheduleAtFixedRate({ exec.execute(p3) }, 0, period, unit)
    }
}

@PublishedApi internal class Arithmetic(
        private val mode: Int
) : (Any?, Any?) -> Any? {

    override fun invoke(p1: Any?, p2: Any?): Any? = when (mode) {
        1 -> plus(p1, p2)
        2 -> minus(p1, p2)
        3 -> times(p1, p2)
        4 -> div(p1, p2)
        else -> throw AssertionError()
    }

    private fun plus(p1: Any?, p2: Any?): Any? = when (p1) {
        is Int -> p1 + (p2 as Int)
        is Long -> p1 + (p2 as Long)
        is Float -> p1 + (p2 as Float)
        is Double -> p1 + (p2 as Double)
        else -> throw AssertionError()
    }

    private fun minus(p1: Any?, p2: Any?): Any? = when (p1) {
        is Int -> p1 - (p2 as Int)
        is Long -> p1 - (p2 as Long)
        is Float -> p1 - (p2 as Float)
        is Double -> p1 - (p2 as Double)
        else -> throw AssertionError()
    }

    private fun times(p1: Any?, p2: Any?): Any? = when (p1) {
        is Int -> p1 * (p2 as Int)
        is Long -> p1 * (p2 as Long)
        is Float -> p1 * (p2 as Float)
        is Double -> p1 * (p2 as Double)
        else -> throw AssertionError()
    }

    private fun div(p1: Any?, p2: Any?): Any? = when (p1) {
        is Int -> p1 / (p2 as Int)
        is Long -> p1 / (p2 as Long)
        is Float -> p1 / (p2 as Float)
        is Double -> p1 / (p2 as Double)
        else -> throw AssertionError()
    }

    companion object {
        @JvmField val Plus = Arithmetic(1)
        @JvmField val Minus = Arithmetic(2)
        @JvmField val Times = Arithmetic(3)
        @JvmField val Div = Arithmetic(4)
    }

}
