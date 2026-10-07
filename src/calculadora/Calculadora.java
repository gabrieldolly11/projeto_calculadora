package calculadora;

/**
 * Núcleo de operações inteiras da FinançApp.
 * <p>Os métodos de operação e {@link #calcular(int, int, String)} são puros.
 * O método {@link #calc(int, int, String)} mantém a interface original da aula,
 * atualizando {@link #r} e exibindo feedback no console.</p>
 * <p>A divisão inteira descarta a parte fracionária em direção a zero.
 * Esta classe didática não representa valores monetários com centavos.</p>
 * @author Gabriel Henrique Fernandes Torres
 * @version 2.0
 */
public class Calculadora {
    /** Último resultado de calc; zero quando a solicitação é inválida. */
    public int r = 0;

    /** Cria uma calculadora com resultado inicial zero. */
    public Calculadora() { }

    /**
     * Soma dois inteiros.
     * @param a primeiro operando
     * @param b segundo operando
     * @return soma dos operandos
     * @throws ArithmeticException se o resultado exceder o intervalo de int
     */
    public int somar(int a, int b) { return Math.addExact(a, b); }

    /**
     * Subtrai o segundo inteiro do primeiro.
     * @param a minuendo
     * @param b subtraendo
     * @return diferença entre os operandos
     * @throws ArithmeticException se o resultado exceder o intervalo de int
     */
    public int subtrair(int a, int b) { return Math.subtractExact(a, b); }

    /**
     * Multiplica dois inteiros.
     * @param a primeiro fator
     * @param b segundo fator
     * @return produto dos fatores
     * @throws ArithmeticException se o resultado exceder o intervalo de int
     */
    public int multiplicar(int a, int b) { return Math.multiplyExact(a, b); }

    /**
     * Divide dois inteiros, truncando a parte fracionária em direção a zero.
     * @param a dividendo
     * @param b divisor
     * @return quociente inteiro
     * @throws IllegalArgumentException se o divisor for zero
     * @throws ArithmeticException se o quociente exceder o intervalo de int
     */
    public int dividir(int a, int b) {
        if (b == 0) throw new IllegalArgumentException("Divisão por zero não é permitida.");
        if (a == Integer.MIN_VALUE && b == -1)
            throw new ArithmeticException("Resultado fora do intervalo de int.");
        return a / b;
    }

    /**
     * Seleciona a operação sem alterar o resultado armazenado nem imprimir.
     * @param a primeiro operando
     * @param b segundo operando
     * @param operador um dos símbolos +, -, * ou /
     * @return resultado inteiro quando a operação for válida
     * @throws IllegalArgumentException se o operador for inválido ou nulo,
     *         ou se ocorrer divisão por zero
     * @throws ArithmeticException se o resultado exceder o intervalo de int
     */
    public int calcular(int a, int b, String operador) {
        if (operador == null) throw new IllegalArgumentException("Operador não pode ser nulo.");
        switch (operador) {
            case "+": return somar(a, b);
            case "-": return subtrair(a, b);
            case "*": return multiplicar(a, b);
            case "/": return dividir(a, b);
            default: throw new IllegalArgumentException("Operador inválido: " + operador);
        }
    }

    /**
     * Executa a interface original com feedback e retorno int em todos os casos
     * de entrada previstos. Falhas de validação são tratadas neste método.
     * <p>Em caso de erro, retorna zero e imprime uma mensagem. O zero isolado
     * não distingue erro de resultado válido; para integração, utilize calcular
     * e trate suas exceções na camada de interface.</p>
     * @param a primeiro operando
     * @param b segundo operando
     * @param op símbolo da operação
     * @return resultado ou zero quando houver erro de entrada ou overflow
     */
    public int calc(int a, int b, String op) {
        try { r = calcular(a, b, op); }
        catch (IllegalArgumentException e) {
            r = 0;
            if ("/".equals(op) && b == 0)
                System.out.println("Erro: divisao por zero, operação irregular");
            else System.out.println("Operacao invalida");
        } catch (ArithmeticException e) {
            r = 0;
            System.out.println("Erro: resultado fora do intervalo de int.");
        }
        System.out.println("Resultado = " + r);
        return r;
    }
}
