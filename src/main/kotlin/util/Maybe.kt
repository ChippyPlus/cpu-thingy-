package org.cuttlefish.util

sealed class Maybe<out T> {
    data class Some<T>(val value: T) : Maybe<T>()
    object Not : Maybe<Nothing>()
}