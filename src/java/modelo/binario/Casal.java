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
    private TipoCrossover tipoCrossover;

    public Casal(Cromossomo pai, Cromossomo mae, TipoCrossover crossover) {
        this.pai = pai;
        this.mae = mae;
        this.tipoCrossover = crossover;
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

    public TipoCrossover getTipoCrossover() {
        return tipoCrossover;
    }

    public void setTipoCrossover(TipoCrossover tipoCrossover) {
        this.tipoCrossover = tipoCrossover;
    }

    public Cromossomo[] cruza(float taxaDeMutacao) {
        return pai.crossover(mae, taxaDeMutacao, tipoCrossover);
    }

    public Cromossomo[] getConjuges() {
        Cromossomo[] pais = {pai, mae};
        return pais;
    }
}
