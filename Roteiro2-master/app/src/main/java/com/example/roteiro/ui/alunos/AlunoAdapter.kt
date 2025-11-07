package com.example.roteiro.ui.alunos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemAlunoBinding
import com.example.roteiro.model.Aluno

class AlunoAdapter(
    private var alunos: List<Aluno>,
    private val onEditClick: (Aluno) -> Unit,
    private val onDeleteClick: (Aluno) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val binding = ItemAlunoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlunoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = alunos[position]
        holder.bind(aluno)
    }

    override fun getItemCount() = alunos.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlunos(novosAlunos: List<Aluno>) {
        alunos = novosAlunos
        notifyDataSetChanged() // Notifica a RecyclerView que os dados mudaram
    }

    inner class AlunoViewHolder(private val binding: ItemAlunoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aluno: Aluno) {
            binding.textViewNomeAluno.text = aluno.nome
            binding.textViewAlunoId.text = "#${aluno.id}"
            binding.textViewEscola.text = aluno.escola
            binding.textViewTurno.text = "Turno: ${aluno.turno}"
            binding.textViewResponsavel.text = "Responsável: ${aluno.nomeResponsavel}"

            binding.buttonEditar.setOnClickListener {
                onEditClick(aluno)
            }

            binding.buttonExcluir.setOnClickListener {
                onDeleteClick(aluno)
            }
        }
    }
}
