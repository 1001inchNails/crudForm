package com.example.crudform

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.time.format.TextStyle
import androidx.core.graphics.toColorInt

private lateinit var textOnline: TextView
private lateinit var textNumFichas: TextView

private var listaFichas: ArrayList<FichaAll> = ArrayList()


class MainActivity : AppCompatActivity() {

    private var btnReconectarRef: WeakReference<Button>? = null

    private val startActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finishAffinity()
                kotlin.system.exitProcess(0)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNuevo = findViewById<Button>(R.id.buttNuevo)
        val btnVer = findViewById<Button>(R.id.buttVer)
        val btnCambiar = findViewById<Button>(R.id.buttCambiar)
        val btnBorrar = findViewById<Button>(R.id.buttBorrar)
        val btnReconectar = findViewById<Button>(R.id.buttReconectar)
        btnReconectarRef = WeakReference(btnReconectar)

        textOnline = findViewById<TextView>(R.id.online)
        textNumFichas = findViewById<TextView>(R.id.max)

        verTodos()

        btnReconectar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finishAffinity()
            kotlin.system.exitProcess(0)

        }

        btnNuevo.setOnClickListener {

            val intent = Intent(this, Nuevo::class.java)
            startActivity.launch(intent)
        }



        btnVer.setOnClickListener {
            val intent = Intent(this, Ver::class.java)
            startActivity.launch(intent)

        }

        btnCambiar.setOnClickListener {

            val intent = Intent(this, Modificar::class.java)
            startActivity.launch(intent)

        }

        btnBorrar.setOnClickListener {

            val intent = Intent(this, Borrar::class.java)
            startActivity.launch(intent)

        }
    }

    private fun verTodos() {
        textOnline.text = "Cargando..."
        textOnline.setTextColor(Color.YELLOW)
        RetrofitClient.instance.verTodas().enqueue(object : Callback<RespuestaAll> {
            override fun onResponse(call: Call<RespuestaAll>, response: Response<RespuestaAll>) {
                if (response.isSuccessful) {
                    textOnline.text = "Online"
                    textOnline.setTextColor("#448560".toColorInt())
                    val respuesta = response.body()

                    if (respuesta != null) {
                        when (respuesta.type) {
                            "success" -> {
                                val data: List<FichaAll>? = response.body()?.data as List<FichaAll>?

                                listaFichas.clear()

                                data?.forEach { modulo ->
                                    listaFichas.add(modulo)
                                }
                                val tamaList = (listaFichas.size).toString()
                                textNumFichas.text = tamaList
                            }

                            "failure" -> {
                                textOnline.text = "Offline"
                                textOnline.setTextColor(Color.RED)
                                textNumFichas.text = "0"
                                mostrarBotonReconexion()
                                listaFichas.clear()
                                Toast.makeText(
                                    this@MainActivity,
                                    "Error del servidor",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        textOnline.text = "Offline"
                        textOnline.setTextColor(Color.RED)
                        textNumFichas.text = "0"
                        mostrarBotonReconexion()
                        listaFichas.clear()
                    }
                } else {
                    textOnline.text = "Offline"
                    textOnline.setTextColor(Color.RED)
                    textNumFichas.text = "0"
                    listaFichas.clear()
                    Toast.makeText(
                        this@MainActivity,
                        "Error del servidor: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RespuestaAll>, t: Throwable) {
                textOnline.text = "Offline"
                textOnline.setTextColor(Color.RED)
                textNumFichas.text = "0"
                mostrarBotonReconexion()
                listaFichas.clear()
                Toast.makeText(
                    this@MainActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                System.out.println(t.message)
            }

            private fun mostrarBotonReconexion() {
                runOnUiThread {
                    btnReconectarRef?.get()?.visibility = View.VISIBLE
                }
            }
        })
    }
}