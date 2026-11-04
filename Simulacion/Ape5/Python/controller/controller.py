from typing import List
from model.model import LCGScenario, SimulationRun
from generator.number_generator import NumberGenerator
from service.analyzer import Analyzer
from service.graphic_generator import GraphicGenerator
from view.view import ConsoleView


class LCGController:

    def __init__(self, view: ConsoleView, graphic_generator: GraphicGenerator) -> None:
        self.view = view
        self.graphic_generator = graphic_generator

    def execute_scenario(self, scenario: LCGScenario) -> SimulationRun:
        x_values, u_values = NumberGenerator.generate(scenario)
        analysis = Analyzer.analyze_period(scenario, x_values)
        return SimulationRun(
            scenario=scenario,
            x_values=x_values,
            u_values=u_values,
            analysis=analysis
        )

    def run_practice(self, scenarios: List[LCGScenario], obs_limit: int = 20) -> None:
        runs: List[SimulationRun] = []

        for sc in scenarios:
            run = self.execute_scenario(sc)
            runs.append(run)
            self.view.show_first_observations(run, limit=obs_limit)
            self.view.show_period_analysis(run)

        if len(runs) >= 2:
            self.view.show_seed_comparison(runs[0], runs[1], limit=obs_limit)

        self.graphic_generator.plot_runs(runs)