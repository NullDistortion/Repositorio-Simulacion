package Java.view;

import Java.model.Chi;
import Java.service.AnalyzerChi;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Locale;

public class GraphicsChi {

    private double chi2Pdf(double x, int df) {
        if (x <= 0.0) return 0.0;
        double kHalf = df / 2.0;
        double logPdf = (kHalf - 1.0) * Math.log(x) - (x / 2.0)
                - kHalf * Math.log(2.0) - AnalyzerChi.logGammaHalf(df);
        return Math.exp(logPdf);
    }

    private double findCriticalValue(int df, double alpha) {
        double low = 0.0;
        double high = Math.max(100.0, df * 5.0);
        for (int i = 0; i < 100; i++) {
            double mid = (low + high) / 2.0;
            double pVal = AnalyzerChi.regularizedUpperGamma(df, mid / 2.0);
            if (pVal > alpha) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return (low + high) / 2.0;
    }

    public void plot(Chi result) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Prueba de Bondad de Ajuste Chi-cuadrado");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(900, 540);
            frame.add(new ChiPanel(result));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private class ChiPanel extends JPanel {
        private final Chi result;
        private final double chiCrit;

        public ChiPanel(Chi result) {
            this.result = result;
            this.chiCrit = findCriticalValue(result.getDegreesOfFreedom(), result.getAlpha());
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

            int df = result.getDegreesOfFreedom();
            double chiCalc = result.getChiSquareStat();
            double xMax = Math.max(df + 4.5 * Math.sqrt(2.0 * df), Math.max(chiCalc * 1.25, chiCrit * 1.35));

            int steps = 500;
            double[] xVals = new double[steps + 1];
            double[] yVals = new double[steps + 1];
            double yPeak = 0.0;

            for (int i = 0; i <= steps; i++) {
                xVals[i] = 0.01 + (xMax - 0.01) * i / steps;
                yVals[i] = chi2Pdf(xVals[i], df);
                if (yVals[i] > yPeak) yPeak = yVals[i];
            }
            double yMax = yPeak * 1.15;

            // 1. Sombrear región de rechazo (x >= chiCrit)
            Path2D rejectRegion = new Path2D.Double();
            double xCritPx = padLeft + (chiCrit / xMax) * plotW;
            double yZeroPx = padTop + plotH;
            rejectRegion.moveTo(xCritPx, yZeroPx);
            for (int i = 0; i <= steps; i++) {
                if (xVals[i] >= chiCrit) {
                    double px = padLeft + (xVals[i] / xMax) * plotW;
                    double py = yZeroPx - (yVals[i] / yMax) * plotH;
                    rejectRegion.lineTo(px, py);
                }
            }
            rejectRegion.lineTo(padLeft + plotW, yZeroPx);
            rejectRegion.closePath();
            g2.setColor(new Color(70, 130, 180, 210));
            g2.fill(rejectRegion);

            // 2. Dibujar curva f(x; gl)
            Path2D curve = new Path2D.Double();
            for (int i = 0; i <= steps; i++) {
                double px = padLeft + (xVals[i] / xMax) * plotW;
                double py = yZeroPx - (yVals[i] / yMax) * plotH;
                if (i == 0) curve.moveTo(px, py);
                else curve.lineTo(px, py);
            }
            g2.setColor(new Color(210, 20, 20));
            g2.setStroke(new BasicStroke(2.5f));
            g2.draw(curve);

            // 3. Línea vertical del valor crítico
            double yCritPx = yZeroPx - (chi2Pdf(chiCrit, df) / yMax) * plotH;
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine((int) xCritPx, (int) yZeroPx, (int) xCritPx, (int) yCritPx);

            // 4. Línea discontinua del estadístico Chi-cuadrado calculado
            double xCalcPx = padLeft + (chiCalc / xMax) * plotW;
            g2.setColor(new Color(0, 120, 0));
            g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10.0f, new float[]{8.0f, 6.0f}, 0.0f));
            g2.drawLine((int) xCalcPx, padTop, (int) xCalcPx, (int) yZeroPx);

            // 5. Ejes coordenados
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(padLeft, padTop, padLeft, (int) yZeroPx);
            g2.drawLine(padLeft, (int) yZeroPx, padLeft + plotW, (int) yZeroPx);

            // 6. Etiquetas de texto y leyenda
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString("Prueba Chi-cuadrado — " + result.getDecision(), padLeft, 28);
            g2.drawString("f(x; gl)", 12, padTop + 20);
            g2.drawString("χ²", padLeft + plotW - 15, (int) yZeroPx + 40);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            int labelNoRejectX = padLeft + (int) ((xCritPx - padLeft) * 0.35);
            int labelNoRejectY = (int) (yZeroPx - plotH * 0.35);
            g2.drawString("Región de no rechazo", labelNoRejectX, labelNoRejectY);
            g2.drawString("(aceptación) de H0", labelNoRejectX + 5, labelNoRejectY + 16);

            g2.drawString(String.format(Locale.US, "χ² crítico = %.4f", chiCrit), (int) xCritPx - 45, (int) yZeroPx + 22);
            g2.setColor(new Color(0, 120, 0));
            g2.drawString(String.format(Locale.US, "χ² calc = %.4f (p = %.4f)", chiCalc, result.getPValue()),
                    (int) xCalcPx - 60, padTop + 18);

            int legX = w - 275, legY = padTop + 15;
            g2.setColor(new Color(248, 248, 248));
            g2.fillRect(legX, legY, 230, 80);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawRect(legX, legY, 230, 80);

            g2.setColor(new Color(210, 20, 20));
            g2.drawString(String.format("— Distribución χ² (gl = %d)", df), legX + 10, legY + 20);
            g2.setColor(new Color(70, 130, 180));
            g2.drawString(String.format(Locale.US, "■ Región de rechazo (α = %.2f)", result.getAlpha()), legX + 10, legY + 42);
            g2.setColor(new Color(0, 120, 0));
            g2.drawString(String.format(Locale.US, "-- χ² calculado = %.4f", chiCalc), legX + 10, legY + 64);
        }
    }
}