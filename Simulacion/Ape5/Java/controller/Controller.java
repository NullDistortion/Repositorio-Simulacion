package controller;

import generator.NumberGenerator;
import generator.NumberGenerator.GeneratedSequences;
import java.util.ArrayList;
import java.util.List;
import model.Model.LCGScenario;
import model.Model.PeriodAnalysisResult;
import model.Model.SimulationRun;
import service.Analyzer;
import service.GraphicGenerator;
import view.View;

public class Controller {
    private final View view;
    private final GraphicGenerator graphicGenerator;

    public Controller(View view, GraphicGenerator graphicGenerator) {
        this.view = view;
        this.graphicGenerator = graphicGenerator;
    }

    public SimulationRun executeScenario(LCGScenario scenario) {
        GeneratedSequences seq = NumberGenerator.generate(scenario);
        PeriodAnalysisResult analysis = Analyzer.analyzePeriod(scenario, seq.getXValues());
        return new SimulationRun(scenario, seq.getXValues(), seq.getUValues(), analysis);
    }

    public void runPractice(List<LCGScenario> scenarios, int obsLimit) {
        List<SimulationRun> runs = new ArrayList<>();

        for (LCGScenario sc : scenarios) {
            SimulationRun run = executeScenario(sc);
            runs.add(run);
            view.showFirstObservations(run, obsLimit);
            view.showPeriodAnalysis(run);
        }

        if (runs.size() >= 2) {
            view.showSeedComparison(runs.get(0), runs.get(1), obsLimit);
        }

        graphicGenerator.plotRuns(runs, 200);
    }
}