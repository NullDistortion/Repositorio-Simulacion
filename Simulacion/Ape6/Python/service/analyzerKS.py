import math
from model.KS import KSModel

class AnalyzerKS:
    def __init__(self, alpha: float = 0.05):
        self.alpha = alpha

    def _kolmogorov_p_value(self, d_stat: float, n: int) -> float:
        """Aproximación de la distribución de Kolmogorov para el valor p."""
        if d_stat <= 0.0:
            return 1.0
        z = d_stat * math.sqrt(n)
        total = 0.0
        for k in range(1, 1000):
            term = ((-1) ** (k - 1)) * math.exp(-2.0 * (k ** 2) * (z ** 2))
            total += term
            if abs(term) < 1e-12:
                break
        p_val = 2.0 * total
        return min(1.0, max(0.0, p_val))

    def analyze(self, numbers: list[float]) -> KSModel:
        # Paso 2: Ordenar de menor a mayor
        sorted_nums = sorted(numbers)
        # Paso 3: Cantidad total de números
        n = len(sorted_nums)

        # Paso 4: Inicializar listas para las distribuciones y diferencias
        empirical_cdf = []
        theoretical_cdf = []
        diff_pos = []
        diff_neg = []

        # Pasos 5 y 6: Recorrer secuencialmente y calcular diferencias
        for idx, x in enumerate(sorted_nums):
            pos = idx + 1                  # Posición (1 a N)
            fn_x = pos / n                 # Distribución acumulada empírica i / N
            f_x = x                        # Distribución acumulada teórica U(0,1)
            
            d_plus = fn_x - f_x            # Diferencia positiva: (i / N) - x_(i)
            d_minus = f_x - (idx / n)      # Diferencia negativa: x_(i) - ((i - 1) / N)

            empirical_cdf.append(fn_x)
            theoretical_cdf.append(f_x)
            diff_pos.append(d_plus)
            diff_neg.append(d_minus)

        # Paso 7: Identificar la mayor diferencia positiva y negativa
        d_plus_max = max(diff_pos) if diff_pos else 0.0
        d_minus_max = max(diff_neg) if diff_neg else 0.0

        # Paso 8: Estadístico KS como la mayor de las dos diferencias
        ks_stat = max(d_plus_max, d_minus_max)

        # Paso 9: Obtener valor p
        p_val = self._kolmogorov_p_value(ks_stat, n)

        # Pasos 10, 11 y 12: Comparar con nivel de significancia 0.05
        reject = p_val < self.alpha
        if not reject:
            decision = "No se rechaza la hipótesis nula (H0: X ~ U(0,1))."
        else:
            decision = "Se rechaza la hipótesis nula (H0: X ~ U(0,1))."

        return KSModel(
            n=n,
            sorted_numbers=sorted_nums,
            empirical_cdf=empirical_cdf,
            theoretical_cdf=theoretical_cdf,
            diff_positive=diff_pos,
            diff_negative=diff_neg,
            d_plus_max=d_plus_max,
            d_minus_max=d_minus_max,
            ks_stat=ks_stat,
            p_value=p_val,
            alpha=self.alpha,
            reject_h0=reject,
            decision=decision
        )