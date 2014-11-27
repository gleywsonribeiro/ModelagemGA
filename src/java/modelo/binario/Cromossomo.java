/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Gleywson
 * Cromossomo modelado para a funcao: x^2+y^2
 * 
 */
public class Cromossomo {

    private final float x[];
    private Gene[] genes;
    //Não vejo necessidade por enquanto de controlar um cromossomo mutante
    //private boolean mutante;
    
    private final float limiteInferior;
    private final float limiteSuperior;

    public Cromossomo(int tamanho) {
        limiteInferior = -2.048f;
        limiteSuperior = 2.048f;
        this.genes = new Gene[tamanho];
        //this.mutante = false;
        this.x = new float[2];
        initCromossomo();
        //initX();
    }

    //inicializa o cromossomo para um estado consistente
    private void initCromossomo() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = new Gene();
        }
    }

    //nao ha real necessidade de inicializar o vetor x logo no inicio, pois o 
    //cromossomo pode sofrer mutacao, o que nao seria refletido na evolucao
    private void initX() {
        int comprimento = getGenes().length;
        int meio = comprimento / 2;
        //a atribuicao de k, eh somente para facilitar o entendimento
        int k = meio;
        int[] decimal = new int[2];

        //Divide o cromossomo ao meio em formato de string
        String segmentos[] = new String[2];
        segmentos[0] = this.toString().substring(0, meio);
        segmentos[1] = this.toString().substring(meio, comprimento);

        /**
         * Cacula os valores de binario para float e atribui no vetor x que representa uma
         * solucao real
         */
        for (int i = 0; i < segmentos.length; i++) {
            decimal[i] = Integer.parseInt(segmentos[i], 2);
            x[i] = toReal(decimal[i], k);
        }
    }

    private float toReal(int valor, int k) {
        float min = this.limiteInferior;
        float max = this.limiteSuperior;

        double xReal = min + ((valor * (max - min)) / (Math.pow(2, k) - 1));
        return (float) xReal;
    }

    public void setGenes(String geneString) {
        if (geneString.length() > getTamanho()) {
            return;
        }

        for (int i = 0; i < geneString.length(); i++) {
            char temp = geneString.charAt(i);
            int gene = Integer.parseInt(String.valueOf(temp));
            this.genes[i].setBit(gene);
        }
    }
 
    public float[] getX() {
        initX();
        return x;
    }

    private int getTamanho() {
        return genes.length;
    }

    public Gene[] getGenes() {
        return genes;
    }

    public void setGenes(Gene[] genes) {
        this.genes = genes;
    }

   

    //A priori esse comportamento de cruzamento vai ficar na classe Populacao
    
//    
//    public Cromossomo[] crossover(Cromossomo cruzador, float mutationRate, TipoCrossover crossover) {
//        Random random = new Random();
//        Cromossomo[] filhos = new Cromossomo[2];
//
//        for (int k = 0; k < filhos.length; k++) {
//            filhos[k] = new Cromossomo(getTamanho());
//        }
//
//        switch (crossover) {
//            case UNIFORME:
//                for (int i = 0; i < getTamanho(); i++) {
//                    int bitMae = this.genes[i].getBit();
//                    int bitPai = cruzador.getGenes()[i].getBit();
//
//                    if (random.nextBoolean()) {
//                        filhos[0].genes[i].setBit(bitMae);
//                        filhos[1].genes[i].setBit(bitPai);
//                    } else {
//                        filhos[1].genes[i].setBit(bitMae);
//                        filhos[0].genes[i].setBit(bitPai);
//                    }
//                }
//                break;
//            case DOIS_PONTOS:
//                int[] cortes = new int[2];
//
//                for (int i = 0; i < cortes.length; i++) {
//                    boolean repete;
//                    do {
//                        repete = false;
//                        cortes[i] = 1 + random.nextInt(genes.length - 1);
//                        for (int j = 0; j < i; j++) {
//                            if (cortes[i] == cortes[j]) {
//                                repete = true;
//                                break;
//                            }
//                        }
//                    } while (repete);
//
//                }
//                Arrays.sort(cortes);
//
//                String[] paiSement = new String[3];
//                String[] maeSement = new String[3];
//
//                paiSement[0] = toString().substring(0, cortes[0]);
//                maeSement[0] = cruzador.toString().substring(0, cortes[0]);
//
//                paiSement[1] = toString().substring(cortes[0], cortes[1]);
//                maeSement[1] = cruzador.toString().substring(cortes[0], cortes[1]);
//
//                paiSement[2] = toString().substring(cortes[1], toString().length());
//                maeSement[2] = cruzador.toString().substring(cortes[1], toString().length());
//
//                /**
//                 int i = 0;
//                 int j = 0;
//                 while (i < cortes.length) {
//                 paiSement[i] = toString().substring(j, cortes[i]);
//                 maeSement[i] = cruzador.toString().substring(j, cortes[i]);
//                 j = cortes[i];
//                 i++;
//                 }
//                 i++;
//                 paiSement[i] = toString().substring(j, cortes.length);
//                 maeSement[i] = cruzador.toString().substring(j, cortes.length);
//                 */
//                String[] stringFilhos = new String[2];
//
//                for (int index = 0; index < paiSement.length; index++) {
//                    if (index % 2 == 0) {
//                        stringFilhos[0] += paiSement[index];
//                        stringFilhos[1] += maeSement[index];
//                    } else {
//                        stringFilhos[1] += paiSement[index];
//                        stringFilhos[0] += maeSement[index];
//                    }
//                }
//
//                for (int index = 0; index < filhos.length; index++) {
//                    filhos[index].setGenes(stringFilhos[index]);
//                }
//
//                break;
//            case UM_PONTO:
//                int pontoDeCorte = 1 + random.nextInt(genes.length - 1);
//                int comprimento = genes.length;
//
//                String paiSegmento1 = toString().substring(0, pontoDeCorte);
//                String paiSegmento2 = toString().substring(pontoDeCorte, comprimento);
//
//                String maeSegmento1 = cruzador.toString().substring(0, pontoDeCorte);
//                String maeSegmento2 = cruzador.toString().substring(pontoDeCorte, comprimento);
//
//                String[] stringfilho = new String[2];
//                stringfilho[0] = paiSegmento1 + maeSegmento2;
//                stringfilho[1] = maeSegmento1 + paiSegmento2;
//
//                for (int index = 0; index < filhos.length; index++) {
//                    filhos[index].setGenes(stringfilho[index]);
//                }
//                break;
//            default:
//                System.err.println("Algo errado no tipo de crossover!");
//        }
//
//        for (Cromossomo i : filhos) {
//            for (Gene gene : i.genes) {
//                gene.mutacao(mutationRate);
//            }
//        }
//
//        return filhos;
//    }

    public float getFitness() {
        double f_x;
        //f_x = 100 * Math.pow((x[1] - Math.pow(x[0], 2)), 2) + Math.pow(1 - x[0], 2);
        
       f_x = Math.pow(getX()[0], 2) + Math.pow(getX()[1], 2);
        return (float) f_x;
    }

    //A priori, este metodo eh tb desnecessario
//    
//    public double getFitComplement(double somatorio) {
//        return (somatorio - getFitness());
//    }

    @Override
    public String toString() {
        String saida = "";
        for (Gene gene : genes) {
            saida += gene;
        }

        return saida;
//        List saida = new ArrayList();
//        saida.addAll(Arrays.asList(genes));
        
    }
    
    
    //Retorna uma lista de genes. Talvez seja retirado tambem, nao vejo necessidade
    List asList() {
        List saida = new ArrayList();
        saida.addAll(Arrays.asList(genes));
        return saida;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Cromossomo)) {
            return false;
        }

        Cromossomo cromossomo = (Cromossomo) object;

        for (int i = 0; i < genes.length; i++) {
            if (!(this.getGenes()[i].equals(cromossomo.getGenes()[i]))) {
                return false;
            }
        }
        return true;
    }
}
