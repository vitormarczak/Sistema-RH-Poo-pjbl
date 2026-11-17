package rh;

import rh.excecoes.FuncionarioException;
import rh.modelo.Desenvolvedor;
import rh.modelo.Funcionario;
import rh.modelo.Gerente;
import rh.servico.Empresa;
import rh.servico.LeitorCsv;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        Empresa empresa = new Empresa();
        carregarCsv(empresa);

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            opcao = -1;
            try {
                opcao = lerInt("Escolha: ");
                switch (opcao) {
                    case 1:
                        listar(empresa);
                        break;
                    case 2:
                        calcularPagamentos(empresa);
                        break;
                    case 3:
                        cadastrar(empresa);
                        break;
                    case 4:
                        buscar(empresa);
                        break;
                    case 5:
                        alterarSalarioBase(empresa);
                        break;
                    case 0:
                        System.out.println("Encerrando...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (FuncionarioException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um número válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Dado inválido: " + e.getMessage());
            }
        }
    }

    private static void carregarCsv(Empresa empresa) {
        try {
            int carregados = 0;
            for (Funcionario f : new LeitorCsv().ler("funcionarios.csv")) {
                try {
                    empresa.cadastrar(f);
                    carregados++;
                } catch (FuncionarioException e) {
                    System.out.println("Ignorado: " + e.getMessage());
                }
            }
            System.out.println(carregados + " funcionário(s) carregado(s) de funcionarios.csv.");
        } catch (FuncionarioException e) {
            System.out.println("Aviso: " + e.getMessage() + "\nIniciando sem dados.");
        }
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("===== SISTEMA DE FUNCIONÁRIOS (RH) =====");
        System.out.println("1. Listar funcionários");
        System.out.println("2. Calcular pagamentos");
        System.out.println("3. Cadastrar funcionário");
        System.out.println("4. Buscar por matrícula");
        System.out.println("5. Alterar salário base");
        System.out.println("0. Sair");
    }

    private static void listar(Empresa empresa) {
        List<Funcionario> funcionarios = empresa.listar();
        if (funcionarios.isEmpty()) {
            System.out.println("(nenhum funcionário cadastrado)");
        }
        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
    }

    private static void calcularPagamentos(Empresa empresa) {
        for (Funcionario f : empresa.listar()) {
            System.out.printf("%s (%s): R$ %.2f%n", f.getNome(), f.getClass().getSimpleName(), f.calcularSalario());
        }
        System.out.printf("TOTAL DA FOLHA: R$ %.2f%n", empresa.calcularFolhaTotal());
    }

    private static void cadastrar(Empresa empresa) throws FuncionarioException {
        String tipo = lerTexto("Cargo (1 = Gerente, 2 = Desenvolvedor): ");
        if (!tipo.equals("1") && !tipo.equals("2")) {
            throw new FuncionarioException("Cargo inválido. Digite 1 ou 2.");
        }
        String matricula = lerTexto("Matrícula: ");
        String nome = lerTexto("Nome: ");
        double salarioBase = lerDouble("Salário base: R$ ");

        Funcionario novo;
        if (tipo.equals("1")) {
            novo = new Gerente(matricula, nome, salarioBase, lerDouble("Bônus: R$ "));
        } else {
            novo = new Desenvolvedor(matricula, nome, salarioBase, lerInt("Horas extras no mês: "));
        }
        empresa.cadastrar(novo);
        System.out.println("Cadastrado: " + novo);
    }

    private static void buscar(Empresa empresa) throws FuncionarioException {
        Funcionario f = empresa.buscar(lerTexto("Matrícula: "));
        System.out.println(f);
        System.out.printf("Salário calculado: R$ %.2f%n", f.calcularSalario());
    }

    private static void alterarSalarioBase(Empresa empresa) throws FuncionarioException {
        Funcionario f = empresa.buscar(lerTexto("Matrícula: "));
        f.setSalarioBase(lerDouble("Novo salário base: R$ "));
        System.out.println("Atualizado: " + f);
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        if (!teclado.hasNextLine()) {
            System.out.println();
            System.out.println("Entrada encerrada. Saindo...");
            System.exit(0);
        }
        return teclado.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        return Integer.parseInt(lerTexto(mensagem));
    }

    private static double lerDouble(String mensagem) {
        return Double.parseDouble(lerTexto(mensagem).replace(',', '.'));
    }
}
