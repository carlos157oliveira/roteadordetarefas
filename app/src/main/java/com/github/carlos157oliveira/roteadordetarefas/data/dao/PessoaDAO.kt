package com.github.carlos157oliveira.roteadordetarefas.data.dao

import androidx.room.*
import com.github.carlos157oliveira.roteadordetarefas.data.model.Pessoa

@Dao
interface PessoaDAO {

    @Insert
    suspend fun inserir(pessoa: Pessoa) : Long

    @Query("DELETE FROM pessoas where id=:id")
    suspend fun deletar(id: Long)

    @Update
    suspend fun update(pessoa: Pessoa)

    @Query("SELECT * FROM pessoas where tarefaId = :tarefaId ORDER BY ordem")
    suspend fun getPessoasByTarefaId(tarefaId: Long): List<Pessoa>?

    @Query("SELECT count(id) FROM pessoas where tarefaId = :tarefaId")
    suspend fun coundPessoasByTarefaId(tarefaId: Long) : Int

}