/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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
     como sendo de um ponto e o tipo de selecao serah a roleta
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
    //no construtor
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

        //se o elitismo foi setado como verdadeiro, serao escolhidos os melhores
        //individuos para passarem direto para a proxima geracao
        if (this.elitismo) {
            //aqui acontece o elitismo
            Cromossomo[] elite = new Cromossomo[2];
            for (int i = 0; i < 2; i++) {
                elite[i] = elitismo();
            }
            temp.addAll(Arrays.asList(elite));
        }
        while (temp.size() < tamanhoPopulacao) {
            Casal casal = casamento();
            if (chanceCruzamento < txCruzamento) {
                Cromossomo[] novosIndividuos = casal.cruza(txMutacao);
                temp.addAll(Arrays.asList(novosIndividuos));

            } else {
                Cromossomo[] cromossomos = casal.getConjuges();
                temp.addAll(Arrays.asList(cromossomos));
                //individuos.removeAll(Arrays.asList(cromossomos));
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
        float valor = Float.MAX_VALUE;
        Iterator<Cromossomo> iterator = individuos.iterator();

        while (iterator.hasNext()) {
            Cromossomo elite = iterator.next();
            if (elite.getFitness() < valor) {
                valor = elite.getFitness();
                maisApto = elite;
            }
        }
        individuos.remove(maisApto);
        return maisApto;
    }

    private Cromossomo torneio(int n) {
        if (n < 1 && n > individuos.size()) {
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
        float valor = Float.MAX_VALUE;
        for (Cromossomo competidor : competidores) {
            if (competidor.getFitness() < valor) {
                valor = competidor.getFitness();
                maisApto = competidor;
            }
        }
        return maisApto;
    }

    private Cromossomo roleta() {
        double somatorioFitness = 0;
        //double somatorioFitComplement = 0;
        double acumulado = 0;
        Cromossomo retorno = null;

        Collection<Object[]> roleta = new ArrayList<>();
        Iterator<Cromossomo> i = individuos.iterator();
        //Iterator<Cromossomo> it = individuos.iterator();

        while (i.hasNext()) {
            somatorioFitness += i.next().getFitness();
        }

//        while (it.hasNext()) {
//            somatorioFitComplement += it.next().getFitComplement(somatorioFitness);
//        }

        Iterator<Cromossomo> it2 = individuos.iterator();
        while (it2.hasNext()) {
            Cromossomo individuo = it2.next();
            roleta.add(new Object[]{
                acumulado, acumulado + individuo.getFitness() / somatorioFitness,
                individuo
            });

            acumulado += individuo.getFitness()/ somatorioFitness;
        }
        double sorteio = Math.random();

        Iterator<Object[]> it3 = roleta.iterator();
        while (it3.hasNext()) {
            Object[] atual = it3.next();

            double limiteInferior = ((Double) atual[0]);
            double limiteSuperior = ((Double) atual[1]);
            Cromossomo individuo = (Cromossomo) atual[2];

            if (sorteio >= limiteInferior && sorteio < limiteSuperior) {
                retorno = individuo;
                break;
            }
        }

        return retorno;
    }

    private Cromossomo seleciona(Selecao selecao) {
        int n = 3;
        Cromossomo c;
        switch (selecao) {
            case ROLETA:
                c = roleta();
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

}
