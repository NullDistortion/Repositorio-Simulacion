from numberTreatment.numberGenerator import NumberGenerator
from numberTreatment.numberArrayer import NumberArrayer
from service.analyzerChi import AnalyzerChi
from service.analyzerKS import AnalyzerKS
from view.view import View
from view.graphicsChi import GraphicsChi
from view.graphicsKS import GraphicsKS
from controller.controller import Controller

def main():

    SEED = 12345
    MULTIPLIER = 1664525
    INCREMENT = 1013904223
    MODULUS = 2**32
    COUNT = 10000 

    generator = NumberGenerator(
        seed=SEED,
        multiplier=MULTIPLIER,
        increment=INCREMENT,
        modulus=MODULUS
    )
    arrayer = NumberArrayer(generator=generator, count=COUNT)

    analyzer_chi = AnalyzerChi(intervals=10, alpha=0.05)
    analyzer_ks = AnalyzerKS(alpha=0.05)

    view = View()
    graphics_chi = GraphicsChi()
    graphics_ks = GraphicsKS()

    app_controller = Controller(
        generator=generator,
        arrayer=arrayer,
        analyzer_chi=analyzer_chi,
        analyzer_ks=analyzer_ks,
        view=view,
        graphics_chi=graphics_chi,
        graphics_ks=graphics_ks
    )

    app_controller.run(show_plots=True)

if __name__ == "__main__":
    main()