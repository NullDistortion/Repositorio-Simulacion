from dataclasses import dataclass
from typing import List, Optional


@dataclass(frozen=True)
class LCGScenario:
    name: str
    a: int
    c: int
    m: int
    seed: int
    n_values: int = 10000


@dataclass
class PeriodAnalysisResult:
    sample_size: int
    unique_values: int
    empirical_period_found: bool
    first_repeat_index: Optional[int]
    cycle_length: Optional[int]
    hull_dobell_full_period: bool
    theoretical_period: int


@dataclass
class SimulationRun:
    scenario: LCGScenario
    x_values: List[int]
    u_values: List[float]
    analysis: PeriodAnalysisResult