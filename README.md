# Projeto Calculadora - FinançApp

**Atividade individual**  
**Aluno:** Gabriel Henrique Fernandes Torres  
**RA:** 251157  
**Curso:** Análise e Desenvolvimento de Sistemas  
**Disciplina:** UX/UI e Testes de Software

## Etapa inicial

Calculadora Java com soma, subtração, multiplicação e divisão de inteiros.
O método `calc(int a, int b, String op)` retorna o resultado, impede divisão
por zero e apresenta mensagem para operadores inválidos.

Os seis testes iniciais verificam as quatro operações, divisão por zero e
operador inválido. A saída está em `evidencias/testes_iniciais.txt`.

## Versão final

A versão refatorada, com 28 testes, JavaDoc e README completo, está na
[branch Refatoracao](https://github.com/gabrieldolly11/projeto_calculadora/tree/Refatoracao).

[Repositório](https://github.com/gabrieldolly11/projeto_calculadora)

## Executar

Requisito: JDK 17 ou superior.

```sh
javac -encoding UTF-8 -d bin src/calculadora/*.java
java -cp bin calculadora.TesteCalculadora
```
