package calculadora;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Testes unitários executáveis sem dependências externas.
 * <p>Compara resultados, estado, feedback e exceções. Uma falha encerra a
 * execução com AssertionError e código de saída diferente de zero.</p>
 * @author Gabriel Henrique Fernandes Torres
 * @version 2.0
 */
public class TesteCalculadora {
    /** Quantidade de casos aprovados na execução atual. */
    private static int aprovados;

    /** Impede instanciação da classe utilitária de testes. */
    private TesteCalculadora() { }

    /**
     * Executa os testes de regressão e os casos de limite.
     * @param args argumentos não utilizados
     */
    public static void main(String[] args) {
        aprovados = 0;
        Calculadora c = new Calculadora();
        System.out.println("FINANCAPP | TESTES UNITARIOS - REFATORACAO");
        testarCalc(c, 2, 3, "+", 5, null);
        testarCalc(c, 10, 4, "-", 6, null);
        testarCalc(c, 3, 5, "*", 15, null);
        testarCalc(c, 8, 2, "/", 4, null);
        testarCalc(c, 8, 0, "/", 0, "divisao por zero");
        testarCalc(c, 5, 5, "x", 0, "Operacao invalida");
        igual("somar(2, 3)", 5, c.somar(2, 3));
        igual("subtrair(10, 4)", 6, c.subtrair(10, 4));
        igual("multiplicar(3, 5)", 15, c.multiplicar(3, 5));
        igual("dividir(8, 2)", 4, c.dividir(8, 2));
        igual("calcular(-2, -3, +)", -5, c.calcular(-2, -3, "+"));
        igual("calcular(-5, 3, -)", -8, c.calcular(-5, 3, "-"));
        igual("calcular(25, 0, *)", 0, c.calcular(25, 0, "*"));
        igual("calcular(7, 2, /)", 3, c.calcular(7, 2, "/"));
        igual("calcular(-7, 2, /)", -3, c.calcular(-7, 2, "/"));
        igual("somar(MAX_VALUE, 0)", Integer.MAX_VALUE, c.somar(Integer.MAX_VALUE, 0));
        erro("calcular(8, 0, /)", IllegalArgumentException.class,
            "Divisão por zero", () -> c.calcular(8, 0, "/"));
        erro("calcular(5, 5, x)", IllegalArgumentException.class,
            "Operador inválido", () -> c.calcular(5, 5, "x"));
        erro("calcular(1, 2, null)", IllegalArgumentException.class,
            "nulo", () -> c.calcular(1, 2, null));
        erro("calcular(1, 2, vazio)", IllegalArgumentException.class,
            "inválido", () -> c.calcular(1, 2, ""));
        erro("somar(MAX_VALUE, 1)", ArithmeticException.class,
            null, () -> c.somar(Integer.MAX_VALUE, 1));
        erro("subtrair(MIN_VALUE, 1)", ArithmeticException.class,
            null, () -> c.subtrair(Integer.MIN_VALUE, 1));
        erro("multiplicar(MAX_VALUE, 2)", ArithmeticException.class,
            null, () -> c.multiplicar(Integer.MAX_VALUE, 2));
        erro("dividir(MIN_VALUE, -1)", ArithmeticException.class,
            null, () -> c.dividir(Integer.MIN_VALUE, -1));
        testarCalc(c, 1, 2, null, 0, "Operacao invalida");
        testarCalc(c, Integer.MAX_VALUE, 1, "+", 0, "fora do intervalo");
        testarCalc(c, 20, 4, "/", 5, null);
        int anterior = c.r;
        c.calcular(2, 3, "+");
        igual("calcular(2, 3, +) preserva r anterior", anterior, c.r);
        System.out.println("TOTAL: " + aprovados + "/28 testes aprovados | 0 falhas");
        if (aprovados != 28) throw new AssertionError("Quantidade inesperada");
    }

    /**
     * Registra uma verificação bem-sucedida.
     * @param nome descrição do caso
     */
    private static void passou(String nome) {
        aprovados++;
        System.out.printf("[OK] %02d - %s%n", aprovados, nome);
    }

    /** Compara inteiros.
     * @param nome descrição
     * @param esperado valor de referência
     * @param obtido valor calculado
     */
    private static void igual(String nome, int esperado, int obtido) {
        if (esperado != obtido) throw new AssertionError(nome + ": esperado " + esperado + ", obtido " + obtido);
        passou(nome + " | esperado=" + esperado + " | obtido=" + obtido);
    }

    /** Verifica o contrato original, incluindo atributo e saída do console.
     * @param c calculadora
     * @param a primeiro operando
     * @param b segundo operando
     * @param op operador
     * @param esperado resultado esperado
     * @param mensagem trecho esperado no feedback de erro, ou null
     */
    private static void testarCalc(Calculadora c, int a, int b, String op,
                                  int esperado, String mensagem) {
        PrintStream console = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int obtido;
        try (PrintStream captura = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(captura);
            obtido = c.calc(a, b, op);
        } finally { System.setOut(console); }
        String saida = buffer.toString(StandardCharsets.UTF_8);
        if (obtido != esperado || c.r != esperado || !saida.contains("Resultado = " + esperado)
            || (mensagem != null && !saida.contains(mensagem))
            || (mensagem == null && (saida.contains("Erro") || saida.contains("invalida"))))
            throw new AssertionError("Falha em calc(" + a + ", " + b + ", " + op + "): " + saida);
        passou("calc(" + a + ", " + b + ", " + op + ") | esperado=" + esperado + " | obtido=" + obtido);
        if (mensagem != null) {
            System.out.println("     Feedback esperado: " + mensagem);
            System.out.println("     Feedback obtido: " + saida.lines().findFirst().orElse(""));
        }
    }

    /** Confere tipo de exceção e mensagem informativa.
     * @param nome descrição
     * @param tipo classe esperada
     * @param trecho mensagem esperada ou null
     * @param acao operação que deve falhar
     */
    private static void erro(String nome, Class<? extends RuntimeException> tipo,
                             String trecho, Runnable acao) {
        try { acao.run(); }
        catch (RuntimeException e) {
            if (!tipo.isInstance(e) || e.getMessage() == null || e.getMessage().isEmpty()
                || (trecho != null && !e.getMessage().contains(trecho)))
                throw new AssertionError(nome + ": exceção inesperada", e);
            passou(nome + " | esperado=" + tipo.getSimpleName()
                + " | obtido=" + e.getClass().getSimpleName());
            System.out.println("     Mensagem obtida: " + e.getMessage());
            return;
        }
        throw new AssertionError(nome + ": exceção não lançada");
    }
}
