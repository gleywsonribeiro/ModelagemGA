/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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
    
        
    /**
     * Creates a new instance of AgController
     * #{agController.ag.tamanhoPopulacao}
     * #{agController.ag.numeroGeracoes}
     * #{agController.ag.tamanhoCromossomo}
     * #{agController.ag.taxaDeCruzamento}
     * #{agController.ag.taxaDeMutacao}
     * #{agController.ag.tipoCrossover}
     * #{agController.crossovers}
     * #{agController.ag.selecao}
     * #{agController.selecoes}
     * #{agController.ag.run()}
     * 
     */
    public AgController() {
    }
    
}
