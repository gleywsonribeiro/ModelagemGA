/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import modelo.binario.Cromossomo;

/**
 *
 * @author Gleywson
 */
public class TestaCromossomo {
    public static void main(String[] args) {
        Cromossomo c = new Cromossomo(10);
        c.setGenes("1111111111");
        System.out.println(c);
        System.out.println(c.getFitness());
        
        for(float i:c.getX()) {
            System.out.println(i);
        }
        Cromossomo c2 = new Cromossomo(10);
        c2.setGenes("11111111110");
        
        System.out.println(c.equals(c2));
        
    }
}
