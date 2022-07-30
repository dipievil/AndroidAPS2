package br.dipievil.androidaps2.data.model

data class TaskItem (
    var id : String?,
    val title: String,
    var status : Boolean = false
)