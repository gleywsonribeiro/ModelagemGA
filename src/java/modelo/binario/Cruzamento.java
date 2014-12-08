/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Raquel
 */
public class Cruzamento {

    public static Cromossomo[] cruza(Cromossomo c1, Cromossomo c2, float txCruzamento, float TxMutacao, TipoCrossover crossover) {
        //Variavel usada para sorteios aletorios
        Random random = new Random();
        //variavel usada para armazenar o comprimento dos cromossomos, usada sempre que necessario
        int comprimentoCromossomo = c1.getTamanho();
        //array que sera retorno no final
        Cromossomo[] filhos = new Cromossomo[2];

        //Inicializacao com cromossomos quaisquer, no final eles sao setados com
        //com os filhos resultantes do cruzamento
        for (int k = 0; k < filhos.length; k++) {
            filhos[k] = new Cromossomo(comprimentoCromossomo);
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
                int pontoDeCorte = 1 + random.nextInt(comprimentoCromossomo - 1);

                String paiSegmento1 = c1.toString().substring(0, pontoDeCorte);
                String paiSegmento2 = c1.toString().substring(pontoDeCorte, comprimentoCromossomo);

                String maeSegmento1 = c2.toString().substring(0, pontoDeCorte);
                String maeSegmento2 = c2.toString().substring(pontoDeCorte, comprimentoCromossomo);

                String[] stringfilho = new String[2];
                stringfilho[0] = paiSegmento1 + maeSegmento2;
                stringfilho[1] = maeSegmento1 + paiSegmento2;

                for (int index = 0; index < filhos.length; index++) {
                    filhos[index].setGenes(stringfilho[index]);
                }
                break;
            case DOIS_PONTOS:
                int[] cortes = new int[2];
                for (int i = 0; i < cortes.length; i++) {
                    boolean repete;
                    do {
                        repete = false;
                        cortes[i] = 1 + random.nextInt(comprimentoCromossomo - 1);
                        for (int j = 0; j < i; j++) {
                            if (cortes[i] == cortes[j]) {
                                repete = true;
                                break;
                            }
                        }
                    } while (repete);

                }
                //Ordena o vetor de corte
                Arrays.sort(cortes);

                String[] paiSement = new String[3];
                String[] maeSement = new String[3];

                //pega parte dos comossomos pra montar os locus que serao combinados
                paiSement[0] = c1.toString().substring(0, cortes[0]);
                maeSement[0] = c2.toString().substring(0, cortes[0]);

                paiSement[1] = c1.toString().substring(cortes[0], cortes[1]);
                maeSement[1] = c2.toString().substring(cortes[0], cortes[1]);

                paiSement[2] = c1.toString().substring(cortes[1], comprimentoCromossomo);
                maeSement[2] = c2.toString().substring(cortes[1], comprimentoCromossomo);
                
                //array de string que vai fazer receber as combinacoes de locus
                String[] stringFilhos = new String[2];
                
                
                //Combinacao ocorrendo aqui
                for (int index = 0; index < paiSement.length; index++) {
                    if (index % 2 == 0) {
                        stringFilhos[0] += paiSement[index];
                        stringFilhos[1] += maeSement[index];
                    } else {
                        stringFilhos[1] += paiSement[index];
                        stringFilhos[0] += maeSement[index];
                    }
                }

                for (int index = 0; index < filhos.length; index++) {
                    filhos[index].setGenes(stringFilhos[index]);
                }

                break;
            default:
                System.err.println("Algo errado no tipo de crossover!");
        }
        return filhos;
    }
}
