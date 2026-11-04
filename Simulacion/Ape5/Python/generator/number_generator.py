from typing import List, Tuple
from model.model import LCGScenario

class NumberGenerator:

    @staticmethod
    def generate(scenario: LCGScenario) -> Tuple[List[int], List[float]]:
        x_values: List[int] = []
        u_values: List[float] = []

        x_current = scenario.seed
        a = scenario.a
        c = scenario.c
        m = scenario.m

        for _ in range(scenario.n_values):
            x_current = (a * x_current + c) % m
            u_current = x_current / m
            x_values.append(x_current)
            u_values.append(u_current)

        return x_values, u_values