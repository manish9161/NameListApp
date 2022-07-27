package com.demo.namelistapp.core

interface RecyclerViewItemClick<T> {

    fun itemRemove(item: T, position: Int)
}