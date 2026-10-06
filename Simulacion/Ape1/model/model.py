class ModeloClima:
    def __init__(self):
        self.tabla_tf = {
            10: 1.00,
            12: 0.90,
            14: 0.80,
            16: 0.70,
            18: 0.60,
            20: 0.50,
            22: 0.40,
            24: 0.30,
            26: 0.20,
            28: 0.10
        }

    def calcular_tf(self, temp):
        if temp <= 10:
            return 1.00
        elif temp >= 28:
            return 0.10
        elif temp in self.tabla_tf:
            return self.tabla_tf[temp]
        else:
            return None

    def calcular_variables_normalizadas(self, humedad, nubosidad):
        h = humedad / 100.0
        n = nubosidad / 100.0
        return h, n

    def calcular_indice(self, h, n, tf):
        return 0.5 * h + 0.3 * n + 0.2 * tf

    def calcular_indice_ajuste(self, h, n, tf): #Ajuste 
        return 0.1 * h + 0.1 * n + 0.8 * tf

    def determinar_estado(self, indice):
        if indice < 0.40:
            return "Sin lluvia"
        elif 0.40 <= indice < 0.60:
            return "Baja posibilidad"
        elif 0.60 <= indice < 0.75:
            return "Lluvia probable"
        else:
            return "Lluvia"