from numberTreatment.numberGenerator import NumberGenerator
from numberTreatment.numberArrayer import NumberArrayer
from service.analyzerChi import AnalyzerChi
from service.analyzerKS import AnalyzerKS
from view.view import View
from view.graphicsChi import GraphicsChi
from view.graphicsKS import GraphicsKS

class Controller:
    def __init__(
        self,
        generator: NumberGenerator,
        arrayer: NumberArrayer,
        analyzer_chi: AnalyzerChi,
        analyzer_ks: AnalyzerKS,
        view: View,
        graphics_chi: GraphicsChi,
        graphics_ks: GraphicsKS
    ):
        self.generator = generator
        self.arrayer = arrayer
        self.analyzer_chi = analyzer_chi
        self.analyzer_ks = analyzer_ks
        self.view = view
        self.graphics_chi = graphics_chi
        self.graphics_ks = graphics_ks

    def run(self, show_plots: bool = True) -> None:
        # Actividad 1: Generación y almacenamiento de los números
        numbers = self.arrayer.generate_array()
        self.view.show_generated_numbers(numbers)

        # Algoritmo 2: Prueba Chi-cuadrado
        chi_result = self.analyzer_chi.analyze(numbers)
        self.view.show_chi_results(chi_result)

        # Algoritmo 3: Prueba Kolmogorov-Smirnov
        ks_result = self.analyzer_ks.analyze(numbers)
        self.view.show_ks_results(ks_result)

        # Representación gráfica
        if show_plots:
            self.graphics_chi.plot(chi_result)
            self.graphics_ks.plot(ks_result)