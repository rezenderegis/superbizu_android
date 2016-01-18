package com.bizu.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fabricio on 1/14/16.
 */
public class Matter implements Serializable {

    @SerializedName("ID_MATERIA")
    private Integer idMateria;

    @SerializedName("NOME_MATERIA")
    private String nomeMateria;

    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }
}
