package cl.unab.m6_ae2abp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.FragmentActualizarNoticiaBinding
import cl.unab.m6_ae2abp.modelo.Noticia
import cl.unab.m6_ae2abp.viewmodel.NoticiaViewModel

class ActualizarNoticiaFragment : Fragment() {

    private var _binding: FragmentActualizarNoticiaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoticiaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActualizarNoticiaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnBuscar.setOnClickListener {
            val id = binding.tidtBuscarId.text.toString().toIntOrNull()
            if (id != null) {
                viewModel.buscarNoticiaPorId(id)
            } else {
                Toast.makeText(requireContext(), "Por favor, ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnActualizar.setOnClickListener {
            val id = binding.tidtId.text.toString().toIntOrNull()
            val titulo = binding.tietTitulo.text.toString()
            val descripcion = binding.tietDescripcion.text.toString()
            var url = binding.tietFuenteURL.text.toString()

            if (url.isNotEmpty() && !url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }

            if (id != null && titulo.isNotEmpty() && descripcion.isNotEmpty() && url.isNotEmpty()) {
                val noticia = Noticia(id, titulo, descripcion, url)
                viewModel.actualizarNoticia(id, noticia)
            } else {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelar.setOnClickListener {
            limpiarFormulario()
            binding.cvFormulario.isVisible = false
            findNavController().navigate(R.id.action_actualizarNoticiaFragment_to_leerNoticiasFragment)
        }
    }

    private fun setupObservers() {
        viewModel.noticiaEncontrada.observe(viewLifecycleOwner) { noticia ->
            if (noticia != null) {
                binding.cvFormulario.isVisible = true
                binding.tidtId.setText(noticia.id.toString())
                binding.tietTitulo.setText(noticia.titulo)
                binding.tietDescripcion.setText(noticia.descripcion)
                binding.tietFuenteURL.setText(noticia.fuente_url)
            } else {
                binding.cvFormulario.isVisible = false
                Toast.makeText(requireContext(), "Noticia no encontrada", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.actualizacionExitosa.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                Toast.makeText(requireContext(), "Noticia actualizada con éxito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_actualizarNoticiaFragment_to_leerNoticiasFragment)
            } else {
                Toast.makeText(requireContext(), "Error al actualizar la noticia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun limpiarFormulario() {
        binding.tidtBuscarId.text?.clear()
        binding.tidtId.text?.clear()
        binding.tietTitulo.text?.clear()
        binding.tietDescripcion.text?.clear()
        binding.tietFuenteURL.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}