# Load Tests

Aquí un ejemplo de un test de carga para la implementación de Spring State Machine.

El test está escrito usando [K6](https://k6.io/open-source/), para ejecutarlo se debe:
1. Instalar K6 en la máquina local.
2. Ejecutar el proyecto Spring
3. Ejecutar el test usando: `k6 loadtest1.js`

El test llama a los diferentes endpoints disponibles y envía eventos a la máquina de estados.
* Durante los primeros 15 segundos pasa de 0 a 20 usuarios simultáneos (VUS).
* A continuación, durante 30 segundos, baja desde 20 a 10 VUS.
* Finalmente, para de 10 a 0 VUS en los últimos 10 segundos del test.
