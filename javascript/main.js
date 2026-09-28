// ABSTRAÇÃO: Conta representa o conceito geral de conta, sem ser usada diretamente.
class Conta {
  // ENCAPSULAMENTO: o "#" torna o campo privado de verdade.
  #saldo = 0;

  constructor(titular) {
    // ABSTRAÇÃO: JS não tem "abstract", então bloqueamos a instância direta.
    if (new.target === Conta) {
      throw new Error("Conta é abstrata e não pode ser instanciada.");
    }
    this.titular = titular;
  }

  // ENCAPSULAMENTO: getter sem setter, o saldo é somente leitura.
  get saldo() {
    return this.#saldo;
  }

  // ENCAPSULAMENTO: o saldo só muda por métodos que validam a operação.
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

  // ABSTRAÇÃO: método "abstrato", as subclasses devem sobrescrever.
  taxaDeSaque() {
    throw new Error("Subclasse deve implementar taxaDeSaque().");
  }
}

// HERANÇA: ContaCorrente herda tudo de Conta (inclusive o construtor).
class ContaCorrente extends Conta {
  // POLIMORFISMO: sobrescreve taxaDeSaque com a regra da conta corrente.
  taxaDeSaque() {
    return 2.5;
  }
}

// HERANÇA: ContaPoupanca também herda de Conta.
class ContaPoupanca extends Conta {
  // POLIMORFISMO: mesmo método, comportamento diferente (sem taxa).
  taxaDeSaque() {
    return 0;
  }
}

const contas = [new ContaCorrente("Ana"), new ContaPoupanca("Bruno")];

for (const conta of contas) {
  conta.depositar(100);
  // POLIMORFISMO: mesma chamada, cada objeto aplica sua própria taxa.
  conta.sacar(50);
  console.log(
    `${conta.titular} (${conta.constructor.name}): saldo R$ ${conta.saldo.toFixed(2)}`
  );
}
