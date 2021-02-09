package com.github.carlos157oliveira.roteadordetarefas.ui.main

import androidx.lifecycle.*
import com.github.carlos157oliveira.roteadordetarefas.data.dao.TarefaDAO
import com.github.carlos157oliveira.roteadordetarefas.data.model.Tarefa
import kotlinx.coroutines.launch
import java.util.*

class TarefaViewModel(private val tarefaDAO: TarefaDAO) : ViewModel() {

    private val _tarefas = MutableLiveData<List<Tarefa>>()
    val tarefas: LiveData<List<Tarefa>> get() = _tarefas

    private val _tarefaSalva = MutableLiveData<Tarefa>()
    val tarefaSalva: LiveData<Tarefa> get() = _tarefaSalva

    fun addTarefa(nomeTarefa: String, dataReferencia: Date) {
        this.viewModelScope.launch {
            val tarefa = Tarefa(
                nome = nomeTarefa,
                dataReferencia = dataReferencia
            )
            tarefa.id = tarefaDAO.insert(tarefa)
            _tarefaSalva.postValue(tarefa)
        }
    }

    fun getTarefas() {
        this.viewModelScope.launch {
            _tarefas.postValue(tarefaDAO.getTarefas())
        }
    }

    class ViewModelFactory<K, L, Q: Any>(private val viewModelType: Class<K>, private val dataSourceType: Class<L>, private val dataSource: Q) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(viewModelType)) {
                return modelClass.getConstructor(dataSourceType)
                    .newInstance(dataSource)
            }
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }


}