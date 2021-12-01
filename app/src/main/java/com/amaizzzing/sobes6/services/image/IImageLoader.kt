package com.amaizzzing.sobes6.services.image

interface IImageLoader<T> {
    fun loadInto(source: Int, container: T)
}