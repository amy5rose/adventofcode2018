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
            System.out.println("Sample is wrong!");
            return;
        }
        System.out.println("-------------------------------------------------------------");

        startTime = System.currentTimeMillis();

        inputList = read.readFile(getRealInputFile());
        System.out.println("final inputList:" + inputList.size());

        String finalAnswer = findAnswer(inputList);
        System.out.println("final answer:" + finalAnswer);

        endTime = System.currentTimeMillis();
        System.out.println("\nfinal program running time:" + (endTime-startTime));
    }

    public abstract String getSampleInputString();
    public abstract String getSampleAnswer();

    public abstract String getRealInputFile();

    public abstract String findAnswer(List<String> list);
}