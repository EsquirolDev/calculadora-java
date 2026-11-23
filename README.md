Clonar repositorio

git clone https://github.com/EsquirolDev/calculadora-java.git
cd calculadora-java
echo "build/" >> .git/info/exclude
Add-Content .git\info\exclude "build/"
git checkout -b backend

Primer commit con el .java

git add Calculadora.java
cd .\backend\
git add Calculadora.java
cd ..
git reset
Move-Item backend\Calculadora.java .
Remove-Item backend
git status
git add Calculadora.java
git commit -m "Añadir lógica de la calculadora con validación de 5 dígitos"

git add Servidor.java
git commit -m "Añadir servidor HTTP con endpoint /calcular"
git push -u origin backend

Crear branch frontend

git checkout main
git checkout -b frontend

git add index.html
git commit -m "Añadir formulario HTML de la calculadora"
git push -u origin frontend

Hacer merge

git checkout main
git merge --no-ff backend -m "Merge rama backend en main"
git merge --no-ff frontend -m "Merge rama frontend en main"
git push origin main

Encender servidor

javac -encoding UTF-8 -d build Calculadora.java Servidor.java
java -cp build Servidor
git status
Set-Content .git\info\exclude "build/" -Encoding ascii
git status

Añadir estilos

git checkout frontend
git add index.html
git commit -m "Añadir estilos CSS al formulario"
git push

git checkout backend
java -cp build Servidor
javac -encoding UTF-8 -d build Calculadora.java Servidor.java
java -cp build Servidor
cd calculadora-java
javac -encoding UTF-8 -d build Calculadora.java Servidor.java
java -cp build Servidor

git checkout main
git merge --no-ff frontend -m "Merge estilos del frontend en main"
git merge --no-ff backend -m "Merge estilos del backend en main"
git push origin main
git status

git checkout backend
git add Servidor.java
git commit -m "Añadir estilos a la página de resultado"
git push
git checkout main
git merge --no-ff backend -m "Merge estilos del backend en main"
git push origin main

Subir README

git add README.md
git commit -m "Documentar comandos y publicación en GitHub"
git push origin main