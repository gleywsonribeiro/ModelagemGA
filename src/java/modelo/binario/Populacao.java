/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    private final Collection<Cromossomo> individuos;
    private Selecao selecao;
    private TipoCrossover tipoCrossover;
    private final Collection<Cromossomo> temp;
    private boolean elitismo;

    /*
     Por padrao, a populacao sempre sera inicializada com o tipo de crossover
     como sendo de um ponto e o tipo de selecao serah o torneio
     */
    public Populacao(int tamanhoPop, int tamanhoCromo) {
        this.tamanhoPopulacao = tamanhoPop;
        this.individuos = new ArrayList<>(tamanhoPopulacao);
        this.temp = new ArrayList<>(tamanhoPopulacao);
        //inicializa a lista de individuos
        initPopulacao(tamanhoCromo);
        this.tipoCrossover = TipoCrossover.UM_PONTO;
        this.selecao = Selecao.TORNEIO;
    }

    //passo o tamanho que cada cromossomo vai ter. Esse parametro eh passado 
    //no construtor. Criacao aleatoria da populacao
    private void initPopulacao(int tamanhoIndividuo) {
        int i = 0;
        while (i < getTamanhoPopulacao()) {
            individuos.add(new Cromossomo(tamanhoIndividuo));
            i++;
        }
    }

    public void geracao(float txMutacao, float txCruzamento) {
        Random random = new Random();
        double chanceCruzamento = random.nextFloat();

        if (isElitismo()) {
            Cromossomo elite = elitismo();
            temp.add(elite);
        }
        /**
         * Aqui acontece o cruzamento mediante a taxa de cruzamento e caso a
         * mesma nao ocorra os pais vao pra proxima geracao
         */
        while (temp.size() < tamanhoPopulacao) {
            Casal casal = casamento();
            Cromossomo[] nextGeneration;
            if (chanceCruzamento < txCruzamento) {
                nextGeneration = casal.cruza(txMutacao);
            } else {
                nextGeneration = casal.getConjuges();
            }

            for (Cromossomo i : nextGeneration) {
                if (temp.size() < tamanhoPopulacao) {
                    temp.add(i);
                } else {
                    break;
                }
            }
        }

        individuos.clear();
        individuos.addAll(temp);
        temp.clear();

    }

    private Casal casamento() {
        Cromossomo pai = seleciona(selecao);

        Cromossomo mae;
        do {
            mae = seleciona(selecao);
        } while (pai.equals(mae));

        return new Casal(pai, mae, tipoCrossover);
    }

    private Cromossomo elitismo() {
        Cromossomo maisApto = null;
        double valor = Float.MIN_VALUE;
        Iterator<Cromossomo> iterator = individuos.iterator();

        while (iterator.hasNext()) {
            Cromossomo elite = iterator.next();
            if (elite.getFitness() > valor) {
                valor = elite.getFitness();
                maisApto = elite;
            }
        }
        //individuos.remove(maisApto); não mais remover pra poder fazer o cruzamento
        return maisApto;
    }

    private Cromossomo torneio(int n) {
        if (n < 1 && n > individuos.size()) {
            //criar um mecanismo pra evitar esse erro
            System.err.println("Numero de competidores fora da faixa válida!");
        }

        Cromossomo[] temporario = new Cromossomo[individuos.size()];
        individuos.toArray(temporario);

        Cromossomo[] competidores = new Cromossomo[n];
        int[] numeroCompetidores = new int[n];
        Random random = new Random();
        for (int i = 0; i < numeroCompetidores.length; i++) {
            boolean repete;
            do {
                repete = false;
                numeroCompetidores[i] = 1 + random.nextInt(individuos.size() - 1);
                for (int j = 0; j < i; j++) {
                    if (numeroCompetidores[i] == numeroCompetidores[j]) {
                        repete = true;
                        break;
                    }
                }
            } while (repete);
        }

        for (int i = 0; i < competidores.length; i++) {
            int current = numeroCompetidores[i];
            competidores[i] = temporario[current];
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
        Cromossomo Cromossomo = new Cromossomo(individuos.size());
        List<Cromossomo> lista = new ArrayList<>(individuos);
        Collections.sort(lista);
        Map<Integer, Cromossomo> mapa = new HashMap();

        for (int i = 0; i < lista.size(); i++) {
            mapa.put(i + 1, lista.get(i));
        }

        int somatorioClassificacao = 0;

        for (Map.Entry<Integer, Cromossomo> i : mapa.entrySet()) {
            somatorioClassificacao += i.getKey();
        }

        Random random = new Random();
        int sorteio = random.nextInt(somatorioClassificacao) + 1;
        int soma = 0;

        for (Map.Entry<Integer, Cromossomo> k : mapa.entrySet()) {
            Integer key = k.getKey();
            soma += key;
            Cromossomo value = k.getValue();
            if (soma > sorteio) {
                return value;
            }

        }

        return Cromossomo;
    }
    
    public Cromossomo roleta2() {
        Cromossomo[] roleta = new Cromossomo[individuos.size()];
        Iterator<Cromossomo> it = individuos.iterator();
        
        
        double somatorio = 0;
        double[] acumulador = new double[individuos.size()];
        
        int i = 0;
        while(i < roleta.length && it.hasNext()) {
            roleta[i] = it.next();
            somatorio += roleta[i].getFitness();
        }
        //somatorio = roleta[0].getFitness();
        acumulador[0] = roleta[0].getFitness();
        
        for (int j = 1; j < roleta.length; j++) {
            acumulador[j] = roleta[j].getFitness() + acumulador[j-1];
        }
        
        double sorteio = new Random().nextFloat() * somatorio;
        
        int k = 0;
        while(k < acumulador.length) {
            if(sorteio > acumulador[k]){
                break;
            }
            k++;
        }
        return roleta[k];
        
    }

    private Cromossomo seleciona(Selecao selecao) {
        int n = 3;
        Cromossomo c;
        switch (selecao) {
            case ROLETA:
                c = roleta2();
                break;
            case TORNEIO:
                c = torneio(n);
                break;
            default:
                c = torneio(n);
        }
        return c;
    }

    public Selecao getSelecao() {
        return selecao;
    }

    public void setSelecao(Selecao selecao) {
        this.selecao = selecao;
    }

    public TipoCrossover getTipoCrossover() {
        return tipoCrossover;
    }

    public void setTipoCrossover(TipoCrossover tipoCrossover) {
        this.tipoCrossover = tipoCrossover;
    }

    public Collection<Cromossomo> getIndividuos() {
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
        Iterator<Cromossomo> iterator = individuos.iterator();

        while (iterator.hasNext()) {
            Cromossomo cromossomo = iterator.next();
            if (cromossomo.getFitness() < valor) {
                valor = cromossomo.getFitness();
                pior = cromossomo;
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
        float media = (somatorio / individuos.size());
        return media;
    }

    public float getDesvioPadrao() {

        float variancia = 0;
        for (Cromossomo individuo : individuos) {
            variancia += Math.pow(individuo.getFitness() - getMedia(), 2);
        }

        float desvioPadrao = (float) Math.sqrt(variancia / (individuos.size() - 1));

        return desvioPadrao;
    }

}
