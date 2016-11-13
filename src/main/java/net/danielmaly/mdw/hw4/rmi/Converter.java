package net.danielmaly.mdw.hw4.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Converter extends Remote {
    Double convert(String from, String to, Double amount) throws RemoteException;
}
