from numberTreatment.numberGenerator import NumberGenerator

class NumberArrayer:
    def __init__(self, generator: NumberGenerator, count: int = 10000):
        self.generator = generator
        self.count = count 

    def generate_array(self) -> list[float]:
        self.generator.reset()
        numbers = []
        # Paso 4 y 9: Repetir hasta completar la cantidad y almacenar cada número
        for _ in range(self.count):
            numbers.append(self.generator.next_number())
        return numbers