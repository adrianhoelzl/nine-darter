package com.adrianhoelzl.ninedarter

/**
 * Determine the head of a list.
 *
 * @return the first element of the list, `null` if none is present
 */
fun <T> List<T>.head(): T? = firstOrNull()

/**
 * Determine the tail of a list.
 *
 * @return the tail of the list, i.e., the list without its first element
 */
fun <T> List<T>.tail(): List<T> = drop(1)

/**
 * Create a list by repeating the given object [amount] times.
 *
 * @param amount the number of times to repeat the given object
 * @return a list containing the element [amount] times
 */
@OptIn(ExperimentalUnsignedTypes::class)
fun <T> T.repeat(amount: UInt): List<T> = (0u until amount).map { this }
