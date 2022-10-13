# Citas medicas en Línea - Curso Integrador 2

Proyecto realizado por los alumnnos:
- Carlos Quispe
- Martha Tarazona

Para iniciar el proyecto de citas médicas en línea, realice los siguientes pasos:

## Paso 1:

Descargue los fuentes desde GITHUB

```
git clone https://github.com/ceqs/i2citasmedicas.git
```

## Paso 2:

Cree una base de datos en MySQL por ejemplo:

```
citasmedicas
```

## Paso 3:

Modifique los datos de conexión a la base de datos en el archivo *application.yaml*, que se */src/main/resources*

```
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    username: <PONER AQUI EL USUARIO, normalmente es root>
    url: jdbc:mysql://localhost:3306/citasmedicas
    password: <PONER AQUI EL PASSWORD>
  jpa:
    show-sql: 'true'
```

## Paso 4:

Ejecute la aplicación, la clase principal es *CitasMedicasApplication* esta se encuentra en */src/main/java*.
Cuando la clase es ejecutada, LIQUIBASE creará todos las tablas para que el sistema funciona correctamente,
asimismo cargará un usuario por defecto para ingresar a la aplicación.


## Paso 5:

Finalmente, para probar la aplicación ingrese a la dirección *http://localhost:8080/* la aplicación automaticamente
lo llevará a la autenticación o al menú si ya esta sesionado. El usuario por defecto es:

```
Usuario : admin
Password: admin
```

### NOTA:
El comando para generar los archivos de liquibase desde una base de datos inicial, es el siguiente:
```
liquibase generate-changelog --changelog-file 0001-database-creation.yaml
```
