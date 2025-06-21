# App S9 - SharedPreferences Demo

Aplicación Android de ejemplo que demuestra el uso básico de SharedPreferences para almacenamiento persistente de datos.

## 📱 Descripción

Esta aplicación implementa un sistema simple de SharedPreferences que permite:
- Guardar y recuperar datos del perfil usuario
- Contar la cantidad de visitas de la app
- Cambiar el tema de la aplicación a modo oscuro

## 🚀 Características

- **SharedPreferencesHelper**: Clase wrapper para simplificar el uso de SharedPreferences
- **Tipos de datos soportados**: String, Boolean, Int, Float, Long
- **Interfaz simple**: Campos de entrada y botones para interactuar con las preferencias
- **Persistencia**: Los datos se mantienen incluso después de cerrar la aplicación

## 📋 Requisitos

- Android Studio Arctic Fox o superior
- SDK mínimo: API 21 (Android 5.0)
- SDK objetivo: API 34 (Android 14)
- Kotlin 1.9.0

## 🛠️ Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/GxJohan/app_s9.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza el proyecto con Gradle

4. Ejecuta la aplicación en un emulador o dispositivo físico

## 💻 Uso

1. **Guardar datos de perfil**: Ingresa tu nombre, edad y correo y presiona "Guardar Perfil"
2. **Cargar datos**: Carga los datos en la vista principal de la aplicación
3. **Modo oscuro**: Cambia el tema de la aplicación a "Modo oscuro" en tiempo real y guarda dicha configuración

## 📂 Estructura del Proyecto

```
app_s9/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/app_s9/
│           │   ├── MainActivity.kt
│           │   └── SharedPreferencesHelper.kt
|           |   └── UserProfileActivity.kt
│           └── res/
│               └── layout/
│                   └── activity_main.xml
|                   └── activity_user_profile.xml
└── SharedPreferences_Guide.md
```

## 📖 Documentación

Para más detalles sobre la implementación y cómo extender la funcionalidad, consulta [SharedPreferences_Guide.md](SharedPreferences_Guide.md)

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la Licencia MIT.
