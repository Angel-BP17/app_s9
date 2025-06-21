package com.example.app_s9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat.applyTheme

class UserProfileActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSaveProfile: Button
    private lateinit var buttonBack: Button
    private lateinit var mainLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        // Inicializar vistas
        mainLayout = findViewById(R.id.main)
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        editTextName = findViewById(R.id.editTextProfileName)
        editTextAge = findViewById(R.id.editTextProfileAge)
        editTextEmail = findViewById(R.id.editTextProfileEmail)
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile)
        buttonBack = findViewById(R.id.buttonBack)

        // Configurar listeners
        buttonSaveProfile.setOnClickListener { saveProfile() }
        buttonBack.setOnClickListener { finish() }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        applySavedTheme()
    }

    private fun applySavedTheme() {
        val isDarkMode = sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_DARK_MODE, false)
        applyTheme(isDarkMode)
    }

    private fun applyTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_background))
            applyTextColorToAll(R.color.dark_text, R.color.dark_hint)
        } else {
            mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_background))
            applyTextColorToAll(R.color.light_text, R.color.light_hint)
        }
    }

    private fun applyTextColorToAll(textColorResId: Int, hintColorResId: Int) {
        val textColor = ContextCompat.getColor(this, textColorResId)
        val hintColor = ContextCompat.getColor(this, hintColorResId)

        // Lista de componentes
        val editTexts = listOf<EditText>(
            findViewById(R.id.editTextProfileName),
            findViewById(R.id.editTextProfileAge),
            findViewById(R.id.editTextProfileEmail),
        )

        editTexts.forEach {
            it.setTextColor(textColor)
            it.setHintTextColor(hintColor)
        }
    }

    private fun saveProfile() {
        val name = editTextName.text.toString().trim()
        val age = editTextAge.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (name.isEmpty() || age.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar formato de email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingresa un email válido", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar datos del perfil
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_PROFILE_NAME, name)
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_PROFILE_AGE, age.toInt())
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_PROFILE_EMAIL, email)

        setResult(RESULT_OK)
        Toast.makeText(this, "Perfil guardado exitosamente", Toast.LENGTH_SHORT).show()

        finish()
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}