package com.github.carlos157oliveira.roteadordetarefas.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.carlos157oliveira.roteadordetarefas.R
import com.github.carlos157oliveira.roteadordetarefas.data.model.Tarefa
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat

class TarefasAdapter : RecyclerView.Adapter<TarefasAdapter.TarefaViewHolder>() {

    var tarefas = listOf<Tarefa>()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }


    inner class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNomeTarefa = itemView.findViewById<MaterialTextView>(R.id.textViewNome)
        private val textViewDataReferencia = itemView.findViewById<MaterialTextView>(
            R.id.textViewDataReferencia
        )
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        fun bind(tarefa: Tarefa) {
            this.textViewNomeTarefa.text = tarefa.nome
            this.textViewDataReferencia.text = this.dateFormat.format(tarefa.dataReferencia)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        return TarefaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        )
    }

    override fun getItemCount() = this.tarefas.size

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        holder.bind(this.tarefas[position])
    }
}