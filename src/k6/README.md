# Load Tests

Aquí un ejemplo de un test de carga para la implementación de Spring State Machine.

El test está escrito usando [K6](https://k6.io/open-source/), para ejecutarlo se debe:
1. Instalar K6 en la máquina local.
2. Ejecutar el proyecto Spring
3. La variable de ambiente `TEST_SCENARIO` define la configuración que se utilizará para la prueba.
Las opciones son:
   * `50vus`: Se ejecutan 50 usuarios en paralelo por 10 segundos.
   * `ramping`: Se sube de 1 a 10 usuarios durante los primeros 10
   segundos, luego se sigue hasta 50 usuarios en los siguientes 20
   segundos, finalmente se baja a 0 usuarios en los últimos 10 segundos de la prueba.

Para ejecutar el test puede usar la siguiente línea:
`TEST_SCENARIO=ramping k6 run loadtest1.js`
Si no se especifica la variable TEST_SCENARIO se usa en forma
predeterminada la configuración `50vus`

El test llama a los diferentes endpoints disponibles y envía eventos a la máquina de estados y verifica
que el resultado de cada transición sea el esperado.
El test pasa si al menos el 99% de los cambios de estado son satisfactorios.

