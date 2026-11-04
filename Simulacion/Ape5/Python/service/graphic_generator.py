from typing import List
import matplotlib.pyplot as plt
from model.model import SimulationRun


class GraphicGenerator:

    @staticmethod
    def plot_runs(runs: List[SimulationRun], subset_points: int = 200) -> None:
        num_runs = len(runs)
        fig, axes = plt.subplots(num_runs, 2, figsize=(13, 4.5 * num_runs))
        if num_runs == 1:
            axes = [axes]

        for row_idx, run in enumerate(runs):
            u_vals = run.u_values
            seed = run.scenario.seed

            ax_seq = axes[row_idx][0]
            ax_seq.plot(
                range(1, subset_points + 1),
                u_vals[:subset_points],
                marker="o",
                markersize=3,
                linewidth=0.8
            )
            ax_seq.set_title(f"{run.scenario.name} (Semilla={seed}) - Primeros {subset_points} valores U(n)")
            ax_seq.set_xlabel("Índice n")
            ax_seq.set_ylabel("U(n)")
            ax_seq.grid(True, linestyle="--", alpha=0.5)

            ax_hist = axes[row_idx][1]
            ax_hist.hist(u_vals, bins=20, edgecolor="black", alpha=0.75)
            ax_hist.axhline(len(u_vals) / 20, color="red", linestyle="--", label="Frecuencia esperada")
            ax_hist.set_title(f"{run.scenario.name} (Semilla={seed}) - Histograma ({len(u_vals)} valores)")
            ax_hist.set_xlabel("Intervalo U(n)")
            ax_hist.set_ylabel("Frecuencia")
            ax_hist.legend()
            ax_hist.grid(True, linestyle="--", alpha=0.5)

        plt.tight_layout()
        plt.show()