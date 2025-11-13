package cl.unab.m6_ae2abp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.ItemNoticiaBinding
import cl.unab.m6_ae2abp.modelo.Noticia
import cl.unab.m6_ae2abp.viewmodel.NoticiaViewModel

class NoticiaAdapter(private var noticias: List<Noticia>, private val viewModel: NoticiaViewModel) : RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {

    inner class NoticiaViewHolder(private val binding: ItemNoticiaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(noticia: Noticia) {
            binding.tvId.text = noticia.id.toString()
            binding.tvTitulo.text = noticia.titulo
            binding.tvDescripcion.text = noticia.descripcion
            binding.tvFuenteUrl.text = noticia.fuente_url

            binding.btnEditar.setOnClickListener {
                it.findNavController().navigate(R.id.action_leerNoticiasFragment_to_actualizarNoticiaFragment)
            }

            binding.btnEliminar.setOnClickListener {
                viewModel.eliminarNoticia(noticia.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val binding = ItemNoticiaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticiaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        holder.bind(noticias[position])
    }

    override fun getItemCount(): Int {
        return noticias.size
    }

    fun updateNoticias(noticias: List<Noticia>){
        this.noticias = noticias
        notifyDataSetChanged()
    }
}