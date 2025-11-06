package com.example.roteiro.ui.alunos

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
import com.example.roteiro.databinding.FragmentEditAlunoBinding
import com.example.roteiro.model.Aluno
import kotlinx.coroutines.launch

class EditAlunoFragment : Fragment() {

    private var _binding: FragmentEditAlunoBinding? = null
    private val binding get() = _binding!!
    private val args: EditAlunoFragmentArgs by navArgs()
    private lateinit var alunoDao: com.example.roteiro.dao.AlunoDao
    private var aluno: Aluno? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAlunoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        alunoDao = db.alunoDao()

        loadAluno()

        binding.buttonAtualizar.setOnClickListener {
            updateAluno()
        }
    }

    private fun loadAluno() {
        lifecycleScope.launch {
            aluno = alunoDao.getById(args.alunoId)
            aluno?.let {
                binding.editTextNomeAluno.setText(it.nome)
                binding.editTextTurno.setText(it.turno)
                binding.editTextEscola.setText(it.escola)
                binding.editTextResponsavel.setText(it.nomeResponsavel)
                binding.editTextTelefoneResponsavel.setText(it.telefoneResponsavel)
            }
        }
    }

    private fun updateAluno() {
        val nome = binding.editTextNomeAluno.text.toString()
        val turno = binding.editTextTurno.text.toString()
        val escola = binding.editTextEscola.text.toString()
        val nomeResponsavel = binding.editTextResponsavel.text.toString()
        val telefoneResponsavel = binding.editTextTelefoneResponsavel.text.toString()

        if (nome.isNotEmpty() && turno.isNotEmpty() && escola.isNotEmpty() && nomeResponsavel.isNotEmpty() && telefoneResponsavel.isNotEmpty()) {
            val updatedAluno = aluno!!.copy(
                nome = nome,
                turno = turno,
                escola = escola,
                nomeResponsavel = nomeResponsavel,
                telefoneResponsavel = telefoneResponsavel
            )

            lifecycleScope.launch {
                alunoDao.update(updatedAluno)
                Toast.makeText(requireContext(), "Aluno atualizado com sucesso!", Toast.LENGTH_SHORT).show()
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
