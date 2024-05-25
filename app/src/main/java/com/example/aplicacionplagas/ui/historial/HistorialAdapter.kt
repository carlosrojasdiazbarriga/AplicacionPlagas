package com.example.aplicacionplagas.ui.historial

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionplagas.R
import com.example.aplicacionplagas.data.DatosPlaga
import com.example.aplicacionplagas.data.database.entity.RegistroEntity
import com.example.aplicacionplagas.util.DateHelper
import com.google.gson.Gson

class HistorialAdapter : ListAdapter<RegistroEntity, HistorialAdapter.ViewHolder>(DiffCallback) {
    private lateinit var onItemClicked : OnItemClickedRegistro
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(registroCaptura: RegistroEntity, listener: OnItemClickedRegistro) {
            Log.d("Datos", registroCaptura.plaga)
            val datos = Gson().fromJson(registroCaptura.plaga, DatosPlaga::class.java)

            val tvNombre = itemView.findViewById<TextView>(R.id.tv_nombre_plaga)
            val tvFecha = itemView.findViewById<TextView>(R.id.tv_fecha)
            val tvUbicacion = itemView.findViewById<TextView>(R.id.tv_ubicacion)
            val imgFoto = itemView.findViewById<ImageView>(R.id.img_foto_plaga)
            val contenedor = itemView.findViewById<View>(R.id.mcv_plaga_item)

            tvNombre.text = datos.nombre
            tvUbicacion.text = registroCaptura.ubicacion
            tvFecha.text = DateHelper.getDate(registroCaptura.fecha)
            imgFoto.setImageURI(registroCaptura.fotoUri.toUri())

            contenedor.setOnClickListener {
                listener.onItemClicked(registroCaptura)
            }
        }
    }

    fun setOnClickListener(listener: OnItemClickedRegistro){
        onItemClicked = listener
    }

    object DiffCallback : DiffUtil.ItemCallback<RegistroEntity>(){
        override fun areItemsTheSame(oldItem: RegistroEntity, newItem: RegistroEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RegistroEntity,
            newItem: RegistroEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_registro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }
}
