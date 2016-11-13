package net.danielmaly.mdw.hw4.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements Converter {

    protected Server() throws RemoteException {

    }

    private static Double GBP_TO_USD = 1.2602;
    private static Double EUR_TO_USD = 1.08565;

    @Override
    public Double convert(String from, String to, Double amount) {
        Double amountUSD;

        if(from.equals("USD")) {
            amountUSD = amount;
        }
        else if(from.equals("GBP")) {
            amountUSD = GBP_TO_USD * amount;
        }
        else if(from.equals("EUR")) {
            amountUSD = EUR_TO_USD * amount;
        }
        else {
            throw new IllegalArgumentException("Uknown currency code: " + from);
        }

        if(to.equals("USD")) {
            return amountUSD;
        }
        else if(to.equals("GBP")) {
            return amountUSD / GBP_TO_USD;
        }
        else if(to.equals("EUR")) {
            return amountUSD / EUR_TO_USD;
        }
        else {
            throw new IllegalArgumentException("Unknown currency code: " + to);
        }
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);

            Server server = new Server();
            Naming.rebind("//0.0.0.0/convert", server);

            System.out.println("Server started...");

        } catch (Exception e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }

}
