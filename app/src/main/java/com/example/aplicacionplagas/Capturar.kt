package com.example.aplicacionplagas

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.aplicacionplagas.data.Resultado
import com.example.aplicacionplagas.databinding.LayoutCapturarBinding
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit


class Capturar : AppCompatActivity() {
    private val CAMERA_PERMISSION_REQUEST_CODE = 505
    private val REQUEST_IMAGE_CAPTURE = 101
    private val REQUEST_GALLERY_IMAGE = 404

    private lateinit var progressDialog: ProgressDialog
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
                obtenerFotoCamara()
            }
        }
    }

    private fun obtenerFotoCamara() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun seleccionarImagenDeGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_IMAGE)
    }

    private fun crearArchivoImagen(): File {
        // Create an image file name
        val timeStamp: String = System.currentTimeMillis().toString()
        val storageDir: File? = getExternalFilesDir(null)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun guardarArchivo(bitmap: Bitmap, photoFile: File) {
        val outputStream: OutputStream = FileOutputStream(photoFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
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

    private fun sendImageToAPI(imageFile: File) {
        val TIMEOUT_SECONDS = 10L
        displayLoader()

        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

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
                if (e is SocketTimeoutException) {
                    runOnUiThread {
                        Toast.makeText(this@Capturar, "Tiempo de espera agotado", Toast.LENGTH_SHORT).show()
                    }
                }
                hideLoader()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.string()?.let {
                        traerDatosPlaga(it)
                        Log.i("resultado", it)
                    }
                } else {
                    Log.e("fallo", response.toString())
                    hideLoader()
                }
            }
        })
    }

    private fun traerDatosPlaga(resultado: String) {
        val result: Result = Gson().fromJson(resultado, Result::class.java)

        val TIMEOUT_SECONDS = 10L

        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url("https://fastapi-jzsq5fw66a-wl.a.run.app/plagas/" + result.classProbabilities.obtenerCampoConProbabilidadMayor())
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                if (e is SocketTimeoutException) {
                    runOnUiThread {
                        Toast.makeText(this@Capturar, "Tiempo de espera agotado", Toast.LENGTH_SHORT).show()
                    }
                }
                hideLoader()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    if (!responseData.isNullOrEmpty()) {
                        Log.i("resultado", responseData)
                        val intent = Intent(this@Capturar, Resultado::class.java)
                        intent.putExtra("resultado", responseData)
                        startActivity(intent)
                    } else {
                        Log.e("error", "El cuerpo de la respuesta es nulo")
                    }
                } else {
                    Log.e("error", response.message)
                }
                hideLoader()
            }
        })
    }

    private fun displayLoader() {
        progressDialog = ProgressDialog(this)
        progressDialog.apply {
            setMessage("Analizando...")
            setCancelable(false)
            show()
        }
    }

    private fun hideLoader() {
        progressDialog.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val archivo = convertirUriAFile(uri)
                if (archivo != null) {
                    sendImageToAPI(archivo)
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            val photoFile = crearArchivoImagen()
            guardarArchivo(bitmap, photoFile)
            sendImageToAPI(photoFile)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}