package Java;

import Java.controller.*;
import Java.numberTreatment.*;
import Java.service.*;
import Java.view.*;;

public class Main {
    public static void main(String[] args) {
        long SEED = 12345L;
        long MULTIPLIER = 1664525L;
        long INCREMENT = 1013904223L;
        long MODULUS = 1L << 32;
        int COUNT = 10000;

        NumberGenerator generator = new NumberGenerator(SEED, MULTIPLIER, INCREMENT, MODULUS);
        NumberArrayer arrayer = new NumberArrayer(generator, COUNT);

        AnalyzerChi analyzerChi = new AnalyzerChi(10, 0.05);
        AnalyzerKS analyzerKs = new AnalyzerKS(0.05);

        View view = new View();
        GraphicsChi graphicsChi = new GraphicsChi();
        GraphicsKS graphicsKs = new GraphicsKS();

        Controller appController = new Controller(
                generator,
                arrayer,
                analyzerChi,
                analyzerKs,
                view,
                graphicsChi,
                graphicsKs
        );

        appController.run(true);
    }
}