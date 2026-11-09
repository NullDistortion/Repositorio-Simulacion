from model.chi import ChiModel
from model.KS import KSModel

class View:
    def show_generated_numbers(self, numbers: list[float], preview_limit: int = 20) -> None:
        total = len(numbers)
        print(f"=== SECUENCIA GENERADA (Total: {total} números) ===")
        if total <= preview_limit:
            print([round(x, 6) for x in numbers])
        else:
            mitad = preview_limit // 2
            primeros = [round(x, 6) for x in numbers[:mitad]]
            ultimos = [round(x, 6) for x in numbers[-mitad:]]
            print(f"Primeros {mitad}: {primeros}")
            print(f"Últimos  {mitad}: {ultimos}")
        print()

    def show_chi_results(self, result: ChiModel) -> None:
        print("=== RESULTADOS PRUEBA CHI-CUADRADO ===")
        print(f"{'Intervalo':<15} | {'Frec. Observada (Oi)':<22} | {'Frec. Esperada (Ei)':<20}")
        print("-" * 63)
        for i in range(result.intervals_count):
            low = result.interval_edges[i]
            high = result.interval_edges[i + 1]
            obs = result.observed_frequencies[i]
            exp = result.expected_frequencies[i]
            interval_str = f"[{low:.1f}, {high:.1f})" if i < result.intervals_count - 1 else f"[{low:.1f}, {high:.1f}]"
            print(f"{interval_str:<15} | {obs:<22} | {exp:<20.2f}")
        print("-" * 63)
        print(f"Estadístico Chi-cuadrado : {result.chi_square_stat:.6f}")
        print(f"Grados de libertad (gl)  : {result.degrees_of_freedom}")
        print(f"Valor p                  : {result.p_value:.6f}")
        print(f"Nivel de significancia   : {result.alpha}")
        print(f"Decisión                 : {result.decision}\n")

    def show_ks_results(self, result: KSModel) -> None:
        print("=== RESULTADOS PRUEBA KOLMOGOROV-SMIRNOV ===")
        print(f"Cantidad de datos (N)    : {result.n}")
        print(f"Mayor dif. positiva (D+) : {result.d_plus_max:.6f}")
        print(f"Mayor dif. negativa (D-) : {result.d_minus_max:.6f}")
        print(f"Estadístico KS (D)       : {result.ks_stat:.6f}")
        print(f"Valor p                  : {result.p_value:.6f}")
        print(f"Nivel de significancia   : {result.alpha}")
        print(f"Decisión                 : {result.decision}\n")