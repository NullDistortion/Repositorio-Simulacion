import matplotlib.pyplot as plt

class VistaClima:  # Clase creada con ayuda de IA
    def mostrar_tabla_resultados(self, datos, encabezados):
        fig, ax = plt.subplots(figsize=(12, 4))
        ax.axis('off')
        ax.axis('tight')

        # Creación de la tabla
        tabla = ax.table(
            cellText=datos,
            colLabels=encabezados,
            loc='center',
            cellLoc='center'
        )

        # Estilos de tabla
        tabla.auto_set_font_size(False)
        tabla.set_fontsize(10)
        tabla.scale(1.2, 1.5)

        # Estilo para los encabezados
        for i in range(len(encabezados)):
            tabla[(0, i)].set_facecolor('#206385')
            tabla[(0, i)].set_text_props(color='white', fontweight='bold')

        plt.title("Resultados de Simulacion Atmosferica (24h)", fontweight="bold", pad=20)
        plt.tight_layout()
        plt.show()

       # -----------------------------
        # Grafica para las practicas
        # -----------------------------
        try:
            horas = [d[0] for d in datos]
            P = [float(d[7]) for d in datos]

            plt.figure(figsize=(10, 5))
            plt.plot(horas, P, marker="o", label="Índice de lluvia")
            plt.axhline(y=0.40, color="gray", linestyle="--", label="Umbral Baja posibilidad (0.40)")
            plt.axhline(y=0.60, color="orange", linestyle="--", label="Umbral Lluvia probable (0.60)")
            plt.axhline(y=0.75, color="red", linestyle="--", label="Umbral Lluvia (0.75)")
            plt.xlabel("Hora")
            plt.ylabel("Índice I")
            plt.title("Modelo de Simulación Atmosférica")
            plt.grid(True)
            plt.legend()
            plt.tight_layout()
            plt.show()
        except Exception as e:
            print(f"Error al generar la gráfica: {e}")