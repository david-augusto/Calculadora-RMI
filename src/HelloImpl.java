/**

 */
import java.rmi.*;
import java.rmi.server.*;
import java.lang.Math;

public abstract class HelloImpl extends UnicastRemoteObject implements Hello {
    public HelloImpl() throws RemoteException{
        super();
    }
    public float getsoma(float n1, float n2){
        return n1+n2;
    }
    public float getsub(float n1, float n2){
        return n1-n2;
    }
    public float getdiv (float n1, float n2){
        return n1/n2;
    }
    public float getmult(float n1, float n2){
        return n1*n2;
    }
    public float getpot(float n1, float n2){
        return (float) Math.pow(n1, n2);
    }
    public float getraiz(float n1){
    return (float) Math.sqrt(n1);
	}
    public float getporcent(float n1, float n2){
    return (n1*n2)/100;
	}
}
