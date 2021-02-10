package com.github.carlos157oliveira.roteadordetarefas.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.github.carlos157oliveira.roteadordetarefas.R
import com.github.carlos157oliveira.roteadordetarefas.data.model.Tarefa
import com.github.carlos157oliveira.roteadordetarefas.ui.tarefapessoas.TarefaPessoasActivity
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat


class TarefasAdapter(
        private val onPessoasBtnClicked: (Long) -> Unit,
        private val onTarefaPessoasBtnClicked: (Long) -> Unit) :
        RecyclerView.Adapter<TarefasAdapter.TarefaViewHolder>() {

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

        private val btnPessoas = itemView.findViewById<ImageButton>(R.id.btn_pessoas)
        private val btnTarefaPessoas = itemView.findViewById<ImageButton>(R.id.btn_tarefa_pessoas)

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        fun bind(tarefa: Tarefa) {
            this.textViewNomeTarefa.text = tarefa.nome
            this.textViewDataReferencia.text = this.dateFormat.format(tarefa.dataReferencia)

            btnPessoas.setOnClickListener {
                onPessoasBtnClicked(tarefa.id)
            }
            btnTarefaPessoas.setOnClickListener {
                onTarefaPessoasBtnClicked(tarefa.id)
            }
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