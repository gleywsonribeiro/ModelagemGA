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
 * @author Gleywson
 */
public class Populacao {

    /*
     Os atributos que sao finais, o sao simplesmente pq nao sao setados durante
     a execucao, caso haja necessidade de modificar, os mesmos deixam de ser final
     */

    private final int tamanhoPopulacao;
    private Cromossomo[] individuos;
    private double[] normalizados;
    private final Cromossomo[] temp;
    private Selecao selecao;
    private TipoCrossover crossover;
    private boolean elitismo;

    /*
     Por padrao, a populacao sempre sera inicializada com o tipo de crossover
     como sendo de um ponto e o tipo de selecao serah o torneio
     */
    public Populacao(int tamanhoPop, int tamanhoCromo) {
        this.tamanhoPopulacao = tamanhoPop;
        this.individuos = new Cromossomo[tamanhoPopulacao];
        this.temp = new Cromossomo[tamanhoPopulacao];
        this.normalizados = new double[tamanhoPopulacao];
        //inicializa a lista de individuos
        initPopulacao(tamanhoCromo);
        this.crossover = TipoCrossover.UM_PONTO;
        this.selecao = Selecao.TORNEIO;
    }

    //passo o tamanho que cada cromossomo vai ter. Esse parametro eh passado 
    //no construtor. Criacao aleatoria da populacao
    private void initPopulacao(int tamanhoIndividuo) {
        for (int i = 0; i < individuos.length; i++) {
            individuos[i] = new Cromossomo(tamanhoIndividuo);
        }
    }

    
    public void geracao(float txMutacao, float txCruzamento) {
        Random random = new Random();
        double chanceCruzamento = random.nextFloat();

        int indice = 0;

        if (isElitismo()) {
            temp[indice] = elitismo();
            indice++;
        }
        /**
         * Aqui acontece o cruzamento mediante a taxa de cruzamento e caso a
         * mesma nao ocorra os pais vao pra proxima geracao
         */
        while (indice < temp.length) {
            Casal casal = casamento();
            Cromossomo[] nextGeneration;
            if (chanceCruzamento < txCruzamento) {
                nextGeneration = casal.cruza(txMutacao);
            } else {
                nextGeneration = casal.getConjuges();
            }

            for (Cromossomo i : nextGeneration) {
                if (indice < tamanhoPopulacao) {
                    temp[indice] = i;
                    indice++;
                } else {
                    break;
                }
            }
        }
        individuos = temp.clone();

    }

    private Casal casamento() {
        Cromossomo pai = seleciona(selecao);

        Cromossomo mae;
        do {
            mae = seleciona(selecao);
        } while (pai.equals(mae));

        return new Casal(pai, mae, crossover);
    }

    private Cromossomo elitismo() {
        Cromossomo maisApto = null;
        double valor = Float.MIN_VALUE;
        
        for (Cromossomo individuo : individuos) {
            if(individuo.getFitness() > valor) {
                valor = individuo.getFitness();
                maisApto = individuo;
            }
        }
        
        return maisApto;
    }

    private Cromossomo torneio(int n) {
        if (n < 1 && n > individuos.length) {
            //criar um mecanismo pra evitar esse erro
            System.err.println("Numero de competidores fora da faixa válida!");
        }

       

        Cromossomo[] competidores = new Cromossomo[n];
        int[] numeroCompetidores = new int[n];
        Random random = new Random();
        for (int i = 0; i < numeroCompetidores.length; i++) {
            boolean repete;
            do {
                repete = false;
                numeroCompetidores[i] = 1 + random.nextInt(individuos.length - 1);
                for (int j = 0; j < i; j++) {
                    if (numeroCompetidores[i] == numeroCompetidores[j]) {
                        repete = true;
                        break;
                    }
                }
            } while (repete);
        }

        for (int i = 0; i < competidores.length; i++) {
            competidores[i] = individuos[numeroCompetidores[i]];
        }

        Cromossomo maisApto = null;
        double valor = Float.MIN_VALUE;
        for (Cromossomo competidor : competidores) {
            if (competidor.getFitness() > valor) {
                valor = competidor.getFitness();
                maisApto = competidor;
            }
        }
        return maisApto;
    }

    private Cromossomo roleta() {
        Cromossomo[] roleta;
        double somatorio = 0;
        double[] acumulador = new double[individuos.length];
        roleta = individuos.clone();
        
        for (Cromossomo i : roleta) {
            somatorio += i.getFitness();
        }

        acumulador[0] = roleta[0].getFitness();

        for (int j = 1; j < roleta.length; j++) {
            acumulador[j] = roleta[j].getFitness() + acumulador[j - 1];
        }

        double sorteio = new Random().nextFloat() * somatorio;

        int k = 0;
        while (k < acumulador.length) {
            if (sorteio < acumulador[k]) {
                break;
            }
            k++;
        }
        return roleta[k];

    }
    
    private Cromossomo normalizacao() {
        Arrays.sort(individuos);
        double min = individuos[0].getFitness();
        double max = individuos[individuos.length - 1].getFitness();
        
        for (int i = 0; i < individuos.length; i++) {
            normalizados[i] = min + ((max - min) / individuos.length - 1) * i;
        }
        //Usando metodo da roleta, so que pela normalizacao
        double[] roleta;
        double somatorio = 0;
        double[] acumulador = new double[individuos.length];
        roleta = normalizados.clone();
        
        for (double i : roleta) {
            somatorio += i;
        }

        acumulador[0] = roleta[0];

        for (int j = 1; j < roleta.length; j++) {
            acumulador[j] = roleta[j] + acumulador[j - 1];
        }

        double sorteio = new Random().nextFloat() * somatorio;

        int k = 0;
        while (k < acumulador.length) {
            if (sorteio < acumulador[k]) {
                break;
            }
            k++;
        }
        return individuos[k];
    }

    private Cromossomo seleciona(Selecao selecao) {
        int n = 3;
        Cromossomo c = null;
        switch (selecao) {
            case ROLETA:
                c = roleta();
                break;
            case TORNEIO:
                c = torneio(n);
                break;
            case NORMALIZACAO_LINEAR:
                c = normalizacao();
        }
        return c;
    }

    public Selecao getSelecao() {
        return selecao;
    }

    public void setSelecao(Selecao selecao) {
        this.selecao = selecao;
    }

    public TipoCrossover getCrossover() {
        return crossover;
    }

    public void setCrossover(TipoCrossover crossover) {
        this.crossover = crossover;
    }

    public Cromossomo[] getIndividuos() {
        return individuos;
    }

    public int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public boolean isElitismo() {
        return elitismo;
    }

    public void setElitismo(boolean elitismo) {
        this.elitismo = elitismo;
    }

    public Cromossomo getPiorIndividuo() {
        Cromossomo pior = null;
        double valor = Float.MAX_VALUE;
        
        for (Cromossomo individuo : individuos) {
            if(individuo.getFitness() < valor){
                valor = individuo.getFitness();
                pior = individuo;
            }
        }
        
        return pior;
    }

    public Cromossomo getMelhorIndividuo() {
        return elitismo();
    }

    public float getMedia() {
        float somatorio = 0;
        for (Cromossomo individuo : individuos) {
            somatorio += individuo.getFitness();
        }
        float media = (somatorio / individuos.length);
        return media;
    }

    public float getDesvioPadrao() {
        float variancia = 0;
        for (Cromossomo individuo : individuos) {
            variancia += Math.pow(individuo.getFitness() - getMedia(), 2);
        }

        float desvioPadrao = (float) Math.sqrt(variancia / (individuos.length - 1));

        return desvioPadrao;
        
    }

}