/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import exceptions.AlgoritmoGeneticoExpection;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.binario.Cromossomo;

/**
 *
 * @author Gleywson
 */
public class TestaCromossomo {
    public static void main(String[] args) {
        try {
            Cromossomo c = new Cromossomo(10);
            c.setGenes("1111111111");
            System.out.println(c);
            System.out.println(c.getFitness());
            
            for(double i:c.getX()) {
                System.out.println(i);
            }
            Cromossomo c2 = new Cromossomo(10);
            c2.setGenes("1111111111");
            
            System.out.println(c.equals(c2));
        } catch (AlgoritmoGeneticoExpection ex) {
            Logger.getLogger(TestaCromossomo.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao setar o Cromossomo");
        }
      
    }
}
