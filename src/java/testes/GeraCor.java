/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.util.Random;

/**
 *
 * @author gleyw
 */
public class GeraCor {
    public static void main(String[] args) {
        Random geraCor = new Random();
        for (int i = 0; i < 10; i++) {
            String cor = String.format("#%06X", geraCor.nextInt(0xFFFFFF+1));
            System.out.println(cor);
        }
    }
}
