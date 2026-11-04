package service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.Model.SimulationRun;

public class GraphicGenerator {

    public void plotRuns(List<SimulationRun> runs, int subsetPoints) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("LCG - Análisis Gráfico");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(runs.size(), 2, 10, 10));

            for (SimulationRun run : runs) {
                frame.add(new SequencePlotPanel(run, subsetPoints));
                frame.add(new HistogramPlotPanel(run, 20));
            }

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static class SequencePlotPanel extends JPanel {
        private final SimulationRun run;
        private final int subsetPoints;

        public SequencePlotPanel(SimulationRun run, int subsetPoints) {
            this.run = run;
            this.subsetPoints = Math.min(subsetPoints, run.getUValues().size());
            setPreferredSize(new Dimension(600, 360));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int padLeft = 55;
            int padRight = 25;
            int padTop = 40;
            int padBottom = 45;
            int width = getWidth() - padLeft - padRight;
            int height = getHeight() - padTop - padBottom;

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            String title = run.getScenario().getName() + " (Semilla=" + run.getScenario().getSeed()
                    + ") - Primeros " + subsetPoints + " valores U(n)";
            g2.drawString(title, padLeft, 22);

            g2.setColor(new Color(230, 230, 230));
            for (int i = 0; i <= 5; i++) {
                int y = padTop + (int) (height * (i / 5.0));
                g2.drawLine(padLeft, y, padLeft + width, y);
            }

            g2.setColor(Color.BLACK);
            g2.drawLine(padLeft, padTop, padLeft, padTop + height);
            g2.drawLine(padLeft, padTop + height, padLeft + width, padTop + height);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            for (int i = 0; i <= 5; i++) {
                double val = 1.0 - (i / 5.0);
                int y = padTop + (int) (height * (i / 5.0));
                g2.drawString(String.format("%.1f", val), 25, y + 4);
            }

            List<Double> u = run.getUValues();
            g2.setColor(new Color(31, 119, 180));
            g2.setStroke(new BasicStroke(1.0f));

            int prevX = -1;
            int prevY = -1;
            for (int i = 0; i < subsetPoints; i++) {
                int x = padLeft + (int) ((double) i / (subsetPoints - 1) * width);
                int y = padTop + height - (int) (u.get(i) * height);
                if (prevX != -1) {
                    g2.drawLine(prevX, prevY, x, y);
                }
                g2.fillOval(x - 2, y - 2, 4, 4);
                prevX = x;
                prevY = y;
            }

            g2.setColor(Color.BLACK);
            g2.drawString("Índice n", padLeft + width / 2 - 20, padTop + height + 30);
        }
    }

    private static class HistogramPlotPanel extends JPanel {
        private final SimulationRun run;
        private final int bins;

        public HistogramPlotPanel(SimulationRun run, int bins) {
            this.run = run;
            this.bins = bins;
            setPreferredSize(new Dimension(600, 360));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int padLeft = 55;
            int padRight = 25;
            int padTop = 40;
            int padBottom = 45;
            int width = getWidth() - padLeft - padRight;
            int height = getHeight() - padTop - padBottom;

            List<Double> u = run.getUValues();
            int[] counts = new int[bins];
            int maxCount = 0;
            for (double val : u) {
                int idx = Math.min((int) (val * bins), bins - 1);
                counts[idx]++;
                if (counts[idx] > maxCount) {
                    maxCount = counts[idx];
                }
            }
            int yMax = (int) (maxCount * 1.15);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            String title = run.getScenario().getName() + " (Semilla=" + run.getScenario().getSeed()
                    + ") - Histograma (" + u.size() + " valores)";
            g2.drawString(title, padLeft, 22);

            double binWidth = (double) width / bins;
            for (int i = 0; i < bins; i++) {
                int barHeight = (int) (((double) counts[i] / yMax) * height);
                int x = padLeft + (int) (i * binWidth);
                int y = padTop + height - barHeight;
                int w = Math.max(1, (int) Math.ceil(binWidth));

                g2.setColor(new Color(100, 160, 210));
                g2.fillRect(x, y, w, barHeight);
                g2.setColor(Color.BLACK);
                g2.drawRect(x, y, w, barHeight);
            }

            double expected = (double) u.size() / bins;
            int expY = padTop + height - (int) ((expected / yMax) * height);
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10.0f, new float[]{6.0f}, 0.0f));
            g2.drawLine(padLeft, expY, padLeft + width, expY);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawLine(padLeft, padTop, padLeft, padTop + height);
            g2.drawLine(padLeft, padTop + height, padLeft + width, padTop + height);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            for (int i = 0; i <= 4; i++) {
                int val = (int) (yMax * (1.0 - i / 4.0));
                int y = padTop + (int) (height * (i / 4.0));
                g2.drawString(String.valueOf(val), 20, y + 4);
            }

            g2.drawString("Intervalo U(n) [0, 1)", padLeft + width / 2 - 45, padTop + height + 30);
        }
    }
}