<div style="font-family: Arial, sans-serif; line-height: 1.6;">

<h1 style="color:#2c3e50;">MiniSIEM – Network Monitoring & Analysis System in Java</h1>

<p style="font-size:16px;">
Sistema tipo mini SIEM desarrollado en Java para el monitoreo, análisis y recolección de eventos en redes locales. 
La aplicación permite descubrir dispositivos en la red, identificar direcciones IP/MAC y analizar puertos abiertos para detectar servicios activos en tiempo real.
</p>

<hr style="border:1px solid #ddd;">

<h2 style="color:#34495e;">📖 Descripción</h2>

<p>
Nanakusa MiniSIEM es una implementación práctica inspirada en plataformas SIEM reales, enfocada en la recolección, procesamiento y análisis de eventos de red. 
El sistema combina técnicas de descubrimiento de dispositivos, escaneo de puertos y monitoreo continuo mediante programación concurrente.
</p>

<p>
Integra una interfaz CLI personalizada que permite ejecutar comandos en tiempo real, junto con un sistema de persistencia que almacena eventos, dispositivos y resultados de análisis en base de datos para su posterior consulta.
</p>

<p>
El proyecto está diseñado como una base sólida para evolucionar hacia herramientas más avanzadas de ciberseguridad, incluyendo correlación de eventos, detección de anomalías y análisis forense.
</p>

<hr>

<h2 style="color:#34495e;">🚀 Características</h2>

<ul>
<li>🔍 Descubrimiento de dispositivos en red local (LAN)</li>
<li>🌐 Identificación de direcciones IP y MAC</li>
<li>🚪 Escaneo de puertos TCP (detección de servicios activos)</li>
<li>🧠 Monitoreo continuo mediante multithreading (ExecutorService)</li>
<li>🧾 Recolección y registro de eventos de red</li>
<li>📊 Persistencia de datos para análisis posterior</li>
<li>💻 Interfaz CLI personalizada con ejecución de comandos</li>
<li>🧩 Arquitectura modular (Repository / Service / Core)</li>
</ul>

<hr>

<h2 style="color:#34495e;">🛠️ Tecnologías</h2>

<ul>
<li><strong>Lenguaje:</strong> Java (Maven)</li>
<li><strong>Concurrencia:</strong> ExecutorService, Threads</li>
<li><strong>Base de datos:</strong> MySQL / MariaDB</li>
<li><strong>Persistencia:</strong> JDBC</li>
<li><strong>Redes:</strong> TCP/IP, ARP, Socket Programming</li>
<li><strong>Arquitectura:</strong> Patrón Repository / Service</li>
</ul>

<hr>

<h2 style="color:#34495e;">🏗️ Arquitectura del Proyecto</h2>

<pre style="background:#f4f4f4; padding:10px; border-radius:8px;">
src/
  ├── minisiem/
        ├── core/        # Configuracio del nucleo del sistema
        ├── database/    # Conexión y configuración DB
        ├── discovery/   # Descubrimiento de dispositivos
        ├── events/      # Gestión de eventos
              ├── log/   # Logs del sistema
        ├── model/       # Entidades del sistema
              ├── port/
        ├── monitor/     # Monitoreo continuo
        ├── net/         # Parametros de red
        ├── repository/  # Acceso a datos (DAO)
        ├── scanner/     # Escaneo global y de puertos
        ├── utils/       # Utilidades del sistema
              ├── CLI/
              ├── Monitor/
        Main.java
</pre>

<hr>

<h2 style="color:#34495e;">⚙️ Ejecución</h2>

<pre style="background:#f4f4f4; padding:10px; border-radius:8px;">
1. Configurar base de datos (MySQL/MariaDB)
2. Ejecutar el script SQL 
3. Configurar credenciales en el proyecto
4. Ejecutar la aplicación (utils/Main.java)
</pre>

<hr>

<h2 style="color:#34495e;">🎯 Objetivo</h2>

<p>
Aplicar de forma práctica conceptos de redes, programación concurrente y ciberseguridad, 
simulando el funcionamiento de un sistema SIEM enfocado en la recolección y análisis de eventos en redes locales.
</p>

<p>
Este proyecto sirve como base para evolucionar hacia herramientas más avanzadas de:
</p>

<ul>
<li>🔐 Detección de intrusiones (IDS)</li>
<li>📈 Correlación de eventos</li>
<li>🧬 Análisis de comportamiento/anomalías</li>
<li>🕵️‍♂️ Informática forense</li>
</ul>

<hr>

<h2 style="color:#34495e;">📌 Estado del Proyecto</h2>

<p>
🚧 En desarrollo activo – nuevas funcionalidades en progreso (monitoreo avanzado, mejora de CLI y análisis de eventos).
</p>

</div>
