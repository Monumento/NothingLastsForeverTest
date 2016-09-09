/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAVARMI;

import Welt.WeltElement;
import java.rmi.Remote;
import java.rmi.RemoteException;

import lebewesen.Lebewesen;
import resourcen.pflanzen.Pflanze;

/**
 *
 * @author Jonas
 */
public interface WeltInterface extends Remote  {
    
     public double getMsPerUpdate()throws RemoteException;

    public Lebewesen[][] getLeichen()throws RemoteException;

    public void setLeichen(Lebewesen[][] leichen)throws RemoteException;

    public int getCountLimit()throws RemoteException;

    public void setCountLimit(int countLimit)throws RemoteException;

    public void setMsPerUpdate(double d)throws RemoteException;


    

    public WeltElement[][] getWeltElemente()throws RemoteException;

    public void setWeltElemente(WeltElement[][] weltElemente)throws RemoteException;

    public Lebewesen[][] getEinheiten()throws RemoteException;

    public void setEinheiten(Lebewesen[][] einheiten)throws RemoteException;

    public Pflanze[][] getPflanzen()throws RemoteException;

    public void setPflanzen(Pflanze[][] pflanzen)throws RemoteException;

    public int getZeit()throws RemoteException;

    public void setZeit(int zeit)throws RemoteException;

    public void spawn(Lebewesen lebewesen)throws RemoteException;

    public boolean addLeiche(Lebewesen lebewesen)throws RemoteException;
}
