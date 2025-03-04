# Utiliser une image de base avec Java 21
FROM openjdk:21-oracle

# Ajoute un label d’auteur à l’image Docker
LABEL authors="LandryDev"

# Crée un volume persistant pour le dossier /tmp
VOLUME /tmp

COPY target/*.jar app.jar

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]


