/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Meus Documentos
 */
public class AlgoritmoGenetico {
    private int tamanhoPopulacao;
    private int numeroGeracoes;
    private int tamanhoCromossomo;
    private float taxaMutacao;
    private float taxaCruzamento;
    private Selecao selecao;
    private TipoCrossover tipoCrossover;
    private boolean elitismo;
    private final List<Float> pioresIndividuos;
    private final List<Float> melhoresIndividuos;
    private final List<Float> desvioPadrao;

    public AlgoritmoGenetico(int tamanhoPopulacao, int numeroGeracoes, int tamanhoCromossomo, float taxaMutacao, float taxaCruzamento, Selecao selecao, TipoCrossover tipoCrossover) {
        this.tamanhoPopulacao = tamanhoPopulacao;
        this.numeroGeracoes = numeroGeracoes;
        this.tamanhoCromossomo = tamanhoCromossomo;
        this.taxaMutacao = taxaMutacao;
        this.taxaCruzamento = taxaCruzamento;
        this.selecao = selecao;
        this.tipoCrossover = tipoCrossover;
        pioresIndividuos = new ArrayList<>();
        melhoresIndividuos = new ArrayList<>();
        desvioPadrao = new ArrayList<>();
    }
    
    public void run() {
        Populacao p = new Populacao(tamanhoPopulacao, tamanhoCromossomo);
        p.setElitismo(elitismo);
        p.setSelecao(selecao);
        p.setTipoCrossover(tipoCrossover);
        
        for(int i = 0; i < numeroGeracoes; i++) {
            p.geracao(taxaMutacao, taxaCruzamento);
            pioresIndividuos.add(p.getPiorIndividuo());
            melhoresIndividuos.add(p.getMelhorIndividuo());
            desvioPadrao.add(p.getDesvioPadrao());
        }
    }

    public List<Float> getPioresIndividuos() {
        return pioresIndividuos;
    }

    public List<Float> getMelhoresIndividuos() {
        return melhoresIndividuos;
    }

    public List<Float> getDesvioPadrao() {
        return desvioPadrao;
    }

    
    
    public boolean isElitismo() {
        return elitismo;
    }

    public void setElitismo(boolean elitismo) {
        this.elitismo = elitismo;
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

    public float getTaxaMutacao() {
        return taxaMutacao;
    }

    public void setTaxaMutacao(float taxaMutacao) {
        this.taxaMutacao = taxaMutacao;
    }

    public float getTaxaCruzamento() {
        return taxaCruzamento;
    }

    public void setTaxaCruzamento(float taxaCruzamento) {
        this.taxaCruzamento = taxaCruzamento;
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
    
    
    
    
}
