/**

 */
import java.rmi.*;

public interface Hello extends Remote {
    float getsoma(float f, float g) throws RemoteException;
    float getsub(float f, float g) throws RemoteException;
    float getdiv(float f, float g) throws RemoteException;
    float getmult(float f, float g) throws RemoteException;
    float getpot(float f, float g) throws RemoteException;
    float getraiz(float f) throws RemoteException;
    float getporcent(float f, float g) throws RemoteException;
}
