package rios.carlos.popcornfactory

// Agrega esta clase al mismo archivo o crea un nuevo archivo Movie.kt
data class Movie(
    val id: Int,  // Agregamos un ID único para cada película
    val titulo: String,
    val image: Int,
    val header: Int,
    val sinopsis: String,
    val totalSeats: Int  // Nuevo campo para asientos totales
)