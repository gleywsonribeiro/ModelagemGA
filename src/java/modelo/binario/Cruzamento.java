/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Raquel
 */
public class Cruzamento {

    public static Cromossomo[] cruza(Cromossomo c1, Cromossomo c2, float txCruzamento, float TxMutacao, TipoCrossover crossover) {
        Random random = new Random();

        Cromossomo[] filhos = new Cromossomo[2];

        for (int k = 0; k < filhos.length; k++) {
            filhos[k] = new Cromossomo(c1.getTamanho());
        }

        switch (crossover) {
            case UNIFORME:
                for (int i = 0; i < c1.getGenes().length; i++) {

                    int bitMae = c1.getGenes()[i].getBit();
                    int bitPai = c2.getGenes()[i].getBit();

                    if (random.nextBoolean()) {
                        filhos[0].getGenes()[i].setBit(bitMae);
                        filhos[1].getGenes()[i].setBit(bitPai);
                    } else {
                        filhos[1].getGenes()[i].setBit(bitMae);
                        filhos[0].getGenes()[i].setBit(bitPai);
                    }
                }
                break;
            case UM_PONTO:
                int comprimento = c1.getTamanho();
                int pontoDeCorte = 1 + random.nextInt(comprimento - 1);

                String paiSegmento1 = c1.toString().substring(0, pontoDeCorte);
                String paiSegmento2 = c1.toString().substring(pontoDeCorte, comprimento);

                String maeSegmento1 = c2.toString().substring(0, pontoDeCorte);
                String maeSegmento2 = c2.toString().substring(pontoDeCorte, comprimento);

                String[] stringfilho = new String[2];
                stringfilho[0] = paiSegmento1 + maeSegmento2;
                stringfilho[1] = maeSegmento1 + paiSegmento2;

                for (int index = 0; index < filhos.length; index++) {
                    filhos[index].setGenes(stringfilho[index]);
                }
                break;
            default:
                System.err.println("Algo errado no tipo de crossover!");
        }
        return null;
    }
}
