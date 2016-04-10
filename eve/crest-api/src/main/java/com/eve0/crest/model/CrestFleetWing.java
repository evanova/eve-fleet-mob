package com.eve0.crest.model;

import java.util.List;
/*{
  "totalCount_str": "2",
  "items": [
    {
      "takesFleetWarp": true,
      "squadID": 3000110005634,
      "solarSystem": {
        "id_str": "30000140",
        "href": "http://localhost:26004/solarsystems/30000140/",
        "id": 30000140,
        "name": "Maurasi"
      },
      "wingID": 2000110005634,
      "boosterID_str": "3",
      "roleID": 3,
      "character": {
        "name": "One One",
        "isNPC": false,
        "href": "http://localhost:26004/characters/90000001/",
        "id_str": "90000001",
        "id": 90000001,
        "capsuleer": {
          "href": "http://localhost:26004/characters/90000001/capsuleer/"
        }
      },
      "boosterID": 3,
      "boosterName": "Squad Booster",
      "squadID_str": "3000110005634",
      "roleID_str": "3",
      "roleName": "Squad Commander (Boss)",
      "ship": {
        "id_str": "670",
        "href": "http://localhost:26004/types/670/",
        "id": 670,
        "name": "Capsule"
      },
      "joinTime": "2016-04-03T14:47:02",
      "wingID_str": "2000110005634"
    },
    {
      "takesFleetWarp": true,
      "squadID": 3000110005634,
      "solarSystem": {
        "id_str": "30000145",
        "href": "http://localhost:26004/solarsystems/30000145/",
        "id": 30000145,
        "name": "New Caldari"
      },
      "wingID": 2000110005634,
      "boosterID_str": "0",
      "roleID": 4,
      "character": {
        "name": "Two One",
        "isNPC": false,
        "href": "http://localhost:26004/characters/90000002/",
        "id_str": "90000002",
        "id": 90000002,
        "capsuleer": {
          "href": "http://localhost:26004/characters/90000002/capsuleer/"
        }
      },
      "boosterID": 0,
      "boosterName": "",
      "station": {
        "id_str": "60000688",
        "href": "http://localhost:26004/stations/60000688/",
        "id": 60000688,
        "name": "New Caldari I (Matigu) - Poksu Mineral Group Mineral Reserve"
      },
      "squadID_str": "3000110005634",
      "roleID_str": "4",
      "roleName": "Squad Member",
      "ship": {
        "id_str": "23913",
        "href": "http://localhost:26004/types/23913/",
        "id": 23913,
        "name": "Nyx"
      },
      "joinTime": "2016-04-03T15:20:06",
      "wingID_str": "2000110005634"
    }
  ],
  "pageCount": 1,
  "pageCount_str": "1",
  "totalCount": 2
}*/
public class CrestFleetWing extends CrestEntity {

    private long wingID;
    private List<Long> members;
    
}
