package com.example.roteiro.ui.alunos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemAlunoBinding
import com.example.roteiro.model.Aluno

class AlunoAdapter(
    private val alunos: MutableList<Aluno>,
    private val onEditClick: (Aluno) -> Unit,
    private val onDeleteClick: (Aluno) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val binding = ItemAlunoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlunoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = alunos[position]
        holder.bind(aluno, onEditClick, onDeleteClick)
    }

    override fun getItemCount() = alunos.size

    fun updateData(newAlunos: List<Aluno>) {
        alunos.clear()
        alunos.addAll(newAlunos)
        notifyDataSetChanged()
    }

    class AlunoViewHolder(private val binding: ItemAlunoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aluno: Aluno, onEditClick: (Aluno) -> Unit, onDeleteClick: (Aluno) -> Unit) {
            binding.textViewNomeAluno.text = aluno.nome
            binding.textViewTurno.text = "Turno: ${aluno.turno}"
            binding.textViewEscola.text = "Escola: ${aluno.escola}"
            binding.textViewResponsavel.text = "Responsável: ${aluno.nomeResponsavel} (${aluno.telefoneResponsavel})"

            binding.buttonEditar.setOnClickListener { onEditClick(aluno) }
            binding.buttonExcluir.setOnClickListener { onDeleteClick(aluno) }
        }
    }
}
