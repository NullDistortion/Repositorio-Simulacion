from typing import List

class MonteCarloGenerator:
    """
    Generador Congruencial Lineal (LCG): X_{i+1} = (a * X_i + c) mod m
    Parámetros Numerical Recipes (sin desbordamiento en enteros de 64 bits).
    """
    M = 4294967296  # 2^32
    A = 1664525
    C = 1013904223

    def __init__(self, semilla: int):
        self.semilla_inicial = semilla
        self.estado = semilla & 0xFFFFFFFF

    def reiniciar(self) -> None:
        self.estado = self.semilla_inicial & 0xFFFFFFFF

    def siguiente_uniforme(self) -> float:
        self.estado = (self.A * self.estado + self.C) % self.M
        return self.estado / self.M

    def generar_secuencia(self, cantidad: int) -> List[float]:
        return [self.siguiente_uniforme() for _ in range(cantidad)]