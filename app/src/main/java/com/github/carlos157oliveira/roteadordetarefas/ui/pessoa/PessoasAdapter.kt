package com.github.carlos157oliveira.roteadordetarefas.ui.pessoa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.carlos157oliveira.roteadordetarefas.R
import com.github.carlos157oliveira.roteadordetarefas.data.model.Pessoa

class PessoasAdapter : RecyclerView.Adapter<PessoasAdapter.PessoasViewHolder>() {

    var pessoas = listOf<Pessoa>()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoasViewHolder {
        return PessoasViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pessoa, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PessoasViewHolder, position: Int) {
        holder.bind(pessoas[position])
    }

    override fun getItemCount(): Int {
        return pessoas.size
    }

    inner class PessoasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtNome = itemView.findViewById<TextView>(R.id.txt_nome)
        private val txtOrder = itemView.findViewById<TextView>(R.id.txt_order)


        fun bind(pessoa: Pessoa) {
            this.txtNome.text = pessoa.nome
            this.txtOrder.text = "${pessoa.ordem.toString()}º"
        }
    }
}
