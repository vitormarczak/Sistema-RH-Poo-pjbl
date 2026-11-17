package rh.modelo;

public class Gerente extends Funcionario {

    private double bonus;

    public Gerente(String matricula, String nome, double salarioBase, double bonus) {
        super(matricula, nome, salarioBase);
        setBonus(bonus);
    }

    public double getBonus() { return bonus; }

    public void setBonus(double bonus) {
        if (bonus < 0) throw new IllegalArgumentException("O bônus não pode ser negativo.");
        this.bonus = bonus;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + bonus;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Bônus: R$ %.2f", bonus);
    }
}
