package cl.unab.m6_ae2abp.remote

import cl.unab.m6_ae2abp.modelo.Noticia
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoticiaService {

    @GET("sebastian/noticias")
    suspend fun leerNoticias(): List<Noticia>

    @GET("sebastian/noticias/{id}")
    suspend fun leerNoticiaPorId(@Path("id") id: Int): Response<Noticia>

    @DELETE("sebastian/noticias/{id}")
    suspend fun eliminarNoticia(@Path("id") id: Int): Response<Unit>

    @PUT("sebastian/noticias/{id}")
    suspend fun actualizarNoticia(@Path("id") id: Int, @Body noticia: Noticia): Response<Noticia>

    @POST("sebastian/noticias")
    suspend fun crearNoticia(@Body noticia: Noticia): Response<Noticia>
}