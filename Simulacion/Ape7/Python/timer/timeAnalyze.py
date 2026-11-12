from typing import List
from model.escenario import Escenario

class TimeAnalyze:
    @staticmethod
    def analizar_tiempos(escenarios: List[Escenario]) -> dict:
        tiempos = [e.tiempo_python for e in escenarios]
        total = sum(tiempos)
        promedio = total / len(tiempos) if tiempos else 0.0
        porcentajes = {
            e.n_puntos: (e.tiempo_python / total * 100.0) if total > 0 else 0.0
            for e in escenarios
        }
        return {
            "tiempo_total": total,
            "tiempo_promedio": promedio,
            "porcentaje_por_escenario": porcentajes
        }