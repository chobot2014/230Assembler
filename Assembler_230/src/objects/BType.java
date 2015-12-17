package objects;

import function.Main;

public class BType extends Instruction{
	private String opCode;
    private String cond;
    private String label;

    //Commands: b, bal
    public BType(String _opCode, String _label) {
        switch(_opCode){
        case "b":
        	opCode = "1000";
        	break;
        case "bal":
        	opCode = "1001";
        	break;
        default:
        	System.out.println("Error in BType");
        }
        cond = "0000";
        //calculate label
        int labelDecimal = Main.pc + 1 + Integer.parseInt(_label);
        label = String.format("%016d", Integer.parseInt(Integer.toBinaryString(labelDecimal)));
    }
    
    public String getOutput(){
        return opCode.concat(cond).concat(label);
    }
}
