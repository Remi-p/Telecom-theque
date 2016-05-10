define({ "api": [
  {
    "type": "get",
    "url": "/logs",
    "title": "Logs au format HTML",
    "version": "1.0.0",
    "name": "htmlLogs",
    "group": "Logs",
    "description": "<p>Retourne les logs au format HTML.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/logs",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "InputStream",
            "description": "<p>Retourne une page HTML.</p>"
          }
        ]
      }
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/LogsEndpoints.java",
    "groupTitle": "Logs"
  },
  {
    "type": "get",
    "url": "/logs/txt",
    "title": "Logs au format TEXT",
    "version": "1.0.0",
    "name": "textLogs",
    "group": "Logs",
    "description": "<p>Retourne les logs au format TXT.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/txt",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "InputStream",
            "description": "<p>Retourne une page TXT.</p>"
          }
        ]
      }
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/LogsEndpoints.java",
    "groupTitle": "Logs"
  },
  {
    "type": "get",
    "url": "/objets/meta/:idobjet/:uuid",
    "title": "Information des likes et des votes",
    "version": "1.0.0",
    "name": "GetLikes",
    "group": "Objets",
    "description": "<p>Retourne un document JSON contenant les informations suivantes : \t\t\t\t\tsi l'utilisateur a aimé ou voté pour l'objet, \t\t\t\t\tle nombre de vote, la moyenne et le nombre de like.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/meta/57169ee995e5008a634be22c/uuid",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>Json contenant les champs relatifs aux votes ainsi qu'aux likes.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "    HTTP/1.1 200 OK\n{\n \"like\": \"false\",\n \"vote\": \"false\",\n \"nb\": 4,\n \"moy\": 2.9,\n \"nbv\": 8,\n \"note\": 0\n}",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/objets/:idobjet",
    "title": "Retourne objet selon son ID",
    "version": "1.0.0",
    "name": "GetObjectbyId",
    "group": "Objets",
    "description": "<p>Retourne un seul objet selon son ID.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID de l'objet.</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/57169ee995e5008a634be22c",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>Json contenant la liste d'objets filtrés.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "Not",
            "description": "<p>Found L'objet n'a pas été trouvé dans la base de données.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "    HTTP/1.1 404 Not Found\n{\n\t\"error\": \"Objet introuvable\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/objets",
    "title": "Retourne tous les objets",
    "version": "1.0.0",
    "name": "GetObjets",
    "group": "Objets",
    "description": "<p>Retourne tous les objets de la collection &quot;Objets&quot; de la base de données.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>Json contenant les objets.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "    HTTP/1.1 200 OK\n\t\t{\n\t\t\t\"_id\": {\n\t\t\t\"$oid\": \"57169ee995e5008a634be22c\"\n\t\t},\n\t\t\"annee\": 1926,\n\t\t\"nom\": \"Caméra HERNEMAN\",\n\t\t\"description\": \"Caméra allemande pour prise de vue de film 35 mm. Manivelle avec poignée en bois. Poignée plate en cuir fixée à deux rivets sur le dessus. Niveau sphérique à bulle vissée sur le dessus. Sur le côté droit, un compteur (de 1 a 60) avec étiquette collée ciné spot. Près de la manivelle, une plaque en métal.\",\n\t\t\"imgs\": [\n\t\t\t{\n\t\t\t\"src\": \"http://www.culture.gouv.fr/Wave/image/joconde/0675/m081633_2-apv-3-2_p.jpg\"\n\t\t\t}\n\t]\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "UserNotFound",
            "description": "<p>L'objet n'a pas été trouvé dans la base de données.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "    HTTP/1.1 404 Not Found\n{\n\t\"error\": \"Objet introuvable\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/objets/dates",
    "title": "Retourne les dates limites",
    "version": "1.0.0",
    "name": "GetRangeAnnes",
    "group": "Objets",
    "description": "<p>Retourne un document json contenant l'année minimal et l'année maximal des objets.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/dates",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>Json contenant deux champs : amin et amax</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "    HTTP/1.1 200 OK\n{\n\t\"amin\": 1900,\n\t\"amax\": 1990\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "Error",
            "description": "<p>Aucun objet dans la base de données.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "    HTTP/1.1 404 Not Found\n{\n\t\"error\": \"Aucun objet disponible\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/objets/recherche",
    "title": "Recherche d'objets",
    "version": "1.0.0",
    "name": "GetRechercheObjet",
    "group": "Objets",
    "description": "<p>Retourne une liste d'objets selon les filtres choisis tel que le nom de l'objet, et une période.</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nom",
            "description": "<p>Nom de l'objet.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "amin",
            "description": "<p>Année minimum.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "amax",
            "description": "<p>Année maximum</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/recherche?nom=projecteur&amin=1900&amax=1975",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>Json contenant la liste d'objets filtrés.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "    HTTP/1.1 200 OK\n[\n\t{\n\t\t\"id\": \"57169f1395e5008a634be22e\",\n\t\t\"nom\": \"Projecteur radiocinéma 35mm 82301 PHILIPS\",\n\t\t\"annee\": \"20ème siècle\",\n\t\t\"cover\": \"http://www.culture.gouv.fr/Wave/image/joconde/0884/m103989_017600_p.jpg\"\n\t}\n]",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/objets/test/:idobjet",
    "title": "Test l'existence d'un objet",
    "version": "1.0.0",
    "name": "TestObjet",
    "group": "Objets",
    "description": "<p>Retourne un boolean déterminant l'existence ou non d'un objet en fonction de son ID.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/test/57169ee995e5008a634be22c",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>Json contenant un champ existence marqué à true ou false</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "    HTTP/1.1 200 OK\n{\n\t\"existence\": \"false\",\n}",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "post",
    "url": "/objets/notes/action",
    "title": "Action de vote sur un objet",
    "version": "1.0.0",
    "name": "addNotes",
    "group": "Objets",
    "description": "<p>Ajoute ou supprime une note à l'objet en étant identifié par l'uuid du périphérique.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/notes/action",
        "type": "json"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "    HTTP/1.1 200 OK\n{\n\t\"moy\": 2.9,\n\t\"msg\": \"Suppression\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "post",
    "url": "/objets/likes/action",
    "title": "Action sur les likes",
    "version": "1.0.0",
    "name": "likeObjet",
    "group": "Objets",
    "description": "<p>Ajoute ou suprime un like sur un objet par son ID et l'uuid de l'utilisateur.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/likes/action",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>String &quot;Ajout&quot; ou &quot;Suppresion&quot;</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n\"Ajout\"",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/vitrines/:idvitrine",
    "title": "Retourner une vitrine selon son ID",
    "version": "1.0.0",
    "name": "GetVitrine",
    "group": "Vitrines",
    "description": "<p>Retourne une seule vitrine selon son ID.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/vitrines/{idvitrine}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>Json contenant la liste des vitrines.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "    HTTP/1.1 200 OK\n\t\t{\n\t\t  \"vitrine\": \"5716a72e95e5008a634be234\",\n\t\t  \"nom\": \"Vidéo\",\n\t\t  \"objets\": [\n\t\t    {\n\t\t      \"id\": \"57169ee995e5008a634be22c\",\n\t\t      \"nom\": \"Caméra HERNEMAN\",\n\t\t      \"annee\": 1926,\n\t\t      \"cover\": \"http://www.culture.gouv.fr/Wave/image/joconde/0675/m081633_2-apv-3-2_p.jpg\"\n\t    },\n\t\t    {\n\t\t      \"id\": \"57169f0295e5008a634be22d\",\n\t\t      \"nom\": \"Caméra Pathé\",\n\t\t      \"annee\": \"20ème siècle\",\n\t\t      \"cover\": \"http://www.culture.gouv.fr/Wave/image/joconde/0675/m081633_2-apv-1-1_p.jpg\"\n\t\t    },\n\t\t    {\n\t\t      \"id\": \"57169f1395e5008a634be22e\",\n\t\t      \"nom\": \"Projecteur radiocinéma 35mm 82301 PHILIPS\",\n\t\t      \"annee\": \"20ème siècle\",\n\t\t      \"cover\": \"http://www.culture.gouv.fr/Wave/image/joconde/0884/m103989_017600_p.jpg\"\n\t\t    }\n\t\t  ]\n\t\t}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 404 Not Found\n{\n  \"error\": \"Les collections vitrines ou objets sont vides\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/VitrinesEndpoints.java",
    "groupTitle": "Vitrines"
  },
  {
    "type": "get",
    "url": "/vitrines",
    "title": "Retourner toutes les vitrines",
    "version": "1.0.0",
    "name": "GetVitrines",
    "group": "Vitrines",
    "description": "<p>Retourne toutes les vitrines en Json.</p>",
    "examples": [
      {
        "title": "Exemple :",
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/vitrines",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>Json contenant la liste des vitrines.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "    HTTP/1.1 200 OK\n\t\t[\n \t\t{\n   \t\t\"id\": \"5716a72e95e5008a634be234\",\n   \t\t\"nom\": \"Vidéo\",\n   \t\t\"nb_obj\": 8,\n   \t\t\"cover\": \"http://www.culture.gouv.fr/Wave/image/joconde/0675/m081633_2-apv-3-2_p.jpg\"\n \t\t},\n \t\t{\n   \t\t\"id\": \"5716bfc63a9dd07fbb6f69db\",\n   \t\t\"nom\": \"Téléphones\",\n   \t\t\"nb_obj\": 2,\n   \t\t\"cover\": \"http://www.culture.gouv.fr/Wave/image/joconde/0623/m041898_010268_p.jpg\"\n \t\t}\n\t\t]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 404 Not Found\n{\n  \"error\": \"Les collections vitrines ou objets sont vides\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "code/serveur/src/main/java/fr/enseirb/t2/telecomtheque/endpoints/VitrinesEndpoints.java",
    "groupTitle": "Vitrines"
  }
] });
