package com.bizu.question;

import com.bizu.network.UpdateListener;
import com.bizu.question.service.QuestionService;
import com.google.gson.annotations.SerializedName;

/**
 * Domain Class to represent a question. Contains all information to show a question and to make the
 * version control.
 * Created by andre.lmello on 11/25/15.
 */
public class Question {

    @SerializedName("ID_QUESTAO")
    private Long mId;

    @SerializedName("DESCRICAO_QUESTAO")
    private String mName;

    @SerializedName("ANO_QUESTAO")
    private Integer ano_questao;

    @SerializedName("NUMERO_QUESTAO")
    private Integer numero_questao;

    @SerializedName("DESCRICAO_QUESTAO")
    private String descricao_questao;

    @SerializedName("COMANDO_QUESTAO")
    private String comando_questao;

    @SerializedName("PROVA")
    private Integer prova;

    @SerializedName("SITUACAO_QUESTAO")
    private Integer situacao_questao;

    @SerializedName("IMAGEM_QUESTAO")
    private String imagem_questao;

    @SerializedName("COMENTARIO_QUESTAO")
    private String comentario_questao;

    @SerializedName("LETRA_ITEM_CORRETO")
    private String letra_item_correto;

    @SerializedName("DIA_PROVA")
    private Integer dia_prova;

    @SerializedName("APLICACAO")
    private Integer aplicacao;

    public String getLetra_item_correto() {
        return letra_item_correto;
    }

    public void setLetra_item_correto(String letra_item_correto) {
        this.letra_item_correto = letra_item_correto;
    }

    public Integer getDia_prova() {
        return dia_prova;
    }

    public void setDia_prova(Integer dia_prova) {
        this.dia_prova = dia_prova;
    }

    public Integer getAplicacao() {
        return aplicacao;
    }

    public void setAplicacao(Integer aplicacao) {
        this.aplicacao = aplicacao;
    }

    public String getComentario_questao() {
        return comentario_questao;
    }

    public void setComentario_questao(String comentario_questao) {
        this.comentario_questao = comentario_questao;
    }

    public Integer getAno_questao() {
        return ano_questao;
    }

    public void setAno_questao(Integer ano_questao) {
        this.ano_questao = ano_questao;
    }

    public Integer getNumero_questao() {
        return numero_questao;
    }

    public void setNumero_questao(Integer numero_questao) {
        this.numero_questao = numero_questao;
    }

    public String getDescricao_questao() {
        return descricao_questao;
    }

    public void setDescricao_questao(String descricao_questao) {
        this.descricao_questao = descricao_questao;
    }

    public String getComando_questao() {
        return comando_questao;
    }

    public void setComando_questao(String comando_questao) {
        this.comando_questao = comando_questao;
    }

    public Integer getProva() {
        return prova;
    }

    public void setProva(Integer prova) {
        this.prova = prova;
    }

    public Integer getSituacao_questao() {
        return situacao_questao;
    }

    public void setSituacao_questao(Integer situacao_questao) {
        this.situacao_questao = situacao_questao;
    }

    public String getImagem_questao() {
        return imagem_questao;
    }

    public void setImagem_questao(String imagem_questao) {
        this.imagem_questao = imagem_questao;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Question() {
    }

    public Question(final Long id) {
        mId = id;
    }

    /**
     * Async
     * @param <T>
     * @return any return. created for network frameworks.
     */
    public <T> T update(final UpdateListener<Question> listener, final QuestionService service) {
        if (mId != null) {
            return service.update(this, listener);
        } else {
            throw new IllegalStateException("Cannot update questions not loaded to the data base yet.");
        }
    }

    /**
     * Async
     * @param repository
     * @param <T>
     * @return
     */
    public <T> T save(final SaveListener listener, final QuestionRepository repository) {
        return repository.save(this, listener);
    }

    public Long getId() {
        return mId;
    }

    public void setId(final Long id) {
        this.mId = id;
    }
}
