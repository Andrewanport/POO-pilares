// ABSTRAÇÃO: Conta é abstrata. Define o que toda conta tem e faz,
// mas deixa o cálculo da taxa para cada tipo de conta.
class Conta {
  // ENCAPSULAMENTO: o "#" torna o atributo privado.
  #saldo = 0;

  constructor(titular) {
    // JavaScript não tem "abstract": impedimos a instância direta manualmente.
    if (new.target === Conta) {
      throw new Error("Conta é abstrata e não pode ser instanciada.");
    }
    this.titular = titular;
  }

  get saldo() {
    return this.#saldo; // somente leitura: não existe setter
  }

  depositar(valor) {
    if (valor <= 0) {
      throw new Error("Depósito deve ser positivo.");
    }
    this.#saldo += valor;
  }

  sacar(valor) {
    const total = valor + this.taxaDeSaque();
    if (valor <= 0 || total > this.#saldo) {
      throw new Error("Saque inválido ou saldo insuficiente.");
    }
    this.#saldo -= total;
  }

  // Método "abstrato": cada subclasse deve sobrescrever.
  taxaDeSaque() {
    throw new Error("Subclasse deve implementar taxaDeSaque().");
  }
}

// HERANÇA: ContaCorrente reaproveita tudo de Conta (extends).
// POLIMORFISMO: cada subclasse implementa taxaDeSaque() do seu jeito.
class ContaCorrente extends Conta {
  taxaDeSaque() {
    return 2.5;
  }
}

class ContaPoupanca extends Conta {
  taxaDeSaque() {
    return 0;
  }
}

const contas = [new ContaCorrente("Ana"), new ContaPoupanca("Bruno")];

for (const conta of contas) {
  conta.depositar(100);
  conta.sacar(50); // mesma chamada, comportamento diferente
  console.log(
    `${conta.titular} (${conta.constructor.name}): saldo R$ ${conta.saldo.toFixed(2)}`
  );
}
