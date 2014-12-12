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
public class Testes {
    public static void main(String[] args) {
        Cromossomo c = new Cromossomo(20);
        Cromossomo c1 = new Cromossomo(10);
        Cromossomo c2 = new Cromossomo(4);
        
        System.out.println(c + " -> " + c.getX()[0] + "," + c.getX()[1]);
        System.out.println(c1 + " -> " + c1.getX()[0] + "," + c1.getX()[1]);
        System.out.println(c2 + " -> " + c2.getX()[0] + "," + c2.getX()[1]);
    }
}
