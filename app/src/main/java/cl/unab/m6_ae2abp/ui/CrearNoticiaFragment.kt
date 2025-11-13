package cl.unab.m6_ae2abp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.FragmentCrearNoticiaBinding
import cl.unab.m6_ae2abp.modelo.Noticia
import cl.unab.m6_ae2abp.viewmodel.NoticiaViewModel

class CrearNoticiaFragment : Fragment() {

    private var _binding: FragmentCrearNoticiaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoticiaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrearNoticiaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancelar.setOnClickListener {
            binding.tidtId.text?.clear()
            binding.tietTitulo.text?.clear()
            binding.tietDescripcion.text?.clear()
            binding.tietFuenteURL.text?.clear()
            findNavController().navigate(R.id.action_crearNoticiaFragment_to_leerNoticiasFragment)
        }

        binding.btnCrear.setOnClickListener {
            val id = binding.tidtId.text.toString().toIntOrNull()
            val titulo = binding.tietTitulo.text.toString()
            val descripcion = binding.tietDescripcion.text.toString()
            var url = binding.tietFuenteURL.text.toString()

            if (url.isNotEmpty() && !url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }

            if (id != null && titulo.isNotEmpty() && descripcion.isNotEmpty() && url.isNotEmpty()) {
                val noticia = Noticia(id, titulo, descripcion, url)
                viewModel.crearNoticia(noticia)
            } else {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.creacionExitosa.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                Toast.makeText(requireContext(), "Noticia creada con éxito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_crearNoticiaFragment_to_leerNoticiasFragment)
            } else {
                Toast.makeText(requireContext(), "Error al crear la noticia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}