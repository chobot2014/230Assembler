package objects;


public class Instruction {
    private String opCode;
    public String output;
    public String hexvalue;
    public int id;

    public Instruction(String opCode) {
        this.opCode = opCode;
    }

    public Instruction(int id, String hexvalue){
        this.id = id;
        this.hexvalue = hexvalue;
    }

    public Instruction(){

    }

    public String getHexvalue() {
        return hexvalue;
    }

    public int getId() {
        return id;
    }
    public String getOpCode() {
        return opCode;
    }
    public String getOutput() {
        return output;
    }
}
