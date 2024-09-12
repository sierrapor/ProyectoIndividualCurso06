package es.cic.curso06.backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ASTRO")
public class Astro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Size(max = 50, min = 1)
    @Column(name = "NOMBRE")
    private String nombre;

    @Size(max = 500, min = 1)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Positive
    @Column(name = "MASA")
    private double masa;

    @Positive
    @Column(name = "DENSIDAD")
    private double densidad;

    @Positive
    @Column(name = "DISTANCIA")
    private double distancia;

    @Column(name = "MAGNITUD_APARENTE")
    private double magnitudAparente;

    @PositiveOrZero
    @Column(name = "CORRIMIENTO_AL_ROJO")
    private double corrimientoAlRojo;

    @Positive
    @Column(name = "TEMPERATURA")
    private double temperatura;

    @Positive
    @Column(name = "EDAD")
    private double edad;

    @ManyToOne
    @JsonIgnoreProperties("astros")
    @JoinColumn(name = "TIPO_ID")
    private Tipo tipo;

    // Constructor vacío
    public Astro() {}

    // Constructor genérico
    public Astro(String nombre, String descripcion, double masa, double densidad, double distancia, double magnitudAparente, double corrimientoAlRojo, double temperatura, double edad, Tipo tipo) {
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

    public double getMasa() {
        return masa;
    }

    public void setMasa(double masa) {
        this.masa = masa;
    }

    public double getDensidad() {
        return densidad;
    }

    public void setDensidad(double densidad) {
        this.densidad = densidad;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getMagnitudAparente() {
        return magnitudAparente;
    }

    public void setMagnitudAparente(double magnitudAparente) {
        this.magnitudAparente = magnitudAparente;
    }

    public double getCorrimientoAlRojo() {
        return corrimientoAlRojo;
    }

    public void setCorrimientoAlRojo(double corrimientoAlRojo) {
        this.corrimientoAlRojo = corrimientoAlRojo;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getEdad() {
        return edad;
    }

    public void setEdad(double edad) {
        this.edad = edad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}