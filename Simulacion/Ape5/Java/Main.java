import controller.Controller;
import java.util.Arrays;
import model.Model.LCGScenario;
import service.GraphicGenerator;
import view.View;

public class Main {
    public static void main(String[] args) {
        long a = 1664525L;
        long c = 1013904223L;
        long m = 1L << 32;
        int n = 10000;

        LCGScenario scenarioBase = new LCGScenario("Escenario Base", a, c, m, 12345L, n);
        LCGScenario scenarioAlt = new LCGScenario("Escenario Semilla Alternativa", a, c, m, 98765L, n);

        Controller controller = new Controller(new View(), new GraphicGenerator());
        controller.runPractice(Arrays.asList(scenarioBase, scenarioAlt), 20);
    }
}