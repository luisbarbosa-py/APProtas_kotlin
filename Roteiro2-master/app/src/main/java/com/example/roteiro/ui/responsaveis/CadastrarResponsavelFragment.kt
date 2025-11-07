package com.example.roteiro.ui.responsaveis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.databinding.FragmentCadastrarResponsavelBinding
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch

class CadastrarResponsavelFragment : Fragment() {

    private var _binding: FragmentCadastrarResponsavelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastrarResponsavelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())
        val responsavelDao = db.responsavelDao()

        binding.buttonSalvar.setOnClickListener {
            val nome = binding.editTextNomeResponsavel.text.toString()
            val aluno = binding.editTextAlunoDependente.text.toString()
            val endereco = binding.editTextEndereco.text.toString()
            val telefone = binding.editTextTelefone.text.toString()

            if (nome.isNotEmpty() && aluno.isNotEmpty() && endereco.isNotEmpty() && telefone.isNotEmpty()) {
                val responsavel = Responsavel(
                    nome = nome,
                    aluno = aluno, // Corrigido de alunoDependente para aluno
                    endereco = endereco,
                    telefone = telefone
                )

                lifecycleScope.launch {
                    responsavelDao.insert(responsavel)
                    Toast.makeText(requireContext(), "Responsável salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        binding.editTextNomeResponsavel.text?.clear()
        binding.editTextAlunoDependente.text?.clear()
        binding.editTextEndereco.text?.clear()
        binding.editTextTelefone.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
