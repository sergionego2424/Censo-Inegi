/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;


public class PopulationChart extends JFrame {
    public String nombre_estado , nombre_municipio, nombre_localidad;
    public PopulationChart(String nombre_estado, String nombre_municipio, String nombre_localidad) {
        setTitle("Gráfico de Población por Género");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.nombre_estado   = nombre_estado ; 
        this.nombre_municipio = nombre_municipio;
        this.nombre_localidad = nombre_localidad;
        initUI();
    }

    private void initUI() {
        try {
            DefaultPieDataset dataset = createDataset();
            JFreeChart chart = createChart(dataset);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(750, 500));
            setContentPane(chartPanel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DefaultPieDataset createDataset() throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
         Ageeml ageeml = new Ageeml();
        ResultSet rs = ageeml.getAgeemlInfo(this.nombre_estado, this.nombre_municipio, this.nombre_localidad);

        int poblacionMasculina = 0;
        int poblacionFemenina = 0;

        while (rs.next()) {
            poblacionMasculina += rs.getInt("poblacion_masculina");
            poblacionFemenina += rs.getInt("poblacion_femenina");
        }

        dataset.setValue("Población Masculina", poblacionMasculina);
        dataset.setValue("Población Femenina", poblacionFemenina);

        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset dataset) {
    JFreeChart chart = ChartFactory.createPieChart(
            "Distribución de Población por Género",
            dataset,
            true,
            true,
            false
    );

    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new java.text.DecimalFormat("0"), new java.text.DecimalFormat("0%")));
    plot.setInteriorGap(0.05);
    plot.setMaximumLabelWidth(0.2);

    return chart;
}


  
}


