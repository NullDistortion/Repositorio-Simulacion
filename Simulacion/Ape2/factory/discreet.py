class DiscreteModel:
    def simulate(self, P0, r, tiempo):
        t = list(range(tiempo + 1))
        poblacion = [P0]
        
        for periodo in range(1, tiempo + 1):
            poblacion_actual = poblacion[-1]
            incremento = r * poblacion_actual
            nueva_poblacion = poblacion_actual + incremento
            poblacion.append(nueva_poblacion)
            
        return t, poblacion