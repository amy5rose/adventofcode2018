import common.DayBase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class day16 extends DayBase {

  public static void main(String[] args) throws IOException {
    day16 day = new day16();
    day.run();
  }

  @Override
  public String getSampleInputString() {
    return "16input.txt";
  }

  @Override
  public String getSampleAnswer() {
    return "5";
  }

  @Override
  public String getRealInputString() {
    return "16input2.txt";
  }

  public String findAnswer(List<String> list, boolean isSample) {
    //parse out the input


    //for each input, test against the possible ideas
    //count inputs that have more possibilities than 3.


    return "";
  }

  public int testOpCodes(Data data) {
    int count = 0;
    //addition
    //addr (add register) stores into register C the result of adding register A and register B.
    if(data.getAfterC() == (data.getBeforeA()  + data.getBeforeB()) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("addr:" + data);
    } else if(data.getAfterC()== (data.getBeforeA() + data.values[2]) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("addi:" + data);
    }

    //multr
    else if(data.getAfterC() == (data.getBeforeA() * data.getBeforeB()) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("multr:" + data);
    } else if(data.getAfterC() == (data.getBeforeA() * data.values[2]) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("multi:" + data);
    }

    //bitwise AND
    else if(data.getAfterC() == (data.getBeforeA() & data.getBeforeB()) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("banr:" + data);
    } else if(data.getAfterC() == (data.getBeforeA() & data.values[2]) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("bani:" + data);
    }

    //bitwise borr
    else if(data.getAfterC() == (data.getBeforeA() | data.getBeforeB()) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("borr:" + data);
    } else if(data.getAfterC() == (data.getBeforeA() | data.values[2]) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("bori:" + data);
    }

    //set
    else if(data.getAfterC() == (data.getBeforeA()) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("setr:" + data);
    } else if(data.getAfterC() == (data.values[1]) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("seti:" + data);
    }

    //greater than
    else if(data.getAfterC() == (data.getBeforeA()) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("setr:" + data);
    } else if(data.getAfterC() == (data.values[1]) && data.assertOtherRegistersSame()) {
      count++;
      System.out.println("seti:" + data);
    }






    return count;
  }


  public class Data {
    @Override
    public String toString() {
      return "Data{" +
              "beforeReg=" + Arrays.toString(beforeReg) +
              ", values=" + Arrays.toString(values) +
              ", afterReg=" + Arrays.toString(afterReg) +
              '}';
    }

    public int[] beforeReg; // 0, 1, 2, 3 -> 4 registers
    public int[] values;  //opcode, A: input, B: input, B:output
    public int[] afterReg; // 0, 1, 2, 3 -> 4 registers

    public Data (int a, int b, int c, int d) {
      beforeReg = new int[]{a,b,c,d};
    }

    int getBeforeA(){
      return this.beforeReg[this.values[1]];
    }

    int getBeforeB(){
      return this.beforeReg[this.values[2]];
    }

    int getBeforeBValue(){
      return this.beforeReg[this.values[2]];
    }

    int getAfterC(){
      return this.afterReg[this.values[3]];
    }

    boolean assertOtherRegistersSame() {
      int register = this.values[3];
      for(int i = 0; i < 4; i++) {
        if(i != register) {
          if( beforeReg[i] != afterReg[i] )
            return false;
        }
      }
      return true;
    }

    boolean assertSameValue(int register) {
      return beforeReg[register] == afterReg[register];
    }
  }
}
