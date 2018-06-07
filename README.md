# Pinball - Tarea CC3002

Tarea 2 del curso Metodologías de Diseño y Programación del semestre de Otoño 2018. 

En esta parte de la tarea incremental se trabajó en la lógica detrás de un juego de Pinball,
con tal de asegurar el funcionamiento correcto de las interacciones entre los objetos del
tablero de juego, los bonus, y el controlador de las partidas.

## Implementación

A continuación se muestra la estructura de organización del código junto con una breve
descripción de cómo se trabajó cada clase.

### Controller

#### Game:
La única clase contenida en __controller__, en la cual se organiza a todos los demás
elementos. Específicamente, se encarga de fiscalizar las interacciones entre distintos objetos;
contiene el tablero, las instancias de bonus y almacena la cantidad de vidas y puntaje en una partida.
 
Basa su funcionamiento en el patrón de diseño _Observer_, esto es, la interfaz __Observer__ para enterarse
de cuándo los elementos del juego requieren que se realice un cambio en el estado de éste, ya sea para activar
algún evento o directamente actualizar el valor de una variable. El registro de este __Observer__
para cada elemento __Observable__ se efectúa dentro del constructor de esta clase.

### Facade

#### HomeworkTwoFacade:
Esta clase es la que se manipulará durante la tarea siguiente para crear una Interfaz Gráfica de Usuario
de este juego.
El uso del patrón de diseño _Facade_ permite que se oculte la mayor parte de las operaciones lógicas,
limitándose a enseñar el estado de los elementos y llamar métodos dependiendo del efecto que tenga 
que el usuario entre en contacto con algún objeto del tablero.

Por el momento no se usa, y su implementación consiste en llamar a la instancia de __Game__ para que
ejecute lo exigido por la documentación entregada.

### Logic  

#### Bonus:

En esta carpeta se agrupan las clases e interfaces que representan a los objetos que producen
eventos particulares al cumplirse ciertos requisitos (golpear cierto elemento y tener un poco de suerte).

Compuesto por los siguientes archivos:
* `AbstractBonus`
* `Bonus` _(Interfaz)_
* `DropTargetBonus`
* `ExtraBallBonus`
* `JackPotBonus`

La interfaz especifica los métodos que todos los tipos de bonus deben poseer, la clase abstracta detalla
la función de aquellos métodos que son comunes a todos los tipos de bonus e implementa la interfaz
__Observable__ para que sea posible vigilar cualquier cambio de estado que puedan producir.
Finalmente, los tres tipos de bonus determinan los efectos que produce activar cada uno (`trigger`) y
notifican a __Game__ (mediante `notifyObservers`) cuando se debe sumar puntaje o vidas.

#### GameElements

Incluye la interfaz __Hittable__, que contiene los métodos que debe tener un objeto golpeable, y
la interfaz __Visitor__ (que hereda a __Hittable__) para comunicar de forma ordenada el evento que
se espera que se active en __Game__, esto mediante _Double Dispatch_ y el patrón de diseño _Visitor_.

La estructura de los elementos del juego es similar a la que ya se explicó en __Bonus__:
la interfaz especifica los métodos que deben poseer todos los tipos de bumpers/targets,
la clase abstracta detalla aquellos métodos que son comunes a todos los tipos e implementa
la interfaz __Observable__ para vigilar cualquier cambio de estado que se pueda producir.
Por último, las clases de cada tipo de elemento determinan los efectos de golpear cada uno (`hit`)
y notifican a __Game__ (mediante `notifyObservers`) cuando se debe sumar puntaje o activar un __Bonus__.

##### Bumper:

En esta carpeta se agrupan las clases e interfaces que representan a los objetos __Bumper__, esto es,
un elemento de juego golpeable que puede subir de nivel y activar bonus del tipo __ExtraBallBonus__.

Compuesto por los siguientes archivos:
* `AbstractBumper`
* `Bumper` _(Interfaz)_
* `KickerBumper`
* `PopBumper`

Los dos tipos de __Bumper__ son __KickerBumper__ y __PopBumper__, y se distinguen por la cantidad de
puntos que otorga cada uno.

##### Target:

En esta carpeta se encuentran las clases e interfaces que representan a los objetos __Target__, esto es,
los elementos de juego golpeables que son desactivados una vez se golpean y pueden activar un bonus. 

Compuesto por los siguientes archivos:
* `AbstractTarget`
* `Target` _(Interfaz)_
* `DropTarget`
* `SpotTarget`

Los dos tipos de __Target__ son __DropTarget__ y __SpotTarget__, y se diferencian en que el primero puede
activar a ___ExtraBallBonus___ y una vez que se desactivan todos los del mismo tipo se produce 
__DropTargetBonus__, mientras que el segundo invoca a __JackPotBonus__ cada vez que se golpea uno activo. 

#### Table

##### PinballTable:

Esta clase implementa todos los métodos necesarios (documentados en la interfaz __Table__) para crear,
diferenciar, almacenar ordenadamente y emplear todos los elementos de juego.
Así también, se encarga de asegurar una forma de distinguir los tableros creados de forma "oficial"
(esto es, desde los métodos `newPlayableTableWithNoTargets` y `newFullPlayableTable` de __Game__) del resto.

## Ejecución del código

Dado que el programa todavía no tiene GUI, no tiene mucho sentido ejecutarlo hasta este punto.

Por lo pronto, es posible exportar el código a su IDE de preferencia (se usó IntelliJ IDEA para su realización)
y revisar el comportamiento de las clases a través de los tests.

### Testing

Se distribuyó el testing de forma similar a la estructuración de las clases. Con un total de 6 archivos,
se revisa en cada uno los tipos de elementos de juego y el funcionamiento correcto de los métodos de
manipulación e interacción entre ellos. Ordenados de menor a mayor (de los más simples a los compuestos),
se consideró los siguientes:

* GameElements:
    * `KickerBumperTest`
    * `PopBumperTest`
    * `DropTargetTest`
    * `SpotTargetTest`
* Table:
    * `PinballTableTest`
* Controller:
    * `GameTest`

__GameTest__ contiene las pruebas de las clases __Bonus__ (implícitas en la utilización de los métodos
`visit`) puesto que su funcionamiento es completamente dependiente de los otros elementos, por lo que no
tiene sentido revisarlos por sí solos. Estos se encuentran en los métodos siguientes:
```
@Test
public void dropTargetBonusActivation();

@Test
public void extraBallBonusActivation();

@Test
public void jackPotBonusActivation();
```

Así también, las notificaciones entre __Observer__ y __Observable__ de cada elemento del juego se prueban solo en este
test, en los métodos:
```
@Test
public void kickerBumperInteraction();

@Test
public void popBumperInteraction();

@Test
public void dropTargetInteraction();

@Test
public void spotTargetInteraction();
```

Por otro lado, no hay tests de la clase __HomeworkTwoFacade__ dado que resultaría en repetir las mismas
pruebas ya realizadas en __GameTest__.

## Desarrollado Con

* [IntelliJ IDEA Ultimate 2018.1](https://www.jetbrains.com/idea/) - IDE para la escritura y revisión de código.  
* [Maven](https://maven.apache.org/) - Gestión y construcción del proyecto.

## Autores

* Sofía Castro - _Implementación y testing_ - [cinnamontea](https://github.com/cinnamontea)
* Juan-Pablo Silva - _Código base, la mayor parte de la estructura y documentación de interfaces y la clase Facade_ -
[juanpablos](https://github.com/juanpablos)  


## Agradecimientos

* [adam-p's Markdown Cheatsheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)
* [PurpleBooth's README Template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
* [U-Cursos](https://www.u-cursos.cl/ingenieria/2018/1/CC3002/) - Por el código base otorgado por el
profesor y auxiliar, y los cientos de dudas tanto preguntadas como respondidas en el foro.
* _Mi hermana_ - Por su paciencia y disposición a hacer proof-reading de cosas que no son de su área.
