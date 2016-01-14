package com.bizu.question;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fabricio on 1/11/16.
 */
public class Item {

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


}
