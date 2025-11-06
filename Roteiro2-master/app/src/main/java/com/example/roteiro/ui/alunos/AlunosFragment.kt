package com.example.roteiro.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.roteiro.AppDatabase
import com.example.roteiro.databinding.FragmentAlunosBinding
import com.example.roteiro.model.Aluno
import kotlinx.coroutines.launch

class AlunosFragment : Fragment() {

    private var _binding: FragmentAlunosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        val alunoDao = db.alunoDao()

        binding.buttonSalvar.setOnClickListener {
            val nome = binding.editTextNomeAluno.text.toString()
            val turno = binding.editTextTurno.text.toString()
            val escola = binding.editTextEscola.text.toString()
            val nomeResponsavel = binding.editTextResponsavel.text.toString()
            val telefoneResponsavel = binding.editTextTelefoneResponsavel.text.toString()

            if (nome.isNotEmpty() && turno.isNotEmpty() && escola.isNotEmpty() && nomeResponsavel.isNotEmpty() && telefoneResponsavel.isNotEmpty()) {
                val aluno = Aluno(
                    nome = nome,
                    turno = turno,
                    escola = escola,
                    nomeResponsavel = nomeResponsavel,
                    telefoneResponsavel = telefoneResponsavel
                )

                lifecycleScope.launch {
                    alunoDao.insert(aluno)
                    Toast.makeText(requireContext(), "Aluno salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        binding.editTextNomeAluno.text?.clear()
        binding.editTextTurno.text?.clear()
        binding.editTextEscola.text?.clear()
        binding.editTextResponsavel.text?.clear()
        binding.editTextTelefoneResponsavel.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
