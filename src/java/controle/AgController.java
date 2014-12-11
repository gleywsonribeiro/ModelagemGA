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
    private TipoCrossover tipoCrossover;
    private Selecao selecao;
    
    private LineChartModel model;
    private BubbleChartModel posicao;
    
    private List<TipoCrossover> crossovers;
    private List<Selecao> selecoes;
    private Map valores;
    

    public AgController() {
        crossovers = new ArrayList<>();
        crossovers.add(TipoCrossover.UM_PONTO);
        crossovers.add(TipoCrossover.DOIS_PONTOS);
        crossovers.add(TipoCrossover.UNIFORME);
        
        selecoes = new ArrayList<>();
        selecoes.add(Selecao.ROLETA);
        selecoes.add(Selecao.TORNEIO);
        
        valores = new HashMap();
        valores.put("Dois Pontos", TipoCrossover.DOIS_PONTOS);
        valores.put("Um Ponto", TipoCrossover.UM_PONTO);
        valores.put("Uniforme", TipoCrossover.UNIFORME);
        
        
        model = new LineChartModel();
        posicao = new BubbleChartModel();
        
    }

    public BubbleChartModel getPosicao() {
        return posicao;
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

    public List<TipoCrossover> getCrossovers() {
        return crossovers;
    }

    public List<Selecao> getSelecoes() {
        return selecoes;
    }

    public Map getValores() {
        return valores;
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
    
        
    
    public void execute() {
        AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanhoPopulacao, numeroGeracoes, tamanhoCromossomo, taxaDeMutacao, taxaDeCruzamento, selecao, tipoCrossover);
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
        
        
    }
    
}
