import numpy as np

class ContinuousModel:
    def simulate(self, P0, r, tiempo, dt):
        t = np.arange(0, tiempo + dt, dt)
        poblacion = [P0]
        
        for i in range(1, len(t)):
            poblacion_actual = poblacion[-1]
            cambio = r * poblacion_actual
            nueva_poblacion = poblacion_actual + dt * cambio
            poblacion.append(nueva_poblacion)
            
        return t, poblacion