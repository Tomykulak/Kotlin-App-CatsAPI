package xokruhli.finalproject.model.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "breed")
    val breed: String?,

    @ColumnInfo(name = "weight")
    val weight: Double?,

    @ColumnInfo(name = "height")
    val height: Double?,

    @ColumnInfo(name = "vaccination")
    val vaccination: String?,

    @ColumnInfo(name = "medical_report")
    val medical_report: String?,

    @ColumnInfo(name = "photo")
    val photo: String?
): Serializable