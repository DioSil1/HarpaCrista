package com.harpacrista.entity;

import com.harpacrista.singleton.SingletonAdapter;

import java.util.List;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class Hino {

    private long id;
    private String nome;
    private String hino;
    private String autor;
    private String favorito;

    public static List<Hino> buscarHinos(){
        return SingletonAdapter.getInstance().getAdapter().findAll(Hino.class);
    }

    public static List<Hino> buscarHinosFavoritos(){
        Hino qHino = new Hino();
        qHino.setFavorito(true);
        return SingletonAdapter.getInstance().getAdapter().findAll(qHino);
    }

    public static List<Hino> buscarHinosPorAutor(String autor){
        Hino qHino = new Hino();
        qHino.setAutor(autor);
        return SingletonAdapter.getInstance().getAdapter().findAll(qHino);
    }

    public void save(){
        Hino qHino = new Hino();
        qHino.setId(getId());
        SingletonAdapter.getInstance().getAdapter().update(this, qHino);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHino() {
        return hino;
    }

    public void setHino(String hino) {
        this.hino = hino;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isFavorito() {
        return favorito.equals("1");
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito ? "1" : "0";
    }

    @Override
    public String toString() {
        return getId() + " " + getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hino hino = (Hino) o;

        if (id != hino.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}