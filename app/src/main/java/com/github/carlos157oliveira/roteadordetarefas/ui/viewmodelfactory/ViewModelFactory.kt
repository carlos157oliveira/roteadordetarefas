package com.github.carlos157oliveira.roteadordetarefas.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelFactory<K, Q: Any>(private val viewModelType: Class<K>, private val dataSource: Q) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelType)) {
            val dataSourceType = dataSource::class.java.interfaces[0]
            return modelClass.getConstructor(dataSourceType)
                .newInstance(dataSource)
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}