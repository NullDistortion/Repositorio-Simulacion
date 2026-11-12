import math
import numpy as np
import matplotlib.pyplot as plt
from typing import List
from model.escenario import Escenario

class ChiView:
    @staticmethod
    def mostrar_prueba_chi(escenarios: List[Escenario]) -> None:
        print("=== PRUEBA DE UNIFORMIDAD CHI-CUADRADO (alfa = 0.05) ===")
        encabezado = f"{'N Puntos':>10} | {'Muestras (2N)':>13} | {'gl':>4} | {'Chi Calc':>10} | {'Chi Tabla':>10} | {'Resultado':>12}"
        print(encabezado)
        print("-" * len(encabezado))

        for e in escenarios:
            gl = e.intervalos_k - 1
            estado = "ACEPTADO" if e.cumple_uniformidad else "RECHAZADO"
            print(f"{e.n_puntos:>10} | {e.total_numeros_generados:>13} | {gl:>4} | {e.chi_calculado:>10.4f} | {e.chi_critico:>10.4f} | {estado:>12}")
        print()

    @staticmethod
    def graficar_chi_cuadrado(escenarios: List[Escenario]) -> None:
        # Configuración de la figura 2x2 para las 4 muestras solicitadas
        fig, axs = plt.subplots(2, 2, figsize=(14, 10))
        axs = axs.ravel() # Aplanar para iterar fácilmente

        for idx, e in enumerate(escenarios):
            ax = axs[idx]
            gl = e.intervalos_k - 1
            
            # 1. Calcular la curva PDF de Chi-cuadrado usando matemáticas directas
            # f(x; k) = (x^(k/2 - 1) * e^(-x/2)) / (2^(k/2) * gamma(k/2))
            x = np.linspace(0.001, 30, 1000)
            numerador = (x ** (gl / 2.0 - 1)) * np.exp(-x / 2.0)
            denominador = (2.0 ** (gl / 2.0)) * math.gamma(gl / 2.0)
            y = numerador / denominador

            # 2. Dibujar la curva principal (Distribución de Chi-cuadrado)
            ax.plot(x, y, 'r-', lw=2, label=f'f(x; gl={gl})')

            # 3. Sombrear la Región de Rechazo (>= Chi Crítico)
            x_rechazo = x[x >= e.chi_critico]
            y_rechazo = y[x >= e.chi_critico]
            ax.fill_between(x_rechazo, 0, y_rechazo, color='#2b5c8f', alpha=0.8, label='Región de rechazo de $H_0$ (α = 0.05)')

            # 4. Sombrear la Región de Aceptación (< Chi Crítico)
            x_aceptacion = x[x < e.chi_critico]
            y_aceptacion = y[x < e.chi_critico]
            ax.fill_between(x_aceptacion, 0, y_aceptacion, color='#f7f7f7', alpha=0.5, edgecolor='gray', linestyle='--')

            # 5. Línea vertical para el valor crítico
            ax.axvline(e.chi_critico, color='black', linestyle='--', lw=1.5)
            
            # 6. Marcar el Chi-cuadrado calculado en el escenario
            y_calc_interp = np.interp(e.chi_calculado, x, y)
            ax.plot(e.chi_calculado, y_calc_interp, 'go', markersize=8, label=f'Calculado ($\chi^2$ = {e.chi_calculado:.2f})')
            
            # Anotación para el valor calculado
            ax.annotate(f'$\chi^2$ Calc\n({e.chi_calculado:.2f})',
                        xy=(e.chi_calculado, y_calc_interp),
                        xytext=(e.chi_calculado - 1.5 if e.chi_calculado > 12 else e.chi_calculado + 1, y_calc_interp + 0.015),
                        arrowprops=dict(facecolor='green', shrink=0.05, width=1, headwidth=6))

            # 7. Textos y etiquetas en la gráfica
            ax.set_title(f"Muestra N = {e.n_puntos} (Datos: {e.total_numeros_generados})", fontsize=11, fontweight='bold')
            ax.set_xlabel("X (Estadístico $\chi^2$)", fontsize=9)
            ax.set_ylabel("f(x; gl)", fontsize=9)
            ax.set_xlim(0, 30)
            ax.set_ylim(0, max(y) * 1.25)
            
            # Etiquetas específicas en el eje X para el crítico
            ax.set_xticks([0, 5, 10, e.chi_critico, 20, 25, 30])
            ax.set_xticklabels(['0', '5', '10', f'$\chi^2$ crit\n({e.chi_critico:.2f})', '20', '25', '30'], fontsize=8)
            
            # Textos de la región en la gráfica, similares a la referencia
            ax.text(3, max(y) * 0.4, "Región de\naceptación de $H_0$", fontsize=9, color='black', ha='center')
            
            # Apuntar a la región de rechazo con flecha si la región es angosta
            ax.annotate("Región de\nrechazo de $H_0$",
                        xy=(e.chi_critico + 3, max(y) * 0.08),
                        xytext=(21, max(y) * 0.45),
                        arrowprops=dict(arrowstyle="->", connectionstyle="arc3,rad=-0.2", color="black"),
                        fontsize=9, ha='center')

            estado_test = "ACEPTADA" if e.cumple_uniformidad else "RECHAZADA"
            ax.text(28, max(y) * 1.1, f"Uniformidad: {estado_test}", 
                    bbox=dict(facecolor='white', alpha=0.8, edgecolor='gray'), 
                    ha='right', fontsize=9, fontweight='bold', color='darkgreen' if e.cumple_uniformidad else 'crimson')

            ax.legend(loc='upper right', fontsize=8)
            ax.grid(True, linestyle=':', alpha=0.6)

        plt.suptitle("Pruebas Estadísticas Chi-cuadrado para los 4 Escenarios de Monte Carlo\n(Validación de Uniformidad en Generación Pseudoaleatoria)", fontsize=14, fontweight='bold')
        plt.tight_layout()
        plt.show()