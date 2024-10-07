package com.example.simonsays

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var turnOfPlayer = false
        var order = mutableListOf<Int>()
        var points = 0
        var lose = false
        var block = false
        var positionSelected = 0
        var positionTested = 0
        var viewAtPosition: View
        var image: ImageView

        var strPoints = findViewById<TextView>(R.id.puntuacion)

        val boton = findViewById<Button>(R.id.boton)

        val lista = generateRandomBotons()

        val grid = findViewById<GridView>(R.id.grid)

        val adapter = AdapterBotonColor(this, R.layout.grid_item, lista)

        grid.adapter = adapter

        grid.setOnItemClickListener { parent, view, position, id ->

            lifecycleScope.launch {
                if (!block) {
                    block = true
                        positionSelected = position
                        viewAtPosition = grid.getChildAt(position) as View
                        image = viewAtPosition.findViewById(R.id.image)
                        image.setImageResource(lista[position].colorSelected)
                        image.invalidate()

                        delay(400)

                        image.setImageResource(lista[position].color)
                        image.invalidate()

                        if (positionSelected == order[positionTested]) {
                            positionTested++
                            if (positionTested == order.size) {
                                turnOfPlayer = false
                                points++
                                strPoints.setText("Puntuación : "+points)
                                Toast.makeText(this@MainActivity, "¡¡¡Pasas a la sigente ronda!!!", Toast.LENGTH_SHORT).show()
                                positionTested = 0
                                order.add(Random.nextInt(4))
                                showOrder(order,grid,lista)
                            }
                        }else{
                            Toast.makeText(this@MainActivity, "¡¡¡HAS PERDIDO!!!", Toast.LENGTH_LONG).show()
                            strPoints.setText("Puntuación : 0")
                            order.clear()
                            points = 0
                            positionTested = 0
                            strPoints.visibility = View.GONE
                            boton.visibility = View.VISIBLE
                        }
                        block = false

                }

            }
        }
        boton.setOnClickListener(){
            lifecycleScope.launch{
                positionTested = 0
                order.add(Random.nextInt(4))
                showOrder(order,grid,lista)
                boton.visibility = View.GONE
                strPoints.visibility = View.VISIBLE
            }

        }

    }
    private suspend fun showOrder(order : MutableList<Int>, grid : GridView ,lista: MutableList<BotonColor>){
        var viewAtPosition: View
        var image : ImageView
        for (i in order) {
            viewAtPosition = grid.getChildAt(i) as View
            image = viewAtPosition.findViewById(R.id.image)


            image.setImageResource(lista[i].colorLight)
            image.invalidate()

            delay(400)


            image.setImageResource(lista[i].color)
            image.invalidate()
            delay(200)

        }
    }

    private fun generateRandomBotons(): MutableList<BotonColor> {
        var listColor = mutableListOf<BotonColor>()
        var listColorsBase = mutableListOf("rojo", "verde", "azul", "amarillo")
        var indexColor: Int
        for (i in 0..3) {
            indexColor = Random.nextInt(listColorsBase.size)
            listColor.add(generateBoton(listColorsBase[indexColor]))
            listColorsBase.removeAt(indexColor)
        }
        return listColor
    }

    private fun generateBoton(color: String): BotonColor {
        var boton: BotonColor
        when (color) {
            "rojo" ->
                boton = BotonColor(
                    "color", R.drawable.rojo_sin_pulsar, R.drawable.rojo_luz,
                    R.drawable.rojo_pulsado
                )

            "verde" ->
                boton = BotonColor(
                    "color",
                    R.drawable.verde_sin_pulsar,
                    R.drawable.verde_luz,
                    R.drawable.verde_pulsado
                )

            "amarillo" ->
                boton = BotonColor(
                    "color",
                    R.drawable.amarillo_sin_pulsar,
                    R.drawable.amarillo_luz,
                    R.drawable.amarillo_pulsado
                )

            else ->
                boton = BotonColor(
                    "color",
                    R.drawable.azul_sin_pulsar,
                    R.drawable.azul_luz,
                    R.drawable.azul_pulsado
                )
        }

        return boton
    }
}