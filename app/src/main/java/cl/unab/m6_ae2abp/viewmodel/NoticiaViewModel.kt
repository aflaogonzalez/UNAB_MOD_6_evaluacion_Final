package cl.unab.m6_ae2abp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.unab.m6_ae2abp.modelo.Noticia
import cl.unab.m6_ae2abp.repositorio.NoticiaRepository
import kotlinx.coroutines.launch

class NoticiaViewModel : ViewModel() {

    private val repository = NoticiaRepository()

    private val _noticias = MutableLiveData<List<Noticia>>()
    val noticias: LiveData<List<Noticia>> = _noticias

    private val _creacionExitosa = MutableLiveData<Boolean>()
    val creacionExitosa: LiveData<Boolean> = _creacionExitosa

    private val _eliminacionExitosa = MutableLiveData<Boolean>()
    val eliminacionExitosa: LiveData<Boolean> = _eliminacionExitosa

    private val _noticiaEncontrada = MutableLiveData<Noticia?>()
    val noticiaEncontrada: LiveData<Noticia?> = _noticiaEncontrada

    private val _actualizacionExitosa = MutableLiveData<Boolean>()
    val actualizacionExitosa: LiveData<Boolean> = _actualizacionExitosa

    init {
        obtenerNoticias()
    }

    fun obtenerNoticias() {
        viewModelScope.launch {
            try {
                val response = repository.leerNoticias()
                _noticias.postValue(response.sortedByDescending { it.id })
            } catch (e: Exception) {
                Log.e("NoticiaViewModel", "Error al obtener noticias", e)
                _noticias.postValue(emptyList())
            }
        }
    }

    fun buscarNoticiaPorId(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.leerNoticiaPorId(id)
                if (response.isSuccessful) {
                    _noticiaEncontrada.postValue(response.body())
                } else {
                    _noticiaEncontrada.postValue(null)
                }
            } catch (e: Exception) {
                _noticiaEncontrada.postValue(null)
            }
        }
    }

    fun crearNoticia(noticia: Noticia) {
        viewModelScope.launch {
            try {
                val response = repository.crearNoticia(noticia)
                if (response.isSuccessful) {
                    _creacionExitosa.postValue(true)
                    obtenerNoticias() // Refresh list on success
                } else {
                    _creacionExitosa.postValue(false)
                    Log.d("NoticiaViewModel", "Error al crear noticia: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _creacionExitosa.postValue(false)
                Log.e("NoticiaViewModel", "Excepción al crear noticia", e)
            }
        }
    }

    fun actualizarNoticia(id: Int, noticia: Noticia) {
        viewModelScope.launch {
            try {
                val response = repository.actualizarNoticia(id, noticia)
                if (response.isSuccessful) {
                    _actualizacionExitosa.postValue(true)
                    obtenerNoticias() // Refresh list on success
                } else {
                    _actualizacionExitosa.postValue(false)
                    Log.d("NoticiaViewModel", "Error al actualizar noticia: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _actualizacionExitosa.postValue(false)
                Log.e("NoticiaViewModel", "Excepción al actualizar noticia", e)
            }
        }
    }

    fun eliminarNoticia(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.eliminarNoticia(id)
                if (response.isSuccessful) {
                    _eliminacionExitosa.postValue(true)
                    obtenerNoticias() // Refresh list on success
                } else {
                    _eliminacionExitosa.postValue(false)
                    Log.d("NoticiaViewModel", "Error al eliminar noticia: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _eliminacionExitosa.postValue(false)
                Log.e("NoticiaViewModel", "Excepción al eliminar noticia", e)
            }
        }
    }
}