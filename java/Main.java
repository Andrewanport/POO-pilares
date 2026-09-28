// ABSTRAÇÃO: classe abstrata, não pode ser instanciada diretamente.
abstract class Conta {

    // ENCAPSULAMENTO: atributos privados, inacessíveis fora da classe.
    private final String titular;
    private double saldo;

    public Conta(String titular) {
        this.titular = titular;
    }

    // ENCAPSULAMENTO: acesso somente leitura via getters (não há setters).
    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    // ENCAPSULAMENTO: o saldo só muda por métodos que validam a operação.
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Depósito deve ser positivo.");
        }
        saldo += valor;
    }

    public void sacar(double valor) {
        double total = valor + taxaDeSaque();
        if (valor <= 0 || total > saldo) {
            throw new IllegalArgumentException("Saque inválido ou saldo insuficiente.");
        }
        saldo -= total;
    }

    // ABSTRAÇÃO: método sem implementação, cada subclasse é obrigada a definir.
    protected abstract double taxaDeSaque();
}

// HERANÇA: ContaCorrente herda atributos e métodos de Conta.
class ContaCorrente extends Conta {

    // HERANÇA: super repassa o titular para o construtor da classe mãe.
    public ContaCorrente(String titular) {
        super(titular);
    }

    // POLIMORFISMO: sobrescreve taxaDeSaque com a regra da conta corrente.
    @Override
    protected double taxaDeSaque() {
        return 2.50;
    }
}

// HERANÇA: ContaPoupanca também herda de Conta.
class ContaPoupanca extends Conta {

    public ContaPoupanca(String titular) {
        super(titular);
    }

    // POLIMORFISMO: mesma assinatura, comportamento diferente (sem taxa).
    @Override
    protected double taxaDeSaque() {
        return 0;
    }
}

public class Main {
    public static void main(String[] args) {
        // POLIMORFISMO: variável do tipo Conta guarda objetos de subclasses.
        Conta[] contas = {
            new ContaCorrente("Ana"),
            new ContaPoupanca("Bruno")
        };

        for (Conta conta : contas) {
            conta.depositar(100);
            // POLIMORFISMO: mesma chamada, cada objeto aplica sua própria taxa.
            conta.sacar(50);
            System.out.printf("%s (%s): saldo R$ %.2f%n",
                conta.getTitular(), conta.getClass().getSimpleName(), conta.getSaldo());
        }
    }
}
