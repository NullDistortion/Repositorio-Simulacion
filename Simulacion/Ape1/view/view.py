import matplotlib.pyplot as plt

class VistaClima: #Clase creada con ayuda de IA 
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
            
        plt.title("Resultados de Simulación Atmosférica (24h)", fontweight="bold", pad=20)
        plt.tight_layout()
        plt.show()