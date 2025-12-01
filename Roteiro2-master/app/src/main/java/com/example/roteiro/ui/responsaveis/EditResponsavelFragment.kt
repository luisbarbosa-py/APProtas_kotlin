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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roteiro.database.AppDatabase
import com.example.roteiro.dao.ResponsavelDao
import com.example.roteiro.databinding.FragmentEditResponsavelBinding
import com.example.roteiro.model.Responsavel
import kotlinx.coroutines.launch

class EditResponsavelFragment : Fragment() {

    private var _binding: FragmentEditResponsavelBinding? = null
    private val binding get() = _binding!!
    private val args: EditResponsavelFragmentArgs by navArgs()
    private lateinit var responsavelDao: ResponsavelDao
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

        val db = AppDatabase.getDatabase(requireContext())
        responsavelDao = db.responsavelDao()

        setupCepFormatting()
        loadResponsavel()

        binding.buttonAtualizar.setOnClickListener {
            updateResponsavel()
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

    private fun loadResponsavel() {
        lifecycleScope.launch {
            responsavel = responsavelDao.getById(args.responsavelId)
            responsavel?.let {
                binding.editTextNomeResponsavel.setText(it.nome)
                val cepFormatado = it.cep.let { if (it.length == 8) it.substring(0, 5) + "-" + it.substring(5) else it }
                binding.editTextCep.setText(cepFormatado)
                binding.editTextEndereco.setText(it.endereco)
                binding.editTextNumero.setText(it.numero)
                binding.editTextBairro.setText(it.bairro)
                binding.editTextTelefone.setText(it.telefone)
            }
        }
    }

    private fun updateResponsavel() {
        val nome = binding.editTextNomeResponsavel.text.toString()
        val cep = binding.editTextCep.text.toString().replace("-", "")
        val endereco = binding.editTextEndereco.text.toString()
        val numero = binding.editTextNumero.text.toString()
        val bairro = binding.editTextBairro.text.toString()
        val telefone = binding.editTextTelefone.text.toString()

        if (nome.isNotEmpty() && cep.length == 8 && endereco.isNotEmpty() && numero.isNotEmpty() && bairro.isNotEmpty() && telefone.isNotEmpty()) {
            val updatedResponsavel = responsavel?.copy(
                nome = nome,
                cep = cep,
                endereco = endereco,
                numero = numero,
                bairro = bairro,
                telefone = telefone
            )

            if (updatedResponsavel != null) {
                lifecycleScope.launch {
                    responsavelDao.update(updatedResponsavel)
                    Toast.makeText(requireContext(), "Responsável atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), "Erro ao preparar atualização do responsável!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Preencha todos os campos corretamente!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
