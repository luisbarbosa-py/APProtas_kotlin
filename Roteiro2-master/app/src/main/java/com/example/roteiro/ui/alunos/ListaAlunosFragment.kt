package com.example.roteiro.ui.alunos

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roteiro.AppDatabase
import com.example.roteiro.R
import com.example.roteiro.databinding.FragmentListaAlunosBinding
import com.example.roteiro.model.Aluno
import kotlinx.coroutines.launch

class ListaAlunosFragment : Fragment() {

    private var _binding: FragmentListaAlunosBinding? = null
    private val binding get() = _binding!!
    private lateinit var alunoAdapter: AlunoAdapter
    private lateinit var alunoDao: com.example.roteiro.dao.AlunoDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        alunoDao = db.alunoDao()

        setupRecyclerView()
        loadAlunos()
    }

    private fun setupRecyclerView() {
        alunoAdapter = AlunoAdapter(
            mutableListOf(),
            onEditClick = { aluno ->
                val action = ListaAlunosFragmentDirections.actionListaAlunosFragmentToEditAlunoFragment(aluno.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { aluno ->
                showDeleteConfirmationDialog(aluno)
            }
        )
        binding.recyclerViewAlunos.apply {
            adapter = alunoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadAlunos() {
        lifecycleScope.launch {
            val alunos = alunoDao.getAll()
            alunoAdapter.updateData(alunos)
        }
    }

    private fun showDeleteConfirmationDialog(aluno: Aluno) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Aluno")
            .setMessage("Tem certeza que deseja excluir ${aluno.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                deleteAluno(aluno)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteAluno(aluno: Aluno) {
        lifecycleScope.launch {
            alunoDao.delete(aluno.id)
            loadAlunos()
        }
    }

    override fun onResume() {
        super.onResume()
        loadAlunos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
