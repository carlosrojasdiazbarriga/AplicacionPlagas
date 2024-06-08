package com.example.aplicacionplagas.ui.prevencion

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionplagas.R
import com.example.aplicacionplagas.ui.prevencion_detalle.DetallePrevencionActivity
import com.example.aplicacionplagas.util.ImageHelper


class PrevencionAdapter(
    private val plagas : List<Pair<String, String>>,
    private val context : Context
) : RecyclerView.Adapter<PrevencionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen = itemView.findViewById<ImageView>(R.id.img_foto_plaga)
        var nombre = itemView.findViewById<TextView>(R.id.tv_nombre_plaga)
        var tarjeta = itemView.findViewById<View>(R.id.mcv_prevencion_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prevencion, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plagas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = plagas.get(position)

        holder.nombre.text = item.first

        holder.tarjeta.setOnClickListener {
            val intent = Intent(context, DetallePrevencionActivity::class.java)
            intent.putExtra("nombre", item.first)
            context.startActivity(intent)
        }

        val bitmap = ImageHelper.getImageBitmapByResId(context, item.second)
        if (bitmap != null) {
            holder.imagen.setImageBitmap(bitmap)
        } else {
            holder.imagen.setImageResource(R.drawable.ic_launcher_background)
        }
    }
}