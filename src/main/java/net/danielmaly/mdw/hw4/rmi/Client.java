package net.danielmaly.mdw.hw4.rmi;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception{
        Converter client = (Converter)Naming.lookup("//localhost/convert");

        Scanner in = new Scanner(System.in);

        System.out.print("Enter from currency [USD, GBP, EUR]: ");
        String from = in.nextLine();

        System.out.print("Enter to currency [USD, GBP, EUR]: ");
        String to = in.nextLine();

        System.out.print("Enter amount [double]: ");
        Double amount = in.nextDouble();

        Double converted = client.convert(from, to, amount);

        System.out.format("%.2f %s is %.2f %s. Goodbye!\n", amount, from, converted, to);
    }

}