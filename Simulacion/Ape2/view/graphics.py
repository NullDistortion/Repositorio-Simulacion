import matplotlib.pyplot as plt

class GraphicsView:
    def display_comparison(self, results, datos_reales):
        plt.figure(figsize=(10, 5))
        
        t_reales = range(len(datos_reales))
        plt.plot(t_reales, datos_reales, 'ko-', label="Datos Reales", linewidth=2)
        
        colores = {"deterministico": "blue", "discreto": "orange", "estocastico": "green", "continuo": "red"}
        
        for nombre, (t, P) in results.items():
            plt.plot(t, P, label=f"Modelo {nombre.capitalize()}", color=colores.get(nombre))

        plt.xlabel("Tiempo")
        plt.ylabel("Población")
        plt.title("Comparación de Modelos de Crecimiento Poblacional")
        plt.grid(True)
        plt.legend()
        plt.show()