package es.cic.curso06.backend.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String descripcion;
    private boolean luminoso;
    private boolean orbitante;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Objeto> objetos = new ArrayList<>();

    // Constructor vacío
    public Tipo() {}

    // Constructor genérico
    public Tipo(String nombre, String descripcion, boolean luminoso, boolean orbitante) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.luminoso = luminoso;
        this.orbitante = orbitante;
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

    public boolean isLuminoso() {
        return luminoso;
    }

    public void setLuminoso(boolean luminoso) {
        this.luminoso = luminoso;
    }

    public boolean isOrbitante() {
        return orbitante;
    }

    public void setOrbitante(boolean orbitante) {
        this.orbitante = orbitante;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = objetos;
    }

    public void addObjeto(Objeto objeto) {
        objetos.add(objeto);
        objeto.setTipo(this);
    }

    public void removeObjeto(Objeto objeto) {
        objetos.remove(objeto);
        objeto.setTipo(null);
    }
}