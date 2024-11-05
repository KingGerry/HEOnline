package com.hy.business.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameMap {
    int maxX;
    int maxY;
    int mapId;
    String mapName;
    List<MapObj> mapObjs = new ArrayList<>();

}
