from abc import ABC, abstractmethod


# ABSTRAÇÃO: Conta é abstrata (herda de ABC). Define o que toda conta tem e faz,
# mas deixa o cálculo da taxa para cada tipo de conta.
class Conta(ABC):

    def __init__(self, titular):
        self.titular = titular
        # ENCAPSULAMENTO: o "__" torna o atributo privado (name mangling).
        self.__saldo = 0

    @property
    def saldo(self):  # somente leitura: não existe setter
        return self.__saldo

    def depositar(self, valor):
        if valor <= 0:
            raise ValueError("Depósito deve ser positivo.")
        self.__saldo += valor

    def sacar(self, valor):
        total = valor + self.taxa_de_saque()
        if valor <= 0 or total > self.__saldo:
            raise ValueError("Saque inválido ou saldo insuficiente.")
        self.__saldo -= total

    # Método abstrato: cada subclasse é obrigada a implementar.
    @abstractmethod
    def taxa_de_saque(self):
        pass


# HERANÇA: ContaCorrente reaproveita tudo de Conta.
# POLIMORFISMO: cada subclasse implementa taxa_de_saque() do seu jeito.
class ContaCorrente(Conta):

    def taxa_de_saque(self):
        return 2.50


class ContaPoupanca(Conta):

    def taxa_de_saque(self):
        return 0


if __name__ == "__main__":
    contas = [ContaCorrente("Ana"), ContaPoupanca("Bruno")]

    for conta in contas:
        conta.depositar(100)
        conta.sacar(50)  # mesma chamada, comportamento diferente
        print(f"{conta.titular} ({type(conta).__name__}): saldo R$ {conta.saldo:.2f}")
