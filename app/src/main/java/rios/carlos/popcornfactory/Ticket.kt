package rios.carlos.popcornfactory

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.annotation.RequiresApi
import android.os.Build



class Ticket : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        var id = -1
        var title = ""
        var seat = -1
        var btnSalir = findViewById<Button>(R.id.btn_exit)

        if (bundle != null) {
            id = bundle.getInt("id")
            title = bundle.getString("title")!!
            seat = bundle.getInt("seat")


            val ticketNumber = (1000000..9999999).random().toString()
            findViewById<TextView>(R.id.txt_ticket_number).text = Html.fromHtml("Ticket No.: <b>$ticketNumber</b>", Html.FROM_HTML_MODE_LEGACY)
            findViewById<TextView>(R.id.txt_pelicula).text = title
            findViewById<TextView>(R.id.txt_asiento).text = Html.fromHtml("Seat selected: <b>$seat</b>", Html.FROM_HTML_MODE_LEGACY)
            findViewById<TextView>(R.id.txt_fecha).text = Html.fromHtml("Date: <b>28/02/2025</b>", Html.FROM_HTML_MODE_LEGACY)
            findViewById<TextView>(R.id.txt_hora).text = Html.fromHtml("Time: <b>19:00</b>", Html.FROM_HTML_MODE_LEGACY)
            findViewById<TextView>(R.id.txt_precio).text = Html.fromHtml("Price: <b>$4.0</b>", Html.FROM_HTML_MODE_LEGACY)

        }

        btnSalir.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}