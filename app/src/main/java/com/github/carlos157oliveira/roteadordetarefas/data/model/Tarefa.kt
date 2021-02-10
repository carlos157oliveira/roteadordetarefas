package com.github.carlos157oliveira.roteadordetarefas.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tarefas")
data class Tarefa (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var nome: String,

    var dataReferencia: Date
) {

    companion object {

        fun normalizarData(data: Calendar): Date {
            /* Nós estamos apenas interessados no dia,
            zerar os outros campos possibilita realizar a conta da ordenação depois
             */
            data.set(Calendar.HOUR_OF_DAY, 0)
            data.set(Calendar.MINUTE, 0)
            data.set(Calendar.SECOND, 0)
            data.set(Calendar.MILLISECOND, 0)
            return data.time
        }
    }

}