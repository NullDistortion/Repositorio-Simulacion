from model.model import LCGScenario
from view.view import ConsoleView
from service.graphic_generator import GraphicGenerator
from controller.controller import LCGController


if __name__ == "__main__":
    A = 1664525
    C = 1013904223
    M = 2**32
    N = 10000

    scenario_base = LCGScenario(
        name="Escenario Base",
        a=A,
        c=C,
        m=M,
        seed=12345,
        n_values=N
    )

    scenario_alt = LCGScenario(
        name="Escenario Semilla Alternativa",
        a=A,
        c=C,
        m=M,
        seed=98765,
        n_values=N
    )

    controller = LCGController(
        view=ConsoleView(),
        graphic_generator=GraphicGenerator()
    )
    controller.run_practice([scenario_base, scenario_alt], obs_limit=20)