package cl.unab.m6_ae2abp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cl.unab.m6_ae2abp.modelo.Producto

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)

    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    fun getAll(): LiveData<List<Producto>>
    
    @Query("SELECT * FROM productos WHERE id = :id")
    fun getById(id: Int): LiveData<Producto>
}