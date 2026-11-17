package rh.servico;

import rh.excecoes.FuncionarioException;
import rh.modelo.Desenvolvedor;
import rh.modelo.Funcionario;
import rh.modelo.Gerente;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LeitorCsv {

    public List<Funcionario> ler(String caminho) throws FuncionarioException {
        List<Funcionario> lista = new ArrayList<>();
        try {
            List<String> linhas = Files.readAllLines(Path.of(caminho), StandardCharsets.UTF_8);
            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i).trim();
                if (linha.isEmpty() || linha.toLowerCase().startsWith("tipo;")) continue;
                lista.add(converter(linha, i + 1));
            }
        } catch (IOException e) {
            throw new FuncionarioException("Não foi possível ler o arquivo \"" + caminho + "\".", e);
        }
        return lista;
    }

    private Funcionario converter(String linha, int numeroLinha) throws FuncionarioException {
        String[] campos = linha.split(";");
        if (campos.length < 5) {
            throw new FuncionarioException("Linha " + numeroLinha + " incompleta (esperados 5 campos): " + linha);
        }
        try {
            double salarioBase = Double.parseDouble(campos[3].trim());
            switch (campos[0].trim().toUpperCase()) {
                case "GERENTE":
                    return new Gerente(campos[1], campos[2], salarioBase, Double.parseDouble(campos[4].trim()));
                case "DESENVOLVEDOR":
                    return new Desenvolvedor(campos[1], campos[2], salarioBase, Integer.parseInt(campos[4].trim()));
                default:
                    throw new FuncionarioException("Linha " + numeroLinha + ": cargo desconhecido \"" + campos[0] + "\".");
            }
        } catch (IllegalArgumentException e) {
            throw new FuncionarioException("Linha " + numeroLinha + " inválida: " + linha, e);
        }
    }
}
