Résumé 

Ce projet s’inscrit dans le cadre de notre formation en Intelligence Artificiel et des Technologies Émergentes à l’École Supérieur de Technologie  et porte sur la mise en place d’un système destiner à digitaliser, automatiser et informatiser la gestion des bibliothèques. L’objectif principale est de remplacer les méthodes traditionnelles de gestion par une solution numérique fiable rapide et sécuriser. Le système développer repose sur l’utilisation de Java Desktop connecter à une base de donner MySQL. Ce rapport détaille les étapes de conception, d’implémentation et de teste du projet, tout en mettant en lumière les résultats et les perspectives d’amélioration.
 

Introduction Générale 

Dans un contexte éducatif en pleine transformation numérique et à une période où les technologies intelligentes transforment les usages quotidiens, l’intégration de solutions intelligentes dans la gestion de nos besoins devient une nécessiter. Ce projet, menait dans le cadre de notre cursus à l’école supérieur de technologies de Meknès en Intelligence artificielle et des technologies émergentes, propose une solution pour la gestion des bibliothèques. L’objectif principal est de concevoir un système fiable, sécurisé et facilement maintenable, capable de faciliter le travail du personnel administratif tout en offrant aux adhérents un accès simple et efficace au catalogue de la bibliothèque. 

Dans ce rapport nous aurons l’occasion de comprendre les fonctionnements du système, d’aller un peu plus en détails dans les phases de conception et d’implémentation en développant successivement trois parties à savoir :  

Dans la première, nous parlerons de la phase de conception. 

Dans la seconde, nous aurons l’occasion de discuter de l’implémentation de l’application 

Dans la dernière, Nous aborderons la présentation proprement dite de l’application, nous verrons les différentes fonctionnalités qu’elle intègre et leur mode d’emploi.
 

: Conception du projet (Partie UML) 

Petite approche 

Dans le cadre de ce projet, trois diagrammes ont été élaborés afin de modéliser le système : un diagramme de cas d’utilisation, un diagramme de séquence et un diagramme de classe. Ces diagrammes ont d’abord été conçus et discutés collectivement de manière manuelle, puis implémentés à l’aide d’un outil de modélisation professionnel, à savoir Visual Paradigm. 

Analyse et Spécification du système 

1.1. Les acteurs 

 

Administrateur (Admin) : Super-utilisateur responsable de la gestion du catalogue (livres), des adhérents, des comptes utilisateurs et du suivi des logs (traçabilité). 

Adhérent (Utilisateur) : Membre de la bibliothèque qui peut consulter le catalogue, rechercher des ouvrages, effectuer et gérer ses propres emprunts. 

Système d'authentification : Acteur externe (système) sollicité pour vérifier les identifiants lors de la connexion. 

 

1.2. Les exigences fonctionnelles 

Le système doit permettre de : 

S'authentifier : Accès sécurisé selon le rôle (Admin/User). 

Gérer le catalogue : Ajouter, modifier et supprimer des livres (réservé à l'Admin). 

Gérer les emprunts : Enregistrer les prêts et les retours avec mise à jour automatique du stock. 

Rechercher : Permettre d’effectuer une recherche dans le catalogue. 

Tracer : Générer des logs pour chaque opération sensible afin d'assurer l'audit du système. 

 

1.3. Règles Métiers Importantes (Contraintes) 

 

Unicité : Le code ISBN (Livre), l'email (Adhérent) et le login (Utilisateur) doivent être uniques. 

Limitation des prêts : Un adhérent ne peut pas avoir plus de 3 emprunts actifs. 

Sanction : Tout retard supérieur à 10 jours bloque automatiquement les droits d'emprunt de l'adhérent. 

Calcul automatique : La date de retour est fixée à DateEmprunt + 14 jours. 

Intégrité du stock : Un livre ne peut être emprunté que si son nombreExemplaire > 0.  À chaque emprunt, ce nombre est décrémenté ; il est incrémenté au retour. 

 

Modélisation UML 

Diagramme de cas d’utilisation 

 

Dans ce diagramme, toutes les actions critiques (Gérer les livres, Emprunter, etc.) incluent obligatoirement le cas d'utilisation "S'authentifier" (relation <<Include>>). L'administrateur hérite des fonctionnalités de l'utilisateur tout en possédant des privilèges exclusifs de gestion. 
 

Figure 1: Diagramme de cas d'utilisation 

Diagramme de séquence (principaux scénarios) 

Ces diagrammes permettent de représenter avec plus de précisions les différentes interactions entre les acteurs et le system. Grace a ces diagrammes tous les participant de conception ont une vue explicite du fonctionnement de l’application. 

Nous avons pour cette application Six (6) diagrammes de séquences que sont : 

Zone de texte 1, Zone de texteAuthentification 
Représente l’enchainement effectuer pour une authentification : 
 

Zone de texte 1, Zone de texteChercher livre 
Représente l’enchainement effectuer pour la recherche d’un livre : 
 

Emprunter livre 
Représente l’enchainement effectuer pour l’emprunt : 

Figure 4: Diagramme de séquence -> Emprunter livre 


Ajouter livre 
Représente l’enchainement effectuer pour ajouter un livre : 
 
 
Figure 5 : Diagramme de séquence -> Ajouter livre 
 

Modifier livre 
Représente l’enchainement effectuer pour les modifications de livres : 
  

Figure 6 : Diagramme de séquence -> Modifier  livre 
 
Supprimer livre 
Représente l’enchainement effectuer pour la suppression d’un livre : 
 
 
Figure 7 : Diagramme de séquence -> Supprimer livre 
 

Ces 6 diagrammes sont ensuite représentés dans un diagramme de séquence générale nommé Bibliothèque  

 

Figure 8 : Diagramme de séquence Générale -> Bibliothèque 

Diagramme de classe 


 Le diagramme présente une structure robuste : 
 

Livre & Catégorie : Relation de type 1..* (un livre appartient à une seule catégorie). 

Emprunt : Classe pivot reliant Livre et Adhèrent. 

Utilisateur & Rôle : Permet une gestion fine des droits (Contrôle d'Accès Fondé sur les Rôles). 

Log : Classe isolée permettant de garder une trace historique des actions de chaque utilisateur. 
 

Figure 9 : Diagramme de Classe 

Implémentation du projet (Partie Java) 

Introduction 

Cette application repose sur une architecture modulaire respectant les bonnes pratiques du développement logiciel, notamment l’adoption du modèle MVC (Model–View–Controller), la séparation claire des responsabilités et l’utilisation d’un projet Maven pour la gestion des dépendances et du cycle de construction. 

Architecture Logicielle  

        1.1. Vue globale  

L’application adopte une architecture MVC (Modèle-Vue-Contrôleur) couplée au design pattern DAO (Data Access Object). Cette approche permet une séparation nette entre l'interface utilisateur, la logique métier et l'accès aux données : 

Le Modèle (Model) : Représente les entités du domaine (Livre, Adhérent, etc.). Ce sont des classes qui reflètent la structure de la base de données. 

La Vue (View) : Développée avec Java Swing, elle gère l'affichage et l'interaction avec l'utilisateur via des JFrame et JPanel. 

Le Contrôleur (Controller) : Fait le pont entre la Vue et le DAO. Il intercepte les événements de l'utilisateur, effectue les vérifications nécessaires (contraintes métiers) et met à jour la base de données. 

Le DAO (Data Access Object) : Isole la logique de persistance. Il contient les requêtes SQL (CRUD) pour interagir avec MySQL. 

      1.2. Structure Maven 

Nous avons utilisé Maven comme outil de gestion et de construction. Le fichier pom.xml centralise la configuration : 

Gestion des dépendances : Intégration du pilote mysql-connector-java pour la connexion à la base de données et de JUnit pour les tests unitaires. 

Standardisation : Maven impose une structure de dossiers standardisée (src/main/java, src/test/java), ce qui facilite le travail collaboratif sur GitHub. 

Build : Automatisation de la compilation et génération du fichier JAR exécutable. 

           1.3. Arborescence du projet 

src 

├── main/java/ 

│     └── ma/est/gestion/model/ 

│             ├── Livre.java ├── Adherent.java ├── Emprunt.java 

│             ├── Utilisateur.java ├── Role.java ├── Log.java 

│             └── Categorie.java 

│     └── ma/est/gestion/dao/ 

│             ├── LivreDao.java      ├── AdherentDao.java 

│             ├── EmpruntDao.java ├── UtilisateurDao.java 

│     └── ma/est/gestion/dao/impl/ 

│             ├── EmpruntDaoImpl.java ├── LivreDaoImpl.java 

│             ├── AdherentDaoImpl.java ├── UtilisateurDaoImpl.java 

│ 

│     

│    └── ma/est/gestion/controller/ 

│            ├── LivreController.java ├── EmpruntController.java  

│            ├── AuthController.java   ├── AdherentController.java 

│     └── ma/est/gestion/util/ 

│            ├── BusinessException.java ├── PasswordUtil.java 

│     └── ma/est/gestion/view/ 

│            ├── LivrePanel.java      ├── LoginFrame.java  

│            ├──AdherentPanel.java ├── EmpruntPanel.java 

│            ├── MainFrame.java     ├── EmpruntDialog.java 

│    └── ma/est/gestion/controller/ 

│            ├── LivreController.java ├── EmpruntController.java  

│            ├── AuthController.java ├── AdherentController.java 

├── test/java/ 

│     └── ma/est/gestion/dao/ 

│            ├── AppTest.java 

│      

└── pom.xml 

 

 

Détails des composants clés : 

ma.est.gestion.dao.impl : Contient les classes concrètes qui utilisent JDBC pour exécuter les instructions SQL. 

ma.est.gestion.util : Propose des outils transverses. PasswordUtil est utilisé pour le hachage des mots de passe, et BusinessException permet de gérer proprement les erreurs liées aux règles de gestion (ex: "Livre non disponible"). 

EmpruntDialog.java : Une fenêtre modale spécifique utilisée pour saisir les informations lors de la création d'un nouvel emprunt. 

ma.est.gestion.model : Contient les classes de données (POJO) qui représentent les entités du système (Livre, Adhérent, Emprunt). Chaque classe correspond à une table de la base de données. 

ma.est.gestion.dao : Regroupe les interfaces définissant les méthodes d'accès aux données (CRUD). Elles permettent d'abstraire la source de données du reste de l'application. 

ma.est.gestion.dao.impl : Contient les classes concrètes qui implémentent les interfaces DAO en utilisant JDBC pour exécuter les requêtes SQL vers la base de données MySQL. 

ma.est.gestion.controller : Contient la logique de contrôle. Ces classes reçoivent les actions de la Vue, appliquent les règles métiers (ex: vérifier si un adhérent a déjà 3 emprunts) et appellent le DAO pour mettre à jour les données. 

ma.est.gestion.view : Contient l'interface graphique (GUI) développée avec Swing. Elle regroupe les fenêtres (JFrame), les panneaux (JPanel) et les boîtes de dialogue nécessaires à l'interaction utilisateur. 

ma/est/gestion/dao (test) : Situé dans src/test/java, ce package contient les tests unitaires (JUnit) pour valider que les méthodes de persistance (DAO) fonctionnent correctement. 

Base de données 

La persistance des données est assurée par une base de données relationnelle MySQL. Le passage du diagramme de classes au modèle relationnel (MLD) respecte les règles de transformation standard : chaque classe devient une table, et les associations sont traduites par des clés étrangères (FK). 

Modèle Relationnel 

2.1.1. Gestion des Livres et Catégories 

 

Categorie  

id_cat (INT, PK, Auto-increment) 

 nomCategorie (VARCHAR(50), NOT NULL) 

 

Livre  

code_isbn (VARCHAR(20), PK) : Identifiant unique du livre. 

titre (VARCHAR(100)) 

auteur (VARCHAR(100)) 

nombreExemplaire (INT) : doit être > 0 

#id_cat (INT, FK) : Référence à la table CATEGORIE. 

 

2.1.2. Gestion des Utilisateurs et Accès (RBAC) 

Role  

id_role (INT, PK) 

nomRole (VARCHAR(20)) : "ADMIN" ou "USER". 

 

Utilisateur  

id_user (INT, PK, Auto-increment) 

login (VARCHAR(50), UNIQUE) 

password (VARCHAR(255))  

statut (BOOLEAN) : true pour actif, false pour désactivé. 

#id_role (INT, FK) : Définit les droits d'accès. 

 

2.2.3. Gestion des Adhérents et Emprunts 

Adherent  

id_adh (INT, PK, Auto-increment) 

num_adh (INT, UNIQUE) 

nom (VARCHAR(50)) 

prenom (VARCHAR(50)) 

email (VARCHAR(100), UNIQUE) 

statut (BOOLEAN) : bloqué si retard > 10 jours. 

 

 

mprunt 

id_emp (INT, PK, Auto-increment) 

dateEmprunt (DATE) 

dateRetourPrevue (DATE) : Calculée à $J+14$. 

statut (VARCHAR(20)) : "En cours", "Rendu", "Retard". 

#code_isbn (VARCHAR(20), FK) 

#id_adh (INT, FK) 

 

2.1.4. Traçabilité (Audit) 

LOG  

id_log (INT, PK, Auto-increment) 

dateAction (DATETIME) 

action (TEXT) : Description de l'opération (. 

#id_user (INT, FK) : L'utilisateur ayant effectué l'action. 

 

 

Fonctionnement de l’application 

La gestion manuelle d’une bibliothèque universitaire présente de nombreuses limites, telles que la perte d’informations, les difficultés de suivi des prêts, l’absence d’un historique fiable des actions, ainsi que l’impossibilité d’assurer un contrôle rigoureux des retards. Face à ces contraintes, le présent projet propose le développement d’une application Java Desktop visant à informatiser et optimiser la gestion des opérations essentielles d’une bibliothèque universitaire, notamment la gestion des livres, des adhérents, des emprunts, des utilisateurs et des rôles. 

 Cette section détaille les flux de travail (workflows) principaux et la manière dont les contrôleurs orchestrent les données entre la vue et la base de données. 

 

3.1. Authentification 

C'est la porte d'entrée obligatoire du système. 

L'utilisateur saisit ses identifiants dans la LoginFrame.(Pour un Admin, après avoir renseigné son login et son mot de passe, il sélectionne le bouton ‘Admin’ pour se connecter) 

Le MainFrame récupère ces données et appelle UtilisateurDaoImpl pour chercher l'utilisateur par son login. 

Sécurité : Le mot de passe saisi est comparé au mot de passe haché en base  

Si le statut de l'utilisateur est "actif", la MainFrame s'affiche avec des menus personnalisés selon le rôle (ADMIN ou USER). 

 

3.2. Gestion des Livres 

Le LivreController gère l'inventaire via le LivrePanel : 

Affichage : Au chargement, le contrôleur demande au DAO la liste de tous les livres pour remplir la table Swing. 

Ajout/Modification : L'administrateur remplit un formulaire. Le contrôleur vérifie que le code ISBN est unique avant de valider l'insertion. 

Suppression : Une règle de gestion critique est appliquée : le contrôleur vérifie auprès d'EmpruntDao qu'aucun exemplaire n'est actuellement en prêt avant d'autoriser la suppression. 

3.3. Gestion des Emprunts 

Cette partie est complexe car elle mobilise plusieurs règles métiers : 

Vérification Adhérent : Le EmpruntController vérifie si l'adhérent n'est pas bloqué (retard > 10 jours) et s'il a moins de 3 prêts actifs. 

Vérification Stock : Il vérifie que nombreExemplaire > 0. 

Action atomique : Si les conditions sont remplies, le système crée une entrée dans la table EMPRUNT, calcule la dateRetourPrevue (dateEmprunt + 14j jours) et décrémente le nombre d’exemplaires 

En cas d'erreur, une BusinessException est levée et affichée à l'écran via un JOptionPane. 

 

3.4. Traçabilité (Login) 

Pour chaque opération sensible (Suppression de livre, validation d'emprunt, retour), une méthode est appelée pour créer une instance de Log. 

Le système enregistre : l'identifiant de l'utilisateur connecté, l'action effectuée et l'horodatage(timestamp) précis. 

Ces logs sont stockés en base de données et peuvent être consultés via le panel. 

Gestion de Version avec Git & GitHub 

Le développement collaboratif a été structuré autour d'un dépôt distant : 

Main Branch : Contient la version stable et fonctionnelle du projet. 

Commits : Chaque membre a effectué des commits réguliers avec des messages explicites (ex : "Implémentation du DAO Livre", "Correction bug authentification"). 

Collaboration : Utilisation des Pull Requests pour intégrer les fonctionnalités développées séparément, garantissant ainsi que le code final est testé et validé par le groupe. 

 

Conclusion générale 

La réalisation de ce projet de gestion de bibliothèque nous a permis de mettre en pratique les concepts avancés de la programmation Java et de la modélisation UML. L'adoption de l'architecture MVC et du pattern DAO a été déterminante pour produire un code propre, modulaire et facile à maintenir. 

Nous avons réussi à intégrer des contraintes métiers complexes (gestion des stocks, blocage des adhérents en retard) directement dans la logique applicative. L'utilisation d'outils professionnels tels que Maven pour la gestion de projet et GitHub pour le versionnage nous a préparés aux réalités du travail en équipe dans le domaine du génie logiciel. 

 
