from model.model import ModeloClima
from view.view import VistaClima

class ControladorClima:
    def __init__(self, datos_atmosfericos):
        self.datos_atmosfericos = datos_atmosfericos
        self.model = ModeloClima()
        self.view = VistaClima()

    def ejecutar_simulacion(self):
        encabezados = ["Hora", "Humedad", "Nubosidad", "Temp.", "H", "N", "Tf", "Índice", "Estado"]
        resultados = []

        for registro in self.datos_atmosfericos:
            hora = registro["Hora"]
            humedad = registro["Humedad"]
            nubosidad = registro["Nubosidad"]
            temp = registro["Temp"]

            tf = self.model.calcular_tf(temp)
            
            if tf is None:
                continue

            h, n = self.model.calcular_variables_normalizadas(humedad, nubosidad)
            #indice = self.model.calcular_indice(h, n, tf)
            indice = self.model.calcular_indice_ajuste(h, n, tf)
            estado = self.model.determinar_estado(indice)

            resultados.append([
                hora, 
                humedad, 
                nubosidad, 
                temp, 
                f"{h:.2f}", 
                f"{n:.2f}", 
                f"{tf:.2f}", 
                f"{indice:.3f}", 
                estado
            ])

        self.view.mostrar_tabla_resultados(resultados, encabezados)