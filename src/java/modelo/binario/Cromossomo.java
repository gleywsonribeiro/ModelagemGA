/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.lang.Math.*;

/**
 *
 * @author Gleywson Cromossomo modelado para a funcao de ACKLEY: a = 20; b =
 * 0.2; c = 2*pi;
 *
 * Z = -a*exp(-b * sqrt((X.^2+Y.^2)/2)) - exp((cos(c*X) + cos(c*Y))/2) + a +
 * exp(1);
 */
public class Cromossomo implements Comparable<Cromossomo> {
    //vetor com os valores das variaveis
    private final double x[];
    //Vetor que guarda o array de genes, que pode ser especificado pelo usuario
    private Gene[] genes;
    //limites do domínio de busca
    private final double limiteInferior;
    private final double limiteSuperior;

    public Cromossomo(int tamanho) {
        //para este problema eles foram definidos, mas podem ser setados
        limiteInferior = -100;
        limiteSuperior = 100;
        //inicializo o vetor de genes com o tamanho passado no construtor
        this.genes = new Gene[tamanho];
        //como sao somentes duas variaveis
        this.x = new double[2];
        //inicializo o cromossomo aqui
        initCromossomo();
    }

    //caso seja preciso setar na tela, esse construtor podera ser usado
    public Cromossomo(int tamanho, float lmtSuperior, float lmtInferior) {
        limiteInferior = lmtInferior;
        limiteSuperior = lmtSuperior;
        this.genes = new Gene[tamanho];
        this.x = new double[2];
        initCromossomo();
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
         * Cacula os valores de binario para float e atribui no vetor x que
         * representa uma solucao real
         */
        for (int i = 0; i < segmentos.length; i++) {
            //aqui converto de binario pra decimal
            decimal[i] = Integer.parseInt(segmentos[i], 2);
            x[i] = toReal(decimal[i], k);
        }
    }

    private float toReal(int valor, int k) {
        double min = this.limiteInferior;
        double max = this.limiteSuperior;
        //aqui converto pra real
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
            this.genes[i].setAlelo(gene);
        }
    }

    //retorna o valor atual do cromossomo
    public double[] getX() {
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

    public double getFitness() {
        double z;
//        double a = 20;
//        double b = 0.2;
//        double c = 2 * Math.PI;
//
//        z = -a * exp(-b * sqrt((pow(getX()[0], 2) + pow(getX()[1], 2)) / 2)) - exp((cos(c * getX()[0]) + cos(c * getX()[1])) / 2) + a + exp(1);
        //Um simples arranjo para evitar erro na escrita da função
        double X = getX()[0];
        double Y = getX()[1];
        double numerador  = pow(sin(sqrt(pow(X, 2) + pow(Y, 2))), 2) -0.5;
        double denominador = pow(1 + 0.001*(pow(X, 2) + pow(Y, 2)), 2);
        z = 0.5 - (numerador/denominador);
        return z;
    }

    @Override
    public String toString() {
        String saida = "";
        for (Gene gene : genes) {
            saida += gene;
        }

        return saida;

    }

    //Retorna uma lista de genes. Talvez seja retirado tambem, nao vejo necessidade

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Arrays.deepHashCode(this.genes);
        return hash;
    }
  

    @Override
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

    @Override
    public int compareTo(Cromossomo o) {
        if (this.getFitness() < o.getFitness()) {
            return 1;
        }
        if (this.getFitness() > o.getFitness()) {
            return -1;
        }
        return 0;
    }
}
