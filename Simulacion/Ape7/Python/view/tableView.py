from typing import List
from model.escenario import Escenario

class TableView:
    @staticmethod
    def mostrar_resultados(escenarios: List[Escenario], analisis_tiempo: dict) -> None:
        print("\n=== RESULTADOS MONTE CARLO (ESTIMACIÓN DE PI) ===")
        encabezado = f"{'N':>10} | {'Pi Estimado':>12} | {'Error Abs.':>12} | {'Tiempo Python (s)':>17} | {' % Tiempo':>9}"
        print(encabezado)
        print("-" * len(encabezado))

        porcentajes = analisis_tiempo["porcentaje_por_escenario"]
        for e in escenarios:
            pct = porcentajes[e.n_puntos]
            print(f"{e.n_puntos:>10} | {e.pi_estimado:>12.6f} | {e.error_absoluto:>12.6f} | {e.tiempo_python:>17.6f} | {pct:>8.2f}%")

        print("-" * len(encabezado))
        print(f"Tiempo total de simulación : {analisis_tiempo['tiempo_total']:.6f} s")
        print(f"Tiempo promedio por muestra: {analisis_tiempo['tiempo_promedio']:.6f} s\n")