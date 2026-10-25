import numpy as np

class StochasticModel:
    def simulate(self, P0, r, tiempo, sigma):
        t = list(range(tiempo + 1))
        poblacion = [P0]
        
        for periodo in range(1, tiempo + 1):
            ruido = np.random.normal(0, sigma)
            poblacion_actual = poblacion[-1]
            nueva_poblacion = poblacion_actual + (r * poblacion_actual) + ruido
            
            if nueva_poblacion < 0:
                nueva_poblacion = 0
                
            poblacion.append(nueva_poblacion)
            
        return t, poblacion