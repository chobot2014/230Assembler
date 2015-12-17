package function;
import java.io.*;

import objects.BType;
import objects.DType;
import objects.Instruction;
import objects.JType;
import objects.RType;

public class Main{
    public static int pc = 0;
    
    // Might want to change these.
    public final static boolean DEBUG = true;
    public final static String FILELOC = "file.txt";
    public final static String FILELOCOUT = "MemoryInitialization.mif";


    public static void main(String[] args) {
        try {
			File file = new File(FILELOC);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			PrintWriter writer = new PrintWriter(FILELOCOUT, "UTF-8");
			
			String line;
            writer.println("WIDTH=24;");
            writer.println("DEPTH=1024;\n");
            writer.println("ADDRESS_RADIX=UNS");
            writer.println("DATA_RADIX=HEX;\n");
            writer.println("CONTENT BEGIN;");
            int i = 0;
			while ((line = bufferedReader.readLine()) != null ) {
				if(!(line.charAt(0) == '#')){ // ignoreing comments
					pc++;
					String currentHex = instructionConverter(line);
                    writer.println("    " + i + "   :   " + currentHex + ";");
                    if(DEBUG){
				        System.out.println("Command: " + line);
				        System.out.println("Hex: " + currentHex);
				        System.out.println("---");
					}
                    i++;
				}
			}
            writer.println("[23..1023]  :   000000");
            writer.println("END;");
			fileReader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Done.");
    }

    public static String instructionConverter(String command) {
    	command = command.replace(",", ""); // removing any commas
    	Instruction instruction = new Instruction();
        String[] splitCommand = command.split(" ");
        switch (splitCommand[0]) {
            
        	//RTYPE
            case "add":
            case "or":
            case "xor":
            case "sub":
            case "and":
            case "sll":
            case "cmp":
            case "jr":
            switch (splitCommand.length) {
	            case 2: instruction = new RType(splitCommand[0],splitCommand[1]);
	            break;//jr
	            case 3: instruction = new RType(splitCommand[0],splitCommand[1],splitCommand[2]);
	            break;//cmp
	            case 4: instruction = new RType(splitCommand[0], splitCommand[1], splitCommand[2], splitCommand[3]);
	            break;// add, sub, and, or, xor, sll
            }
            break;
            
            //DTYPE
            case "lw":
            case "sw":
            case "addi":
            case "si":
            switch (splitCommand.length){
                case 2: instruction = new DType(splitCommand[0],splitCommand[1]);
                break;//si
                case 3: instruction = new DType(splitCommand[0],splitCommand[1],splitCommand[2]);
                break;//sw, lw
                case 4: instruction = new DType(splitCommand[0],splitCommand[1],splitCommand[2],splitCommand[3]);
                break;//addi
                }
            break;
            
            //BTYPE
            case "b":
            case "bal":
                instruction = new BType(splitCommand[0],splitCommand[1]);
                break;
                
            //JTYPE
            case "j":
            case "jal":
            case "li":
               switch (splitCommand.length) {
                   case 2: instruction = new JType(splitCommand[0], splitCommand[1]);
                   break; //j, jal
                   case 3: instruction = new JType(splitCommand[0], splitCommand[1], splitCommand[2]);
                   break; //li
               }
                break;
            default:
                System.out.println("command " + splitCommand[0] + " not found.");
                break;
        }
        
        String returnedBinary = instruction.getOutput(); 
        if(returnedBinary.length() != 24){
        	System.out.println("Error, Size of binary representation does not equal 24");
        }
        int returnedDecimal = Integer.parseInt(returnedBinary,2);
        if(DEBUG){
        	System.out.println("Binary: " + returnedBinary);
        }
        String returnedHex = Integer.toString(returnedDecimal,16);
        return returnedHex;
    }
}