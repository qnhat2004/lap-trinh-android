package com.example.mysimplenote.data

data class Note(
    var id: String? = null,
    var title: String? = null,
    var content: String? = null
) {
    constructor() : this(null)
}
