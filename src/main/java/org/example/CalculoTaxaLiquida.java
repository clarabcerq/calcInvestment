package org.example;

import java.util.Scanner;

public class CalculoTaxaLiquida {
    public static void main(String[] args) {
        CalculoTaxaLiquida c = new CalculoTaxaLiquida();
        c.lerEntradas();

        double montante = c.montante(c.capIn, c.periodo);
        double lb = c.lucroBruto();
        double ir = c.impostoRenda();
        double mLiq = c.mLiquido();
        double taxaLiquida = c.taxaLiquidaMensal();

        System.out.println("O seu montante final bruto é R$" + String.format("%.2f", montante));
        System.out.println("Seu lucro bruto é de R$" + String.format("%.2f", lb));
        System.out.println("O imposto de renda que deverá ser pago é R$" + String.format("%.2f", ir));
        System.out.println("Seu montante líquido é R$" + String.format("%.2f", mLiq));
        System.out.println("A sua taxa líquida mensal, ou seja, quanto seu dinheiro renderá por mês é de " + String.format("%.2f", taxaLiquida) + "%");
    }

    private double capIn;
    private double taxa_anual;
    private int periodo;
    //private double aliquota;

    Scanner sc = new Scanner(System.in);

    public void lerEntradas() {
        System.out.println("Informe o capital inicial: ");
        capIn = sc.nextDouble();

        System.out.println("Informe a taxa anual: ");
        taxa_anual = sc.nextDouble();

        System.out.println("Informe o número de meses: ");
        periodo = sc.nextInt();
    }

    //convertendo a taxa anual de rendimeno em uma taxa de rendimento mensal
    public double conversaoTaxaAnualEmMensal(double taxa_anual) {
        double taxaM = Math.pow(1 + taxa_anual / 100, (double) 1 / 12) - 1;
        return taxaM;
    }

    //1- Montante final bruto usando juros compostos
    public double montante(double capIn, int periodo) {
        double resultadoPotencia = Math.pow(1 + conversaoTaxaAnualEmMensal(taxa_anual), periodo);
        double m = capIn * resultadoPotencia;
        return m;
    }

    //2- Lucro bruto
    public double lucroBruto(){
        double m = montante(capIn, periodo);
        double lucroBruto = m - capIn;
        return lucroBruto;
    }

    public double calculoAliquota(int periodo) {
        double aliquota;
        if (periodo >= 1 && periodo <= 6) {
            aliquota = 22.5;
        }else if (periodo > 6 && periodo <= 12) {
            aliquota = 20;
        }else if (periodo > 12 && periodo <= 24) {
            aliquota = 17.5;
        }else{
            aliquota = 15;
        }
        return aliquota;
    }

    //3- Imposto de Renda. Como o investimento é de 2 anos, a alíquota de IR será de 15%.
    public double impostoRenda() {
        double aliquota = calculoAliquota(periodo);
        double aliq = aliquota / 100;
        double m = montante(capIn, periodo);
        double lucroBruto = m - capIn;
        double ir = lucroBruto * aliq;
        return ir;
    }

    //4- Montante líquido após o IR
    public double mLiquido() {
        double m = montante(capIn, periodo);
        double ir = impostoRenda();
        double mLiq = m - ir;
        return mLiq;
    }

    //5- Taxa líquida anual
    public double taxaLiquidaMensal() {
        double mLiq = mLiquido();
        double resultadoPotencia = Math.pow(mLiq / capIn, 1.0 / periodo);
        double taxaLiquida = (resultadoPotencia - 1) * 100;
        return taxaLiquida;
    }
}
