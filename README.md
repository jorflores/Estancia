# Estancia (starter)

Ejercicio integrador: CRUD con Retrofit (GET, POST, PUT, DELETE) contra una API local con json-server.
TC2007B · Desarrollo Móvil (Android).

## Qué ya está hecho

- Lista de niños, detalle, y un formulario que sirve para dar de alta y para editar. Todo conectado con Navigation.
- Los datos viven en memoria (`mutableStateListOf` en `MainNavigation`): la app ya agrega, edita y borra, pero al cerrarla se pierde todo.
- Ya están las dependencias de Retrofit, kotlinx.serialization y Lifecycle, y el permiso INTERNET.

## Servidor local

En la carpeta `servidor` está `db.json`, la "base de datos". Para levantar la API (necesitas Node.js):

```bash
cd servidor
npx json-server db.json
```

La API queda en `http://localhost:3000/ninos`. `db.original.json` es una copia para restaurar los datos iniciales.

## Qué hacemos en el ejercicio

Cambiar la lista en memoria por la API: Retrofit, un repositorio y un ViewModel. La solución está en la rama `solucion`.
