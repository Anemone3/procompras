# Backend Java

Proyecto Maven con Java 21. Contiene la extracción de productos con Selenium,
la lectura del dataset de recetas y la persistencia con PostgreSQL/Hibernate.

Desde esta carpeta:

```powershell
mvn test
```

Desde la raíz del monorepo:

```powershell
mvn -f .\JavaProcompras\pom.xml test
docker compose up -d postgres
```

En IntelliJ, importa `JavaProcompras/pom.xml` como proyecto Maven y configura
el SDK Java 21. El punto de entrada actual es `com.procompras.Main`; su ejecución
conecta a PostgreSQL y comienza la extracción de productos.

El CSV original se mantiene en `src/main/resources/datasets`. El análisis Python
está en `../notebook` y utiliza su propio entorno `.venv`.
