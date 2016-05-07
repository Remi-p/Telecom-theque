define({ "api": [
  {
    "type": "get",
    "url": "/recherche",
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
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/recherche",
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
      }
    },
    "filename": "src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/objets",
    "title": "Retourne tous les objets",
    "version": "1.0.0",
    "name": "GetObjets",
    "group": "Objets",
    "description": "<p>Retourne tous les objets de la collection &quot;Objets&quot; de la base de données</p>",
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
      }
    },
    "filename": "src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/dates",
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
      }
    },
    "filename": "src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/recherche",
    "title": "Recherche d'objets",
    "version": "1.0.0",
    "name": "GetRechercheObjet",
    "group": "Objets",
    "description": "<p>Retourne une liste d'objets selon les filtres choisis.</p>",
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
        "content": "curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/recherche",
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
      }
    },
    "filename": "src/main/java/fr/enseirb/t2/telecomtheque/endpoints/ObjetsEndpoints.java",
    "groupTitle": "Objets"
  },
  {
    "type": "get",
    "url": "/recherche",
    "title": "Retourne les vitrines",
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
      }
    },
    "filename": "src/main/java/fr/enseirb/t2/telecomtheque/endpoints/VitrinesEndpoints.java",
    "groupTitle": "Vitrines"
  },
  {
    "type": "get",
    "url": "/\\{test}",
    "title": "Retourne une vitrine selon son ID",
    "version": "1.0.0",
    "name": "GetVitrines",
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
      }
    },
    "filename": "src/main/java/fr/enseirb/t2/telecomtheque/endpoints/VitrinesEndpoints.java",
    "groupTitle": "Vitrines"
  }
] });
