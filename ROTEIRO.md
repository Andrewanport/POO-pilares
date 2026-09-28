# Roteiro de Apresentação — Pilares da POO

Tempo sugerido: ~12 minutos (1 min de abertura, 3 min por linguagem, 2 min de fechamento).

No código, cada ponto relevante tem um comentário começando com o nome do pilar (`ABSTRAÇÃO:`, `ENCAPSULAMENTO:`, `HERANÇA:`, `POLIMORFISMO:`), o que facilita apontar na tela durante a fala.

---

## Abertura (1 min)

> "Vamos mostrar os quatro pilares da Programação Orientada a Objetos — **Abstração, Encapsulamento, Herança e Polimorfismo** — em três linguagens: **Java, JavaScript e Python**.
>
> Para facilitar a comparação, usamos o **mesmo exemplo** nas três: um sistema de contas bancárias. Existe uma `Conta` genérica, e dois tipos concretos: `ContaCorrente`, que cobra R$ 2,50 por saque, e `ContaPoupanca`, que não cobra taxa.
>
> Assim, a lógica é idêntica e o que muda é só **como cada linguagem expressa cada pilar**."

**Saída esperada nas três linguagens** (depósito de 100, saque de 50):

```
Ana (ContaCorrente): saldo R$ 47.50
Bruno (ContaPoupanca): saldo R$ 50.00
```

---

## 1. Java — `java/Main.java` (3 min)

> "Começamos pelo Java porque é a linguagem mais explícita: cada pilar tem uma palavra-chave própria."

### Abstração — linhas 1–2 e 37–38

- Mostrar `abstract class Conta` e `protected abstract double taxaDeSaque();`
- Fala:
  > "A `Conta` é **abstrata**: ela define o que toda conta tem — titular, saldo, depositar, sacar — mas **não diz** qual é a taxa de saque. Isso fica para cada tipo de conta. O próprio compilador **impede** fazer `new Conta(...)`, e obriga toda subclasse a implementar `taxaDeSaque()`."

### Encapsulamento — linhas 4–6 e 12–35

- Mostrar `private final String titular;` e `private double saldo;`, depois os getters e os métodos `depositar`/`sacar`.
- Fala:
  > "Os atributos são **`private`**: ninguém fora da classe consegue fazer `conta.saldo = 1000000`. O saldo só muda através de `depositar` e `sacar`, que **validam** a operação — não aceitam valor negativo nem saque acima do saldo. Para ler, existe apenas o `getSaldo()`; não há `setSaldo()`."

### Herança — linhas 41–46 e 56–57

- Mostrar `class ContaCorrente extends Conta` e o `super(titular)`.
- Fala:
  > "Com **`extends`**, `ContaCorrente` e `ContaPoupanca` herdam tudo de `Conta`: atributos, depositar, sacar, getters. Repare que elas **não reescrevem** nada disso — só o construtor, que repassa o titular com `super`, e a taxa."

### Polimorfismo — linhas 49–53, 63–67 e 72–84

- Mostrar os dois `@Override` e depois o `main`.
- Fala:
  > "Cada subclasse **sobrescreve** `taxaDeSaque()` do seu jeito — o `@Override` deixa isso explícito. No `main`, o array é do tipo **`Conta[]`**, mas os objetos são específicos. Quando chamamos `conta.sacar(50)`, **a mesma linha** produz resultados diferentes: a Ana paga R$ 2,50 de taxa, o Bruno não. O Java decide em tempo de execução qual versão chamar."

---

## 2. JavaScript — `javascript/main.js` (3 min)

> "No JavaScript as ideias são as mesmas, mas a linguagem é mais flexível — então alguns pilares precisam ser **garantidos manualmente**."

### Abstração — linhas 1–2, 7–10 e 35–38

- Mostrar o `if (new.target === Conta)` no construtor e o `taxaDeSaque()` que lança erro.
- Fala:
  > "JavaScript **não tem** a palavra `abstract`. Então simulamos: o `new.target` diz qual classe foi instanciada — se for a própria `Conta`, lançamos um erro. E o método `taxaDeSaque()` da classe base também lança erro, forçando as subclasses a sobrescrevê-lo. A diferença do Java é que aqui o erro aparece **em execução**, não na compilação."

### Encapsulamento — linhas 3–4, 14–17 e 19–33

- Mostrar `#saldo = 0;` e o `get saldo()`.
- Fala:
  > "O **`#`** antes do nome cria um campo **realmente privado** — tentar acessar `conta.#saldo` de fora é erro de sintaxe. Para leitura, usamos um **getter** (`get saldo()`), então escrevemos `conta.saldo` como se fosse atributo, mas é só leitura: como não existe setter, não dá para alterar o saldo diretamente."

### Herança — linhas 41–42 e 49–50

- Mostrar `class ContaCorrente extends Conta`.
- Fala:
  > "Mesma palavra do Java: **`extends`**. Aqui nem precisamos escrever construtor — o JavaScript usa automaticamente o construtor da `Conta`."

### Polimorfismo — linhas 43–46, 51–54 e 57–66

- Mostrar as duas implementações de `taxaDeSaque()` e o laço final.
- Fala:
  > "Cada subclasse define sua própria `taxaDeSaque()`. No laço, percorremos uma lista com contas diferentes e chamamos `conta.sacar(50)` — **mesma chamada, comportamento diferente**. Como JavaScript não tem tipos declarados, isso funciona naturalmente: o que importa é o objeto ter o método."

---

## 3. Python — `python/main.py` (3 min)

> "Por fim, Python. Ele tem uma sintaxe mais enxuta e usa **módulos e convenções** para expressar os pilares."

### Abstração — linhas 1, 4–5 e 29–32

- Mostrar `from abc import ABC, abstractmethod`, `class Conta(ABC)` e `@abstractmethod`.
- Fala:
  > "Python usa o módulo **`abc`** (*Abstract Base Classes*). A `Conta` herda de `ABC` e o método `taxa_de_saque` recebe o decorador **`@abstractmethod`**. Com isso, tentar criar `Conta("x")` gera `TypeError`, e qualquer subclasse que esqueça de implementar a taxa também não pode ser instanciada."

### Encapsulamento — linhas 9–10, 12–15 e 17–27

- Mostrar `self.__saldo = 0` e o `@property`.
- Fala:
  > "Os **dois underscores** (`__saldo`) ativam o *name mangling*: o Python renomeia o atributo internamente para `_Conta__saldo`, dificultando o acesso externo. É uma proteção por **convenção** — menos rígida que o `private` do Java, mas cumpre o papel. O **`@property`** cria um acesso somente leitura: `conta.saldo` funciona, mas `conta.saldo = 10` dá erro."

### Herança — linhas 35–36 e 43–44

- Mostrar `class ContaCorrente(Conta):`.
- Fala:
  > "Em Python a herança é indicada **entre parênteses**: `ContaCorrente(Conta)`. Assim como no JavaScript, o construtor é herdado automaticamente."

### Polimorfismo — linhas 38–40, 46–48 e 51–58

- Mostrar as duas implementações de `taxa_de_saque` e o bloco `if __name__ == "__main__"`.
- Fala:
  > "Cada classe implementa `taxa_de_saque` com seu valor. No laço, `conta.sacar(50)` é a mesma chamada para todas, e cada objeto responde do seu jeito. Python segue o *duck typing*: se o objeto tem o método, ele funciona."

---

## Fechamento — comparação (2 min)

> "Resumindo: os quatro pilares são **conceitos**, não recursos de uma linguagem específica. O que muda é o quanto cada linguagem **impõe** esses conceitos."

| Pilar | Java | JavaScript | Python |
|---|---|---|---|
| **Abstração** | `abstract class` / `abstract` (verificado na compilação) | Simulada com `new.target` e erro no método base | `ABC` + `@abstractmethod` |
| **Encapsulamento** | `private` + getters | Campo `#privado` + `get` | `__atributo` (name mangling) + `@property` |
| **Herança** | `extends` + `super(...)` | `extends` | `class Filha(Mae)` |
| **Polimorfismo** | `@Override` + variável do tipo da superclasse | Sobrescrita de método (tipagem dinâmica) | Sobrescrita de método (*duck typing*) |

> "O Java é o mais **rígido** — erros aparecem antes de rodar. JavaScript e Python são mais **flexíveis**, e parte da garantia fica por conta do programador. Mas nas três o resultado é o mesmo: código organizado, reutilizável e protegido. Obrigado!"

---

## Como rodar (para a demonstração ao vivo)

```bash
# Java
cd java && javac Main.java && java Main

# JavaScript
node javascript/main.js

# Python
python3 python/main.py
```
