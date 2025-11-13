package cl.unab.m6_ae2abp.repositorio

import cl.unab.m6_ae2abp.modelo.Noticia
import cl.unab.m6_ae2abp.remote.NoticiaService
import cl.unab.m6_ae2abp.remote.RetrofitClient

class NoticiaRepository(
    private val api: NoticiaService = RetrofitClient.api
) {

    suspend fun leerNoticias(): List<Noticia> = api.leerNoticias()

    suspend fun leerNoticiaPorId(id: Int) = api.leerNoticiaPorId(id)

    suspend fun eliminarNoticia(id: Int) = api.eliminarNoticia(id)

    suspend fun actualizarNoticia(id: Int, noticia: Noticia) = api.actualizarNoticia(id, noticia)

    suspend fun crearNoticia(noticia: Noticia) = api.crearNoticia(noticia)

}