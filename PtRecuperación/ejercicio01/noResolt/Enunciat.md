<div style="display: flex; width: 100%;">
    <div style="flex: 1; padding: 0px;">
        <p>© Albert Palacios Jiménez, 2023</p>
    </div>
    <div style="flex: 1; padding: 0px; text-align: right;">
        <img src="../../assets/ieti.png" height="32" alt="Logo de IETI" style="max-height: 32px;">
    </div>
</div>
<hr/>

### Exercici 0 (originalment un exercici d'exàmen)

##### Puntuació

- Passar els testos 'A': 2 punts
- Passar els testos 'B': 1 punt
- Passar els testos 'A' i 'B': (2 + 1) = 3 punts

S'entén que la puntuació màxima és de 3 punts

##### Enunciat

Fent servir el Singleton 'AppData' i una base de dades *sqlite* anomenada **dades.sqlite**, desenvolupa una aplicació que gestioni Estudiants, Assignatures i Matriculacions.

**Taula Estudiants:**

```sql
id: INTEGER (Clau Primària)
nom: (TEXT)
pais: (TEXT)
goles: INTEGER
```

**Taula Assignatures:**

```sql
id: INTEGER (Clau Primària)
nom: VARCHAR
año: INTEGER

```

**Taula Matriculacions:**

```sql
jugadorId: INTEGER (Clau Externa que apunta a Estudiants)
equipoId: INTEGER (Clau Externa que apunta a Assignatures)
temporada: INTEGER
```


Defineix també les següents funcions, i mira l'exemple de sortida per saber com mostren les dades:



```java
createTables(): Crea les taules i les relacions en la base de dades.

// Crea les taules i les relacions en la base de dades.
void createTables();

// Afegir un jugador
void addJugador(String nom, String cognoms, int goles);

// Afegir un equip
void addEquipo(String nom, int credits);

// Afegir un pichichi
void addPichchi(int jugadorId, int equipoId, int temporada);

// Llista tots els estudiants
listJugadores();

con esta estructura: 
"id: 1, nom: Messi, pais: Argentina, goles: 870"

// Llista totes les assignatures
listEquipos();


con esta estructura: 
"id: 1, nom: Al Nassr, credits: 1955"


// Llista totes les matriculacions
listPichichi();

con esta estructura: 
"id jugador: 1, id equipo: 2, temporada: 2009"


// Actualitza les dades d'un estudiant
void updateJugadores(int id, String nom, String pais, int goles);

// Esborra un estudiant
void deleteJugadores(int id);


Fes anar els programes:

```bash
./run.sh com.project.MainA

```

Assegura't que passa els testos:

```bash
./runTest.sh com.project.TestMain#testMainOutputA
./runTest.sh com.project.TestMain#testMainTablesA
./runTest.sh com.project.TestMain#testMainCallsA


```




Fes anar els programes:

```bash
./run.sh com.project.MainA
```

