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
import modelo.binario.Ag;
import modelo.binario.Cromossomo;
import modelo.binario.Selecao;
import modelo.binario.TipoCrossover;
import org.jfree.data.category.DefaultCategoryDataset;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Gleywson
 */
@ManagedBean
@RequestScoped
public class AgController {

    private Ag ag;
    private long tempoExecucao;

    private final LineChartModel model;
    private DefaultCategoryDataset dataset;

    private final Map selecoes;
    private final Map crossovers;

    public AgController() {
        ag = new Ag();
        selecoes = new HashMap();
        selecoes.put("Roleta", Selecao.ROLETA);
        selecoes.put("Torneio", Selecao.TORNEIO);

        crossovers = new HashMap();
        crossovers.put("Dois Pontos", TipoCrossover.DOIS_PONTOS);
        crossovers.put("Um Ponto", TipoCrossover.UM_PONTO);
        crossovers.put("Uniforme", TipoCrossover.UNIFORME);
        
        ag.setTamanhoPopulacao(100);
        ag.setNumeroGeracoes(50);
        ag.setTamanhoCromossomo(50);
        ag.setTaxaCruzamento(0.85f);
        ag.setTaxaMutacao(0.01f);
        ag.setElitismo(false);
        ag.setSelecao(Selecao.ROLETA);
        ag.setTipoCrossover(TipoCrossover.UM_PONTO);

        model = new LineChartModel();
        dataset = new DefaultCategoryDataset();
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

    public Ag getAg() {
        return ag;
    }

    public void setAg(Ag ag) {
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
        model.setLegendPosition("e"); //nw
        model.setAnimate(true);
        model.setSeriesColors("00cc00,cc0000,e5e55f,0099ff");

        long tempoFinal = System.currentTimeMillis();
        tempoExecucao = (tempoFinal - tempoInicial) / 1000;

    }
    
    public Cromossomo getMelhorSolucaoEncontrada() {
        double melhorFit = Float.MIN_VALUE;
        Cromossomo temp = null;
        for (Cromossomo i:ag.getMelhoresIndividuos()) {
            
            if(i.getFitness() > melhorFit) {
                melhorFit = i.getFitness();
                temp = i;
            }
        } 
        return temp;
    }

}
