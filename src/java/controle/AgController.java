/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.binario.AlgoritmoGenetico;
import modelo.binario.Selecao;
import modelo.binario.TipoCrossover;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Gleywson
 */
@ManagedBean
@RequestScoped
public class AgController {

    private int tamanhoPopulacao;
    private int numeroGeracoes;
    private int tamanhoCromossomo;
    private float taxaDeMutacao;
    private float taxaDeCruzamento;
    private boolean elitismo;
    private TipoCrossover tipoCrossover;
    private Selecao selecao;

    private long tempoExecucao;

    private final LineChartModel model;

    private final Map selecoes;
    private final Map crossovers;

    public AgController() {
        selecoes = new HashMap();
        selecoes.put("Roleta", Selecao.ROLETA);
        selecoes.put("Torneio", Selecao.TORNEIO);

        crossovers = new HashMap();
        crossovers.put("Dois Pontos", TipoCrossover.DOIS_PONTOS);
        crossovers.put("Um Ponto", TipoCrossover.UM_PONTO);
        crossovers.put("Uniforme", TipoCrossover.UNIFORME);

        model = new LineChartModel();
    }

    public LineChartModel getModel() {
        return model;
    }

    public int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public void setTamanhoPopulacao(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public int getNumeroGeracoes() {
        return numeroGeracoes;
    }

    public void setNumeroGeracoes(int numeroGeracoes) {
        this.numeroGeracoes = numeroGeracoes;
    }

    public int getTamanhoCromossomo() {
        return tamanhoCromossomo;
    }

    public void setTamanhoCromossomo(int tamanhoCromossomo) {
        this.tamanhoCromossomo = tamanhoCromossomo;
    }

    public float getTaxaDeMutacao() {
        return taxaDeMutacao;
    }

    public void setTaxaDeMutacao(float taxaDeMutacao) {
        this.taxaDeMutacao = taxaDeMutacao;
    }

    public float getTaxaDeCruzamento() {
        return taxaDeCruzamento;
    }

    public void setTaxaDeCruzamento(float taxaDeCruzamento) {
        this.taxaDeCruzamento = taxaDeCruzamento;
    }

    public Map getSelecoes() {
        return selecoes;
    }

    public Map getCrossovers() {
        return crossovers;
    }

    public TipoCrossover getTipoCrossover() {
        return tipoCrossover;
    }

    public void setTipoCrossover(TipoCrossover tipoCrossover) {
        this.tipoCrossover = tipoCrossover;
    }

    public Selecao getSelecao() {
        return selecao;
    }

    public void setSelecao(Selecao selecao) {
        this.selecao = selecao;
    }

    public boolean isElitismo() {
        return elitismo;
    }

    public void setElitismo(boolean elitismo) {
        this.elitismo = elitismo;
    }

    public float getTempoExecucao() {
        return tempoExecucao;
    }

    public void execute() {
        long tempoInicial = System.currentTimeMillis();

        AlgoritmoGenetico ag = new AlgoritmoGenetico();

        ag.setElitismo(elitismo);
        ag.setNumeroGeracoes(numeroGeracoes);
        ag.setSelecao(selecao);
        ag.setTipoCrossover(tipoCrossover);
        ag.setTamanhoCromossomo(tamanhoCromossomo);
        ag.setTamanhoPopulacao(tamanhoPopulacao);
        ag.setTaxaCruzamento(taxaDeCruzamento);
        ag.setTaxaMutacao(taxaDeMutacao);

        ag.run();
        ChartSeries melhores = new ChartSeries();
        melhores.setLabel("Melhores");

        ChartSeries piores = new ChartSeries();
        piores.setLabel("Piores");

        ChartSeries desvioPadrao = new ChartSeries();
        desvioPadrao.setLabel("Desvio Padrão");

        ChartSeries media = new ChartSeries();
        media.setLabel("Média");

        for (int i = 0; i < ag.getMelhoresIndividuos().size(); i++) {
            melhores.set(i, ag.getMelhoresIndividuos().get(i));
            piores.set(i, ag.getPioresIndividuos().get(i));
            desvioPadrao.set(i, ag.getDesvioPadrao().get(i));
            media.set(i, ag.getMedia().get(i));

        }
        model.addSeries(melhores);
        model.addSeries(piores);
        model.addSeries(desvioPadrao);
        model.addSeries(media);
        model.setTitle("Desempenho por Gerações");
        model.setLegendPosition("ne");
        model.setAnimate(true);
        model.setSeriesColors("00cc00,cc0000,e5e55f,0099ff");

        long tempoFinal = System.currentTimeMillis();
        tempoExecucao = (tempoFinal - tempoInicial)/1000;
        
    }

}
