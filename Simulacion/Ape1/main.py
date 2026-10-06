from controller.controller import ControladorClima

if __name__ == "__main__":
    datos_observacion = [
        {"Hora": "06:00", "Humedad": 65, "Nubosidad": 40, "Temp": 14},
        {"Hora": "08:00", "Humedad": 70, "Nubosidad": 50, "Temp": 16},
        {"Hora": "10:00", "Humedad": 68, "Nubosidad": 45, "Temp": 18},
        {"Hora": "12:00", "Humedad": 60, "Nubosidad": 30, "Temp": 22},
        {"Hora": "14:00", "Humedad": 75, "Nubosidad": 70, "Temp": 20},
        {"Hora": "16:00", "Humedad": 85, "Nubosidad": 85, "Temp": 18},
        {"Hora": "18:00", "Humedad": 92, "Nubosidad": 95, "Temp": 16},
        {"Hora": "20:00", "Humedad": 88, "Nubosidad": 90, "Temp": 17},
        {"Hora": "22:00", "Humedad": 80, "Nubosidad": 75, "Temp": 15}
    ]

    app = ControladorClima(datos_observacion)
    app.ejecutar_simulacion()