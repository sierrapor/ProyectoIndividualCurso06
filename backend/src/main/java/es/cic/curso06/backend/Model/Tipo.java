package es.cic.curso06.backend.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TIPO")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "LUMINOSO")
    private boolean luminoso;

    @Column(name = "ORBITANTE")
    private boolean orbitante;

    @OneToMany(mappedBy = "tipo",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Objeto> objetos;

    // Constructor vacío
    public Tipo() {
        this.objetos = new ArrayList<>();
        }

    // Constructor genérico
    public Tipo(String nombre, String descripcion, boolean luminoso, boolean orbitante) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.luminoso = luminoso;
        this.orbitante = orbitante;
        this.objetos = new ArrayList<>();
    }

    public Tipo(String nombre, String descripcion, boolean luminoso, boolean orbitante, ArrayList<Objeto> objetos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.luminoso = luminoso;
        this.orbitante = orbitante;
        this.objetos = objetos;
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