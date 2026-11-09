package Java.controller;

import Java.numberTreatment.*;
import Java.service.*;
import Java.view.*;
import Java.model.*;

public class Controller {
    private final NumberGenerator generator;
    private final NumberArrayer arrayer;
    private final AnalyzerChi analyzerChi;
    private final AnalyzerKS analyzerKs;
    private final View view;
    private final GraphicsChi graphicsChi;
    private final GraphicsKS graphicsKs;

    public Controller(NumberGenerator generator, NumberArrayer arrayer,
                      AnalyzerChi analyzerChi, AnalyzerKS analyzerKs,
                      View view, GraphicsChi graphicsChi, GraphicsKS graphicsKs) {
        this.generator = generator;
        this.arrayer = arrayer;
        this.analyzerChi = analyzerChi;
        this.analyzerKs = analyzerKs;
        this.view = view;
        this.graphicsChi = graphicsChi;
        this.graphicsKs = graphicsKs;
    }

    public void run(boolean showPlots) {
        // Actividad 1: Generación y almacenamiento de los números
        double[] numbers = this.arrayer.generateArray();
        this.view.showGeneratedNumbers(numbers, 20);

        // Algoritmo 2: Prueba Chi-cuadrado
        Chi chiResult = this.analyzerChi.analyze(numbers);
        this.view.showChiResults(chiResult);

        // Algoritmo 3: Prueba Kolmogorov-Smirnov
        KS ksResult = this.analyzerKs.analyze(numbers);
        this.view.showKsResults(ksResult);

        // Representación gráfica
        if (showPlots) {
            this.graphicsChi.plot(chiResult);
            this.graphicsKs.plot(ksResult);
        }
    }
}