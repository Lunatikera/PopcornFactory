package rios.carlos.popcornfactory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MovieDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        var numSeats = 0
        var id = -1
        var title = ""

        if (bundle != null) {
            title = bundle.getString("titulo") ?: ""
            val header = bundle.getInt("header")
            val sinopsis = bundle.getString("sinopsis")
            numSeats = bundle.getInt("numSeats")
            id = bundle.getInt("id")

            findViewById<TextView>(R.id.txt_titulo).text = title
            findViewById<TextView>(R.id.txt_sinopsis).text = sinopsis
            findViewById<ImageView>(R.id.img_pelicula).setImageResource(header)
            findViewById<TextView>(R.id.seats_left).text = "$numSeats seats available"
        }

        val btnBuy = findViewById<Button>(R.id.btn_buy_tickets)
        if (numSeats == 0) {
            findViewById<TextView>(R.id.seats_left).text = "No seats available"
            btnBuy.isEnabled = false
        } else {
            btnBuy.setOnClickListener {
                val intent = Intent(this, SeatSelection::class.java)
                intent.putExtra("id", id)
                intent.putExtra("title", title)
                intent.putExtra("numSeats", numSeats)
                startActivity(intent)
            }
        }
    }
}