from model.model import SimulationRun


class ConsoleView:

    @staticmethod
    def show_first_observations(run: SimulationRun, limit: int = 20) -> None:
        sc = run.scenario
        print("=" * 65)
        print(f"ESCENARIO: {sc.name}")
        print(f"Parámetros: a={sc.a}, c={sc.c}, m={sc.m}, semilla={sc.seed}")
        print("-" * 65)
        print(f"{'n':<6} | {'X(n)':<15} | {'U(n)':<20}")
        print("-" * 65)
        for i in range(min(limit, len(run.x_values))):
            print(f"{i + 1:<6} | {run.x_values[i]:<15} | {run.u_values[i]:<20.12f}")
        print("=" * 65)

    @staticmethod
    def show_period_analysis(run: SimulationRun) -> None:
        an = run.analysis
        print(f"\n[Análisis de Período - {run.scenario.name}]")
        print(f"  Muestra evaluada        : {an.sample_size} valores")
        print(f"  Valores únicos en muestra: {an.unique_values}")
        if an.empirical_period_found:
            print(f"  Ciclo empírico detectado: Sí (índice {an.first_repeat_index}, longitud = {an.cycle_length})")
        else:
            print("  Ciclo empírico detectado: No se repiten valores dentro de los 10 000 datos.")
        print(f"  Cumple Hull-Dobell      : {'Sí' if an.hull_dobell_full_period else 'No'}")
        if an.hull_dobell_full_period:
            print(f"  Período teórico exacto  : m = {an.theoretical_period} (2^32)")
        print()

    @staticmethod
    def show_seed_comparison(run_a: SimulationRun, run_b: SimulationRun, limit: int = 20) -> None:
        print("=" * 80)
        print(
            f"COMPARACIÓN DE LAS PRIMERAS {limit} OBSERVACIONES "
            f"(Semilla 1 = {run_a.scenario.seed} vs Semilla 2 = {run_b.scenario.seed})"
        )
        print("-" * 80)
        header = (
            f"{'n':<4} | {'X1(n)':<12} | {'U1(n)':<14} || "
            f"{'X2(n)':<12} | {'U2(n)':<14} | {'|U1 - U2|':<12}"
        )
        print(header)
        print("-" * 80)
        for i in range(min(limit, len(run_a.x_values), len(run_b.x_values))):
            u1 = run_a.u_values[i]
            u2 = run_b.u_values[i]
            diff = abs(u1 - u2)
            print(
                f"{i + 1:<4} | {run_a.x_values[i]:<12} | {u1:<14.10f} || "
                f"{run_b.x_values[i]:<12} | {u2:<14.10f} | {diff:<12.10f}"
            )
        print("=" * 80)