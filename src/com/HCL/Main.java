package com.HCL;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("\n///////////////////////////////////////");
        System.out.println("///     Java Calculator Program     ///");
        System.out.println("///////////////////////////////////////\n");
        System.out.println("Note: Remember to place a space between each number and operator");
        System.out.println("Example: 2 + 13 * 4.2");


        Scanner sc = new Scanner(System.in);
        boolean more = true;

        while (more){
            System.out.println("\nPlease enter Equation (input 'e' to exit): ");
            String input = sc.nextLine();
            if (input.equals("e")){
                break;
            }
            else{
                String[] allInputs = input.split(" ");
                String[] output = infixToPostfix(allInputs);
                System.out.println(getPostfixCalculation(output));
            }
        }

        /*
        String input = sc.nextLine();

        String[] allInputs = input.split(" ");

        for (int i = 0; i < allInputs.length; i++){
            System.out.print(allInputs[i] + " ");
        }

        System.out.println();

        String[] output = infixToPostfix(allInputs);

        for (int i = 0; i < output.length; i++){
            System.out.print(output[i] + " ");
        }

        System.out.println();

        System.out.println(getPostfixCalculation(output));
        */
    }


    public static String[] infixToPostfix(String[] input) {

        Stack<String> operatorStack = new Stack<>();

        int lengthOfOutput = 0;
        for (int i = 0; i < input.length; i++){
            if (!input[i].equals("(") && !input[i].equals(")"))
                lengthOfOutput++;
        }

        String[] output = new String[lengthOfOutput];
        int j = 0;

        for (int i = 0; i < input.length; i++){
            if (getPrecedence(input[i]) > 0) {
                while(operatorStack.isEmpty()==false && getPrecedence(operatorStack.peek()) >= getPrecedence(input[i])){
                    output[j] = operatorStack.pop();
                    j++;
                }
                operatorStack.push(input[i]);
            }

            else if (input[i].equals(")")){
                String s = operatorStack.pop();
                while (!s.equals("(")){
                    output[j] = s;
                    j++;
                    s = operatorStack.pop();
                }
            }

            else if (input[i].equals("(")){
                operatorStack.push(input[i]);
            }

            else {
                output[j] = input[i];
                j++;
            }
        }

        while(operatorStack.isEmpty()==false){
            output[j] = operatorStack.pop();
            j++;
        }


        return output;
    }

    public static int getPrecedence(String s){
        if (s.equals("^"))
            return 3;
        else if (s.equals("*") || s.equals("/"))
            return 2;
        else if (s.equals("+") || s.equals("-"))
            return 1;
        else
            return 0;
    }

    public static double getPostfixCalculation(String[] input){

        Stack<Double> operandStack = new Stack<Double>();

        for (int i = 0; i < input.length; i++){
            if (getPrecedence(input[i]) == 0)
                operandStack.push(Double.parseDouble(input[i]));
            else {
                double rightOperand =  operandStack.pop();
                double leftOperand = operandStack.pop();
                String Operator = input[i];
                double result = 0;

                switch (Operator){
                    case "+" :
                        result = leftOperand + rightOperand;
                        break;
                    case "-" :
                        result = leftOperand - rightOperand;
                        break;
                    case "*" :
                        result = leftOperand * rightOperand;
                        break;
                    case "/" :
                        result = leftOperand / rightOperand;
                        break;
                    case "^" :
                        result = Math.pow(leftOperand, rightOperand);
                        break;
                }
                operandStack.push(result);
            }
        }
        return operandStack.pop();
    }

}
