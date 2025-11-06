package com.example.roteiro.ui.responsaveis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roteiro.AppDatabase
import com.example.roteiro.databinding.FragmentEditResponsavelBinding
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch

class EditResponsavelFragment : Fragment() {

    private var _binding: FragmentEditResponsavelBinding? = null
    private val binding get() = _binding!!
    private val args: EditResponsavelFragmentArgs by navArgs()
    private lateinit var responsavelDao: com.example.roteiro.dao.ResponsavelDao
    private var responsavel: Responsavel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditResponsavelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        responsavelDao = db.responsavelDao()

        loadResponsavel()

        binding.buttonAtualizar.setOnClickListener {
            updateResponsavel()
        }
    }

    private fun loadResponsavel() {
        lifecycleScope.launch {
            responsavel = responsavelDao.getById(args.responsavelId)
            responsavel?.let {
                binding.editTextNomeResponsavel.setText(it.nome)
                binding.editTextAlunoDependente.setText(it.aluno)
                binding.editTextEndereco.setText(it.endereco)
                binding.editTextTelefone.setText(it.telefone)
            }
        }
    }

    private fun updateResponsavel() {
        val nome = binding.editTextNomeResponsavel.text.toString()
        val aluno = binding.editTextAlunoDependente.text.toString()
        val endereco = binding.editTextEndereco.text.toString()
        val telefone = binding.editTextTelefone.text.toString()

        if (nome.isNotEmpty() && aluno.isNotEmpty() && endereco.isNotEmpty() && telefone.isNotEmpty()) {
            val updatedResponsavel = responsavel!!.copy(
                nome = nome,
                aluno = aluno,
                endereco = endereco,
                telefone = telefone
            )

            lifecycleScope.launch {
                responsavelDao.update(updatedResponsavel)
                Toast.makeText(requireContext(), "Responsável atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        } else {
            Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
