<div style="font-family: Arial, sans-serif; line-height: 1.6;">

<h1 style="color:#2c3e50;">MiniSIEM – Network Scanner en Java</h1>

<p style="font-size:16px;">
Sistema de monitoreo de red desarrollado en Java que permite descubrir dispositivos dentro de una red local, identificar direcciones IP y MAC, y analizar puertos abiertos para detectar servicios activos.
</p>

<hr style="border:1px solid #ddd;">

<h2 style="color:#34495e;">Descripción</h2>

<p>
Nanakusa-miniSIEM simula el comportamiento básico de herramientas de análisis de red y SIEM. 
El sistema detecta dispositivos en LAN, registra su información y almacena los resultados en una base de datos para análisis posterior.
</p>

<hr>

<h2 style="color:#34495e;">Características</h2>

<ul>
<li>🔍 Descubrimiento de dispositivos en red (LAN)</li>
<li>🌐 Obtención de direcciones IP y MAC</li>
<li>🚪 Escaneo de puertos TCP/UDP comunes</li>
<li>🧾 Identificación básica de servicios</li>
<li>📊 Registro de eventos y tareas</li>
<li>💾 Persistencia en base de datos (MySQL/MariaDB)</li>
<li>🧩 Arquitectura modular (Repository / Service)</li>
</ul>

<hr>

<h2 style="color:#34495e;">Tecnologías</h2>

<ul>
<li>Java (Maven, ExecutorService)</li>
<li>MySQL / MariaDB</li>
<li>SQL (diseño relacional)</li>
<li>Redes (TCP/IP, ARP, puertos)</li>
</ul>

<hr>

<h2 style="color:#34495e;">🏗️ Arquitectura</h2>

<pre style="background:#f4f4f4; padding:10px; border-radius:8px;">
src/
  ├── minisiem/
        ├── core/
        ├── database/
        ├── discovery/
        ├── events/
              ├── log/
        ├── model/
              ├── port/
        ├── monitor/
        ├── net/
        ├── repository/
        ├── scanner/
        Main.java
</pre>

<hr>

<h2 style="color:#34495e;">Ejecución</h2>

<pre style="background:#f4f4f4; padding:10px; border-radius:8px;">
1. Configurar base de datos
2. Ejecutar script SQL
3. Configurar conexión JDBC
4. Ejecutar el proyecto
</pre>

<hr>

<h2 style="color:#34495e;">Objetivo</h2>

<p>
Este proyecto tiene como objetivo aplicar conceptos de redes y ciberseguridad en un entorno práctico, 
simulando la recolección y análisis de información en una red local.
</p>

<hr>

<h2 style="color:#34495e;">📌 Estado del proyecto</h2>

<p style="color:green;"><strong>En desarrollo activo</strong></p>

</div>
