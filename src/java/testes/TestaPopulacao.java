/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.util.Arrays;
import modelo.binario.Cromossomo;
import modelo.binario.Populacao;

/**
 *
 * @author Gleywson
 */
public class TestaPopulacao {

    public static void main(String[] args) {
        Populacao p = new Populacao(10, 10);

        System.out.println(p.getIndividuos()[0].toString());
        System.out.println("Fitness");
        for (Cromossomo i : p.getIndividuos()) {
            System.out.printf("%.4f\n", i.getFitness());
        }
        
        double[] valores = {1.5, 3.6, 2.4, 9.1, 7.345, 7.256, 6.34, 10.34,10.31};

        Arrays.sort(valores);

        for (int i = 0; i < valores.length; i++) {
            System.out.printf("%.4f\t",valores[i]);
        }

    }
}
