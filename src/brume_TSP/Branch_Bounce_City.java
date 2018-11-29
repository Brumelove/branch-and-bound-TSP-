/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brume_TSP;

/**
 *
 * @author brume
 */


import java.awt.geom.Point2D;

/**
 * Branch_Bounce_City class that represents a city by a point and a name
 */

public class Branch_Bounce_City extends Point2D.Double {
    private static final long serialVersionUID = 1L;
    private String name;

    /**
     * Constructs a city by point data and name
     *
     * @param name The city name
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public Branch_Bounce_City(String name, double x, double y) {
        super(x, y);
        this.name = name;
    }

    /**
     * Gets the city's name
     *
     * @return The city's name
     */
    
    public String getName() {
        return name;
    }
}
