
package Utils;

import java.util.Random;


public class RNG
{
    private static Random l_rng = new Random();
    
    // Return a random int in range [a_min, a_max].
    public static int RandomInt(int a_min, int a_max)
    {
        int l_range_magnitude = a_max - a_min + 1;
        
        return l_rng.nextInt(l_range_magnitude) + a_min;
    }
    
    // Return a random int in range [0, a_max].
    public static int RandomInt(int a_max)
    {   
        return l_rng.nextInt(a_max + 1);
    }
    
}