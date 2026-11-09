import math
import numpy as np
import matplotlib.pyplot as plt
from model.chi import ChiModel

class GraphicsChi:
    def _chi2_pdf(self, x: float, df: int) -> float:
        """Función de densidad de probabilidad (PDF) de la distribución Chi-cuadrado."""
        if x <= 0:
            return 0.0
        k_half = df / 2.0
        log_pdf = (k_half - 1.0) * math.log(x) - (x / 2.0) - k_half * math.log(2.0) - math.lgamma(k_half)
        return math.exp(log_pdf)

    def _regularized_upper_gamma(self, a: float, x: float) -> float:
        """Calcula el valor p acumulado en la cola derecha."""
        if x <= 0.0:
            return 1.0
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

    def _find_critical_value(self, df: int, alpha: float) -> float:
        """Obtiene el valor crítico de Chi-cuadrado mediante bisección para cualquier gl y alpha."""
        low, high = 0.0, max(100.0, df * 5.0)
        for _ in range(100):
            mid = (low + high) / 2.0
            p_val = self._regularized_upper_gamma(df / 2.0, mid / 2.0)
            if p_val > alpha:
                low = mid
            else:
                high = mid
        return (low + high) / 2.0

    def plot(self, result: ChiModel) -> None:
        df = result.degrees_of_freedom
        alpha = result.alpha
        chi_calc = result.chi_square_stat
        chi_crit = self._find_critical_value(df, alpha)

        # Límite superior dinámico del eje X para asegurar que se visualicen tanto el crítico como el calculado
        x_max = max(df + 4.5 * math.sqrt(2 * df), chi_calc * 1.25, chi_crit * 1.35)
        x_vals = np.linspace(0.01, x_max, 600)
        y_vals = np.array([self._chi2_pdf(x, df) for x in x_vals])

        fig, ax = plt.subplots(figsize=(9, 5))

        # 1. Curva de densidad f(x; k)
        ax.plot(x_vals, y_vals, color="red", linewidth=2.2, label=f"Distribución $\\chi^2$ ($gl = {df}$)")

        # 2. Región de rechazo sombreada (x >= chi_crítico)
        x_reject = x_vals[x_vals >= chi_crit]
        y_reject = y_vals[x_vals >= chi_crit]
        ax.fill_between(
            x_reject, y_reject, 0,
            color="steelblue", alpha=0.85,
            label=f"Región de rechazo de $H_0$ ($\\alpha = {alpha}$)"
        )

        # 3. Línea límite del valor crítico
        y_crit = self._chi2_pdf(chi_crit, df)
        ax.vlines(chi_crit, 0, y_crit, color="black", linestyle="-", linewidth=1.2)
        ax.axvline(
            chi_crit, color="navy", linestyle=":", linewidth=1.2,
            label=f"$\\chi^2_{{\\mathrm{{crítico}}}} = {chi_crit:.4f}$"
        )

        # 4. Línea del estadístico Chi-cuadrado calculado
        ax.axvline(
            chi_calc, color="darkgreen", linestyle="--", linewidth=2.0,
            label=f"$\\chi^2_{{\\mathrm{{calculado}}}} = {chi_calc:.4f}$ ($p = {result.p_value:.4f}$)"
        )

        # 5. Etiquetas de texto internas sobre la gráfica
        y_peak = max(y_vals)
        ax.text(
            chi_crit * 0.48, y_peak * 0.35,
            "Región de no rechazo\n(aceptación) de $H_0$",
            ha="center", va="center", fontsize=10, color="black"
        )
        ax.annotate(
            "Región de\nrechazo de $H_0$",
            xy=(chi_crit + (x_max - chi_crit) * 0.18, y_crit * 0.45),
            xytext=(chi_crit + (x_max - chi_crit) * 0.38, y_peak * 0.55),
            arrowprops=dict(facecolor="black", shrink=0.05, width=1.2, headwidth=6),
            ha="center", va="center", fontsize=10
        )

        # Configuración estética de ejes
        ax.set_xlim(0, x_max)
        ax.set_ylim(0, y_peak * 1.15)
        ax.set_title(f"Prueba de Bondad de Ajuste Chi-cuadrado — {result.decision}", fontsize=11)
        ax.set_xlabel("$\\chi^2$", fontsize=12)
        ax.set_ylabel("$f(x; gl)$", fontsize=12)
        ax.spines["top"].set_visible(False)
        ax.spines["right"].set_visible(False)
        ax.legend(loc="upper right", frameon=True)
        ax.grid(True, linestyle=":", alpha=0.4)

        plt.tight_layout()
        plt.show()