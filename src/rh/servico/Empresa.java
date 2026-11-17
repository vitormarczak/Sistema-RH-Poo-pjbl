package rh.servico;

import rh.excecoes.FuncionarioException;
import rh.modelo.Funcionario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Empresa {

    private final List<Funcionario> funcionarios = new ArrayList<>();

    private final Map<String, Funcionario> porMatricula = new HashMap<>();

    public void cadastrar(Funcionario funcionario) throws FuncionarioException {
        if (porMatricula.containsKey(funcionario.getMatricula())) {
            throw new FuncionarioException("Já existe um funcionário com a matrícula " + funcionario.getMatricula() + ".");
        }
        funcionarios.add(funcionario);
        porMatricula.put(funcionario.getMatricula(), funcionario);
    }

    public Funcionario buscar(String matricula) throws FuncionarioException {
        Funcionario funcionario = porMatricula.get(matricula.trim());
        if (funcionario == null) {
            throw new FuncionarioException("Nenhum funcionário com a matrícula \"" + matricula + "\".");
        }
        return funcionario;
    }

    public List<Funcionario> listar() {
        return Collections.unmodifiableList(funcionarios);
    }

    public double calcularFolhaTotal() {
        double total = 0;
        for (Funcionario f : funcionarios) {
            total += f.calcularSalario();
        }
        return total;
    }
}
