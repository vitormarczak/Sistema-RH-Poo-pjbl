# Sistema de Funcionários (RH)

Integrantes: Vitor Marczak e Emily Stofella

Trabalho final de Programação Orientada a Objetos. É um sistema feito em Java que roda no terminal.

## O que o sistema faz
Cadastra os funcionários de uma empresa e calcula quanto cada um recebe. Existem dois cargos, gerente e
desenvolvedor, e cada um tem um jeito diferente de calcular o salário:

- Gerente: salário base + bônus
- Desenvolvedor: salário base + horas extras (R$ 50,00 por hora)

Quando o programa abre, ele já carrega alguns funcionários do arquivo `funcionarios.csv`. No menu dá para:

1. Listar os funcionários
2. Calcular os pagamentos (de cada um e o total da folha)
3. Cadastrar um funcionário novo
4. Buscar um funcionário pela matrícula
5. Alterar o salário base de um funcionário

## Como rodar
Precisa do Java (JDK 17 ou mais novo).

No IntelliJ: abrir a pasta do projeto, clicar com o botão direito em `src`, ir em Mark Directory as e
escolher Sources Root. Depois é só rodar a classe `Main`. O arquivo `funcionarios.csv` precisa ficar na pasta
principal do projeto.

Pelo terminal, dentro da pasta do projeto:

```
javac -encoding UTF-8 -d out src/rh/*.java src/rh/modelo/*.java src/rh/servico/*.java src/rh/excecoes/*.java
java -cp out rh.Main
```

## Arquivo funcionarios.csv
Cada linha é um funcionário, com os campos separados por ponto e vírgula. O último campo é o bônus (gerente)
ou as horas extras (desenvolvedor):

```
tipo;matricula;nome;salario_base;bonus_ou_horas_extras
GERENTE;1001;Ana Souza;8000;2000
DESENVOLVEDOR;2001;Bruno Lima;5000;10
```

## Organização do código
```
src/rh/
  Main.java                 menu do terminal
  modelo/                   Funcionario, Gerente, Desenvolvedor
  servico/                  Empresa, LeitorCsv
  excecoes/                 FuncionarioException
```

## Decisões que tomamos
- **Classe abstrata:** `Funcionario` é abstrata porque na empresa ninguém é só "funcionário", é gerente ou
  desenvolvedor. Ela guarda o que é igual para todos (matrícula, nome e salário base) e tem o método abstrato
  `calcularSalario()`, que cada subclasse é obrigada a escrever.
- **Encapsulamento:** os atributos são `private` e o acesso é por getters e setters. Os setters não deixam
  colocar nome vazio nem salário negativo. A matrícula não tem setter porque ela identifica o funcionário e
  não deve mudar.
- **Herança:** `Gerente` e `Desenvolvedor` herdam de `Funcionario` e só acrescentam o que é deles (bônus e
  horas extras).
- **Polimorfismo:** cada subclasse calcula o salário do seu jeito. Na `Empresa` e no `Main` chamamos
  `calcularSalario()` sem precisar saber se é gerente ou desenvolvedor. Se aparecer um cargo novo, basta
  criar outra subclasse.
- **Sobrescrita:** `Gerente` e `Desenvolvedor` sobrescrevem o `toString()` para mostrar também o bônus ou as
  horas extras.
- **Coleções:** a `Empresa` guarda os funcionários em uma `List` (para listar na ordem do cadastro) e em um
  `Map` com a matrícula como chave (para buscar rápido e não deixar cadastrar matrícula repetida).
- **Exceção:** criamos a `FuncionarioException` (herda de `Exception`) para os erros do sistema, como matrícula
  repetida ou funcionário não encontrado. O `Main` trata com `try/catch` e mostra a mensagem, em vez de o
  programa fechar.
- **Leitura do CSV:** a classe `LeitorCsv` lê o `funcionarios.csv`, separa cada linha pelo ponto e vírgula e
  cria um `Gerente` ou um `Desenvolvedor`. Se o arquivo não existir ou tiver alguma linha errada, o programa
  avisa e abre sem dados.
