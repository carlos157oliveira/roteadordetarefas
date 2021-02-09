package com.github.carlos157oliveira.roteadordetarefas.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.carlos157oliveira.roteadordetarefas.R
import com.github.carlos157oliveira.roteadordetarefas.data.dao.TarefaDAO
import com.github.carlos157oliveira.roteadordetarefas.data.database.AppDatabase
import com.github.carlos157oliveira.roteadordetarefas.data.model.Tarefa
import com.github.carlos157oliveira.roteadordetarefas.ui.viewmodelfactory.ViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {

    private val editNomeTarefa by lazy { findViewById<TextInputEditText>(R.id.editNomeTarefa) }
    private val buttonAdicionar by lazy { findViewById<MaterialButton>(R.id.buttonAdicionar) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val tarefaAdapter by lazy { TarefasAdapter() }

    private lateinit var viewModel: TarefaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
                viewModelStore, ViewModelFactory(
                    TarefaViewModel::class.java,
                    AppDatabase.getInstance(this).tarefaDAO)
        ).get(TarefaViewModel::class.java)

        this.viewModel.tarefaSalva.observe(this, Observer<Tarefa> { tarefa: Tarefa ->
            this.tarefaAdapter.tarefas += tarefa
        })
        this.viewModel.tarefas.observe(this, Observer<List<Tarefa>> { tarefas ->
            this.tarefaAdapter.tarefas = tarefas
        })
        this.viewModel.getTarefas()

        this.recyclerView.adapter = this.tarefaAdapter

        this.buttonAdicionar.setOnClickListener{
            this.viewModel.addTarefa(this.editNomeTarefa.text.toString(), Date())
        }
    }
}