# Java_project
I. Introduction générale : 

La gestion manuelle de la bibliothèque universitaire entraine de nombreuses limites : pertes d’informations, difficultés de suivi des prêts, absence d’historique des actions ou encore impossibilité d’assurer un contrôle fiable des retards. Afin de répondre à ces besoins, cette application propose une solution Java Desktop permettant de gérer les opérations essentielles d’une bibliothèque universitaire notamment la gestion des livres, des adhérents, des emprunts, des utilisateurs et rôles. Ainsi repose-t-elle sur une architecture modulaire, organisée selon les bonnes pratiques de développement logiciel, tel que l’utilisation du modèle MVC, la séparation claire des responsabilités et l’utilisation d’un projet Maven pour la gestion des dépendances et du cycle de construction. L’objectif est de fournir un système fiable, maintenable et sécurisé, facilitant le travail du personnel tout en offrant aux adhérents un accès simple au catalogue. 

 

II. Conception du projet (Partie UML) 

Analyse et Spécification du système 

1.1. Les acteurs 

1.2. Les exigences fonctionnelles 

1.3. Règles Métiers Importantes (Contraintes) 

Modélisation UML 

2.1. Diagramme de cas d’utilisation 

2.2. Diagramme de séquence (principaux scénarios) 

2.3. Diagramme de classe 

III. Implémentation du projet (Partie Java) 

Architecture Logicielle 

1.1. Vue globale 

1.2. Structure Maven 

1.3. Arborescence du projet 

src 

├── main/java/ 

│└── ma/est/gestion/model/ 

│          ├── Livre.java ├── Adherent.java ├── Emprunt.java 

│          ├── Utilisateur.java ├── Role.java ├── Log.java 

│          └── Categorie.java 

│└── ma/est/gestion/dao/ 

│        ├── LivreDao.java ├── AdherentDao.java 

│        ├── EmpruntDao.java ├── UtilisateurDao.java 

│└── ma/est/gestion/dao/impl/ 

│        ├── EmpruntDaoImpl.java├── LivreDaoImpl.java 

│        ├── AdherentDaoImpl.java ├── UtilisateurDaoImpl.java 

│└── ma/est/gestion/service/ 

│       ├── LivreService.java ├── AdherentService.java 

│      ├── EmpruntService.java ├── UtilisateurService.java         {pour les contraintes} 

           │└── ma/est/gestion/controller/ 

│       ├── LivreController.java ├── EmpruntController.java  

│        ├── AuthController.java ├── AdherentController.java 

│└── ma/est/gestion/util/ 

│       ├── BusinessException.java ├── PasswordUtil.java 

│└── ma/est/gestion/view/ 

│       ├── LivrePanel.java ├── LoginFrame.java  

│        ├──AdherentPanel.java ├── EmpruntPanel.java 

│       ├── MainFrame.java 

│└── ma/est/gestion/controller/ 

│       ├── LivreController.java ├── EmpruntController.java  

│        ├── AuthController.java ├── AdherentController.java 

├── test/java/ 

│└── ma/est/gestion/dao/ 

│       ├── LivreDaoTest.java ├── EmpruntDaoTest.java 

│       ├── AdherentDaoTest.java  

└── pom.xml 

   

Base de données 

2.1. Modèle Relationnel 

Fonctionnement de l’application 

3.1. Authentification 

3.2. Gestion des Livres 

3.3. Gestion des Emprunts 

3.4. Traçabilité 

Gestion de Version avec Git & GitHub 

Le développement collaboratif est réalisé via un dépot GitHub 

 

IV. Conclusion générale 
