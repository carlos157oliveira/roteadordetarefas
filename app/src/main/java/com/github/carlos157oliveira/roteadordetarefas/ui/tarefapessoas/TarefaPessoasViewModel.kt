package com.github.carlos157oliveira.roteadordetarefas.ui.tarefapessoas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.carlos157oliveira.roteadordetarefas.data.dao.TarefaPessoasDAO
import com.github.carlos157oliveira.roteadordetarefas.data.model.TarefaPessoas
import kotlinx.coroutines.launch

class TarefaPessoasViewModel(val tarefaPessoasDAO: TarefaPessoasDAO): ViewModel() {

    private val _pessoasTarefa = MutableLiveData<TarefaPessoas>()
    public val pessoasTarefa: LiveData<TarefaPessoas> get() = this._pessoasTarefa

    fun getPessoasTarefa(id: Long) {
        viewModelScope.launch {
            _pessoasTarefa.postValue(tarefaPessoasDAO.getTarefaPessoas(id))
        }
    }
}