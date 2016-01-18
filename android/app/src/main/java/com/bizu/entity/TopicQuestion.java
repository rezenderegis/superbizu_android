package com.bizu.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fabricio on 1/15/16.
 */
public class TopicQuestion {

    @SerializedName("ID_QUESTAO")
    private Integer idQuestao;

    @SerializedName("ID_ASSUNTO")
    private Integer idAssunto;

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public Integer getIdAssunto() {
        return idAssunto;
    }

    public void setIdAssunto(Integer idAssunto) {
        this.idAssunto = idAssunto;
    }
}
