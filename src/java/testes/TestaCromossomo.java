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
        Cromossomo novo = new Cromossomo(10);
        System.out.println("Novo Cromossomo: " + novo);
        System.out.println(novo.getX()[0]+", "+novo.getX()[1]);
        System.out.println(novo.getFitness());
        System.out.println(novo.getTamanho());
    }
}
