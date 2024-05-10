<div style="display: flex; width: 100%;">
    <div style="flex: 1; padding: 0px;">
        <p>© Manuel Ruano García, 2024</p>
    </div>
    <div style="flex: 1; padding: 0px; text-align: right;">
        <img src="../../assets/ieti.png" height="32" alt="Logo de IETI" style="max-height: 32px;">
    </div>
</div>
<hr/>

### Exercici 0 (originalment un exercici d'exàmen)

##### Puntuació


##### Enunciat

Fent servir el Singleton 'AppData' i una base de dades *sqlite* anomenada **dades.sqlite**, desenvolupa una aplicació que gestioni expècies, naus i missions.

**Tabla jugadores:**

```sql
id (INTEGER): identificador únic
nombre (TEXT): nom del jugador
pais (TEXT): pais d´origen
zurdo (BOOLEAN): es esquerre
```

**Tabla equipos:**

```sql
id (INTEGER): Identificador únic
nombre (TEXT): nom del club
pais (TEXT): pais donde 
```

**Tabla Balon_De_Oro:**

```sql
id (INTEGER): identificador únic
jugador_id (INTEGER): identificador del jugador 
equipo_id (INTEGER): identificador del equip on juega
año (TEXT): año en el que es guanya
```

Els camps 'jugador_id' i 'equipo_id' es relacionen amb les respectives taules.

Defineix també les següents funcions, i mira l'exemple de sortida per saber com mostren les dades:

```java
// Test A
// Crear les taules (i esborrar les antigues si cal)
createTables() 

// Test A
// Afegir una espècie
addJugadors(String nombre, String pais, boolean zurdo)

// Test A
// Afegir una nau
addEquipos(String name, String pais)

// Test A
// Afegir una missió
addBalonDeOro(int jugador_id, int equipo_id, String año)

// Test A
// Llista els jugadors
listJugadores()

// Test A
// Llista els equips
listEquipos()

// Test A
// Llita els premis
listBalonDeOro()

// Test B
// Actualitza les dades d´un jugador
void updateJugadores(int id, String nombre, String pais, boolean zurdo)

// Test B
// Actualitza les dades d´un premi
updateBalonDeOro(int id, String año)

// Test B
// Esborra un element de la taula de equipos
deleteEquipos(int id)

// Test B
// Esborra un element de la taula de jugadores
deleteJugador(int id)

// Substitueix els caràcters ' per ''
private static String escapeSQL(String input)
```

Fes anar els programes:

```bash
./run.sh com.project.MainA
./run.sh com.project.MainB
```

Assegura't que passa els testos:

```bash
./runTest.sh com.project.TestMain#testMainOutputA
./runTest.sh com.project.TestMain#testMainTablesA
./runTest.sh com.project.TestMain#testMainCallsA
./runTest.sh com.project.TestMain#testMainExtraA

./runTest.sh com.project.TestMain#testMainOutputB
./runTest.sh com.project.TestMain#testMainCallsB
./runTest.sh com.project.TestMain#testMainExtraB
```

