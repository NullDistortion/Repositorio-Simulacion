class ModeloClima:
    def __init__(self, w_h=0.5, w_n=0.3, w_tf=0.2):
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
        self.w_h = w_h
        self.w_n = w_n
        self.w_tf = w_tf

    def set_pesos(self, w_h, w_n, w_tf):
        self.w_h = w_h
        self.w_n = w_n
        self.w_tf = w_tf

    def validar_pesos(self, w_h, w_n, w_tf):
        suma = w_h + w_n + w_tf
        if abs(suma - 1.0) < 1e-9:
            return True
        return False

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

    def calcular_indice(self, h, n, tf, w_h=None, w_n=None, w_tf=None):
        if w_h is None:
            w_h = self.w_h
        if w_n is None:
            w_n = self.w_n
        if w_tf is None:
            w_tf = self.w_tf
        return w_h * h + w_n * n + w_tf * tf

    def calcular_indice_ajuste(self, h, n, tf):  # Ajuste
        return self.w_h * h + self.w_n * n + self.w_tf * tf

    def determinar_estado(self, indice):
        if indice < 0.40:
            return "Sin lluvia"
        elif 0.40 <= indice < 0.60:
            return "Baja posibilidad"
        elif 0.60 <= indice < 0.75:
            return "Lluvia probable"
        else:
            return "Lluvia"