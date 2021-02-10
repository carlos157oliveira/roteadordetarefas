package com.github.carlos157oliveira.roteadordetarefas.ui.pessoa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.carlos157oliveira.roteadordetarefas.data.dao.PessoaDAO
import com.github.carlos157oliveira.roteadordetarefas.data.model.Pessoa
import kotlinx.coroutines.launch

class PessoasViewModel : ViewModel()  {

    private lateinit var pessoaDAO: PessoaDAO

    fun create( pPessoaDAO: PessoaDAO) {
        pessoaDAO = pPessoaDAO
    }

    private val mPessoas = MutableLiveData<List<Pessoa>>()
    val pessoas: LiveData<List<Pessoa>> get() = mPessoas

    private val mSavalPessoa = MutableLiveData<Pessoa>()
    val pessoaSalva: LiveData<Pessoa> get() = mSavalPessoa

    fun addPessoa(nomePessoa: String, pTarefaId : Long) {
        this.viewModelScope.launch {
            val pOrdem = pessoaDAO.coundPessoasByTarefaId(pTarefaId) +1
            val pessoa = Pessoa(
                nome = nomePessoa,
                tarefaId = pTarefaId,
                ordem = pOrdem
            )
            pessoa.id = pessoaDAO.inserir(pessoa)
            mSavalPessoa.postValue(pessoa)
        }
    }

    fun getPessoas(tarefaId : Long) {
        this.viewModelScope.launch {
            mPessoas.postValue(pessoaDAO.getPessoasByTarefaId(tarefaId))
        }
    }

    fun moveItem(from: Int, to: Int, tarefaId : Long) {
        this.viewModelScope.launch {
            Log.e("PessoaViewModel", "Finished: ${from} -> ${to}")
            if (from < 0) {
                return@launch;
            }

            var originalOrder = pessoaDAO.getPessoasByTarefaId(tarefaId)

            if (to < from) {
                var pessoa = originalOrder?.get(from)!!
                Log.e("PessoaViewModel", "${pessoa.nome} -> ${pessoa.ordem}")

                pessoa.ordem = to + 1
                Log.e("PessoaViewModel", "${pessoa.nome} -> ${pessoa.ordem}")

                pessoaDAO.update(pessoa)
                for (index in to .. (from - 1)) {
                    var tPessoa = originalOrder?.get(index)!!
                    tPessoa.ordem = index + 2
                    pessoaDAO.update(tPessoa)
                }

            } else if (from < to) {
                var pessoa = originalOrder?.get(from)!!
                Log.e("PessoaViewModel", "${pessoa.nome} -> ${pessoa.ordem}")

                pessoa.ordem = to + 1
                Log.e("PessoaViewModel", "${pessoa.nome} -> ${pessoa.ordem}")

                pessoaDAO.update(pessoa)
                for (index in from +1 until to+1) {
                    var tPessoa = originalOrder?.get(index)!!
                    tPessoa.ordem = index
                    Log.e("PessoaViewModel", "${tPessoa.nome} -> ${tPessoa.ordem}")
                    pessoaDAO.update(tPessoa)
                }

            }

            mPessoas.postValue(pessoaDAO.getPessoasByTarefaId(tarefaId))
        }

    }

    fun remove(pessoa: Pessoa, tarefaId : Long) {
        this.viewModelScope.launch {
            var originalOrder = pessoaDAO.getPessoasByTarefaId(tarefaId)

            for (index in pessoa.ordem .. originalOrder!!.size-1) {
                originalOrder?.get(index)?.let {
                    it.ordem = index
                    Log.e("PessoaViewModel", "${it.nome} -> ${it.ordem}")
                    pessoaDAO.update(it)
                }
            }
            pessoaDAO.deletar(pessoa.id)

            mPessoas.postValue(pessoaDAO.getPessoasByTarefaId(tarefaId))

        }

    }
}