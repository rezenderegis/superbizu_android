package com.bizu.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fabricio on 1/16/16.
 */
public class Topic {

    @SerializedName("ID_ASSUNTO")
    private Integer idAssunto;

    @SerializedName("DESCRICAO_ASSUNTO")
    private String descricaoAssunto;

    @SerializedName("ID_MATERIA")
    private Integer idMateria;

    @SerializedName("ID_ASSUNTO_PAI")
    private Integer idAssuntoPai;

    public Integer getIdAssunto() {
        return idAssunto;
    }

    public void setIdAssunto(Integer idAssunto) {
        this.idAssunto = idAssunto;
    }

    public String getDescricaoAssunto() {
        return descricaoAssunto;
    }

    public void setDescricaoAssunto(String descricaoAssunto) {
        this.descricaoAssunto = descricaoAssunto;
    }

    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public Integer getIdAssuntoPai() {
        return idAssuntoPai;
    }

    public void setIdAssuntoPai(Integer idAssuntoPai) {
        this.idAssuntoPai = idAssuntoPai;
    }
}
