<div style="display: flex; width: 100%;">
    <div style="flex: 1; padding: 0px;">
        <p>© Manuel Ruano García, 2024</p>
    </div>
    <div style="flex: 1; padding: 0px; text-align: right;">
        <img src="../../assets/ieti.png" height="32" alt="Logo de IETI" style="max-height: 32px;">
    </div>
</div>
<hr/>



### Exercici 10 (originalment un exercici d'exàmen)

##### Puntuació


##### Enunciat

**SENSE** fer servir el Singleton 'AppData' i una base de dades *SQLite* anomenada **dades.sqlite**, desenvolupa una aplicació que gestioni taules de los premios ganados de cada jugador.

**Taula trofeos:**

```sql
id (INTEGER): Identificador únic
nombre (VARCHAR 255): columna
id_jugador (INT): columna
```

**Taula jugadores:**

```sql
id (INTEGER): Identificador únic
nombre (VARCHAR 255): columna
fecha_nacimiento (DATE): columna
pais (VARCHAR 255): columna
```

**Nota**: La columna id_jugador fa referència amb id(tabla jugador)

Crea els objectes 'Trofeo' i 'Jugador' per tal de guardar la informació de les taules anteriors.

Hauràs de sobreescriure el mètode 'toString' de cada objecte per mostrar la informació en aquest format:

```java
Trofeo{id=1, nombre='123', id_jugador=1}
Jugador{id=1, nombre='abc', fecha_nacimiento='2024-04-26', pais='def'}
```

Defineix també les següents funcions, i mira l'exemple de sortida per saber com mostren les dades:

```java
// Test A
// Crea les taules segons les definicions anteriors
createTables()

// Test A
// Afegeix una fila a la taula Trofeo i retorna el valor del nou identificador afegit
int insertTrofeo(Trofeo trofeo)

// Test A
// Afegeix una fila a la taula Jugador i retorna el valor del nou identificador afegit
int insertJugador(Jugador jugar)

// Test A
// Esborra una fila de la taula Trofeo a partir d'un identificador de fila
boolean deleteTrofeo(int id)

// Test A
// Esborra una fila de la taula Jugador a partir d'un identificador de fila
boolean deleteJugador(int id)

// Test A
// Llista totes les files de la taula Trofeo
List<Trofeo> getAllTrofeo()

// Test A
// Llista totes les files de la taula Jugador
List<Jugador> getAllJugador()

// Test B
// Actualitza una fila de la taula Trofeo (no pot actualitzar el camp de l'identificador)
// retorna 'true' si la fila s'ha actualitzat correctament, 'false' en cas contrari
updateTrofeo(Trofeo trofeo)

// Test B
// Actualitza una fila de la taula Jugador (no pot actualitzar el camp de l'identificador)
// retorna 'true' si la fila s'ha actualitzat correctament, 'false' en cas contrari
boolean updateJugador(Jugador jugador)

// Funció de suport que ajuda a gestionar les excepcions SQL
void handleSQLException(SQLException e)

// Funció de suport que ajuda a imprimir els elements d'una llista
<T> void printList(List<T> list)
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

