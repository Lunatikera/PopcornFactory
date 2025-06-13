package rios.carlos.popcornfactory

import android.os.Bundle
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

        if (bundle != null) {
            val titulo = bundle.getString("titulo")
            val header = bundle.getInt("header")
            val sinopsis = bundle.getString("sinopsis")

            findViewById<TextView>(R.id.txt_titulo).text = titulo
            findViewById<TextView>(R.id.txt_sinopsis).text = sinopsis
            findViewById<ImageView>(R.id.img_pelicula).setImageResource(header)
        }
    }

}