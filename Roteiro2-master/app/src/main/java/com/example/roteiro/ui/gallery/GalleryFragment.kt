package com.example.roteiro.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roteiro.R
import com.example.roteiro.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageButtonAlunos.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_alunosHubFragment)
        }
        binding.imageButtonResponsaveis.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_responsaveisHubFragment)
        }
        binding.imageButtonTurma.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_turmaFragment)
        }
        binding.imageButtonEscola.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_escolaFragment)
        }
        binding.imageButtonCondutor.setOnClickListener{
            findNavController().navigate(R.id.action_nav_gallery_to_condutoresFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
/*
        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}