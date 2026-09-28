from abc import ABC, abstractmethod


# ABSTRAÇÃO: herdar de ABC torna Conta uma classe abstrata.
class Conta(ABC):

    def __init__(self, titular):
        self.titular = titular
        # ENCAPSULAMENTO: "__" torna o atributo privado (name mangling).
        self.__saldo = 0

    # ENCAPSULAMENTO: @property sem setter, o saldo é somente leitura.
    @property
    def saldo(self):
        return self.__saldo

    # ENCAPSULAMENTO: o saldo só muda por métodos que validam a operação.
    def depositar(self, valor):
        if valor <= 0:
            raise ValueError("Depósito deve ser positivo.")
        self.__saldo += valor

    def sacar(self, valor):
        total = valor + self.taxa_de_saque()
        if valor <= 0 or total > self.__saldo:
            raise ValueError("Saque inválido ou saldo insuficiente.")
        self.__saldo -= total

    # ABSTRAÇÃO: método abstrato, cada subclasse é obrigada a implementar.
    @abstractmethod
    def taxa_de_saque(self):
        pass


# HERANÇA: ContaCorrente herda tudo de Conta (inclusive o __init__).
class ContaCorrente(Conta):

    # POLIMORFISMO: sobrescreve taxa_de_saque com a regra da conta corrente.
    def taxa_de_saque(self):
        return 2.50


# HERANÇA: ContaPoupanca também herda de Conta.
class ContaPoupanca(Conta):

    # POLIMORFISMO: mesmo método, comportamento diferente (sem taxa).
    def taxa_de_saque(self):
        return 0


if __name__ == "__main__":
    contas = [ContaCorrente("Ana"), ContaPoupanca("Bruno")]

    for conta in contas:
        conta.depositar(100)
        # POLIMORFISMO: mesma chamada, cada objeto aplica sua própria taxa.
        conta.sacar(50)
        print(f"{conta.titular} ({type(conta).__name__}): saldo R$ {conta.saldo:.2f}")
