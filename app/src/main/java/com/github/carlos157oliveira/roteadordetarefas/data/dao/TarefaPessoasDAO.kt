package com.github.carlos157oliveira.roteadordetarefas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.carlos157oliveira.roteadordetarefas.data.model.TarefaPessoas

@Dao
interface TarefaPessoasDAO {

    @Query("SELECT * FROM tarefas WHERE id=:id")
    suspend fun getTarefaPessoas(id: Long): TarefaPessoas
}