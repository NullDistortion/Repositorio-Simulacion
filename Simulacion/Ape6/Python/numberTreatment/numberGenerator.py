class NumberGenerator:
    def __init__(self, seed: int, multiplier: int, increment: int, modulus: int):
        if modulus <= 0:
            raise ValueError("El módulo debe ser mayor que cero.")
        self.seed = seed
        self.multiplier = multiplier
        self.increment = increment
        self.modulus = modulus
        self.current_value = seed 

    def next_number(self) -> float:
        #(a * X_n + c) mod m
        next_val = (self.multiplier * self.current_value + self.increment) % self.modulus
        self.current_value = next_val
        return next_val / self.modulus

    def reset(self) -> None:
        self.current_value = self.seed