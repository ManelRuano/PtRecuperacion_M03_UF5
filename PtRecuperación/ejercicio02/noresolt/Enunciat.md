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

**Taula Professors :**

```sql

professorId INTEGER PRIMARY KEY,  -- Un identificador únic.
nom VARCHAR(255),                 -- El nom del professor.
departament VARCHAR(255)          -- Departament al qual pertany el professor.


```

**Taula Cursos:**

```sql
cursId INTEGER PRIMARY KEY,       -- Un identificador únic.
nom VARCHAR(255),                 -- El nom del curs.
professorId INTEGER,              -- Relació amb la taula Professors.
FOREIGN KEY (professorId) REFERENCES Professors(professorId)
```



Defineix també les següents funcions, i mira l'exemple de sortida per saber com mostren les dades:

```text
void createTables(Connection conn)

public static void addProfessor(Connection conn, int id, String nom, String departament)

public static void addCourse(Connection conn, int id, String nom, int professorId)



// Llista els telescopis de la base de dades fent servir el 'toString' de l'objecte 'Professors'
public static void listProfessors(Connection conn) 

// Llista els objectes celestials de la base de dades fent servir el 'toString' de l'objecte 'CelestialBody'
public static void listCursos(Connection conn)



public static void deleteCourse(Connection conn, int courseId)


public static void updateProfessorDepartment(Connection conn, int id, String newDepartament)
    


```

Per resoldre l'exercici necessiteu crear els següents objectes:

**Objecte abstracto Persona**

Atributs: int id , String nom.

public abstract String descripcio();

**Objecte Professor que extends de persona**

Atributs: String departament.
Format a mostrar: [professorId=3, nom=pepe, departament=mates]"
public abstract String descripcio();

**Objecte Curs que extends de persona**

Atributs: int cursId, String nom , Professor profesor.
Format a mostrar: [cursId=3, nom=mates, professor=pepe]
public abstract String descripcio();


Assegura't que passa els testos:

./run.sh com.project.Main

```bash
./runTest.sh com.project.TestMain#testMainOutput
./runTest.sh com.project.TestMain#testMainTables
./runTest.sh com.project.TestMain#testMainCalls
./runTest.sh com.project.TestMain#testMainExtra


```

