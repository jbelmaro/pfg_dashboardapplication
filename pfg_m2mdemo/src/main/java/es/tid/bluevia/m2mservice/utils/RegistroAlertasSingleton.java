package es.tid.bluevia.m2mservice.utils;

import java.util.ArrayList;

import es.tid.bluevia.m2mservice.api.auxbeans.RegistroAlerta;

public class RegistroAlertasSingleton {
    private static RegistroAlertasSingleton INSTANCE = null;
    private ArrayList<RegistroAlerta> registro = new ArrayList<RegistroAlerta>();

    // Private constructor suppresses
    private RegistroAlertasSingleton() {
    }

    // creador sincronizado para protegerse de posibles problemas multi-hilo
    // otra prueba para evitar instanciación múltiple
    private synchronized static void createInstance() {
        if (RegistroAlertasSingleton.INSTANCE == null) {
            RegistroAlertasSingleton.INSTANCE = new RegistroAlertasSingleton();
        }
    }

    public static RegistroAlertasSingleton getInstance() {
        RegistroAlertasSingleton.createInstance();
        return RegistroAlertasSingleton.INSTANCE;
    }

    public ArrayList<RegistroAlerta> getRegistro() {
        return registro;
    }

    public void setRegistro(ArrayList<RegistroAlerta> registro) {
        this.registro = registro;
    }
}