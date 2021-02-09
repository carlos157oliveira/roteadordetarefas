package com.github.carlos157oliveira.roteadordetarefas.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "pessoas")
class Pessoa(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var nome: String,
    var ordem: Int,

    @ForeignKey
        (entity = Tarefa::class,
        parentColumns = ["id"],
        childColumns = ["tarefaId"],
        onDelete = ForeignKey.CASCADE
    )
    var tarefaId: Long
)