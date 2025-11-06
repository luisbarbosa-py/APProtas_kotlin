package com.example.roteiro.ui.responsaveis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roteiro.databinding.ItemResponsavelBinding
import com.example.roteiro.model.Responsavel

class ResponsavelAdapter(
    private val responsaveis: MutableList<Responsavel>,
    private val onEditClick: (Responsavel) -> Unit,
    private val onDeleteClick: (Responsavel) -> Unit
) : RecyclerView.Adapter<ResponsavelAdapter.ResponsavelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponsavelViewHolder {
        val binding = ItemResponsavelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResponsavelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResponsavelViewHolder, position: Int) {
        val responsavel = responsaveis[position]
        holder.bind(responsavel, onEditClick, onDeleteClick)
    }

    override fun getItemCount() = responsaveis.size

    fun updateData(newResponsaveis: List<Responsavel>) {
        responsaveis.clear()
        responsaveis.addAll(newResponsaveis)
        notifyDataSetChanged()
    }

    class ResponsavelViewHolder(private val binding: ItemResponsavelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(responsavel: Responsavel, onEditClick: (Responsavel) -> Unit, onDeleteClick: (Responsavel) -> Unit) {
            binding.textViewNomeResponsavel.text = responsavel.nome
            binding.textViewAlunoDependente.text = "Aluno: ${responsavel.aluno}"
            binding.textViewEndereco.text = "Endereço: ${responsavel.endereco}"
            binding.textViewTelefone.text = "Telefone: ${responsavel.telefone}"

            binding.buttonEditar.setOnClickListener { onEditClick(responsavel) }
            binding.buttonExcluir.setOnClickListener { onDeleteClick(responsavel) }
        }
    }
}
