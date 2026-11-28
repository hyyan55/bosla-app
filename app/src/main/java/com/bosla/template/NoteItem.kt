package com.bosla.template

data class NoteItem(
    val id: String,
    var title: String,
    var description: String,
    val category: String, // العمل, شخصي, دراسة
    val priority: Boolean = false, // urgent or normal
    val date: String,
    val isTask: Boolean = true,
    var isCompleted: Boolean = false
)
