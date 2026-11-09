from dataclasses import dataclass

@dataclass
class KSModel:
    n: int
    sorted_numbers: list[float]
    empirical_cdf: list[float]
    theoretical_cdf: list[float]
    diff_positive: list[float]
    diff_negative: list[float]
    d_plus_max: float
    d_minus_max: float
    ks_stat: float
    p_value: float
    alpha: float
    reject_h0: bool
    decision: str