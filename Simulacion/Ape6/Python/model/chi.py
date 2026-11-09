from dataclasses import dataclass

@dataclass
class ChiModel:
    intervals_count: int
    interval_edges: list[float]
    observed_frequencies: list[int]
    expected_frequencies: list[float]
    chi_square_stat: float
    degrees_of_freedom: int
    p_value: float
    alpha: float
    reject_h0: bool
    decision: str