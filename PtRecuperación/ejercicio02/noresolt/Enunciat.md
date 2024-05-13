<div style="display: flex; width: 100%;">
    <div style="flex: 1; padding: 0px;">
        <p>© Albert Palacios Jiménez, 2023</p>
    </div>
    <div style="flex: 1; padding: 0px; text-align: right;">
        <img src="../../assets/ieti.png" height="32" alt="Logo de IETI" style="max-height: 32px;">
    </div>
</div>
<hr/>

### Exercici 7

SENSE fer servir el Singleton 'AppData', FENT SERVIR *ResultSet* i una base de dades *MySQL* anomenada **astronomy**, desenvolupa una aplicació que gestioni Persona , professor y curs

**Nota**: Si ja hi ha un contenedor MySQL a docker, anomenat 'mysqlServer', es pot carregar iniciar la base de dades 'astronomy' amb:

A Linux i macOS:
```bash
docker exec -i mysqlServer mysql -uroot -ppwd < institut.sql
```

A Windows:
```bash
type institut.sql | docker exec -i mysqlServer mysql -uroot -ppwd
```

**Important**: Al corregir, els testos han de funcionar amb base de dades 'astronomy', port 3308, usuari 'root' i codi 'pwd'

**Important**: Cada vegada que s'executa el main, s'han d'esborrar i tornar a crear les taules de la base de dades

**Taula Equipos :**

```sql

equipoId INTEGER PRIMARY KEY,  -- Un identificador únic.
nom TEXT1,                 -- El nombre del equipo.
pais TEXT          -- pais al cual pertenece el professor.


```

**Taula Jugadores:**

```sql
jugadorId INTEGER PRIMARY KEY,       -- Un identificador únic.
nombre TEXT,                 -- El nom del curs.
edad INTEGER,              -- Relació amb la taula Professors.
posicion TEXT,
equipoId INTEGER,
FOREIGN KEY (equipoId) REFERENCES Equipos(equipoId)
```



Defineix també les següents funcions, i mira l'exemple de sortida per saber com mostren les dades:

```text
void createTables(Connection conn)

public static void addJugador(Connection conn, int id, String nombre, int edad, String posicion, int equipoId)

public static void addEquipo(Connection conn, int id, String nombre, String pais)



// Llista els telescopis de la base de dades fent servir el 'toString' de l'objecte 'Jugadores'
public static void listJugadores(Connection conn) 

// Llista els objectes celestials de la base de dades fent servir el 'toString' de l'objecte 'Equipos'
public static void listEquipos(Connection conn)



public static void deleteEquipo(Connection conn, int equipoId)


public static void updateEdadJugador(Connection conn, int jugadorId, int nuevaEdad)
    


```

Per resoldre l'exercici necessiteu crear els següents objectes:

**Objecte abstracto Persona**

Atributs: int id , String nom.

public abstract String descripcio();

**Objecte Jugador que extends de persona**

Atributs: Int edad, String posicion, in equipoId.
Format a mostrar: Jugador{"id=1, nombre=Pepe, edad=30, posicion=central, equipoId=1};"

**Objecte Curs que extends de persona**

Atributs: int cursId, String nom , Professor profesor.
Format a mostrar: "Equipo{equipoId=1, nombre=Madrid, pais=España}";


Assegura't que passa els testos:

./run.sh com.project.Main

```bash
./runTest.sh com.project.TestMain#testMainOutput
./runTest.sh com.project.TestMain#testMainTables
./runTest.sh com.project.TestMain#testMainCalls
./runTest.sh com.project.TestMain#testMainExtra


```

