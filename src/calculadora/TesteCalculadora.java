package calculadora;

public class TesteCalculadora {
    public static void main(String[] args) {
        Calculadora c = new Calculadora();
        verificar(c.calc(2, 3, "+"), 5);
        verificar(c.calc(10, 4, "-"), 6);
        verificar(c.calc(3, 5, "*"), 15);
        verificar(c.calc(8, 2, "/"), 4);
        verificar(c.calc(8, 0, "/"), 0);
        verificar(c.calc(5, 5, "x"), 0);
        System.out.println("6/6 testes iniciais aprovados.");
    }
    private static void verificar(int obtido, int esperado) {
        if (obtido != esperado) throw new AssertionError("Resultado incorreto");
    }
}
