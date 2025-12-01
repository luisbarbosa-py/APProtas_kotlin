package com.example.roteiro.ui.responsaveis

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        setupCepFormatting()

        binding.buttonSalvar.setOnClickListener {
            val nome = binding.editTextNomeResponsavel.text.toString()
            val cep = binding.editTextCep.text.toString().replace("-", "")
            val endereco = binding.editTextEndereco.text.toString()
            val numero = binding.editTextNumero.text.toString()
            val bairro = binding.editTextBairro.text.toString()
            val telefone = binding.editTextTelefone.text.toString()

            if (nome.isNotEmpty() && cep.length == 8 && endereco.isNotEmpty() && numero.isNotEmpty() && bairro.isNotEmpty() && telefone.isNotEmpty()) {
                val responsavel = Responsavel(
                    nome = nome,
                    cep = cep,
                    endereco = endereco,
                    numero = numero,
                    bairro = bairro,
                    telefone = telefone
                )

                lifecycleScope.launch {
                    responsavelDao.insert(responsavel)
                    Toast.makeText(requireContext(), "Responsável salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos corretamente!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupCepFormatting() {
        binding.editTextCep.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                isUpdating = true
                val cep = s.toString()
                if (cep.length == 5 && !cep.contains("-")) {
                    s?.insert(5, "-")
                }
                isUpdating = false
            }
        })
    }

    private fun clearFields() {
        binding.editTextNomeResponsavel.text?.clear()
        binding.editTextCep.text?.clear()
        binding.editTextEndereco.text?.clear()
        binding.editTextNumero.text?.clear()
        binding.editTextBairro.text?.clear()
        binding.editTextTelefone.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
