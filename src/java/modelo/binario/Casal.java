/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.binario;

/**
 *
 * @author gleywson
 */
public class Casal {
    private Cromossomo pai;
    private Cromossomo mae;
    private TipoCrossover crossover;

    public Casal(Cromossomo pai, Cromossomo mae, TipoCrossover crossover) {
        this.pai = pai;
        this.mae = mae;
        this.crossover = crossover;
    }

    public Cromossomo getPai() {
        return pai;
    }

    public void setPai(Cromossomo pai) {
        this.pai = pai;
    }

    public Cromossomo getMae() {
        return mae;
    }

    public void setMae(Cromossomo mae) {
        this.mae = mae;
    }

    public TipoCrossover getCrossover() {
        return crossover;
    }

    public void setCrossover(TipoCrossover crossover) {
        this.crossover = crossover;
    }

    public Cromossomo[] cruza(float taxaDeMutacao) {
        return Cruzamento.cruza(pai, mae, taxaDeMutacao, crossover);
    }

    
    public Cromossomo[] getConjuges() {
        Cromossomo[] pais = {pai, mae};
        return pais;
    }
}
