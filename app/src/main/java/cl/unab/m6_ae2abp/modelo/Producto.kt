package cl.unab.m6_ae2abp.modelo

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "descripcion") var descripcion: String,
    @ColumnInfo(name = "precio") var precio: Int,
    @ColumnInfo(name = "cantidad") var cantidad: Int
) : Parcelable