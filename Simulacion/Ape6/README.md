# Validación Estadística de Números Pseudoaleatorios (LCG, Chi-cuadrado y Kolmogorov-Smirnov) - UNL

## 1. Descripción del Proyecto

Este proyecto corresponde a la **Guía de Actividades Práctico-Experimentales Nro. 006** de la asignatura de **Simulación** (Carrera de Computación — FEIRNNR, Universidad Nacional de Loja).

* **Propósito:** Evaluar la calidad estadística (uniformidad) de secuencias de números pseudoaleatorios generadas mediante el **Método Congruencial Lineal (LCG)** utilizando pruebas de bondad de ajuste **Chi-cuadrado ($\chi^2$)** y **Kolmogorov-Smirnov (KS)**.
* **Problema Resuelto:** Permite verificar de forma determinista y reproducible, tanto en **Python** como en **Java**, si una secuencia generada se ajusta a una distribución uniforme continua en el intervalo $[0, 1]$ ($H_0: X \sim U(0,1)$) con un nivel de significancia $\alpha = 0.05$, incluyendo visualización gráfica de la región crítica y de las distribuciones acumuladas.

---

## 2. Tecnologías Utilizadas

### Implementación en Python
* **Python 3.10+**
* **NumPy:** Generación de vectores y soporte matemático para trazado de curvas continuas.
* **Matplotlib:** Visualización de la función de densidad de probabilidad $\chi^2$ (con regiones de rechazo/no rechazo) y de la función de distribución acumulada empírica vs. teórica (KS).

### Implementación en Java
* **Java JDK 17+** (Aritmética entera de 64 bits con `long` para evitar desbordamiento en el módulo $2^{32}$).
* **Java Swing & AWT (`javax.swing`, `java.awt`):** Renderizado gráfico nativo sin dependencias externas.

---

## 3. Configuración y Parámetros del Generador

Ambas implementaciones utilizan los mismos parámetros por defecto para garantizar que produzcan exactamente la misma secuencia pseudoaleatoria:

| Parámetro | Símbolo | Valor por defecto | Descripción |
| :--- | :---: | :--- | :--- |
| **Semilla inicial** | $X_0$ | `12345` | Valor inicial de la secuencia |
| **Multiplicador** | $a$ | `1664525` | Factor multiplicativo del LCG |
| **Incremento** | $c$ | `1013904223` | Constante aditiva del LCG |
| **Módulo** | $m$ | $2^{32}$ (`4294967296`) | Límite cíclico del generador |
| **Tamaño de muestra** | $N$ | `10000` | Cantidad de números generados |
| **Subintervalos ($\chi^2$)** | $k$ | `10` | Divisiones del rango $[0, 1]$ ($gl = 9$) |
| **Nivel de significancia** | $\alpha$ | `0.05` | Umbral de decisión estadística |

> Si deseas modificar estos parámetros, puedes editarlos directamente en las constantes declaradas dentro de `Python/main.py` y `Java/Main.java`.

---

## 4. Instalación

### Preparación del entorno en Python

1. Navegar al directorio de Python:
```bash
cd Python
```

2. Crear y activar el entorno virtual (recomendado):
```bash
if [ ! -d "venv" ]; then
    python3 -m venv venv
fi

source venv/bin/activate
```

3. Instalar las dependencias necesarias:
```bash
pip install --upgrade pip
pip install numpy matplotlib
```

### Preparación del entorno en Java

1. Verificar que el compilador de Java esté instalado:
```bash
javac --version
java --version
```

2. Asegurar que los archivos dentro de `Java/` no tengan prefijos de paquetes padres autogenerados por el IDE:
```bash
cd Java
sed -i 's/package Java\./package /g' */*.java
sed -i 's/import Java\./import /g' *.java */*.java
sed -i '/^package Java;/d' Main.java
```

---

## 5. Uso y Ejecución

### Ejecutar en Python

Desde la carpeta `Python/`:
```bash
python main.py
```

### Ejecutar en Java

Desde la carpeta `Java/`:
```bash
# 1. Compilar el proyecto
javac Main.java

# 2. Ejecutar la aplicación
java Main
```

### Ejemplo de Salida (Idéntica en Python y Java)

```text
=== SECUENCIA GENERADA (Total: 10000 números) ===
Primeros 10: [0.020403, 0.016548, 0.543156, 0.634904, 0.910030, 0.112462, 0.495889, 0.548349, 0.596100, 0.783450]
Últimos  10: [0.549802, 0.782449, 0.368316, 0.657494, 0.932363, 0.969974, 0.559279, 0.390124, 0.052949, 0.650447]

=== RESULTADOS PRUEBA CHI-CUADRADO ===
Intervalo       | Frec. Observada (Oi)   | Frec. Esperada (Ei) 
---------------------------------------------------------------
[0.0, 0.1)      | 1016                   | 1000.00             
[0.1, 0.2)      | 993                    | 1000.00             
[0.2, 0.3)      | 1049                   | 1000.00             
[0.3, 0.4)      | 940                    | 1000.00             
[0.4, 0.5)      | 949                    | 1000.00             
[0.5, 0.6)      | 1063                   | 1000.00             
[0.6, 0.7)      | 1014                   | 1000.00             
[0.7, 0.8)      | 1016                   | 1000.00             
[0.8, 0.9)      | 984                    | 1000.00             
[0.9, 1.0]      | 976                    | 1000.00             
---------------------------------------------------------------
Estadístico Chi-cuadrado : 14.160000
Grados de libertad (gl)  : 9
Valor p                  : 0.116746
Nivel de significancia   : 0.05
Decisión                 : No se rechaza la hipótesis nula (H0: X ~ U(0,1)).

=== RESULTADOS PRUEBA KOLMOGOROV-SMIRNOV ===
Cantidad de datos (N)    : 10000
Mayor dif. positiva (D+) : 0.005960
Mayor dif. negativa (D-) : 0.005411
Estadístico KS (D)       : 0.005960
Valor p                  : 0.869508
Nivel de significancia   : 0.05
Decisión                 : No se rechaza la hipótesis nula (H0: X ~ U(0,1)).
```

---

## 6. Estructura del Proyecto

Ambos proyectos siguen una arquitectura modular desacoplada sin patrón Factory en los modelos:

```text
Ape6/
├── Python/
│   ├── main.py                      # Punto de entrada y configuración de parámetros
│   ├── numberTreatment/
│   │   ├── numberGenerator.py       # Cálculo recursivo del LCG: X_{n+1} = (a*X_n + c) mod m
│   │   └── numberArrayer.py         # Agrupador de secuencias de tamaño N (10 000)
│   ├── model/
│   │   ├── chi.py                   # Estructura de datos para resultados de Chi-cuadrado
│   │   └── KS.py                    # Estructura de datos para resultados de Kolmogorov-Smirnov
│   ├── service/
│   │   ├── analyzerChi.py           # Algoritmo estadístico Chi-cuadrado y cálculo de valor p
│   │   └── analyzerKS.py            # Algoritmo estadístico Kolmogorov-Smirnov y cálculo de valor p
│   ├── view/
│   │   ├── view.py                  # Salida formateada por consola
│   │   ├── graphicsChi.py           # Curva de densidad teórica Chi-cuadrado y región de rechazo
│   │   └── graphicsKS.py            # Gráfica escalonada empírica Fn(x) vs. teórica F(x)
│   └── controller/
│       └── controller.py            # Orquestador del flujo de generación, análisis y vista
│
└── Java/
    ├── Main.java                    # Punto de entrada en Java
    ├── numberTreatment/
    │   ├── NumberGenerator.java     # Generador LCG con precisión de 64 bits (long)
    │   └── NumberArrayer.java       # Constructor del arreglo de números pseudoaleatorios
    ├── model/
    │   ├── Chi.java                 # Modelo de estado para la prueba Chi-cuadrado
    │   └── KS.java                  # Modelo de estado para la prueba Kolmogorov-Smirnov
    ├── service/
    │   ├── AnalyzerChi.java         # Lógica de subintervalos, estadístico Chi-cuadrado y valor p
    │   └── AnalyzerKS.java          # Ordenamiento, cálculo de D+, D-, estadístico D y valor p
    ├── view/
    │   ├── View.java                # Impresión de tablas y métricas en terminal
    │   ├── GraphicsChi.java         # Gráfica en Swing de la distribución Chi-cuadrado
    │   └── GraphicsKS.java          # Gráfica en Swing de la prueba Kolmogorov-Smirnov
    └── controller/
        └── Controller.java          # Controlador principal en Java
```

---

## 7. Información Adicional

### Fundamento Matemático Aplicado

1. **Generador Congruencial Lineal (LCG):**
   $$X_{n+1} = (a X_n + c) \bmod m, \quad u_{n+1} = \frac{X_{n+1}}{m} \in [0, 1)$$

2. **Estadístico Chi-cuadrado ($\chi^2$):**
   $$\chi^2 = \sum_{i=1}^{k} \frac{(O_i - E_i)^2}{E_i}, \quad gl = k - 1$$
   Con $\alpha = 0.05$ y $gl = 9$, el valor crítico es $\chi^2_{\text{crítico}} \approx 16.9190$. Si $\chi^2_{\text{calculado}} < \chi^2_{\text{crítico}}$ (equivalente a $p \ge 0.05$), el estadístico cae en la región de no rechazo de $H_0$.

3. **Estadístico Kolmogorov-Smirnov ($D$):**
   $$D = \sup_{x} |F_n(x) - F(x)| = \max(D^+, D^-)$$
   donde para los datos ordenados $x_{(1)} \le x_{(2)} \le \dots \le x_{(N)}$:
   $$D^+ = \max_{1 \le i \le N} \left( \frac{i}{N} - x_{(i)} \right), \quad D^- = \max_{1 \le i \le N} \left( x_{(i)} - \frac{i - 1}{N} \right)$$