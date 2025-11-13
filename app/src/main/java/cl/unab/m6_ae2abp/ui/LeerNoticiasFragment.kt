package cl.unab.m6_ae2abp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.FragmentLeerNoticiasBinding
import cl.unab.m6_ae2abp.viewmodel.NoticiaViewModel

class LeerNoticiasFragment : Fragment() {

    private var _binding: FragmentLeerNoticiasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoticiaViewModel by viewModels()
    private lateinit var noticiaAdapter: NoticiaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeerNoticiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Adapter and RecyclerView
        noticiaAdapter = NoticiaAdapter(emptyList(), viewModel)
        binding.rvNoticias.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNoticias.adapter = noticiaAdapter

        // Observe LiveData from ViewModel
        viewModel.noticias.observe(viewLifecycleOwner) { noticias ->
            noticiaAdapter.updateNoticias(noticias)
        }

        viewModel.eliminacionExitosa.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                Toast.makeText(requireContext(), "Noticia eliminada con éxito", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigation to Create Fragment
        binding.btnCrearNoticia.setOnClickListener {
            findNavController().navigate(R.id.action_leerNoticiasFragment_to_crearNoticiaFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}