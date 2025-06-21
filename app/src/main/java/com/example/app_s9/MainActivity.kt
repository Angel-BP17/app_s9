package com.example.app_s9

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat.applyTheme
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var buttonGoToProfile: Button
    private lateinit var textViewVisitCount: TextView
    private lateinit var buttonResetCounter: Button
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextUsername: EditText
    private lateinit var buttonClear: Button
    private lateinit var textViewResult: TextView
    private lateinit var textViewProfileData: TextView
    private lateinit var switchDarkMode: Switch
    private lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Inicializar SharedPreferencesHelper
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        mainLayout = findViewById(R.id.main)
        
        // Inicializar vistas
        initViews()
        
        // Configurar listeners
        setupListeners()

        //Cargar perfil
        loadProfileData()
        
        // Verificar si es la primera vez que se abre la app
        checkFirstTime()

        // Nuevo: Actualizar contador al iniciar
        updateVisitCount()

        setupDarkModeListener()
        applySavedTheme()
    }
    
    private fun initViews() {
        buttonClear = findViewById(R.id.buttonClear)
        buttonGoToProfile = findViewById(R.id.buttonGoToProfile)
        buttonResetCounter = findViewById(R.id.buttonResetCounter)
        textViewResult = findViewById(R.id.textViewResult)
        textViewVisitCount = findViewById(R.id.textViewVisitCount)
        textViewProfileData = findViewById(R.id.textViewProfileData)
        switchDarkMode = findViewById(R.id.switchDarkMode)
    }
    
    private fun setupListeners() {

        buttonClear.setOnClickListener {
            clearAllData()
        }
        buttonResetCounter.setOnClickListener {
            resetVisitCounter()
        }

        buttonGoToProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        buttonGoToProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivityForResult(intent, PROFILE_REQUEST_CODE)
        }
    }

    private fun saveData() {
        val username = editTextUsername.text.toString().trim()

        if (username.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa un nombre", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar datos
        sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, false)
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_USER_ID, (1000..9999).random())

        Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
        editTextUsername.setText("")
    }

    private fun loadData() {
        val visitCount = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_VISIT_COUNT, 0)

        textViewVisitCount.text = "Visitas: $visitCount"
    }

    private fun clearAllData() {
        // Guardar contador actual temporalmente
        val currentVisits = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_VISIT_COUNT, 0)

        // Limpiar todo
        sharedPreferencesHelper.clearAll()

        // Restaurar contador
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_VISIT_COUNT, currentVisits)

        // Restaurar estado inicial
        sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, true)
        textViewResult.text = ""
        editTextUsername.setText("")
        textViewVisitCount.text = "Visitas: $currentVisits"
        Toast.makeText(this, "Datos borrados (visitas conservadas)", Toast.LENGTH_SHORT).show()
    }
    
    private fun checkFirstTime() {
        val isFirstTime = sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, true)
        
        if (isFirstTime) {
            Toast.makeText(this, "¡Bienvenido por primera vez!", Toast.LENGTH_LONG).show()
        }
    }
    private fun updateVisitCount() {
        val currentCount = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_VISIT_COUNT, 0)
        val newCount = currentCount + 1
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_VISIT_COUNT, newCount)
        textViewVisitCount.text = "Visitas: $newCount"
    }
    private fun resetVisitCounter() {
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_VISIT_COUNT, 0)
        textViewVisitCount.text = "Visitas: 0"
        Toast.makeText(this, "Contador reiniciado", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfileData() {
        if (sharedPreferencesHelper.profileExists()) {
            val name = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_PROFILE_NAME, "")
            val age = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_PROFILE_AGE, 0)
            val email = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_PROFILE_EMAIL, "")

            val profileText = "Nombre: $name\nEdad: $age años\nEmail: $email"
            textViewProfileData.text = profileText
        } else {
            textViewProfileData.text = "Perfil no creado"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_REQUEST_CODE) {
            loadProfileData() // Actualizar datos del perfil
        }
    }

    companion object {
        private const val PROFILE_REQUEST_CODE = 1001
    }

    private fun setupDarkModeListener() {
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Guardar preferencia inmediatamente
            sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_DARK_MODE, isChecked)
            applyTheme(isChecked)
        }
    }

    private fun applySavedTheme() {
        val isDarkMode = sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_DARK_MODE, false)
        switchDarkMode.isChecked = isDarkMode
        applyTheme(isDarkMode)
    }

    private fun applyTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            // Modo oscuro
            mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_background))
            applyTextColorToAll(R.color.dark_text)
        } else {
            // Modo claro
            mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_background))
            applyTextColorToAll(R.color.light_text)
        }
    }

    private fun applyTextColorToAll(colorResId: Int) {
        val color = ContextCompat.getColor(this, colorResId)

        // Lista de todos los componentes de texto
        val textComponents = listOf<TextView>(
            findViewById(R.id.textViewResult),
            findViewById(R.id.textViewVisitCount),
            findViewById(R.id.textViewProfileTitle),
            findViewById(R.id.textViewProfileData),
        )

        // Aplicar color a todos
        textComponents.forEach { it.setTextColor(color) }

        // Cambiar color del texto del Switch
        switchDarkMode.setTextColor(color)
    }

}