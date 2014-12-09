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
    //retorna o valor atual do cromossomo
    public float[] getX() {
        initX();
        return x;
    }

    public int getTamanho() {
        return genes.length;
    }

    public Gene[] getGenes() {
        return genes;
    }

    public void setGenes(Gene[] genes) {
        this.genes = genes;
    }

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
