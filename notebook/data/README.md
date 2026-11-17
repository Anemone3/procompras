# Salidas del análisis

Rutas desde la raíz del monorepo:

Fuente: `JavaProcompras/src/main/resources/datasets/recetasdelaabuela.csv`.
Proceso: `notebook/01_limpieza_recetas.ipynb`.

| Archivo | Contenido |
| --- | --- |
| `processed/recetas_limpias.csv` | Recetas con texto normalizado, campos numéricos y listas. Se retiran registros incompletos y copias de contenido. |
| `processed/ingredientes_lineas.csv` | Una fila por línea, con cantidad, rango, unidad, descripción y equivalencias explícitas a g/ml. |
| `processed/recetas_revision.csv` | Recetas con motivos concretos de revisión. |
| `reports/recetas_descartadas.csv` | Registros retirados, motivo e ID conservado cuando son duplicados. |
| `reports/cambios_texto.csv` | Reparaciones de codificación, antes y después. |
| `reports/comparacion_limpieza.csv` | Métricas del dataset antes y después. |
| `reports/perfil_fuentes.csv` | Cantidad de registros y cobertura de comensales por fuente. |
| `reports/resumen_calidad.json` | Conteos, motivos pendientes y hash del original. |

Las listas se serializan como JSON dentro del CSV. Los nulos se exportan vacíos.
Las unidades caseras y cantidades no interpretables quedan explícitas para el
siguiente análisis; no se convierten a gramos con factores genéricos.

El original no se modifica. Los CSV generados se excluyen de Git; el notebook,
las reglas y las dependencias permiten reproducirlos. Los reportes resumidos
pueden versionarse después de revisarlos.
