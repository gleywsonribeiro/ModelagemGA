/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import modelo.binario.Populacao;

/**
 *
 * @author Gleywson
 */
public class TestaPopulacao {
    public static void main(String[] args) {
        Populacao p = new Populacao(50, 10);
        
        System.out.println(p.getSelecao());
        System.out.println(p.getCrossover());
        System.out.println(p.getIndividuos().length);
        System.out.println(p.getIndividuos()[0].toString());
        
        System.out.println(p.getMedia());
        System.out.println(p.getMelhorIndividuo().getFitness());
        System.out.println(p.getDesvioPadrao());
        System.out.println("passou aqui");
        
        
//        for (int i = 0; i < 10; i++) {
//            p.geracao(0.01f, 0.85f);
//        }
//        System.out.println(p.getMedia());
    }
}
