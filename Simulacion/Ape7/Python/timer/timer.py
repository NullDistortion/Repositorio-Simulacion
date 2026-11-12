import time

class Timer:
    def __init__(self):
        self._inicio = 0.0
        self._fin = 0.0

    def iniciar(self) -> None:
        self._inicio = time.perf_counter()

    def detener(self) -> float:
        self._fin = time.perf_counter()
        return self.obtener_duracion()

    def obtener_duracion(self) -> float:
        return self._fin - self._inicio