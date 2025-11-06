package com.example.roteiro.ui.responsaveis

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
import com.example.roteiro.databinding.FragmentListarResponsaveisBinding
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch

class ListarResponsaveisFragment : Fragment() {

    private var _binding: FragmentListarResponsaveisBinding? = null
    private val binding get() = _binding!!
    private lateinit var responsavelAdapter: ResponsavelAdapter
    private lateinit var responsavelDao: com.example.roteiro.dao.ResponsavelDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListarResponsaveisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        responsavelDao = db.responsavelDao()

        setupRecyclerView()
        loadResponsaveis()
    }

    private fun setupRecyclerView() {
        responsavelAdapter = ResponsavelAdapter(
            mutableListOf(),
            onEditClick = { responsavel ->
                val action = ListarResponsaveisFragmentDirections.actionListarResponsaveisFragmentToEditResponsavelFragment(responsavel.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { responsavel ->
                showDeleteConfirmationDialog(responsavel)
            }
        )
        binding.recyclerViewResponsaveis.apply {
            adapter = responsavelAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadResponsaveis() {
        lifecycleScope.launch {
            val responsaveis = responsavelDao.getAll()
            responsavelAdapter.updateData(responsaveis)
        }
    }

    private fun showDeleteConfirmationDialog(responsavel: Responsavel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Responsável")
            .setMessage("Tem certeza que deseja excluir ${responsavel.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                deleteResponsavel(responsavel)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteResponsavel(responsavel: Responsavel) {
        lifecycleScope.launch {
            responsavelDao.delete(responsavel.id)
            loadResponsaveis()
        }
    }

    override fun onResume() {
        super.onResume()
        loadResponsaveis()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
