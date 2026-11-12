from timer.timer import Timer
from timer.timeAnalyze import TimeAnalyze
from service.monteCarloGenerator import MonteCarloGenerator
from service.calculator import Calculator
from view.tableView import TableView
from view.chiView import ChiView
from controller.tableController import TableController
from controller.chiController import ChiController

def main():
    SEMILLA = 123456789
    TAMANOS_MUESTRA = [1000, 10000, 100000, 1000000]
    INTERVALOS_CHI = 10  # gl = 9, valor crítico alfa 0.05 = 16.9190

    # Inicialización de componentes
    reloj = Timer()
    analizador_tiempo = TimeAnalyze()
    generador = MonteCarloGenerator(semilla=SEMILLA)
    calculadora = Calculator(generador=generador, timer=reloj)

    # Ejecución de los escenarios solicitados en la práctica
    escenarios = [
        calculadora.ejecutar_escenario(n_puntos=n, k_intervalos=INTERVALOS_CHI)
        for n in TAMANOS_MUESTRA
    ]

    # Controladores y vistas
    table_controller = TableController(vista=TableView(), analizador_tiempo=analizador_tiempo)
    chi_controller = ChiController(vista=ChiView())

    table_controller.procesar_y_mostrar(escenarios)
    chi_controller.procesar_y_mostrar(escenarios)

if __name__ == "__main__":
    main()