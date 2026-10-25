import numpy as np

class DeterministicModel:
    def simulate(self, P0, r, tiempo):
        t = np.linspace(0, tiempo, tiempo + 1)
        P = P0 * np.exp(r * t)
        return t, P