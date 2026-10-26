# Practica 04
Modelo de simulacion epidemiologica SIR basado en la guia de la practica 004.
El programa no crea graficas. Genera `resultados_java.csv` para cargarlo en NetLogo.

## Parametros

- Poblacion: 100
- Infectados iniciales: 5
- Susceptibles iniciales: 95
- Recuperados iniciales: 0
- Beta: 0.30
- Gamma: 0.10
- Tiempo maximo: 100
- Semilla: 2026

## Decisiones de implementacion

La guia indica seleccionar contactos susceptibles, pero no define cuantos
contactos realiza cada infectado por paso. Por simplicidad, cada infectado
elige exactamente un individuo distinto al azar por paso. Si ese contacto
esta susceptible, se contagia con probabilidad beta. Cada infectado que
existia al inicio del paso se recupera con probabilidad gamma.

Los cambios se hacen sobre una copia de estados y se aplican juntos. De
esta forma un contagio nuevo no contagia ni se recupera hasta el proximo
paso. Los primeros 5 individuos empiezan infectados. La semilla permite
repetir el mismo experimento en Java.

## Compilar y ejecutar

Desde la carpeta SIR_Java_MVC:

```bash
javac -encoding UTF-8 -d bin -sourcepath src src/Main.java
java -cp bin Main
```

Se genera `resultados_java.csv` en la carpeta desde la cual se ejecuta el
comando `java`.

## Integracion con NetLogo

Copia `resultados_java.csv` a la carpeta de `modelo_sir_netlogo.nlogo`,
junto con `resultados_python.csv`. NetLogo debe leer el CSV sin volver a
simular los valores importados.

Formato del CSV:

```csv
tiempo,susceptibles,infectados,recuperados
0,95,5,0
```

Incluye 101 filas de datos: tiempo 0 hasta tiempo 100.

## MVC

- Modelo: `EstadoSalud`, `Individuo`, `RegistroSir`, `ModeloSir`, `ExportadorCsv`.
- Vista: `VistaConsola`.
- Controlador: `ControladorSir`.
- Entrada: `Main`.

## Requisitos del Sistema y Dependencias

Este proyecto no requiere gestores de paquetes externos (como Maven o Gradle) ni librerías `.jar` adicionales, ya que el motor de simulación fue construido utilizando exclusivamente la biblioteca estándar de **Java SE** (`java.nio`, `java.util`, `java.io`).

### Herramientas Requeridas

1. **Java Development Kit (JDK):**
   - Versión: **JDK 17** o superior (recomendado **OpenJDK 21**).
   - Verifica tu instalación en terminal con:
     ```bash
     javac -version
     java -version
     ```
   - Si no está instalado (en distribuciones basadas en Ubuntu/Debian):
     ```bash
     sudo apt update && sudo apt install openjdk-21-jdk
     ```
   - En distribuciones basadas en Arch Linux:
     ```bash
     sudo pacman -S jdk-openjdk
     ```

2. **NetLogo (Visualización):**
   - Versión: **NetLogo 7.0.x** (probado en NetLogo 7.0.4).
   - Requiere la extensión nativa `csv` (incluida por defecto en la instalación de NetLogo).

---

## Compilación y Ejecución

1. **Compilar el proyecto Java:**
   ```bash
   cd SIMULACION_04/src
   javac Main.java
   java Main.java
