from factory.factory import ModelFactory
from view.graphics import GraphicsView

class SimulationController:
    def __init__(self):
        self.factory = ModelFactory()
        self.view = GraphicsView()

    def execute_simulation(self):
        datos = [1000, 1100, 1250, 1400, 1600, 1850, 2100]
        
        # Parámetros (Ajuste estos valores para responder las preguntas de control de la guía)
        P0 = datos[0]
        tiempo = len(datos) - 1
        r = 0.1  # Ejemplo de tasa de crecimiento (modifique este valor)
        sigma = 50.0  # Ejemplo de nivel de aleatoriedad
        dt = 0.1  # Tamaño del paso
        
        resultados = {}
        
        modelo_det = self.factory.get_model("deterministico")
        resultados["deterministico"] = modelo_det.simulate(P0, r, tiempo)
        
        modelo_disc = self.factory.get_model("discreto")
        resultados["discreto"] = modelo_disc.simulate(P0, r, tiempo)
        
        modelo_est = self.factory.get_model("estocastico")
        resultados["estocastico"] = modelo_est.simulate(P0, r, tiempo, sigma)
        
        modelo_cont = self.factory.get_model("continuo")
        resultados["continuo"] = modelo_cont.simulate(P0, r, tiempo, dt)
        
        self.view.display_comparison(resultados, datos)