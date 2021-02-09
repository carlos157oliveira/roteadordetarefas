package com.github.carlos157oliveira.roteadordetarefas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.carlos157oliveira.roteadordetarefas.data.model.Pessoa

@Dao
interface PessoaDAO {

    @Insert
    suspend fun inserir(pessoa: Pessoa)

    @Query("DELETE FROM pessoas where id=:id")
    suspend fun deletar(id: Long)

    @Update
    suspend fun update(pessoa: Pessoa)
}