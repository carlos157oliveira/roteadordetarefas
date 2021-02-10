package com.github.carlos157oliveira.roteadordetarefas.ui.pessoa

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.github.carlos157oliveira.roteadordetarefas.R
import com.github.carlos157oliveira.roteadordetarefas.data.database.AppDatabase
import com.github.carlos157oliveira.roteadordetarefas.data.model.Pessoa


class PessoaActivity : AppCompatActivity() {

    companion object {
        const val PESSOA_ACTIVITY_EXTRA = "PessoaAcitivity_INTENT_BUNDLE_EXTRA"
        const val TAREFA_ID: String = "PessoaAcitivity_TAREFA_ID"
    }

    private val txtNomePessoa by lazy {findViewById<EditText>(R.id.txt_pessoa_nome)}
    private val btnAddPessoa by lazy { findViewById<ImageButton>(R.id.btn_add_pessoa) }
    private val rcyPessoas by lazy { findViewById<RecyclerView>(R.id.rcy_pessoas) }
    private val pessoasAdapter by lazy { PessoasAdapter() }

    private var tarefaId : Long = 1L
    private lateinit var viewModel: PessoasViewModel

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(START or END or UP or DOWN, LEFT or RIGHT) {

            var beginPos = -1
            var endPos = 0
            var isMoving = false

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val adapter = recyclerView.adapter as PessoasAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                // 2. Update the backing model. Custom implementation in
                //    MainRecyclerViewAdapter. You need to implement
                //    reordering of the backing model inside the method.
                //viewModel.moveItem(from, to, tarefaId)
                // 3. Tell adapter to render the model update.
                adapter.notifyItemMoved(from, to)
                Log.e("PessoaActivity", "Drang and Drop: ${from} -> ${to}")

                if (beginPos < 0) {
                    beginPos = from
                }
                endPos = to

                //return true
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                // 4. Code block for horizontal swipe.
                //    ItemTouchHelper handles horizontal swipe as well, but
                //    it is not relevant with reordering. Ignoring here.
                val position = viewHolder.layoutPosition
                val pessoa = pessoasAdapter.pessoas[position]
                Log.e("PessoaActivity", "Swiped: $position -> ${pessoa.nome}")
                viewModel.remove(pessoa, tarefaId)
            }

            // 1. This callback is called when a ViewHolder is selected.
            //    We highlight the ViewHolder here.
            override fun onSelectedChanged(
                viewHolder: RecyclerView.ViewHolder?,
                actionState: Int
            ) {
                super.onSelectedChanged(viewHolder, actionState)

                if (actionState == ACTION_STATE_DRAG) {
                    isMoving = true
                    viewHolder?.itemView?.alpha = 0.5f
                } else if (actionState == ACTION_STATE_IDLE && isMoving){
                    isMoving = false;
                    viewModel.moveItem(beginPos, endPos, tarefaId)
                    beginPos = -1

                }
            }
            // 2. This callback is called when the ViewHolder is
            //    unselected (dropped). We unhighlight the ViewHolder here.
            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder?.itemView?.alpha = 1.0f
            }
        }

        ItemTouchHelper(simpleItemTouchCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoa)

       /*intent.getBundleExtra(PESSOA_ACTIVITY_EXTRA)?.let {
            it.getLong(TAREFA_ID)?.let {
                tarefaId = it
                Log.e("caio.v", "tarefaId = $tarefaId")
            }
        }*/

        viewModel = ViewModelProvider(this).get(PessoasViewModel::class.java).also {
            it.create(AppDatabase.getInstance(this).pessoaDAO)
        }

        viewModel.pessoaSalva.observe(this, Observer<Pessoa> { pessoa: Pessoa ->
            this.pessoasAdapter.pessoas += pessoa
        })

        viewModel.pessoas.observe(this, Observer<List<Pessoa>> { pessoas ->
            this.pessoasAdapter.pessoas = pessoas
        })

        viewModel.getPessoas(tarefaId)

        rcyPessoas.adapter = pessoasAdapter
        itemTouchHelper.attachToRecyclerView(rcyPessoas)

        btnAddPessoa.setOnClickListener{
            viewModel.addPessoa(txtNomePessoa.text.toString(), tarefaId)
            txtNomePessoa.setText("")
        }

    }

}