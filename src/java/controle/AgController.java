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
