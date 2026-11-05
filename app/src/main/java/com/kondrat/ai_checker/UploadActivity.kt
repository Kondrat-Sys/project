package com.kondrat.ai_checker // Обратите внимание: пакет взят из вашего скриншота!

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class UploadActivity : AppCompatActivity() {

    private lateinit var ivSelectedImage: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnAnalyze: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvResult: TextView
    private var selectedImageUri: Uri? = null

    // Регистрация контракта для выбора изображения из галереи
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            ivSelectedImage.setImageURI(uri)
            btnAnalyze.isEnabled = true
            tvResult.visibility = View.GONE // Сбрасываем старый результат
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        // Инициализация View (привязка к элементам из XML)
        ivSelectedImage = findViewById(R.id.ivSelectedImage)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        btnAnalyze = findViewById(R.id.btnAnalyze)
        progressBar = findViewById(R.id.progressBar)
        tvResult = findViewById(R.id.tvResult)

        // Обработчик нажатия на кнопку выбора фото
        btnSelectImage.setOnClickListener {
            // Открываем системную галерею, запрашивая только картинки ("image/*")
            pickImageLauncher.launch("image/*")
        }

        // Обработчик нажатия на кнопку анализа
        btnAnalyze.setOnClickListener {
            simulateAnalysis()
        }
    }

    // Имитация процесса анализа (заглушка до интеграции ML-модели)
    private fun simulateAnalysis() {
        if (selectedImageUri == null) return

        // Показываем загрузку
        progressBar.visibility = View.VISIBLE
        btnAnalyze.isEnabled = false
        btnSelectImage.isEnabled = false
        tvResult.visibility = View.GONE

        // Имитируем сетевой запрос к нашему бэкенду (задержка 2 секунды)
        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.GONE
            btnAnalyze.isEnabled = true
            btnSelectImage.isEnabled = true

            // Выводим фейковый результат
            tvResult.text = "Сгенерировано ИИ: 94%"
            tvResult.visibility = View.VISIBLE
        }, 2000)
    }
}