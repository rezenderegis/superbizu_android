package com.bizu.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bizu.android.database.SaveListener;
import com.bizu.question.QuestionRepository;
import com.bizu.question.item.Item;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain Class to represent a question. Contains all information to show a question and to make the
 * version control.
 * Created by andre.lmello on 11/25/15.
 */
public class Question implements Parcelable {

    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };


    @SerializedName("ID_QUESTAO")
    private Long mId;

    @SerializedName("DESCRICAO_QUESTAO")
    private String mDescription;

    @SerializedName("ANO_QUESTAO")
    private Integer mQuestionYear;

    @SerializedName("NUMERO_QUESTAO")
    private Integer mQuestionNumber;

    @SerializedName("COMANDO_QUESTAO")
    private String comandoQuestao;

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

    private List<Item> mItems;

    public Question (final Parcel in) {
        mDescription = in.readString();
        comandoQuestao = in.readString();
        mItems = new ArrayList<>();
        in.readList(mItems, Item.class.getClassLoader());
    }

    /**
     *
     * @param id
     * @param description
     * @param items
     */
    public Question(@Nullable final Long id, @NonNull final String description, @NonNull final List<Item> items) {
        mId = id;
        mDescription = description;
        mItems = items;
    }

    /**
     *
     * @param name
     * @param items
     */
    public Question(final String name, final List<Item> items) {
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

    public String getComandoQuestao() {
        return comandoQuestao;
    }

    public void setComandoQuestao(final String comandoQuestao) {
        this.comandoQuestao = comandoQuestao;
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

    public List<Item> getItems () {
        return mItems;
    }

    public void setItems(final List<Item> items) {
        mItems = items;
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
     * @param repository
     * @param <T>
     * @return
     */
    public <T> T save(final SaveListener<Question> listener, final QuestionRepository repository) {
        return repository.save(this, listener);
    }

    public Long getId() {
        return mId;
    }

    public void setId(final Long id) {
        this.mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDescription);
        dest.writeString(comandoQuestao);
        dest.writeList(getItems());
    }
}
