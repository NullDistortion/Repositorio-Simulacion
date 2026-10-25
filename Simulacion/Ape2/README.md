# Práctica APE 02: Comparación de Modelos Determinísticos, Estocásticos, Discretos y Continuos - UNL

## 1. Descripción del Proyecto
Este proyecto implementa y compara cuatro paradigmas de simulación matemática aplicados a la dinámica de crecimiento poblacional, estructurados mediante el patrón de diseño **Factory Method** y una arquitectura **MVC**:

1. **Modelo Determinístico:** Solución analítica exacta del crecimiento exponencial $P(t) = P_0 e^{rt}$.
2. **Modelo Discreto:** Evolución en intervalos de tiempo definidos $P_{t+1} = P_t + r P_t$.
3. **Modelo Estocástico:** Incorporación de perturbaciones aleatorias gaussianas $P_{t+1} = P_t + r P_t + \epsilon_t$, con $\epsilon_t \sim \mathcal{N}(0, \sigma^2)$.
4. **Modelo Continuo (Método de Euler):** Aproximación numérica de la ecuación diferencial $\frac{dP}{dt} = rP$ mediante $P_{t+\Delta t} = P_t + \Delta t (r P_t)$.

---

## 2. Tecnologías Utilizadas
- **Lenguaje:** Python 3.12+
- **Librerías Científicas:**
  - `numpy` (Cálculo vectorial y generación de ruido aleatorio normal)
  - `matplotlib` (Visualización comparativa de las trayectorias poblacionales)
  - `scipy` / `pandas` / `pynetlogo` (Soporte científico e integración según guía práctica)
- **Patrones de Diseño:** Factory Method (`factory/`) y MVC (`controller/`, `view/`)

---

## 3. Requisitos e Instalación

### Requisitos
- Python 3.12 (o Python 3.10+)
- `pip` y módulo `venv`

### Pasos de Instalación

1. Todo se debe ejecutar en la carpeta raíz del proyecto (`ape2`):

```bash
cd ape2
```

2. Crear y activar el entorno virtual:

```bash
# Crear entorno virtual si no existe (en Linux / macOS / Git Bash)
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

---

## 4. Configuración y Parámetros de Simulación

Los modelos utilizan los siguientes parámetros matemáticos configurables y el arreglo de datos base de observación (`datos = [1000, 1100, 1250, 1400, 1600, 1850, 2100]`):

| Parámetro | Descripción | Ejemplo / Valor de Referencia |
| :--- | :--- | :--- |
| **P0** | Población inicial en $t = 0$ | `1000` |
| **r** | Tasa intrínseca de crecimiento poblacional | `0.12` (crecimiento) / `-0.08` (decrecimiento) |
| **sigma ($\sigma$)** | Desviación estándar del ruido normal en el modelo estocástico | `50.0` |
| **dt ($\Delta t$)** | Tamaño de paso de integración para el método de Euler | `0.1` |
| **tiempo** | Horizonte temporal total de simulación (períodos) | `6` |

---

## 5. Uso y Ejecución

Para ejecutar la simulación comparativa de los cuatro modelos y generar las gráficas:

```bash
python main.py
```

---

## 6. Estructura del Proyecto

```text
ape2/
├── controller/
│   └── controller.py      # Gestiona la ejecución de los 4 modelos y envía resultados a la vista
├── factory/
│   ├── continuous.py      # Implementación del modelo continuo mediante el método de Euler
│   ├── deterministic.py   # Implementación del modelo determinístico exponencial
│   ├── discreet.py        # Implementación del modelo discreto paso a paso
│   ├── factory.py         # Clase Fábrica (Factory Method) para instanciar los modelos
│   └── stochastic.py      # Implementación del modelo estocástico con ruido gaussiano
├── view/
│   └── graphics.py        # Generación de gráficas comparativas con Matplotlib
├── .gitignore             # Exclusión de entorno virtual y archivos temporales
├── main.py                # Punto de entrada principal de la simulación
├── README.md              # Documentación del proyecto
└── requirements.txt       # Dependencias del entorno virtual
```

---

## 7. Notas Adicionales
- En el modelo estocástico se aplica una restricción de no negatividad ($\text{si } P_{t+1} < 0 \Rightarrow P_{t+1} = 0$) para evitar tamaños poblacionales biológicamente imposibles ante valores altos de $\sigma$.
- **Asignatura:** Simulación — Ciclo 5 — Carrera de Computación (FEIRNNR - UNL).