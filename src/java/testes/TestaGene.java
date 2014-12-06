/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.util.Random;
import modelo.binario.Gene;

/**
 *
 * @author Gleywson
 */
public class TestaGene {

    public static void main(String[] args) {
        Gene[] genes = new Gene[3];
        
        //inicializacao
        for (int i = 0; i < genes.length; i++) {
            genes[i] = new Gene();
            genes[i].mutacao(0.19f);
        }
        
        
        int pontoDeCorte = 1 + new Random().nextInt(genes.length - 1);
        System.out.println(pontoDeCorte);
    }
}
