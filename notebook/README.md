# Análisis y limpieza de recetas

El notebook incluye perfil de nulos por fuente, limpieza de texto con pandas y
ftfy, recuperación de raciones, extracción de cantidades y unidades y deduplicación.
Ejecuta las celdas en orden con **Shift + Enter**.

## Primera instalación en PowerShell

Python comprobado en mise: **3.13.16**.

```powershell
Set-Location D:\Dev\procompras\notebook
mise trust .\mise.toml
$pythonBase = Join-Path (mise where python@3.13.16) 'python.exe'
& $pythonBase -m venv .venv
.\.venv\Scripts\python.exe -m pip install -r .\requirements.txt
.\.venv\Scripts\python.exe -m pip check
.\.venv\Scripts\python.exe -m ipykernel install --prefix .\.venv --name procompras-notebook --display-name "Python (procompras notebook)"
```

`mise where` localiza Python. `venv` crea el entorno de este módulo. `-m pip`
instala sus dependencias. `ipykernel` registra el Python que ejecutará las celdas.

Si necesitas instalar esa versión en otro equipo, usa primero
`mise install python@3.13.16`.

## Abrir Jupyter

Desde `procompras/notebook`:

```powershell
.\.venv\Scripts\python.exe -m jupyterlab .\01_limpieza_recetas.ipynb
```

Selecciona **Python (procompras notebook)**. Para cerrar Jupyter, pulsa
**Ctrl + C** en la terminal. Las siguientes sesiones solo necesitan el comando
para abrir Jupyter; el entorno ya estará creado.

Para actualizar dependencias:

```powershell
.\.venv\Scripts\python.exe -m pip install -r .\requirements.txt
```

Para guardar todas las versiones instaladas:

```powershell
.\.venv\Scripts\python.exe -m pip freeze | Set-Content -Encoding utf8 .\requirements.lock.txt
```

## IntelliJ IDEA

1. Instala o habilita los plugins **Python** de JetBrains y **Jupyter**, y reinicia el IDE.
2. Abre esta carpeta como proyecto Python, o añade su intérprete al monorepo:
   `D:\Dev\procompras\notebook\.venv\Scripts\python.exe`.
3. Selecciona ese entorno al abrir el notebook. `JavaProcompras` utiliza su JDK 21.

## Datos

El original se lee de
`../JavaProcompras/src/main/resources/datasets/recetasdelaabuela.csv`.
La última celda exporta a `data/processed` y `data/reports` dentro de esta carpeta.
Los archivos generados se actualizan al repetir la exportación.

Los registros incompletos y duplicados se guardan con su motivo e ID de referencia.
Los casos pendientes se guardan en `recetas_revision.csv`.
Consulta el [detalle de las salidas](data/README.md).

El notebook conserva tablas resumidas para su revisión en GitHub. `.venv`, los
CSV procesados y los reportes extensos se excluyen de Git.
