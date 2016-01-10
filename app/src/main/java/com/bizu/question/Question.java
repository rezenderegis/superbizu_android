package com.bizu.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bizu.network.UpdateListener;
import com.bizu.question.item.QuestionItem;
import com.bizu.question.service.QuestionService;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain Class to represent a question. Contains all information to show a question and to make the
 * version control.
 * Created by andre.lmello on 11/25/15.
 */
public class Question {

    @SerializedName("ID_QUESTAO")
    private Long mId;

    @SerializedName("DESCRICAO_QUESTAO")
    private String mDescription;

    @SerializedName("ANO_QUESTAO")
    private Integer mQuestionYear;

    @SerializedName("NUMERO_QUESTAO")
    private Integer mQuestionNumber;

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

    private final List<QuestionItem> mItems;

    /**
     *
     * @param id
     * @param description
     * @param items
     */
    public Question(@Nullable final Long id, @NonNull final String description, @NonNull final List<QuestionItem> items) {
        mId = id;
        mDescription = description;
        mItems = items;
    }

    /**
     *
     * @param name
     * @param items
     */
    public Question(final String name, final List<QuestionItem> items) {
        this(null, name, items);
    }


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

    public Integer getQuestionYear() {
        return mQuestionYear;
    }

    public void setAno_questao(Integer ano_questao) {
        this.mQuestionYear = ano_questao;
    }

    public Integer getQuestionNumber() {
        return mQuestionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.mQuestionNumber = questionNumber;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
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

    public Question() {
        mItems = new ArrayList<>();
    }

    public Question(final Long id) {
        mId = id;
        mItems = new ArrayList<>();
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
