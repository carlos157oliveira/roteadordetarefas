package com.github.carlos157oliveira.roteadordetarefas.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tarefas")
data class Tarefa (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var nome: String,

    var dataReferencia: Date
)