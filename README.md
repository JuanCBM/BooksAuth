# BooksAuth
> docker run -p 3306:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -e -d mysql:latest


# Https en Spring
- Ejecutando como administrador una terminal, en el directorio:
> C:\Program Files\Java\jdk-11.0.5\bin

- Ejecutamos el comando y tras rellenar el formulario generará el archivo necesario ('keystore.jks')
> keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360 -keysize 2048


# Https en node
Git incorpora OpenSSL.
- Ejecutando como administrador una terminal, en el directorio:  
> C:\Program Files\Git\usr\bin\openssl.exe

- Ejecutamos el comando
> openssl.exe
 
- En su interior, con el comando y tras rellenar el formulario generará los dos archivos necesarios en proyecto node para https ('server.cert' y 'server.key')
> req -nodes -new -x509 -keyout server.key -out server.cert




