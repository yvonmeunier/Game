package com.company.ROGUELITE_GAME.WorldGen.Parser;

import com.company.ROGUELITE_GAME.WorldGen.Room;
import com.company.engine.Tiled.XMLParser;
import org.w3c.dom.Node;

public class RoomParser extends XMLParser<Room> {
    public RoomParser(String file) {
        super(file);
    }

    @Override
    public Room result() {
        Node mapNode = document.getElementsByTagName("map").item(0);
        int width = Integer.parseInt(mapNode.getAttributes().getNamedItem("width").getTextContent());
        int height = Integer.parseInt(mapNode.getAttributes().getNamedItem("height").getTextContent());

        Node dataNode = document.getElementsByTagName("data").item(0);
        String[] encodedStringData = dataNode.getTextContent().split(",");
        int[] encodedIntData = new int[width  * height];

        for (int i = 0; i < encodedIntData.length; i++) {
            encodedIntData[i] = Integer.parseInt(encodedStringData[i]);
        }



        return null;
    }



}
