package com.bizu.entity;

import java.util.Date;

/**
 * Created by fabricio on 1/14/16.
 */
public class LogProcessamento {

    private Integer idLogProcessamento;
    private Date dataInicioProcessamento;
    private Date dataFimProcessamento;
    private Integer qtdAtualizados;
    private Integer qtdInseridos;
    private String tipoTabelaAtualizada;

    public Integer getIdLogProcessamento() {
        return idLogProcessamento;
    }

    public void setIdLogProcessamento(Integer idLogProcessamento) {
        this.idLogProcessamento = idLogProcessamento;
    }

    public Date getDataInicioProcessamento() {
        return dataInicioProcessamento;
    }

    public void setDataInicioProcessamento(Date dataInicioProcessamento) {
        this.dataInicioProcessamento = dataInicioProcessamento;
    }

    public Date getDataFimProcessamento() {
        return dataFimProcessamento;
    }

    public void setDataFimProcessamento(Date dataFimProcessamento) {
        this.dataFimProcessamento = dataFimProcessamento;
    }

    public Integer getQtdAtualizados() {
        return qtdAtualizados;
    }

    public void setQtdAtualizados(Integer qtdAtualizados) {
        this.qtdAtualizados = qtdAtualizados;
    }

    public Integer getQtdInseridos() {
        return qtdInseridos;
    }

    public void setQtdInseridos(Integer qtdInseridos) {
        this.qtdInseridos = qtdInseridos;
    }

    public String getTipoTabelaAtualizada() {
        return tipoTabelaAtualizada;
    }

    public void setTipoTabelaAtualizada(String tipoTabelaAtualizada) {
        this.tipoTabelaAtualizada = tipoTabelaAtualizada;
    }
}
