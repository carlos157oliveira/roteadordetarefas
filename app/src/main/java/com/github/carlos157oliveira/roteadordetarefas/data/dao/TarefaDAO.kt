package com.github.carlos157oliveira.roteadordetarefas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.carlos157oliveira.roteadordetarefas.data.model.Tarefa

@Dao
interface TarefaDAO {

    @Insert
    suspend fun inserir(tarefa: Tarefa): Long

    @Query("DELETE FROM tarefas where id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM tarefas")
    suspend fun getTarefas(): List<Tarefa>

}