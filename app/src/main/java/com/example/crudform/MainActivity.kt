package com.example.crudform

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

private val JsonObject?.HP: Int
    get() = this?.get("HP")?.asInt ?: 0

private val JsonObject?.clase: String
    get() = this?.get("clase")?.asString ?: ""

private val JsonObject?.nombre: String
    get() = this?.get("nombre")?.asString ?: ""

private val JsonObject?.indice: Int
    get() = this?.get("indice")?.asInt ?: 0

private lateinit var textOnline: TextView
private lateinit var textNumFichas: TextView

private var listaFichas: ArrayList<FichaAll> = ArrayList()



class MainActivity : AppCompatActivity() {

    private var btnReconectarRef: WeakReference<Button>? = null


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
            startActivity(intent)


//            val nombre = textNombre.text.toString()
//            val clase = textClase.text.toString()
//            val HP = textHP.text.toString()
//
//            if (nombre.isBlank() || clase.isBlank() || HP.isBlank()) {
//                Toast.makeText(this, "Rellena todos los campos menos Indice", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                val fichaCreate = FichaCreate(
//                    nombre = nombre,
//                    clase = clase,
//                    HP = HP
//                )
//                crearFicha(fichaCreate)
//            }
        }



        btnVer.setOnClickListener {
            val intent = Intent(this, Ver::class.java)
            startActivity(intent)
//            val indice = textIndice.text.toString()
//            val nombre = textNombre.text.toString()
//            val clase = textClase.text.toString()
//            val HP = textHP.text.toString()
//            System.out.println(indice)
//            if ((indice.isBlank() && nombre.isBlank() && clase.isBlank() && HP.isBlank())) {
//                Toast.makeText(this, "Rellene un campo", Toast.LENGTH_SHORT).show()
//            } else {
//
//                var filtroKey: String = "";
//                var filtroValue: String = "";
//
//                if (indice.isNotBlank()) {
//                    filtroKey = "indice";
//                    filtroValue = indice;
//                } else if (nombre.isNotBlank()) {
//                    filtroKey = "nombre";
//                    filtroValue = nombre;
//                } else if (clase.isNotBlank()) {
//                    filtroKey = "clase";
//                    filtroValue = clase;
//                } else if (HP.isNotBlank()) {
//                    filtroKey = "HP";
//                    filtroValue = HP;
//                }
//
//                val fichaRead = FichaRead(
//                    filtroKey = filtroKey,
//                    filtroValue = filtroValue,
//                )
//                verFicha(fichaRead)
//
//            }

        }

        btnCambiar.setOnClickListener {

            val intent = Intent(this, Modificar::class.java)
            startActivity(intent)


//            val indice = textNombre.text.toString()
//            if (indice.isBlank()) {
//                Toast.makeText(this, "Introduzca Indice para modificar, y los datos nuevos", Toast.LENGTH_SHORT).show()
//            } else {
//                val indice = textIndice.text.toString()
//                val nombre = textNombre.text.toString()
//                val clase = textClase.text.toString()
//                val hp = textHP.text.toString()
//
//                val fichaUpdate = FichaUpdate(
//                    indice = indice,
//                    nombre = nombre,
//                    clase = clase,
//                    HP = hp
//                )
//                cambiarFicha (fichaUpdate)
//
//            }
        }

        btnBorrar.setOnClickListener {

            val intent = Intent(this, Borrar::class.java)
            startActivity(intent)

//            val indice = textIndice.text.toString()
//            if (indice.isBlank()) {
//                Toast.makeText(this, "Introduzca Indice para borrar", Toast.LENGTH_SHORT).show()
//            } else {
//                val fichaDelete = FichaDelete(
//                    indice = indice
//                )
//                borrarFicha (fichaDelete)
//
//            }
        }
    }

//    private fun crearFicha(fichaCreate: FichaCreate) {
//        RetrofitClient.instance.crearFicha(fichaCreate).enqueue(object : Callback<Respuesta> {
//            override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@MainActivity, "Nueva ficha creada", Toast.LENGTH_SHORT).show()
//                    limpiarCampos()
//                } else {
//                    limpiarCampos()
//                    Toast.makeText(this@MainActivity, "Error al crear ficha", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Respuesta>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
//                System.out.println(t.message);
//            }
//        })
//    }

//    private fun verFicha(fichaRead: FichaRead) {
//        RetrofitClient.instance.leerFicha(fichaRead).enqueue(object : Callback<Respuesta> {
//            override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
//                if (response.isSuccessful) {
//                    //Toast.makeText(this@MainActivity, "Nueva ficha creada", Toast.LENGTH_LONG).show()
//                    val respuesta = response.body()?.data
//                    System.out.println(respuesta)
//                    if(respuesta == null){
//                        limpiarCampos()
//                        Toast.makeText(this@MainActivity, "Ficha no encontrada", Toast.LENGTH_SHORT).show()
//                    }else{
//                        val indiceEncontrado: Int = respuesta.indice;
//                        val nombreEncontrado: String = respuesta.nombre;
//                        val claseEncontrada: String = respuesta.clase;
//                        val hpEncontrado: Int = respuesta.HP;
//                        textIndice.setText(indiceEncontrado.toString())
//                        textNombre.setText(nombreEncontrado)
//                        textClase.setText(claseEncontrada)
//                        textHP.setText(hpEncontrado.toString())
//                    }
//
//                } else {
//                    limpiarCampos()
//                    Toast.makeText(this@MainActivity, "Ficha no encontrada", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Respuesta>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
//                System.out.println(t.message);
//            }
//        })
//    }
//
//    private fun cambiarFicha(cambiarFicha: FichaUpdate) {
//        RetrofitClient.instance.actualizarFicha(cambiarFicha).enqueue(object : Callback<Respuesta> {
//            override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
//                if (response.isSuccessful) {
//
//                    val respuesta = response.body()
//                    System.out.println(respuesta)
//                    if(respuesta?.type == "failure"){
//                        limpiarCampos()
//                        Toast.makeText(this@MainActivity, "Ficha no encontrada", Toast.LENGTH_SHORT).show()
//                    }else{
//                        limpiarCampos()
//                        Toast.makeText(this@MainActivity, "Ficha cambiada", Toast.LENGTH_LONG).show()
//                    }
//
//                } else {
//                    limpiarCampos()
//                    Toast.makeText(this@MainActivity, "Ficha no encontrada", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Respuesta>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
//                System.out.println(t.message);
//            }
//        })
//    }
//
//    private fun borrarFicha(fichaDelete: FichaDelete) {
//        RetrofitClient.instance.borrarFicha(fichaDelete).enqueue(object : Callback<Respuesta> {
//            override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
//                if (response.isSuccessful) {
//                    val respuesta = response.body()
//                    //System.out.println(respuesta)
//                    if(respuesta?.type == "failure"){
//                        limpiarCampos()
//                        Toast.makeText(this@MainActivity, "Ficha no encontrada", Toast.LENGTH_SHORT).show()
//                    }else{
//                        limpiarCampos()
//                        Toast.makeText(this@MainActivity, "Ficha borrada", Toast.LENGTH_LONG).show()
//                    }
//
//                } else {
//                    limpiarCampos()
//                    Toast.makeText(this@MainActivity, "Ficha no encontrada", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Respuesta>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
//                System.out.println(t.message);
//            }
//        })
//    }
//
//
//    private fun limpiarCampos() {
//        findViewById<EditText>(R.id.indice).text.clear()
//        findViewById<EditText>(R.id.nombre).text.clear()
//        findViewById<EditText>(R.id.clase).text.clear()
//        findViewById<EditText>(R.id.HP).text.clear()
//    }



    private fun verTodos() {
        RetrofitClient.instance.verTodas().enqueue(object : Callback<RespuestaAll> {
            override fun onResponse(call: Call<RespuestaAll>, response: Response<RespuestaAll>) {
                if (response.isSuccessful) {
                    textOnline.text = "Online"
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
                                // Server responded but with failure status
                                textOnline.text = "Offline"
                                textNumFichas.text = "0"
                                mostrarBotonReconexion()
                                listaFichas.clear()
                                Toast.makeText(this@MainActivity, "Error del servidor", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // Response body is null
                        textOnline.text = "Offline"
                        textNumFichas.text = "0"
                        mostrarBotonReconexion()
                        listaFichas.clear()
                    }
                } else {
                    // HTTP error (4xx, 5xx)
                    textOnline.text = "Offline"
                    textNumFichas.text = "0"
                    listaFichas.clear()
                    Toast.makeText(this@MainActivity, "Error del servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RespuestaAll>, t: Throwable) {
                // Network failure (no connection, timeout, etc.)
                textOnline.text = "Offline"
                textNumFichas.text = "0"
                mostrarBotonReconexion()
                listaFichas.clear()
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
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