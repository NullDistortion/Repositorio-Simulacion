# Práctica APE 07: Estimación de Resultados mediante el Método de Monte Carlo - UNL

## 1. Descripción del Proyecto

Este proyecto implementa el **Método de Monte Carlo** en **Python** y **Java** bajo una arquitectura **Modelo-Vista-Controlador (MVC)** para estimar numéricamente el valor de $\pi$ a partir de experimentos pseudoaleatorios.

- **Propósito:** Evaluar la convergencia de la aproximación $\hat{\pi} = 4 \cdot (N_c / N)$, el error absoluto $\vert{}\hat{\pi} - \pi\vert{}$ y el costo computacional al escalar el tamaño de muestra en cuatro escenarios ($N \in \{1\,000, 10\,000, 100\,000, 1\,000\,000\}$).
- **Problema Resuelto:** Garantiza la reproducibilidad exacta entre Python y Java mediante un Generador Congruencial Lineal (LCG) compartido, aísla el cronometraje del experimento numérico y valida la uniformidad de los $2N$ números generados mediante la prueba estadística **Chi-cuadrado ($\chi^2$)** con representación gráfica en cuadrícula $2\times 2$.

---

## 2. Tecnologías Utilizadas

### Implementación en Python
- **Python 3.10+**
- **NumPy (`>=1.24.0`):** Cálculo vectorial de la función de densidad Chi-cuadrado.
- **Matplotlib (`>=3.7.0`):** Renderizado de la cuadrícula $2\times 2$ con regiones de aceptación y rechazo.
- **Pandas (`>=2.0.0`):** Soporte de estructuras de datos.
- **`time.perf_counter`:** Medición de tiempos de ejecución de alta precisión.

### Implementación en Java
- **Java SE / JDK 17+:** Aritmética entera de 64 bits (`long`) para evitar desbordamiento en el módulo $2^{32}$.
- **Java Swing & AWT (`javax.swing`, `java.awt`):** Renderizado gráfico nativo en ventana sin dependencias externas.
- **`System.nanoTime`:** Cronometraje en nanosegundos por tamaño de muestra.

---

## 3. Requisitos e Instalación

### Requisitos
- Python 3.10+ (recomendado Python 3.12)
- Java JDK 17 o superior
- `pip` y módulo `venv`

### Pasos de Instalación en Python

1. Ejecutar desde la carpeta `Python`:

```bash
cd Python
```

2. Entorno virtual:

```bash
# Crear entorno virtual si no existe (opcional, pero recomendado)
if [ ! -d "venv" ]; then
    python3 -m venv venv
fi

# Activar entorno en Linux / macOS
source venv/bin/activate

# Activar entorno en Windows (PowerShell)
.\venv\Scripts\Activate.ps1
```

3. Instalar dependencias:

```bash
pip install --upgrade pip
pip install -r requirements.txt
```

### Pasos de Compilación en Java

1. Ejecutar desde la carpeta `Java`:

```bash
cd Java
javac Main.java
```

---

## 4. Configuración y Parámetros

Ambas implementaciones utilizan los siguientes parámetros por defecto definidos en `Python/main.py`, `Java/Main.java` y el servicio `MonteCarloGenerator`:

| Parámetro | Variable / Constante | Valor por Defecto | Descripción |
| :--- | :--- | :--- | :--- |
| **Semilla inicial** | `SEMILLA` | `123456789` | Estado inicial del generador LCG para reproducibilidad |
| **Multiplicador LCG** | `A` | `1664525` | Factor multiplicativo de la recurrencia LCG |
| **Incremento LCG** | `C` | `1013904223` | Constante aditiva de la recurrencia LCG |
| **Módulo LCG** | `M` | `4294967296` ($2^{32}$) | Límite cíclico del generador |
| **Tamaños de muestra** | `TAMANOS_MUESTRA` | `1000, 10000, 100000, 1000000` | Cantidad de puntos $(x, y)$ simulados por corrida |
| **Subintervalos $\chi^2$** | `INTERVALOS_CHI` | `10` | Clases de amplitud $0.1$ ($gl = 9$, $\chi^2_{\text{crítico}} = 16.9190$, $\alpha = 0.05$) |

El resto de variables están configuradas por defecto; si se requiere evaluar otra semilla o cantidad de intervalos, se pueden modificar manualmente en `main.py` y `Main.java`.

---

## 5. Uso y Ejecución

### Ejecutar en Python

```bash
cd Python
python main.py
```

### Ejecutar en Java

```bash
cd Java
java Main
```

Al ejecutar cualquiera de las dos versiones, el programa:
1. Imprime en consola la tabla de resultados de Monte Carlo (`N`, `Pi Estimado`, `Error Abs.`, `Tiempo (s)` y `% Tiempo`).
2. Imprime la tabla de validación de uniformidad Chi-cuadrado para los $2N$ valores pseudoaleatorios.
3. Abre la ventana gráfica $2\times 2$ con las curvas de distribución $\chi^2$, el umbral crítico ($16.9190$) y el estadístico calculado de cada muestra.

---

## 6. Estructura del Proyecto

```text
Ape7/
├── Python/
│   ├── controller/
│   │   ├── tableController.py       # Orquesta el análisis de tiempos y la vista tabular
│   │   └── chiController.py         # Orquesta la tabla y gráficas de la prueba Chi-cuadrado
│   ├── model/
│   │   └── escenario.py             # Dataclass con métricas de Monte Carlo, tiempos y Chi-cuadrado
│   ├── service/
│   │   ├── monteCarloGenerator.py   # Generador LCG (a=1664525, c=1013904223, m=2^32)
│   │   └── calculator.py            # Ejecución aislada de Monte Carlo y prueba Chi-cuadrado
│   ├── timer/
│   │   ├── timer.py                 # Cronómetro de alta precisión con time.perf_counter()
│   │   └── timeAnalyze.py           # Cálculo de tiempo total, promedio y porcentaje por muestra
│   ├── view/
│   │   ├── tableView.py             # Salida en consola de estimación de Pi y tiempos
│   │   └── chiView.py               # Tabla en consola y figura 2x2 en Matplotlib
│   ├── .gitignore                   # Exclusión de entorno virtual y caché
│   ├── main.py                      # Punto de entrada en Python
│   └── requirements.txt             # Dependencias del entorno virtual
│
└── Java/
    ├── controller/
    │   ├── TableController.java     # Controlador de resultados numéricos y tiempos
    │   └── ChiController.java       # Controlador de validación Chi-cuadrado
    ├── model/
    │   └── Escenario.java           # Modelo inmutable de resultados por escenario
    ├── service/
    │   ├── MonteCarloGenerator.java # Generador LCG con aritmética long de 64 bits
    │   └── Calculator.java          # Cálculo de Monte Carlo y estadístico Chi-cuadrado
    ├── timer/
    │   ├── Timer.java               # Cronómetro basado en System.nanoTime()
    │   └── TimeAnalyze.java         # Estadísticas de tiempo de ejecución en Java
    ├── view/
    │   ├── TableView.java           # Impresión formateada de resultados de Monte Carlo
    │   └── ChiView.java             # Tabla en consola y ventana 2x2 en Java Swing/Graphics2D
    ├── .gitignore                   # Exclusión de archivos .class y carpetas de compilación
    └── Main.java                    # Punto de entrada en Java
```

---

## 7. Información Adicional

- **Aislamiento del cronometraje:** En `Calculator`, el reloj (`Timer`) mide únicamente el bucle de generación de pares $(x, y)$ y verificación de $x^2 + y^2 \le 1$. Posteriormente se reinicia la semilla del generador para contabilizar las frecuencias del histograma Chi-cuadrado sobre los $2N$ valores, evitando que la validación estadística contamine el tiempo medido de Monte Carlo.
- **Asignatura:** Simulación — Ciclo 5 — Carrera de Computación (FEIRNNR - Universidad Nacional de Loja).