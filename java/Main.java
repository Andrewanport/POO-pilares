// ABSTRAÇÃO: Conta é abstrata. Define o que toda conta tem e faz,
// mas deixa o cálculo da taxa para cada tipo de conta.
abstract class Conta {

    // ENCAPSULAMENTO: atributos privados, só acessíveis pelos métodos da classe.
    private final String titular;
    private double saldo;

    public Conta(String titular) {
        this.titular = titular;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

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

    // Método abstrato: cada subclasse é obrigada a implementar.
    protected abstract double taxaDeSaque();
}

// HERANÇA: ContaCorrente reaproveita tudo de Conta (extends).
// POLIMORFISMO: cada subclasse implementa taxaDeSaque() do seu jeito.
class ContaCorrente extends Conta {

    public ContaCorrente(String titular) {
        super(titular);
    }

    @Override
    protected double taxaDeSaque() {
        return 2.50;
    }
}

class ContaPoupanca extends Conta {

    public ContaPoupanca(String titular) {
        super(titular);
    }

    @Override
    protected double taxaDeSaque() {
        return 0;
    }
}

public class Main {
    public static void main(String[] args) {
        // O tipo da variável é Conta, mas o objeto criado é o específico.
        Conta[] contas = {
            new ContaCorrente("Ana"),
            new ContaPoupanca("Bruno")
        };

        for (Conta conta : contas) {
            conta.depositar(100);
            conta.sacar(50); // mesma chamada, comportamento diferente
            System.out.printf("%s (%s): saldo R$ %.2f%n",
                conta.getTitular(), conta.getClass().getSimpleName(), conta.getSaldo());
        }
    }
}
