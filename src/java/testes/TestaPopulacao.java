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
        System.out.println(p.getIndividuos().size());
        System.out.println(p.getIndividuos().iterator().next().toString());
    }
}
