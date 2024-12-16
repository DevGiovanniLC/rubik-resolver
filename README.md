# **Rubik Solver**
## Aplicación para resolver cubo de rubik estándar
*Giovanni León Corujo*  
*Naixin Chen Zhou*

2024-12-14

## 1. Introducción

La Aplicación a presentar en este documento es Rubik Solver. Una aplicación para resolver cubos de Rubik 3x3 independientemente de su distribución de colores.

Fue desarrollada siguiendo los estándares del cubo de Rubik oficial. Estos se pueden visualizar en la página oficial del cubo de rubik[^1]. Los estándares del cubo de rubik conlleva una distribución de los colores del cubo de Rubik específicas. Además de unas anotaciones a los movimientos posibles del cubo de Rubik.

La aplicación está pensada para cubos de Rubik que sigue esos colores estándar y hace uso de las anotaciones de los movimientos simples.


![image](https://github.com/user-attachments/assets/a4f61fc4-5f77-4472-b67a-825d514e4352)

![image](https://github.com/user-attachments/assets/4747da12-1d23-4738-a3e5-ef4664ba5c60)


## 2. Objetivos

Esta aplicación fue desarrollada con el objetivo de facilitar la resolución del cubo de rubik a sus usuarios. Con una explicación paso a paso de los movimientos a realizar para su correcta resolución. Para ello, el usuario tendrá que realizar los siguientes pasos.

1. Iniciar la aplicación y darle al botón start.

2. Tomar una foto de cada cara y que la aplicación detecte automáticamente la disposición de colores en esa cara o como alternativa darle al botón de editar el cubo y editarlo de forma manual.

3. En caso de haber fallado el detector de caras, podrá editar manualmente la cara donde ha fallado el detector.

4. Verificar la correcta selección de los colores en todas las caras del cubo.  
     
5. Visualizar la resolución del cubo, sus animaciones y los pasos a dar.

6. Realizar los movimientos paso a paso siguiendo las indicaciones.

Estos pasos a seguir concluirán en la resolución del cubo de rubik independientemente de la distribución de colores del mismo.

## 3. Diseño

![image](https://github.com/user-attachments/assets/80da12dc-b193-4b72-a6c2-05a0089301c5)

El diseño final no ha variado mucho del prototipo inicial, el objetivo de este diseño era usar colores amigables a la vista que contrastan con los colores del cubo de Rubik. 

Al principio la propuesta era un color negro de fondo pero ese color daría una percepción más seria y formal de la aplicación. El color azúl de fondo, en el diseño final da una sensación más amigable y para todos los públicos, como si fuera un juguete. 

Esa percepción es adecuada para que todo tipo de usuarios pruebe la aplicación y entren en el mundo del cubo de Rubik. Ya que el caso de uso de la aplicación es de nicho, donde si una persona está comenzando a intentar resolver el cubo de Rubik. Y comienza teniendo la percepción de que es muy complicado y que solo unos pocos pueden resolverlo, puede dejar de intentarlo.

En cuanto al color secundario, el blanco, forma parte de lo anteriormente comentado además de que hace buen contraste con el fondo de la aplicación.

El cubo que se muestra en el prototipo ha acabado siendo un cubo 3D animado. Lo   
que da una percepción de calidad mayor a que usaramos una foto estática de un cubo. Con esto se consigue que sea más sencillo a la hora de simular los movimientos. Al usar la misma distribución de colores que el cubo del usuario, conseguimos que sea más intuitiva la resolución del cubo y que sea más sencillo seguir los pasos para la correcta resolución del cubo de Rubik.

En cuanto a las activities de editor de cara y visualización del cubo, fueron creadas para que el usuario pudiera interactuar con los colores del cubo en el caso que el detector de cara fallara o que el usuario no tuviera un cubo de rubik estándar. Pudiendo seleccionar el color en su posición adecuada, sin estar obligado a usar la cámara.

![image](https://github.com/user-attachments/assets/add3273e-a29c-4ce9-acf4-7af48940afbf)


##  4.  Arquitectura

Para el desarrollo de la aplicación se ha utilizado la arquitectura MVVM (Model-View-ViewModel).

El Modelo representa los datos y la lógica de la aplicación, que en este caso son las clases:

- RubikCube: clase que define el estado del cubo  
- RubikCubeMovement: clase que se encarga de aplicar movimientos a una instancias de RubikCube  
- RubikSolver: clase que obtiene la solución del cubo de rubik que se le pase como parámetro

La Vista es la interfaz gráfica que el usuario ve y con la que interactúa. Se ha implementado la vista utilizando Jetpack Compose y GLSurfaceView.

- **Jetpack Compose:**  
  - Renderiza componentes de la interfaz como botones, textos  
  - Estructura los componentes haciendo uso de Column y Row

- **GLSurfaceView (OpenGL):**
  - Renderiza objetos en 3D  
  - Implementa animaciones visuales para movimientos usando transformaciones matriciales

ViewModel actúa como intermediario entre vista y modelo. Asegura que los datos mostrados estén sincronizados con el estado interno de la aplicación.

-**SolverRenderer:**

Esta clase guarda el estado actual del cubo y se vincula a un Composable de Jetpack Compose, haciendo uso de la función **remember**, con esto se logra una sincronización entre la vista y el modelo.

## 5.   Funcionalidades

### Captura y reconocimiento del cubo

Se utiliza la cámara del dispositivo para capturar imágenes en tiempo real, las cuales son procesadas mediante la librería OpenCV. A estas imágenes se les aplican máscaras de color para identificar y segmentar los colores presentes en las caras del cubo de Rubik. Este procesamiento permite detectar con precisión los contornos de los 9 cuadrados de cada cara, facilitando la identificación de su disposición y configuración actual.

### Resolución del cubo de rubik

A partir del estado inicial del cubo de Rubik capturado mediante la cámara del dispositivo, se procede a calcular su solución utilizando el **algoritmo de Kociemba**. Este método avanzado optimiza la secuencia de movimientos necesarios para resolver el cubo, garantizando que la solución no exceda un máximo de **20 movimientos**. Este enfoque es reconocido por su eficiencia y precisión, permitiendo encontrar una resolución óptima que minimiza el número de pasos requeridos.

### Animación del cubo de rubik

Una vez calculada la solución del cubo de Rubik, el usuario puede visualizar cada uno de los movimientos necesarios para resolverlo, facilitando el seguimiento paso a paso. Estas animaciones están implementadas utilizando **OpenGL**, un potente motor gráfico que permite renderizar animaciones en 3D con gran fluidez. Gracias a OpenGL, se pueden realizar rotaciones precisas en distintos ejes, cambiar perspectivas y ofrecer una experiencia visual interactiva y dinámica, lo que mejora significativamente la comprensión de los movimientos y la interacción con la solución.

## 6.  Conclusiones

Este programa ofrece una solución práctica y eficiente para resolver un cubo de Rubik de forma asistida. A través del uso de la cámara del dispositivo, se captura y analiza el estado inicial del cubo utilizando técnicas de procesamiento de imágenes con OpenCV. Posteriormente, se aplica el algoritmo de Kociemba, que garantiza una solución óptima en 20 movimientos o menos. Finalmente, se presenta al usuario una animación interactiva en 3D, implementada con OpenGL, que muestra cada paso necesario para resolver el cubo.

Está diseñado para quienes desean resolver un cubo de Rubik que tengan en casa y no han logrado completar en mucho tiempo, proporcionando una herramienta intuitiva y accesible que combina tecnología avanzada con facilidad de uso.



[^1]:  [https://www.rubiks.com/solution-guides](https://www.rubiks.com/solution-guides)
