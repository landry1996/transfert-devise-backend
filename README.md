
# Documentation du Backend

## Aperçu
Ce backend est construit avec Spring Boot et fournit une API RESTful pour la gestion des utilisateurs, des comptes et des transferts de devises. Il inclut également une authentification et une autorisation basées sur JWT (JSON Web Tokens).

## Technologies Utilisées
- **Spring Boot** : Framework pour construire le backend.
- **Spring Data JPA** : Pour les interactions avec la base de données.
- **Spring Security** : Pour l'authentification et l'autorisation.
- **JWT** : Pour l'authentification basée sur des tokens.
- **MySQL** : Base de données pour stocker les informations des utilisateurs et des comptes.
- **Swagger** : Pour la documentation de l'API.

## Points d'Accès de l'API

### Gestion des Utilisateurs
- **POST /api/user/register** : Enregistrer un nouvel utilisateur.
- **POST /api/user/authenticate** : Authentifier un utilisateur et retourner des tokens JWT.
- **POST /api/user/refresh-token** : Rafraîchir le token JWT.
- **PATCH /api/user/change-password** : Changer le mot de passe de l'utilisateur.
- **GET /api/user/compte** : Obtenir tous les utilisateurs avec leurs comptes.
- **GET /api/user/{id}** : Obtenir un utilisateur par son ID.

### Gestion des Comptes
- **POST /api/compte** : Ajouter un nouveau compte pour un utilisateur.

### Taux de Change
- **GET /api/Exchange-Rate/USD-CAD/{fromCurrency}/{toCurrency}** : Obtenir le taux de change entre deux devises.

### Transfert d'Argent
- **POST /api/transfert** : Transférer de l'argent entre deux comptes.

## Schéma de la Base de Données
La base de données contient les tables suivantes :
- **users** : Stocke les informations des utilisateurs.
- **accounts** : Stocke les informations des comptes liés aux utilisateurs.
- **tokens** : Stocke les tokens JWT pour les utilisateurs authentifiés.

## Configuration
L'application est configurée via le fichier `application.yml`. Les configurations clés incluent :
- **Connexion à la base de données** : URL, nom d'utilisateur et mot de passe de la base de données MySQL.
- **Paramètres JWT** : Clé secrète et durée de validité des tokens.
- **Port du serveur** : Le backend fonctionne sur le port `2077`.

## Documentation d'Installation et d'Utilisation

### Installation

#### Backend

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/landry1996/transfert-devise-backend.git
   cd Transfert-de-Devise
   ```
2. Configurer la base de données dans `application.yml` :
   - Mettez à jour les champs `url`, `username` et `password` avec vos informations de connexion MySQL.
3. Construire l'image Docker et exécuter le container Docker du backend:
   ```bash
   docker build -t transfert-devise-backend .
   docker-compose -f docker-compose-backend.yml up
   ```
   Si vous souhaitez exécuter en arrière-plan :
   ```bash
   docker-compose -f docker-compose-backend.yml up -d
    ```

### Exécution de l'Application
1. Assurez-vous que MySQL est en cours d'exécution et que la base de données `transfert_devise` est créée.
2. Mettez à jour le fichier `application.yml` avec vos identifiants MySQL.
3. Exécutez l'application Spring Boot via votre IDE ou Maven :
   ```bash
   mvn spring-boot:run
   ```
4. Accédez à la documentation de l'API à l'adresse [http://localhost:2077/swagger-ui.html](http://localhost:2077/swagger-ui.html).

## Sécurité
- **Authentification JWT** : Tous les points d'accès (sauf `/api/user/register` et `/api/user/authenticate`) nécessitent un token JWT valide.
- **Accès basé sur les rôles** : Certains points d'accès sont restreints aux utilisateurs ayant des rôles spécifiques.

## Journalisation
L'application enregistre les événements importants tels que l'enregistrement des utilisateurs, l'authentification et la création de comptes. Les logs sont disponibles dans la console ou peuvent être configurés pour être stockés dans un fichier.

## Gestion des Erreurs
Le backend inclut des exceptions personnalisées et une gestion des erreurs pour des scénarios courants tels que des identifiants invalides, un solde insuffisant et des conversions de devises non supportées.


