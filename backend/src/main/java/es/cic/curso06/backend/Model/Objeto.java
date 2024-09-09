package es.cic.curso06.backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String descripcion;
    private float masa;
    private float densidad;
    private float distancia;
    private float magnitudAparente;
    private float corrimientoAlRojo;
    private float temperatura;
    private float edad;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    // Constructor vacío
    public Objeto() {}

    // Constructor genérico
    public Objeto(String nombre, String descripcion, float masa, float densidad, float distancia, float magnitudAparente, float corrimientoAlRojo, float temperatura, float edad, Tipo tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.masa = masa;
        this.densidad = densidad;
        this.distancia = distancia;
        this.magnitudAparente = magnitudAparente;
        this.corrimientoAlRojo = corrimientoAlRojo;
        this.temperatura = temperatura;
        this.edad = edad;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getMasa() {
        return masa;
    }

    public void setMasa(float masa) {
        this.masa = masa;
    }

    public float getDensidad() {
        return densidad;
    }

    public void setDensidad(float densidad) {
        this.densidad = densidad;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public float getMagnitudAparente() {
        return magnitudAparente;
    }

    public void setMagnitudAparente(float magnitudAparente) {
        this.magnitudAparente = magnitudAparente;
    }

    public float getCorrimientoAlRojo() {
        return corrimientoAlRojo;
    }

    public void setCorrimientoAlRojo(float corrimientoAlRojo) {
        this.corrimientoAlRojo = corrimientoAlRojo;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getEdad() {
        return edad;
    }

    public void setEdad(float edad) {
        this.edad = edad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}