import math
from model.escenario import Escenario
from service.monteCarloGenerator import MonteCarloGenerator
from timer.timer import Timer

class Calculator:
    # Cuadro estadístico Chi-cuadrado para alfa = 0.05 (gl = k - 1)
    TABLA_CHI_ALFA_005 = {
        9: 16.9190,   # k = 10 intervalos
        19: 30.1435,  # k = 20 intervalos
        99: 123.2252  # k = 100 intervalos
    }

    def __init__(self, generador: MonteCarloGenerator, timer: Timer):
        self.generador = generador
        self.timer = timer

    def ejecutar_escenario(self, n_puntos: int, k_intervalos: int = 10) -> Escenario:
        # 1. Fase Monte Carlo cronometrada aisladamente
        self.generador.reiniciar()
        puntos_dentro = 0

        self.timer.iniciar()
        for _ in range(n_puntos):
            x = self.generador.siguiente_uniforme()
            y = self.generador.siguiente_uniforme()
            if (x * x + y * y) <= 1.0:
                puntos_dentro += 1
        tiempo_ejecucion = self.timer.detener()

        pi_estimado = 4.0 * (puntos_dentro / n_puntos)
        error_absoluto = abs(pi_estimado - math.pi)

        # 2. Fase de validación estadística Chi-cuadrado (sobre los 2*N números usados)
        self.generador.reiniciar()
        total_numeros = 2 * n_puntos
        frecuencias_obs = [0] * k_intervalos

        for _ in range(total_numeros):
            u = self.generador.siguiente_uniforme()
            indice = int(u * k_intervalos)
            if indice == k_intervalos:
                indice = k_intervalos - 1
            frecuencias_obs[indice] += 1

        frecuencia_esp = total_numeros / k_intervalos
        chi_calc = sum(
            ((obs - frecuencia_esp) ** 2) / frecuencia_esp
            for obs in frecuencias_obs
        )

        grados_libertad = k_intervalos - 1
        chi_crit = self.TABLA_CHI_ALFA_005.get(grados_libertad, 16.9190)
        cumple = chi_calc <= chi_crit

        return Escenario(
            n_puntos=n_puntos,
            puntos_dentro=puntos_dentro,
            pi_estimado=pi_estimado,
            error_absoluto=error_absoluto,
            tiempo_python=tiempo_ejecucion,
            total_numeros_generados=total_numeros,
            intervalos_k=k_intervalos,
            frecuencias_observadas=frecuencias_obs,
            frecuencia_esperada=frecuencia_esp,
            chi_calculado=chi_calc,
            chi_critico=chi_crit,
            cumple_uniformidad=cumple
        )