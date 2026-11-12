from typing import List
from model.escenario import Escenario
from timer.timeAnalyze import TimeAnalyze
from view.tableView import TableView

class TableController:
    def __init__(self, vista: TableView, analizador_tiempo: TimeAnalyze):
        self.vista = vista
        self.analizador_tiempo = analizador_tiempo

    def procesar_y_mostrar(self, escenarios: List[Escenario]) -> None:
        metricas_tiempo = self.analizador_tiempo.analizar_tiempos(escenarios)
        self.vista.mostrar_resultados(escenarios, metricas_tiempo)