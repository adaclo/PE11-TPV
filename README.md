# PE11 - Projecte Final TPV

Projecte final de programació per a una aplicació de TPV d'una botiga de roba.

L'aplicació permet gestionar una petita botiga especialitzada en la venda de camises i pantalons. El programa funciona per consola i utilitza una base de dades MySQL per guardar articles, clients, tiquets i línies de factura.

## Membres de l'equip

- Adrián Acarreta - Scrum Master i desenvolupador
- Miguel Sánchez - Desenvolupador
- Oriol Sardà - Desenvolupador

## Funcionalitats principals

L'aplicació inclou les següents funcionalitats:

- Importació d'articles des d'un fitxer JSON.
- Gestió d'articles.
- Gestió de clients.
- Registre de vendes mitjançant TPV.
- Creació de tiquets i línies de factura.
- Control i actualització d'estoc.
- Consulta de vendes per client.
- Consulta de vendes per article.
- Càlcul de beneficis.
- Recompra automàtica d'articles amb poc estoc.

## Estructura del projecte

```text
PE11/
├── json/
│   └── articles.json
├── src/
│   ├── App.java
│   ├── models/
│   │   ├── Article.java
│   │   ├── Camisa.java
│   │   ├── Pantalon.java
│   │   └── Client.java
│   └── utils/
│       ├── manageDB.java
│       └── manageJSON.java
├── createDB.sql
└── README.md
```

## Tecnologies utilitzades

- Java
- MySQL
- JDBC
- JSON Simple
- GitHub Projects
- Git / GitHub

## Base de dades

La base de dades utilitzada és:

```sql
tpv_botiga
```

Per crear la base de dades cal executar el fitxer:

```text
createDB.sql
```

Aquest script crea les taules principals del projecte:

- `families`
- `articles`
- `clients`
- `tiquets`
- `linies_factura`

La taula `families` guarda els tipus d'article:

- camisa
- pantaló

La taula `articles` utilitza una relació amb `families` mitjançant el camp `id_familia`.

## Configuració de la connexió

La connexió a la base de dades es fa des de la classe `manageDB`.

```java
this.db = new manageDB("tpv_botiga");
this.db.establirConexio();
```

La configuració actual és:

```java
USER = "root";
PASSWORD = "";
```

En cas de tenir una contrasenya diferent a MySQL, cal modificar-la a `manageDB.java`.

## Importació d'articles JSON

El fitxer d'articles es troba a:

```text
json/articles.json
```

La importació es fa des de l'opció 1 del menú principal.

El procés d'importació:

1. Carrega el fitxer JSON.
2. Compta el nombre total d'articles.
3. Mostra quantes camises i pantalons es carregaran.
4. Si l'article no existeix, l'insereix.
5. Si l'article ja existeix, actualitza les seves dades.
6. Mostra quants articles s'han afegit i quants s'han actualitzat.

## Menú principal

El programa mostra el següent menú:

```text
========== TPV BOTIGA ==========
1. Importació articles
2. Gestió d'articles
3. Gestió de clients
4. TPV
5. Consultes vendes per client
6. Consultes vendes per article
7. Calcula beneficis totals
8. Recompra automàtica articles
0. Sortir
================================
```

## Gestió d'articles

La gestió d'articles permet:

- Donar d'alta un article.
- Modificar un article.
- Esborrar un article.
- Consultar un article per ID.

En donar d'alta un article, l'ID és automàtic perquè la base de dades utilitza `AUTO_INCREMENT`.

Els articles poden ser de dues famílies:

- `1` - camisa
- `2` - pantaló

Per a les camises es demana:

- talla del coll
- amplada del pit

Per als pantalons es demana:

- talla de cintura
- llargada del camal

També es validen les dades introduïdes per evitar errors amb els `CHECK` de la base de dades:

- IVA entre 4 i 21.
- Stock igual o superior a 0.
- Talla coll entre 36 i 52.
- Amplada pit entre 10 i 15.
- Talla cintura entre 24 i 56.
- Llargada camal entre 32 i 46.

La modificació d'articles es fa camp per camp mitjançant un submenú, de manera que no cal reescriure tot l'article per canviar una sola dada.

## Gestió de clients

La gestió de clients permet:

- Donar d'alta un client.
- Modificar un client.
- Esborrar un client.
- Consultar un client per DNI.
- Consultar tots els clients.

El client amb DNI `000` és el client genèric i no s'ha d'eliminar.

## TPV i registre de vendes

L'opció TPV permet registrar una venda.

Funcionament general:

1. Es demana el DNI del client.
2. Si el client no existeix, s'utilitza el client genèric `000`.
3. Es demanen articles per ID.
4. Per finalitzar la venda, s'introdueix l'article `0`.
5. Es comprova que l'article existeix.
6. Es comprova que hi ha estoc suficient.
7. Es calcula el total base, l'IVA i el total final.
8. Es crea el tiquet.
9. Es creen les línies de factura.
10. S'actualitza l'estoc dels articles venuts.
11. Es mostra una simulació del tiquet per pantalla.

## Consultes de vendes

L'aplicació inclou consultes per obtenir informació de vendes.

### Vendes per client

Mostra:

- DNI del client.
- Nom del client.
- Nombre de tiquets.
- Total gastat.

### Vendes per article

Mostra:

- Codi de l'article.
- Nom de l'article.
- Quantitat venuda.

## Càlcul de beneficis

L'aplicació permet calcular els beneficis dels articles venuts.

Per als pantalons s'utilitza la fórmula:

```text
Preu_Cost = preu_base * 0,30 + llargada_camal * 0,2
```

Per a les camises s'utilitza la fórmula:

```text
Preu_Cost = preu_base * 0,35 + talla_coll * 0,3
```

L'informe mostra:

- ID de l'article.
- Nom.
- Família.
- Preu base.
- Quantitat venuda.
- Preu cost unitari.
- Total de vendes sense IVA.
- Benefici.

El resultat es pot ordenar:

- De major a menor benefici.
- De menor a major benefici.

## Recompra automàtica

La recompra automàtica permet consultar articles que tenen un estoc inferior a un llindar indicat per teclat.

La proposta de recompra ha d'incloure:

- Codi de l'article.
- Nom de l'article.
- Quantitat.

Si es confirma la recompra, s'actualitza l'estoc dels articles i es genera un fitxer JSON de sortida amb la proposta.

## Classes principals

### `App.java`

Conté el menú principal i la lògica de funcionament de l'aplicació per consola.

Des d'aquesta classe es criden els mètodes de:

- `manageDB`
- `manageJSON`

### `manageDB.java`

Classe encarregada de gestionar l'accés a la base de dades.

Inclou mètodes per:

- Establir connexió.
- Consultar dades.
- Inserir registres.
- Actualitzar registres.
- Eliminar registres.
- Consultar vendes.
- Consultar stock.
- Calcular beneficis.

### `manageJSON.java`

Classe encarregada de gestionar el fitxer JSON d'articles.

Inclou mètodes per:

- Carregar el JSON.
- Comptar articles.
- Importar articles a la base de dades.
- Afegir o actualitzar articles segons si ja existeixen.

## Models

El projecte inclou models per representar les dades principals:

- `Article`
- `Camisa`
- `Pantalon`
- `Client`

Els articles tenen dades comunes i després dades específiques segons la seva família.

## Execució del projecte

Passos per executar el projecte:

1. Obrir el projecte amb l'IDE.
2. Executar l'script `createDB.sql` a MySQL.
3. Comprovar la configuració de connexió a `manageDB.java`.
4. Executar `App.java`.
5. Utilitzar el menú principal.

## Notes de desenvolupament

El projecte s'ha desenvolupat aplicant metodologia Scrum i organitzant les tasques amb GitHub Projects.

Les tasques s'han dividit en diferents sprints i branques de Git per separar millor el treball de cada funcionalitat.

## Estat del projecte

Funcionalitats implementades o en procés:

- Importació JSON.
- Gestió d'articles.
- Gestió de clients.
- TPV.
- Càlcul de beneficis.
- Consultes i informes.
- Recompra automàtica.

## Autors

Projecte desenvolupat per:

- Adrián Acarreta
- Miguel Sánchez
- Oriol Sardà
