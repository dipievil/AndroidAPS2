package br.dipievil.androidaps2.model

data class TaskItem (
    val id : String?,
    val title: String,
    var status : Boolean = false
)