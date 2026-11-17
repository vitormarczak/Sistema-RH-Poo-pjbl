package rh.modelo;

public abstract class Funcionario {

    private final String matricula;
    private String nome;
    private double salarioBase;

    protected Funcionario(String matricula, String nome, double salarioBase) {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("A matrícula é obrigatória.");
        }
        this.matricula = matricula.trim();
        setNome(nome);
        setSalarioBase(salarioBase);
    }

    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public double getSalarioBase() { return salarioBase; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("O salário base não pode ser negativo.");
        }
        this.salarioBase = salarioBase;
    }

    public abstract double calcularSalario();

    @Override
    public String toString() {
        return String.format("%s | %s | %s | Salário base: R$ %.2f",
                matricula, getClass().getSimpleName(), nome, salarioBase);
    }
}
