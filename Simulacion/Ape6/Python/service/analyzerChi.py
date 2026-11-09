import math
from model.chi import ChiModel

class AnalyzerChi:
    def __init__(self, intervals: int = 10, alpha: float = 0.05):
        self.intervals = intervals  # Paso 2 y 3: 10 subintervalos de igual amplitud
        self.alpha = alpha          # Nivel de significancia 0.05

    def _regularized_upper_gamma(self, a: float, x: float) -> float:
        """Calcula Q(a, x) = 1 - P(a, x) para obtener el valor p de Chi-cuadrado."""
        if x <= 0.0:
            return 1.0
        # Serie para la función gamma incompleta inferior P(a, x)
        term = 1.0 / a
        total = term
        for n in range(1, 1000):
            term *= x / (a + n)
            total += term
            if abs(term) < 1e-12 * abs(total):
                break
        log_p = a * math.log(x) - x - math.lgamma(a) + math.log(total)
        p_lower = min(1.0, max(0.0, math.exp(log_p)))
        return 1.0 - p_lower

    def analyze(self, numbers: list[float]) -> ChiModel:
        n = len(numbers)
        k = self.intervals
        # Paso 3: Subintervalos de igual amplitud en [0, 1]
        edges = [i / k for i in range(k + 1)]
        # Paso 4: Frecuencia esperada E_i = N / k
        expected_freq = n / k
        expected_list = [expected_freq] * k

        # Pasos 5 y 6: Determinar intervalo y contabilizar frecuencias observadas
        observed_list = [0] * k
        for num in numbers:
            idx = int(num * k)
            if idx >= k:
                idx = k - 1
            elif idx < 0:
                idx = 0
            observed_list[idx] += 1

        # Pasos 7 y 8: Calcular estadístico Chi-cuadrado
        chi_sq = 0.0
        for obs, exp in zip(observed_list, expected_list):
            diff = obs - exp
            chi_sq += (diff ** 2) / exp

        # Paso 9: Grados de libertad gl = k - 1
        df = k - 1

        # Paso 10: Valor p utilizando la distribución Chi-cuadrado
        p_val = self._regularized_upper_gamma(df / 2.0, chi_sq / 2.0)

        # Pasos 11, 12 y 13: Comparación con nivel de significancia 0.05
        reject = p_val < self.alpha
        if not reject:
            decision = "No se rechaza la hipótesis nula (H0: X ~ U(0,1))."
        else:
            decision = "Se rechaza la hipótesis nula (H0: X ~ U(0,1))."

        return ChiModel(
            intervals_count=k,
            interval_edges=edges,
            observed_frequencies=observed_list,
            expected_frequencies=expected_list,
            chi_square_stat=chi_sq,
            degrees_of_freedom=df,
            p_value=p_val,
            alpha=self.alpha,
            reject_h0=reject,
            decision=decision
        )