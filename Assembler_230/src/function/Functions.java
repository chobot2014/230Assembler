package function;

public class Functions {
	// Thought I was going to have more functions than this, but oh well.
	
    public static String registerConverter(String reg){
    	int value = Integer.parseInt(reg.substring(1));
        String binary = Integer.toString(value, 2);
        return String.format("%04d", Integer.parseInt(binary));
    }

}
