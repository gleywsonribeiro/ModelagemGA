/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Gleywson
 */
@Named(value = "jfreeChartController")
@RequestScoped
public class JfreeChartController {

    private StreamedContent graphicText;
         
    private StreamedContent chart;
 
    @PostConstruct
    public void init() {
        try {
            //Graphic Text
            BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImg.createGraphics();
            g2.drawString("This is a text", 0, 10);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImg, "png", os);
            graphicText = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png"); 
 
            //Chart
            JFreeChart jfreechart = ChartFactory.createPieChart("Cities", createDataset(), true, true, false);
            File chartFile = new File("dynamichart");
            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 375, 300);
            chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public StreamedContent getGraphicText() {
        return graphicText;
    }
         
    public StreamedContent getChart() {
        return chart;
    }
     
    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("New York", new Double(45.0));
        dataset.setValue("London", new Double(15.0));
        dataset.setValue("Paris", new Double(25.2));
        dataset.setValue("Berlin", new Double(14.8));
 
        return dataset;
    }
    
}
