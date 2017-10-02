/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homefinder;

import java.util.Random;

/**
 *
 * @author alicia
 */
public class Home
{

    public static Random randomiser = null;
    
    public int x, y;
    public int rooms;
    public int price;
    
    public Home(int minX, int maxX, int minY, int maxY, int minRoom, int maxRoom, int minPrice, int maxPrice) 
    {
        if(randomiser == null)
        {
            initRandomiser();
        }
        
        x = randomiser.nextInt(maxX - minX) + minX;
        y = randomiser.nextInt(maxX - minY) + minY;
        rooms = randomiser.nextInt(maxRoom - minRoom) + minRoom;
        price = randomiser.nextInt(maxPrice - minPrice) + minPrice;
    
    }
    
    public void initRandomiser()
    {
        randomiser = new Random();
        randomiser.setSeed(System.currentTimeMillis());
    }
    
}
