package com.example.aplicacionplagas.ui.historial

import com.example.aplicacionplagas.data.database.entity.RegistroEntity

interface OnItemClickedRegistro {
    fun onItemClicked(registroCaptura: RegistroEntity)
    fun onItemDeleted(registroCaptura: RegistroEntity)

}
