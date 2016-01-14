package com.bizu.question.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fabricio on 1/11/16.
 */
public class Item implements Serializable {

    @SerializedName("ID_QUESTAO")
    private Integer idQuestao;

    @SerializedName("ID_ITEM")
    private Integer idItem;

    @SerializedName("DESCRICAO")
    private String descricao;

    @SerializedName("SITUACAO_ITEM")
    private Integer situacaoItem;

    @SerializedName("NOME_IMAGEM_ITEM")
    private String nomeImagemItem;

    @SerializedName("NOME_IMAGEM_ITEM_SISTEMA")
    private String nomeImagemItemSistema;

    @SerializedName("LETRA_ITEM")
    private String letraItem;

    public String getNomeImagemItem() {
        return nomeImagemItem;
    }

    public void setNomeImagemItem(String nomeImagemItem) {
        this.nomeImagemItem = nomeImagemItem;
    }

    public String getNomeImagemItemSistema() {
        return nomeImagemItemSistema;
    }

    public void setNomeImagemItemSistema(String nomeImagemItemSistema) {
        this.nomeImagemItemSistema = nomeImagemItemSistema;
    }

    public String getLetraItem() {
        return letraItem;
    }

    public void setLetraItem(String letraItem) {
        this.letraItem = letraItem;
    }

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getSituacaoItem() {
        return situacaoItem;
    }

    public void setSituacaoItem(Integer situacaoItem) {
        this.situacaoItem = situacaoItem;
    }



    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idItem == null) ? 0 : idItem.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (idItem == null) {
            if (other.idItem != null)
                return false;
        } else if (!idItem.equals(other.idItem))
            return false;
        return true;
    }
}
