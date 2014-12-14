/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.binario.AlgoritmoGenetico;
import modelo.binario.Cromossomo;
import modelo.binario.Selecao;
import modelo.binario.TipoCrossover;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Gleywson
 */
@ManagedBean
@RequestScoped
public class AgController {

    private AlgoritmoGenetico ag;
    private long tempoExecucao;

    private final LineChartModel model;

    private final Map selecoes;
    private final Map crossovers;

    public AgController() {
        ag = new AlgoritmoGenetico();
        selecoes = new HashMap();
        selecoes.put("Roleta", Selecao.ROLETA);
        selecoes.put("Torneio", Selecao.TORNEIO);

        crossovers = new HashMap();
        //crossovers.put("Dois Pontos", TipoCrossover.DOIS_PONTOS);
        crossovers.put("Um Ponto", TipoCrossover.UM_PONTO);
        crossovers.put("Uniforme", TipoCrossover.UNIFORME);

        model = new LineChartModel();
    }

    public LineChartModel getModel() {
        return model;
    }

    public Map getSelecoes() {
        return selecoes;
    }

    public Map getCrossovers() {
        return crossovers;
    }

    public AlgoritmoGenetico getAg() {
        return ag;
    }

    public void setAg(AlgoritmoGenetico ag) {
        this.ag = ag;
    }

    public float getTempoExecucao() {
        return tempoExecucao;
    }

    public void execute() {
        long tempoInicial = System.currentTimeMillis();

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
            melhores.set(i, ag.getMelhoresIndividuos().get(i).getFitness());
            piores.set(i, ag.getPioresIndividuos().get(i).getFitness());
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
        tempoExecucao = (tempoFinal - tempoInicial) / 1000;

    }
    
    public Cromossomo getMelhorSolucaoEncontrada() {
        float melhorFit = Float.MAX_VALUE;
        Cromossomo temp = null;
        for (Cromossomo i:ag.getMelhoresIndividuos()) {
            
            if(i.getFitness() < melhorFit) {
                melhorFit = i.getFitness();
                temp = i;
            }
        } 
        return temp;
    }

}
