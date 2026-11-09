package Java.view;

import Java.model.KS;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Locale;

public class GraphicsKS {

    public void plot(KS result) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Prueba Kolmogorov-Smirnov");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(850, 540);
            frame.add(new KSPanel(result));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static class KSPanel extends JPanel {
        private final KS result;

        public KSPanel(KS result) {
            this.result = result;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int padLeft = 70, padRight = 40, padTop = 50, padBottom = 60;
            int plotW = w - padLeft - padRight;
            int plotH = h - padTop - padBottom;
            int yZeroPx = padTop + plotH;

            // Ejes coordenados
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(padLeft, padTop, padLeft, yZeroPx);
            g2.drawLine(padLeft, yZeroPx, padLeft + plotW, yZeroPx);

            // 1. Distribución Acumulada Teórica F(x) = x (diagonal de (0,0) a (1,1))
            g2.setColor(new Color(210, 20, 20));
            g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10.0f, new float[]{8.0f, 6.0f}, 0.0f));
            g2.drawLine(padLeft, yZeroPx, padLeft + plotW, padTop);

            // 2. Distribución Acumulada Empírica Fn(x) (escalonada)
            double[] xSorted = result.getSortedNumbers();
            double[] fnX = result.getEmpiricalCdf();
            int n = result.getN();
            int step = Math.max(1, n / 1500);

            Path2D empPath = new Path2D.Double();
            empPath.moveTo(padLeft, yZeroPx);
            double prevPy = yZeroPx;

            for (int i = 0; i < n; i += step) {
                double px = padLeft + xSorted[i] * plotW;
                double py = yZeroPx - fnX[i] * plotH;
                empPath.lineTo(px, prevPy);
                empPath.lineTo(px, py);
                prevPy = py;
            }
            empPath.lineTo(padLeft + plotW, padTop);

            g2.setColor(new Color(0, 40, 140));
            g2.setStroke(new BasicStroke(1.8f));
            g2.draw(empPath);

            // Etiquetas y títulos
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString(String.format(Locale.US, "Prueba Kolmogorov-Smirnov (D = %.4f, p = %.4f)",
                    result.getKsStat(), result.getPValue()), padLeft, 28);
            g2.drawString("F(x)", 20, padTop + 20);
            g2.drawString("Valor pseudoaleatorio x", padLeft + plotW / 2 - 70, yZeroPx + 40);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.drawString("0.0", padLeft - 25, yZeroPx + 5);
            g2.drawString("1.0", padLeft - 25, padTop + 5);
            g2.drawString("0.0", padLeft - 5, yZeroPx + 20);
            g2.drawString("1.0", padLeft + plotW - 10, yZeroPx + 20);

            // Leyenda
            int legX = padLeft + 25, legY = padTop + 20;
            g2.setColor(new Color(248, 248, 248));
            g2.fillRect(legX, legY, 300, 55);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawRect(legX, legY, 300, 55);

            g2.setColor(new Color(0, 40, 140));
            g2.drawString("— Distribución Acumulada Empírica Fn(x)", legX + 10, legY + 22);
            g2.setColor(new Color(210, 20, 20));
            g2.drawString("-- Distribución Acumulada Teórica F(x) = x", legX + 10, legY + 44);
        }
    }
}