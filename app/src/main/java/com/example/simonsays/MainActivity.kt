package com.example.simonsays

import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val lista = generateRandomBotons()

        val grid = findViewById<GridView>(R.id.grid)

        val adapter = AdapterBotonColor(this,R.layout.grid_item,lista)

        grid.adapter = adapter

        playGame(grid,lista)


    }

    private fun playGame(grid: GridView, lista: MutableList<BotonColor>) {
        var order = mutableListOf<Int>()
        var lose = false
        var viewAtPosition : View
        var image : ImageView
        do {
            order.add(Random.nextInt(4))
            for (i in order){
                viewAtPosition = grid.getChildAt(i) as View
                image = viewAtPosition.findViewById(R.id.image)
                GlobalScope.launch {
                    runOnUiThread {
                        image.setImageResource(lista[i].colorLight)
                    }
                }
                Thread.sleep(1000)
                GlobalScope.launch {
                    runOnUiThread {
                        image.setImageResource(lista[i].color)
                    }
                }
            }
            for (i in order){

            }


        }while (!lose)
    }

    private fun generateRandomBotons(): MutableList<BotonColor> {
        var listColor = mutableListOf<BotonColor>()
        var listColorsBase = mutableListOf("rojo","verde","azul","amarillo")
        var indexColor : Int
            for (i in 0..3) {
                indexColor = Random.nextInt(listColorsBase.size)
                listColor.add(generateBoton(listColorsBase[indexColor]))
                listColorsBase.removeAt(indexColor)
            }
        return listColor
    }

    private fun generateBoton(color:String): BotonColor {
        var boton : BotonColor
        when(color){
            "rojo"->
                boton = BotonColor("color",R.drawable.rojo_sin_pulsar,R.drawable.rojo_luz,R.drawable.rojo_pulsado)
            "verde"->
                boton = BotonColor("color",R.drawable.verde_sin_pulsar,R.drawable.verde_luz,R.drawable.verde_pulsado)
            "amarillo"->
                boton = BotonColor("color",R.drawable.amarillo_sin_pulsar,R.drawable.amarillo_luz,R.drawable.amarillo_pulsado)
            else->
                boton = BotonColor("color",R.drawable.azul_sin_pulsar,R.drawable.azul_luz,R.drawable.azul_pulsado)
        }

        return boton
    }
}