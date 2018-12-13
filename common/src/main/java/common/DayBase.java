package common;

import java.io.IOException;
import java.util.List;

public abstract class DayBase {

	public void run() throws IOException {
		long startTime = System.currentTimeMillis();
		ReadInput read = new ReadInput();

		List<String> inputList = read.readInput(getSampleInputString());
		System.out.println("sample inputList:" + inputList.size());

        String answer = findAnswer(inputList);
		System.out.println("sample answer:" + answer);

        long endTime = System.currentTimeMillis();
        System.out.println("\nsample program running time:" + (endTime-startTime));

		if(!getSampleAnswer().equals(answer)) {
            System.out.println("Sample is wrong! Expected:" + getSampleAnswer());
            return;
        }
        System.out.println("-----------------------------------------------------------------------------");

        startTime = System.currentTimeMillis();

        inputList = read.readInput(getRealInputString());
        System.out.println("final inputList:" + inputList.size());

        String finalAnswer = findAnswer(inputList, false);
        System.out.println("final answer:" + finalAnswer);

        endTime = System.currentTimeMillis();
        System.out.println("\nfinal program running time:" + (endTime-startTime));
    }

    public abstract String getSampleInputString();
    public abstract String getSampleAnswer();

    public abstract String getRealInputString();

    public String findAnswer(List<String> list) {
        return findAnswer(list, true);
    }

    public abstract String findAnswer(List<String> list, boolean isSample);
}
