from dataclasses import dataclass
from typing import List

@dataclass
class Escenario:
    n_puntos: int
    puntos_dentro: int
    pi_estimado: float
    error_absoluto: float
    tiempo_python: float
    # Datos para la prueba Chi-cuadrado
    total_numeros_generados: int
    intervalos_k: int
    frecuencias_observadas: List[int]
    frecuencia_esperada: float
    chi_calculado: float
    chi_critico: float
    cumple_uniformidad: bool
    tiempo_java: float = 0.0  # Se completará al cruzar datos con Java