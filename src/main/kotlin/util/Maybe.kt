package org.cuttlefish.util

public sealed class Maybe<out T> {
    public data class Some<T>(val value: T) : Maybe<T>()
    public object None : Maybe<Nothing>()
}