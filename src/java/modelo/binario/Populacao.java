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
 * @author gleywson
 */
public class Populacao {
    private Collection<Cromossomo> individuos;
    private Collection<Cromossomo> temp;
    private int tamanhoDaPopulacao;
    private TipoCrossover tipoCrossover;
    private Selecao selecao;

    /**
     *  Reajuste da classe populacao. A mesma agora vai ser responsável pelo cruzamento dos individuos
     * @param tamanho
     * @param comprimentoCromossomo 
     */
    
    public Populacao(int tamanho, int comprimentoCromossomo) {
        this.tamanhoDaPopulacao = tamanho;
        individuos = new ArrayList<>(tamanho);
        temp = new ArrayList<>(tamanho);
        initPopulacao(comprimentoCromossomo);
    }

    public Selecao getSelecao() {
        return selecao;
    }

    public void setSelecao(Selecao selecao) {
        this.selecao = selecao;
    }

    public Collection<Cromossomo> getIndividuos() {
        return individuos;
    }

    public Collection<Cromossomo> getTemp() {
        return temp;
    }

    public int getTamanhoDaPopulacao() {
        return tamanhoDaPopulacao;
    }

    public TipoCrossover getTipoCrossover() {
        return tipoCrossover;
    }

    public void setTipoCrossover(TipoCrossover tipoCrossover) {
        this.tipoCrossover = tipoCrossover;
    }

    private void initPopulacao(int tamanho) {
        for (int i = 1; i <= tamanhoDaPopulacao; i++) {

            individuos.add(new Cromossomo(tamanho));
        }
    }

    public void geracao(float taxaDeCruzamento, float taxaDeMutacao) {
        Random random = new Random();
        double chances = random.nextFloat();

        //aqui aconttece o elitismo
        Cromossomo[] elite = new Cromossomo[2];
        for (int i = 0; i < 2; i++) {
            elite[i] = elitismo();
        }
        temp.addAll(Arrays.asList(elite));

        while (temp.size() < tamanhoDaPopulacao) {
            Casal casal = casamento();
            if (chances < taxaDeCruzamento) {
                Cromossomo[] novosIndividuos = casal.cruza(taxaDeMutacao);
                temp.addAll(Arrays.asList(novosIndividuos));

            } else {
                Cromossomo[] cromossomos = casal.getConjuges();
                temp.addAll(Arrays.asList(cromossomos));
            }
        }
        individuos.clear();
        individuos.addAll(temp);
        temp.clear();
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
        return maisApto;
    }

    private Casal casamento() {
        Cromossomo pai = seleciona(selecao);

        Cromossomo mae;
        do {
            mae = seleciona(selecao);
        } while (pai.equals(mae));

        return new Casal(pai, mae, tipoCrossover);
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
        double somatorioFitComplement = 0;
        double acumulado = 0;
        Cromossomo retorno = null;

        Collection<Object[]> roleta = new ArrayList<>();
        Iterator<Cromossomo> i = individuos.iterator();
        Iterator<Cromossomo> it = individuos.iterator();

        while (i.hasNext()) {
            somatorioFitness += i.next().getFitness();
        }

        while (it.hasNext()) {
            somatorioFitComplement += it.next().getFitComplement(somatorioFitness);
        }

        Iterator<Cromossomo> it2 = individuos.iterator();
        while (it2.hasNext()) {
            Cromossomo individuo = it2.next();
            roleta.add(new Object[]{
                acumulado, acumulado + individuo.getFitComplement(somatorioFitness) / somatorioFitComplement,
                individuo
            });

            acumulado += individuo.getFitComplement(somatorioFitness) / somatorioFitComplement;
        }
        double sorteio = Math.random();

        Iterator<Object[]> it3 = roleta.iterator();
        while (it3.hasNext()) {
            Object[] atual = it3.next();

            double limiteInferior = ((Double) atual[0]).doubleValue();
            double limiteSuperior = ((Double) atual[1]).doubleValue();
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

    public String saida(int geracao) {
        String saida;
        String x1 = "x1 = [";
        String x2 = "x2 = [";

        Iterator<Cromossomo> it = getIndividuos().iterator();
        while (it.hasNext()) {
            float valores[] = it.next().getX();
            x1 += valores[0] + ",";
            x2 += valores[1] + ",";
        }
        x1 += "];";
        x2 += "];";

        StringBuilder builder = new StringBuilder(x1);
        builder.deleteCharAt(x1.length() - 3);
        x1 = builder.toString();

        StringBuilder builder2 = new StringBuilder(x2);
        builder2.deleteCharAt(x2.length() - 3);
        x2 = builder2.toString();

        saida = ""
                + x1 + "\n"
                + x2 + "\n"
                + "plot(x1, x2, '*');\n"
                + "title('geracao: " + geracao + "');\n"
                + "xlabel('eixo x1');\n"
                + "ylabel('eixo x2');\n"
                + "pause(0.10);";

        return saida;
    }

    @Override
    public String toString() {
        StringBuilder saida = new StringBuilder("Populacao: \n");

        Iterator<Cromossomo> iterator = individuos.iterator();

        while (iterator.hasNext()) {
            saida.append("\n");
            saida.append(iterator.next().toString());
        }
        return saida.toString();
    }

    public float getPiorIndividuo() {
        Cromossomo pior = null;
        float valor = Float.MIN_VALUE;
        Iterator<Cromossomo> iterator = individuos.iterator();

        while (iterator.hasNext()) {
            Cromossomo cromossomo = iterator.next();
            if (cromossomo.getFitness() > valor) {
                valor = cromossomo.getFitness();
                pior = cromossomo;
            }
        }
        return pior.getFitness();
    }

    public float getMelhorIndividuo() {
        return elitismo().getFitness();
    }
}
