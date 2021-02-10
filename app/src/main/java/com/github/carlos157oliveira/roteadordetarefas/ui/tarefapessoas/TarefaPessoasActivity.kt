package com.github.carlos157oliveira.roteadordetarefas.ui.tarefapessoas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.carlos157oliveira.roteadordetarefas.R
import com.github.carlos157oliveira.roteadordetarefas.data.database.AppDatabase
import com.github.carlos157oliveira.roteadordetarefas.ui.viewmodelfactory.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class TarefaPessoasActivity : AppCompatActivity() {

    companion object {
        const val TAREFA_PESSOAS_ACTIVITY_EXTRA = "TAREFA_PESSOAS_ACTIVITY_EXTRA"
        const val TAREFA_ID = "TAREFA_ID"
    }

    private val listView by lazy { findViewById<ListView>(R.id.listView) }
    private lateinit var viewModel: TarefaPessoasViewModel

    private val dateFormat = SimpleDateFormat("dd MMM YYYY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefa_pessoas)

        val tarefaId = this.intent.getBundleExtra(TAREFA_PESSOAS_ACTIVITY_EXTRA)?.getLong(TAREFA_ID)!!

        this.viewModel = ViewModelProvider(viewModelStore, ViewModelFactory(
            TarefaPessoasViewModel::class.java,
            AppDatabase.getInstance(this).tarefaPessoasDAO)
        ).get(TarefaPessoasViewModel::class.java)

        this.viewModel.getPessoasTarefa(tarefaId)
        this.viewModel.pessoasTarefa.observe(this, Observer { tarefaPessoas ->

            val pessoas = tarefaPessoas.pessoas
                    .sortedBy { pessoa -> pessoa.ordem }

            if(pessoas.isEmpty()) return@Observer

            val list = mutableListOf<String>()
            val calendarAtual = Calendar.getInstance()
            val diffInMilis =  calendarAtual.timeInMillis - tarefaPessoas.tarefa.dataReferencia.time
            val diffInDays: Long = diffInMilis / (24 * 60 * 60 * 1000)

            var start: Int = diffInDays.toInt() % pessoas.size
            for(idx in start until start + pessoas.size * 3) {
                val p = pessoas[idx % pessoas.size]
                val data = this.dateFormat.format(calendarAtual.time)
                val texto = "%-40s %11s".format(p.nome, data)
                list.add(texto)
                calendarAtual.add(Calendar.DAY_OF_MONTH, 1)
            }
            this.listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list)
        })
    }
}