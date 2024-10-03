package com.example.simonsays

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.annotation.ContentView
import androidx.lifecycle.findViewTreeViewModelStoreOwner

class AdapterBotonColor (context : Context,val layout: Int,val listaBoton : MutableList<BotonColor>):
    ArrayAdapter<BotonColor>(context,layout,listaBoton) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view:View
        if (convertView != null){
            view = convertView
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false)
        }

        bindPlanet (view,listaBoton[position])

        return view
    }

    private fun bindPlanet(view: View, botonColor: BotonColor) {
        val image = view.findViewById<ImageView>(R.id.image)
        image.setImageResource(botonColor.color)
    }
}