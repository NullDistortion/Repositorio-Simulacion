# Práctica 5 (APE5) - Generador Congruencial Lineal (LCG) - UNL

---

## 1. Descripción del Proyecto

Este proyecto implementa y analiza el **Generador Congruencial Lineal (LCG)** para la generación de secuencias de números pseudoaleatorios, desarrollado de manera paralela e idéntica en **Java** y **Python** bajo una arquitectura **Modelo-Vista-Controlador (MVC)**.

*   **Propósito:** Simular la generación de $N = 10,000$ números pseudoaleatorios uniformes $U(n) \in [0, 1)$ evaluando el impacto de distintas semillas iniciales y verificando teórica y empíricamente la longitud del período.
*   **Problema Resuelto:** Permite validar la calidad estadística de un generador LCG comprobando las condiciones del **Teorema de Hull-Dobell** para período completo ($m = 2^{32}$), detectando ciclos empíricos y visualizando la uniformidad e independencia mediante gráficos de serie temporal e histogramas de frecuencia.

### Ecuaciones del Modelo

El generador produce una secuencia de enteros mediante la relación de recurrencia lineal:

$$X_{n} = (a \cdot X_{n-1} + c) \bmod m$$

Para obtener valores uniformemente distribuidos en el intervalo $[0, 1)$, se normaliza cada observación:

$$U_{n} = \frac{X_{n}}{m}$$

---

## 2. Tecnologías Utilizadas

El proyecto está construido en dos lenguajes de programación independientes manteniendo la misma lógica de negocio:

| Entorno | Lenguaje / Versión | Librerías y Herramientas |
| :--- | :--- | :--- |
| **Implementación Python** | Python 3.10+ | `matplotlib` (Gráficos), `dataclasses`, `typing`, `math` |
| **Implementación Java** | Java JDK 11+ | `javax.swing`, `java.awt` (Gráficos nativos 2D), Colecciones estándar |

---

## 3. Instalación y Configuración

Asegúrate de ubicarte en la carpeta raíz del proyecto (`Ape5/`) antes de seguir los pasos correspondientes a cada lenguaje.

### Configuración para Python

1. Navegar al directorio de Python:
```bash
cd Python
```

2. Crear y activar el entorno virtual (recomendado):
```bash
# En Linux / macOS
if [ ! -d "venv" ]; then
    python3 -m venv venv
fi
source venv/bin/activate

# En Windows (CMD / PowerShell)
python -m venv venv
.\venv\Scripts\activate
```

3. Instalar las dependencias necesarias:
```bash
pip install --upgrade pip
pip install matplotlib
```

### Configuración para Java

Java no requiere instalación de librerías externas ya que utiliza los paquetes nativos `Swing` y `AWT`. Solo debes verificar que tienes instalado el compilador `javac`:

```bash
java -version
javac -version
```

---

## 4. Uso y Ejecución

### Ejecutar versión en Python

Desde el directorio `Python/` (con el entorno virtual activado):

```bash
python main.py
```

### Ejecutar versión en Java

1. Navegar al directorio `Java/`:
```bash
cd Java
```

2. Compilar todos los paquetes y clases:
```bash
javac Main.java controller/*.java generator/*.java model/*.java service/*.java view/*.java
```

3. Ejecutar el programa principal:
```bash
java Main
```

---

## 5. Parámetros de Simulación Configurados

Ambos programas evalúan por defecto dos escenarios comparativos con los siguientes parámetros estándar (estándar *Numerical Recipes*):

| Parámetro | Símbolo | Valor Configurado | Descripción |
| :--- | :---: | :--- | :--- |
| **Multiplicador** | $a$ | `1664525` | Constante multiplicativa |
| **Incremento** | $c$ | `1013904223` | Constante aditiva |
| **Módulo** | $m$ | $2^{32}$ (`4294967296`) | Límite del período máximo |
| **Muestra** | $N$ | `10000` | Cantidad de valores generados por corrida |
| **Semilla Base** | $X_0^{(1)}$ | `12345` | Valor inicial para el Escenario Base |
| **Semilla Alternativa** | $X_0^{(2)}$ | `98765` | Valor inicial para el Escenario Alternativo |

---

## 6. Estructura del Proyecto

La carpeta `Ape5/` organiza las implementaciones y la documentación de la siguiente manera:

```text
Ape5/
├── APE5___Simulacion.pdf          # Documento / Guía de la práctica 5
├── README.md                      # Documentación general del proyecto
├── .gitignore                     # Reglas de exclusión de archivos para Git
│
├── Java/                          # Implementación completa en Java
│   ├── Main.java                  # Punto de entrada y definición de escenarios
│   ├── controller/
│   │   └── Controller.java        # Orquestador del flujo de simulación
│   ├── generator/
│   │   └── NumberGenerator.java   # Motor de generación matemática LCG
│   ├── model/
│   │   └── Model.java             # Entidades (LCGScenario, PeriodAnalysisResult, SimulationRun)
│   ├── service/
│   │   ├── Analyzer.java          # Verificación de ciclos y Teorema de Hull-Dobell
│   │   └── GraphicGenerator.java  # Renderizado de gráficos con Java Swing / AWT
│   └── view/
│       └── View.java              # Salida tabular formateada en consola
│
└── Python/                        # Implementación completa en Python
    ├── main.py                    # Punto de entrada y definición de escenarios
    ├── controller/
    │   └── controller.py          # Controlador principal (LCGController)
    ├── generator/
    │   └── number_generator.py    # Algoritmo generador de secuencias X(n) y U(n)
    ├── model/
    │   └── model.py               # Dataclasses del dominio del problema
    ├── service/
    │   ├── analyzer.py            # Análisis de período empírico y Hull-Dobell
    │   └── graphic_generator.py   # Generación de gráficas e histogramas con Matplotlib
    └── view/
        └── view.py                # Impresión de tablas y resúmenes en consola
```

---

## 7. Información Adicional y Funcionalidades

Al ejecutar cualquiera de las dos implementaciones, el sistema realiza de forma automática las siguientes tareas:

1. **Tablas de Observaciones Iniciales:** Imprime en consola las primeras $20$ iteraciones mostrando el índice $n$, el valor entero $X(n)$ y el valor pseudoaleatorio uniforme $U(n)$ con alta precisión decimal.
2. **Análisis de Período y Teorema de Hull-Dobell:**
   * Evalúa si existen valores repetidos dentro de la muestra de $10,000$ observaciones.
   * Comprueba las 3 condiciones del **Teorema de Hull-Dobell**:
     1. $\text{mcd}(c, m) = 1$
     2. $a - 1$ es divisible por todos los factores primos de $m$.
     3. Si $m$ es múltiplo de $4$, entonces $a - 1$ también es múltiplo de $4$.
3. **Comparativa de Sensibilidad a la Semilla:** Muestra una tabla comparativa lado a lado entre la Semilla 1 (`12345`) y la Semilla 2 (`98765`), calculando la diferencia absoluta $|U_1(n) - U_2(n)|$.
4. **Visualización Gráfica Dual:** Despliega una ventana interactiva con 4 subgráficos:
   * **Gráfico de Secuencia:** Primeros $200$ valores de $U(n)$ conectados para inspeccionar saltos y aleatoriedad visual.
   * **Histograma de Frecuencias:** Distribución de los $10,000$ valores en $20$ intervalos (*bins*) contrastados contra la línea roja de frecuencia esperada ($500$ observaciones por intervalo).