package com.example.aplicacionplagas

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionplagas.databinding.LayoutCapturarBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class Capturar : AppCompatActivity() {

    val binding : LayoutCapturarBinding by lazy { LayoutCapturarBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        iniciarBotonesCaptura()
    }

    private fun iniciarBotonesCaptura() {
        binding.apply{
            BGaleria.setOnClickListener{
                seleccionarImagenDeGaleria()
            }
            BCamara.setOnClickListener {

            }
        }
    }

    private fun seleccionarImagenDeGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 404)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 404 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val archivo = convertirUriAFile(uri)
                if (archivo != null) {
                    sendImageToAPI(archivo)
                }
            }
        }
    }

    private fun convertirUriAFile(uri: Uri): File? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            val nombreColumna = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            if (nombreColumna != -1) {
                val nombreArchivo = cursor.getString(nombreColumna)
                val archivo = File(cacheDir, nombreArchivo)
                try {
                    val inputStream = contentResolver.openInputStream(uri)
                    val outputStream = FileOutputStream(archivo)
                    inputStream?.copyTo(outputStream)
                    inputStream?.close()
                    outputStream.close()
                    return archivo
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    fun sendImageToAPI(imageFile: File) {
        val client = OkHttpClient()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                imageFile.name,
                RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
            )
            .build()

        val request = Request.Builder()
            .url("https://testapi-jzsq5fw66a-wl.a.run.app/predict")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if( response.isSuccessful) {
                    response.body?.string()?.let { Log.e("resultado", it) }
                }else{
                    Log.e("fallo",response.toString())
                }
            }
        })
    }

}