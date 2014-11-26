/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import modelo.binario.Gene;

/**
 *
 * @author Gleywson
 */
public class TestaGene {

    public static void main(String[] args) {
        Gene[] genes = new Gene[100];
        
        //inicializacao
        for (int i = 0; i < genes.length; i++) {
            genes[i] = new Gene();
            genes[i].mutacao(0.19f);
        }
        int contador = 0;
        for (Gene gene : genes) {
            if(gene.isMutante()) {
                contador++;
            }
        }
        
        System.out.println(contador);
        
    }
}
