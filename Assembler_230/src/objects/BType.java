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
        Main.pc = Main.pc + Integer.parseInt(_label);
        int labelDecimal = Main.pc;
        label = String.format("%016d", Integer.parseInt(Integer.toBinaryString(labelDecimal)));
        Main.pc++;
    }
    @Override
    public String getOutput(){
        return opCode.concat(cond).concat(label);
    }

    @Override
    public int getPcState(){
        return pcState;
    }
}
