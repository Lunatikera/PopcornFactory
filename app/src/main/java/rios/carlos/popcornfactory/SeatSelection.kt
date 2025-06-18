package rios.carlos.popcornfactory

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeatSelection : AppCompatActivity() {
    private lateinit var radioButtons: List<RadioButton>
    private lateinit var title: TextView
    private lateinit var gridSeats: GridLayout
    private var movieId: Int = -1
    private var totalSeats: Int = 0
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("SeatSelection", "onCreate")

        title = findViewById(R.id.txt_title_seats)
        gridSeats = findViewById(R.id.grid_seats)
        movieId = intent.extras?.getInt("id", -1) ?: -1
        totalSeats = intent.extras?.getInt("totalSeats", 20) ?: 20 // Default 20 seats
        title.text = intent.extras?.getString("title", "")

        prefs = getSharedPreferences("popcorn_factory", MODE_PRIVATE)

        initRadioButtons()
        initBtnConfirm()
    }

    private fun initRadioButtons() {
        // Crear lista de todos los posibles asientos
        radioButtons = listOf(
            findViewById(R.id.seat1),
            findViewById(R.id.seat2),
            findViewById(R.id.seat3),
            findViewById(R.id.seat4),
            findViewById(R.id.seat5),
            findViewById(R.id.seat6),
            findViewById(R.id.seat7),
            findViewById(R.id.seat8),
            findViewById(R.id.seat9),
            findViewById(R.id.seat10),
            findViewById(R.id.seat11),
            findViewById(R.id.seat12),
            findViewById(R.id.seat13),
            findViewById(R.id.seat14),
            findViewById(R.id.seat15),
            findViewById(R.id.seat16),
            findViewById(R.id.seat17),
            findViewById(R.id.seat18),
            findViewById(R.id.seat19),
            findViewById(R.id.seat20)
        )

        // Obtener asientos reservados
        val key = "booked_seats_$movieId"
        val bookedSeats = prefs.getStringSet(key, mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // Configurar visibilidad y estado de los asientos
        for (i in 0 until radioButtons.size) {
            val seatNumber = i + 1
            val button = radioButtons[i]

            if (seatNumber > totalSeats) {
                // Ocultar asientos que no existen para esta película
                button.visibility = View.GONE
            } else {
                // Configurar texto y comportamiento
                button.text = seatNumber.toString()
                button.isEnabled = !bookedSeats.contains(seatNumber.toString())

                button.setOnClickListener {
                    if (button.isEnabled) {
                        radioButtons.forEach { it.isChecked = false }
                        button.isChecked = true
                    }
                }
            }
        }

        // Ajustar el GridLayout para mostrar solo las filas necesarias
        val rows = (totalSeats + gridSeats.columnCount - 1) / gridSeats.columnCount
        gridSeats.rowCount = rows
    }

    private fun initBtnConfirm() {
        val btnConfirm = findViewById<Button>(R.id.btn_confirm)

        btnConfirm.setOnClickListener {
            val selectedSeat = getSelectedSeat()
            if (selectedSeat == null) {
                Toast.makeText(this, "Please select a seat", Toast.LENGTH_SHORT).show()
                Log.d("SeatSelection", "No seat selected")
                return@setOnClickListener
            }
            Log.d("SeatSelection", "Selected seat: $selectedSeat")

            val key = "booked_seats_$movieId"
            val bookedSeats = prefs.getStringSet(key, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            bookedSeats.add(selectedSeat.toString())
            prefs.edit().putStringSet(key, bookedSeats).apply()

            val intent = Intent(this, Ticket::class.java)
            intent.putExtra("id", movieId)
            intent.putExtra("title", title.text.toString())
            intent.putExtra("seat", selectedSeat)
            startActivity(intent)
            finish()
        }
    }

    private fun getSelectedSeat(): Int? {
        return radioButtons.find { it.isChecked }?.text?.toString()?.toIntOrNull()
    }
}