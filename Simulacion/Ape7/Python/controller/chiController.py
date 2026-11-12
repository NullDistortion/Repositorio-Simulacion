from typing import List
from model.escenario import Escenario
from view.chiView import ChiView

class ChiController:
    def __init__(self, vista: ChiView):
        self.vista = vista

    def procesar_y_mostrar(self, escenarios: List[Escenario]) -> None:
        self.vista.mostrar_prueba_chi(escenarios)
        self.vista.graficar_chi_cuadrado(escenarios)