
public class TriTyp {
     
    public String tryTip(int s1, int s2, int s3)
    {
        if (s1 == s2 && s2 == s3)
            return "equilateral";
        
        if (s1 == s2 && s2 != s3)
            return "isoscelese";
        
        if (s1 != s2 && s2 == s3)
            return "Isoscelese";
                
        return "scalene";                
    }
}
