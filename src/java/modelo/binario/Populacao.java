/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
        while(i < getTamanhoPopulacao()) {
            individuos.add(new Cromossomo(tamanhoIndividuo));
            i++;
        }
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

    
    
    
    
    
    
}
