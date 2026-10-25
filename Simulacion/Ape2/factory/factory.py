from factory.deterministic import DeterministicModel
from factory.discreet import DiscreteModel
from factory.stochastic import StochasticModel
from factory.continuous import ContinuousModel

class ModelFactory:
    @staticmethod
    def get_model(model_type):
        modelos = {
            "deterministico": DeterministicModel,
            "discreto": DiscreteModel,
            "estocastico": StochasticModel,
            "continuo": ContinuousModel
        }
        if model_type in modelos:
            return modelos[model_type]()
        else:
            raise ValueError(f"Modelo '{model_type}' no reconocido.")