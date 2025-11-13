package cl.unab.m6_ae2abp.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Noticia(
    var id: Int,
    var titulo: String,
    var descripcion: String,
    var fuente_url: String
) : Parcelable
