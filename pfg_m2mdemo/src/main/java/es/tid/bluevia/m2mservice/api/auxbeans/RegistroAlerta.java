package es.tid.bluevia.m2mservice.api.auxbeans;

public class RegistroAlerta {

    private String deviceId;
    private String fechaRegistro;
    private String parametro;
    private String valor;
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public String getParametro() {
        return parametro;
    }
    public void setParametro(String parametro) {
        this.parametro = parametro;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
}
