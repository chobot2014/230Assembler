package function;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import objects.BType;
import objects.DType;
import objects.Instruction;
import objects.JType;
import objects.RType;

public class Main{
    public static int pc;
    
    // Might want to change these.
    public final static boolean DEBUG = true;
    public final static String FILELOC = "file.txt";
    public final static String FILELOCOUT = "MemoryInitialization.mif";

    public static void main(String[] args) {
        pc = 0;
        try {
            System.out.println("Reading File:"+FILELOC);
			File file = new File(FILELOC);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			PrintWriter writer = new PrintWriter(FILELOCOUT, "UTF-8");
			ArrayList<Instruction> instruct = new ArrayList<Instruction>();
            Instruction currentHex;
			String line;
            int i = 1;
            while((line = bufferedReader.readLine()) != null) {
                String[] sp = line.split(" ");
                if(sp[0].equalsIgnoreCase("lw")) {
                    if(sp[2].equalsIgnoreCase("0")){
                        currentHex = instructionConverter(line, i);
                        instruct.add(currentHex);
                        i++;
                    } else {
                        currentHex = instructionConverter(line, Integer.parseInt(sp[2]));
                        instruct.add(currentHex);
                    }
                } else {
                    currentHex = instructionConverter(line, i);
                    instruct.add(currentHex);
                    i++;
                }
            }
            Collections.sort(instruct, new Comparator<Instruction>() {
                public int compare(Instruction i1, Instruction i2) {
                    return Integer.compare(i1.getId(), i2.getId());
                }
            });

            Instruction out2 = new Instruction(0, "000000");
            instruct.add(0, out2);

            Instruction test1 = new Instruction(instruct.get(instruct.size()-1).getId()+1, "000000");
            instruct.add(test1);

            writer.println("WIDTH=" + (instruct.get(instruct.size()-1).getId() + 1) + ";");
            writer.println("DEPTH=" + (instruct.get(instruct.size()-1).getId() + 1001 + ";\n"));

            writer.println("ADDRESS_RADIX=UNS;");
            writer.println("DATA_RADIX=HEX;\n");

            writer.println("CONTENT BEGIN");
            instruct.get(0).pcState =0;

            for(i =0;i<instruct.size();i++){
                if(instruct.get(i).getId()!=i){
                    instruct.add(i,test1);
                }
            }


            for(i = 0; i<instruct.size(); i++) {
                if(i != instruct.size()-1) {
                    System.out.println("\t" + i + "\t:\t" + instruct.get(i).getHexvalue() + ";" + "   PC: "+instruct.get(i).getPcState());

                    writer.println("\t" + i + "\t:\t" + instruct.get(i).getHexvalue() + ";");
                }else if(i != instruct.size()-1 && instruct.get(i).getId()!= i) {
                 System.out.println("\t" + i + "\t:" + instruct.get(i).getHexvalue() + ";" + "   PC: "+instruct.get(0).getPcState());
                }else {
                    writer.println("\t[" + i + ".." + (instruct.get(i).getId() + 999) + "]\t:\t" + instruct.get(i).getHexvalue() + ";");
                    System.out.println("\t[" +i + ".." + (instruct.get(i).getId() + 999) + "]\t:" + instruct.get(i).getHexvalue() + ";"+ "    PC: "+instruct.get(i).getPcState());
                }
            }
            writer.println("END;");
            fileReader.close();
			writer.close();
            System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static Instruction instructionConverter(String command, int id) {
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
                default: System.out.println("d- type fuck up");
            }
            break;
            //BTYPE
            case "b":
            case "bal": instruction = new BType(splitCommand[0],splitCommand[1]);
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
        	//System.out.println("Binary: " + returnedBinary);
        }
        String returnedHex = Integer.toString(returnedDecimal,16);
        while(returnedHex.length()!=6){
            returnedHex = "0".concat(returnedHex);
        }
        Instruction instruct = new Instruction(id, returnedHex);
        return instruct;
    }
}
