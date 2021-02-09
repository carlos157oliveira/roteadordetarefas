package com.github.carlos157oliveira.roteadordetarefas.data.model

import androidx.room.Embedded
import androidx.room.Relation

class TarefaPessoas(

    @Embedded
    val tarefa: Tarefa,

    @Relation(parentColumn = "id", entityColumn = "tarefaId", entity = Pessoa::class)
    var pessoas: List<Pessoa>
)