package com.example.roteiro.ui.turma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.roteiro.R
import com.example.roteiro.databinding.FragmentCadastrarTurmaBinding

class CadastrarTurmaFragment : Fragment() {

    private var _binding: FragmentCadastrarTurmaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastrarTurmaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura o seletor de turnos
        setupTurnoDropDown()

        // Configura o clique do botão salvar
        binding.buttonSalvarTurma.setOnClickListener {
            salvarTurma()
        }
    }

    private fun setupTurnoDropDown() {
        val turnos = resources.getStringArray(R.array.turnos_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, turnos)
        binding.autoCompleteTextViewTurno.setAdapter(adapter)
    }

    private fun salvarTurma() {
        val turnoSelecionado = binding.autoCompleteTextViewTurno.text.toString()

        if (turnoSelecionado.isNotEmpty()) {
            // Lógica para salvar a turma no banco de dados virá aqui
            Toast.makeText(context, "Turma do turno '$turnoSelecionado' salva!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Por favor, selecione um turno.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
