/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Gleywson
 */
@ManagedBean
@RequestScoped
public class GraficoController {

     private LineChartModel modeloGrafico;
    
    public GraficoController() {
        modeloGrafico = new LineChartModel();
        LineChartSeries serie = new LineChartSeries("Queda Exponencial");
        
        for(int i = 0; i <= 40; i++) {
            serie.set(i, Math.cos(i)*Math.exp(-i/10));
        }
        modeloGrafico.addSeries(serie);
        //modeloGrafico.setLegendPosition();
    }

    public LineChartModel getModeloGrafico() {
        return modeloGrafico;
    }

    public void setModeloGrafico(LineChartModel modeloGrafico) {
        this.modeloGrafico = modeloGrafico;
    }
}
