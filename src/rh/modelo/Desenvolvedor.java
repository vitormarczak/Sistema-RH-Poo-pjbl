package rh.modelo;

public class Desenvolvedor extends Funcionario {

    private static final double VALOR_HORA_EXTRA = 50.00;

    private int horasExtras;

    public Desenvolvedor(String matricula, String nome, double salarioBase, int horasExtras) {
        super(matricula, nome, salarioBase);
        setHorasExtras(horasExtras);
    }

    public int getHorasExtras() { return horasExtras; }

    public void setHorasExtras(int horasExtras) {
        if (horasExtras < 0) throw new IllegalArgumentException("As horas extras não podem ser negativas.");
        this.horasExtras = horasExtras;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + horasExtras * VALOR_HORA_EXTRA;
    }

    @Override
    public String toString() {
        return super.toString() + " | Horas extras: " + horasExtras;
    }
}
