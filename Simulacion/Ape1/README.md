# Práctica APE 01: Construcción y Simulación Computacional de un Modelo Matemático - UNL

## 1. Descripción del Proyecto
Este proyecto implementa un modelo matemático de simulación atmosférica de 24 horas bajo el patrón arquitectónico **Modelo-Vista-Controlador (MVC)**. Su propósito es predecir la probabilidad de lluvia a partir de datos observados de humedad relativa, nubosidad y temperatura ambiente mediante la ecuación ponderada:

$$I = w_h H + w_n N + w_{tf} T_f$$

El sistema normaliza las variables de entrada, asigna el factor de temperatura ($T_f$) para temperaturas pares tabuladas, clasifica el estado meteorológico en cuatro umbrales (`Sin lluvia`, `Baja posibilidad`, `Lluvia probable` y `Lluvia`) y genera tanto la tabla resumen como la curva temporal del índice de lluvia.

---

## 2. Tecnologías Utilizadas
- **Lenguaje:** Python 3.12+
- **Librerías Científicas y Gráficas:**
  - `matplotlib` (Renderizado de tablas de resultados y gráficas de evolución temporal)
  - `numpy` (Soporte numérico)
- **Arquitectura:** Patrón MVC (`model`, `view`, `controller`)

---

## 3. Requisitos e Instalación

### Requisitos
- Python 3.12 (o Python 3.10+)
- `pip` y módulo `venv`

### Pasos de Instalación

1. Todo se debe ejecutar en la carpeta raíz del proyecto (`ape1`):

```bash
cd ape1
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

## 4. Configuración y Parámetros del Modelo

El modelo utiliza tres pesos de ponderación que deben sumar exactamente `1.0` ($w_h + w_n + w_{tf} = 1.0$):

| Parámetro | Descripción | Valor Base |
| :--- | :--- | :--- |
| **w_h** | Peso asignado a la humedad normalizada ($H$) | `0.5` |
| **w_n** | Peso asignado a la nubosidad normalizada ($N$) | `0.3` |
| **w_tf** | Peso asignado al factor de temperatura ($T_f$) | `0.2` |

### Reglas de Clasificación del Índice ($I$)

| Rango del Índice ($I$) | Estado Pronosticado |
| :--- | :--- |
| $I < 0.40$ | Sin lluvia |
| $0.40 \le I < 0.60$ | Baja posibilidad |
| $0.60 \le I < 0.75$ | Lluvia probable |
| $I \ge 0.75$ | Lluvia |

---

## 5. Uso y Ejecución

Para iniciar la simulación interactiva por consola y desplegar las ventanas gráficas:

```bash
python main.py
```

Al ejecutar el programa, selecciona una de las opciones en consola:
- **Opción `1` (Parámetros base):** Ejecuta la simulación con $w_h = 0.5$, $w_n = 0.3$ y $w_{tf} = 0.2$.
- **Opción `2` (Nuevos parámetros):** Permite ingresar pesos personalizados validando que su sumatoria sea igual a `1.0`.

---

## 6. Estructura del Proyecto

```text
ape1/
├── controller/
│   └── controller.py    # Orquesta el flujo entre los datos, el modelo matemático y la vista
├── model/
│   └── model.py         # Contiene ModeloClima: normalización, tabla Tf, cálculo de I y reglas
├── view/
│   └── view.py          # Contiene VistaClima: tabla gráfica y curva temporal con Matplotlib
├── .gitignore           # Exclusión de entorno virtual y archivos temporales
├── main.py              # Punto de entrada y menú interactivo de configuración de pesos
├── README.md            # Documentación del proyecto
└── requirements.txt     # Dependencias del entorno virtual
```

---

## 7. Notas Adicionales
- **Criterio sobre temperaturas impares:** En la tabla de observación horaria, los registros con temperaturas impares (`17 °C` a las `20:00` y `15 °C` a las `22:00`) se excluyen del cálculo debido a que la tabla de factores $T_f$ de la guía define únicamente intervalos pares de $10\text{ }^\circ\text{C}$ a $28\text{ }^\circ\text{C}$.
- **Asignatura:** Simulación — Ciclo 5 — Carrera de Computación (FEIRNNR - UNL).