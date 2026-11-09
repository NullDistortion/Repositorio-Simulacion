import matplotlib.pyplot as plt
from model.KS import KSModel

class GraphicsKS:
    def plot(self, result: KSModel) -> None:
        plt.figure(figsize=(9, 5))
        plt.step(result.sorted_numbers, result.empirical_cdf, where="post", label="Distribución Acumulada Empírica Fn(x)", color="navy", linewidth=1.5)
        plt.plot([0, 1], [0, 1], linestyle="--", color="crimson", linewidth=1.5, label="Distribución Acumulada Teórica F(x) = x")
        plt.title(f"Prueba Kolmogorov-Smirnov (D = {result.ks_stat:.4f}, p = {result.p_value:.4f})")
        plt.xlabel("Valor pseudoaleatorio x")
        plt.ylabel("Probabilidad acumulada")
        plt.legend()
        plt.grid(True, linestyle=":", alpha=0.6)
        plt.tight_layout()
        plt.show()