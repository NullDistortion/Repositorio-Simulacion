from controller.controller import ControladorClima


def main():
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

    while True:
        print("1. usar parametros base")
        print("2. Ingresar nuevos parametros")
        opcion = input("Seleccione una opcion: ").strip()

        if opcion == "1":
            app = ControladorClima(datos_observacion, w_h=0.5, w_n=0.3, w_tf=0.2)
            app.ejecutar_simulacion()
            break

        elif opcion == "2":
            while True:
                try:
                    w_h_str = input("Ingrese el primer parametro (w_h): ").strip()
                    w_n_str = input("Ingrese el segundo parametro (w_n): ").strip()
                    w_tf_str = input("Ingrese el tercer parametro (w_tf): ").strip()

                    w_h = float(w_h_str.replace(",", "."))
                    w_n = float(w_n_str.replace(",", "."))
                    w_tf = float(w_tf_str.replace(",", "."))

                    suma = w_h + w_n + w_tf
                    if abs(suma - 1.0) < 1e-9:
                        app = ControladorClima(datos_observacion, w_h=w_h, w_n=w_n, w_tf=w_tf)
                        app.ejecutar_simulacion()
                        return
                    else:
                        print("Error: la sumatoria de los 3 parametros debe ser exactamente 1.")
                        print(f"Sumatoria actual: {suma:.6f}")
                        print("Por favor, ingrese nuevamente los parametros.\n")
                except ValueError:
                    print("Error: Ingrese valores numericos validos.")
                    print("Por favor, ingrese nuevamente los parametros.\n")
                except Exception as e:
                    print(f"Error: {e}\n")
            break
        else:
            print("Opcion invalida. Seleccione 1 o 2.\n")


if __name__ == "__main__":
    main()