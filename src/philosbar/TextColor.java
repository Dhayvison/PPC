package philosbar;

public class TextColor {
    
    private static String CSI = "\u001B[";
    
    public static String endColor(){
        return (CSI + "m");
    }

    public static String red(){
        return (CSI + "31" + "m");
    }
    
    public static String green(){
        return (CSI + "32" + "m");
    }
    
    public static String yellow(){
        return (CSI + "33" + "m");
    }

    public static void defColor(){
        System.out.print (CSI + "39" + "m");    
    }

}