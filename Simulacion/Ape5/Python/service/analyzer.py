import math
from typing import List, Optional
from model.model import LCGScenario, PeriodAnalysisResult


class Analyzer:

    @staticmethod
    def _prime_factors(n: int) -> List[int]:
        factors: List[int] = []
        if n % 2 == 0:
            factors.append(2)
            while n % 2 == 0:
                n //= 2
        d = 3
        while d * d <= n:
            if n % d == 0:
                factors.append(d)
                while n % d == 0:
                    n //= d
            d += 2
        if n > 1:
            factors.append(n)
        return factors

    @classmethod
    def check_hull_dobell(cls, a: int, c: int, m: int) -> bool:
        if c == 0:
            return False
        if math.gcd(c, m) != 1:
            return False
        for p in cls._prime_factors(m):
            if (a - 1) % p != 0:
                return False
        if m % 4 == 0 and (a - 1) % 4 != 0:
            return False
        return True

    @classmethod
    def analyze_period(cls, scenario: LCGScenario, x_values: List[int]) -> PeriodAnalysisResult:
        seen_indices: dict[int, int] = {scenario.seed: 0}
        first_repeat_idx: Optional[int] = None
        cycle_len: Optional[int] = None

        for idx, val in enumerate(x_values, start=1):
            if val in seen_indices:
                first_repeat_idx = idx
                cycle_len = idx - seen_indices[val]
                break
            seen_indices[val] = idx

        full_period = cls.check_hull_dobell(scenario.a, scenario.c, scenario.m)

        return PeriodAnalysisResult(
            sample_size=len(x_values),
            unique_values=len(set(x_values)),
            empirical_period_found=(first_repeat_idx is not None),
            first_repeat_index=first_repeat_idx,
            cycle_length=cycle_len,
            hull_dobell_full_period=full_period,
            theoretical_period=scenario.m if full_period else -1
        )