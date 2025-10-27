package com.sgaap.servicos.interfaces;

import java.util.List;

/**
 * Interface genérica para serviços no sistema SGAAP.
 * Define contratos para operações de negócio básicas (CRUD e outras específicas).
 * Responsável apenas por especificar métodos, sem implementação.
 * Cada serviço concreto implementará isso para uma entidade específica.
 *
 * @param <T> Tipo da entidade (ex.: Cliente, Fatura)
 * @param <ID> Tipo da chave primária (ex.: Long)
 */
public interface ServicoInterface<T, ID> {

    /**
     * Cadastra uma nova entidade no sistema.
     * @param entity Entidade a ser cadastrada
     * @return Entidade cadastrada
     */
    T cadastrar(T entity);

    /**
     * Atualiza uma entidade existente.
     * @param entity Entidade a ser atualizada
     * @return Entidade atualizada
     */
    T atualizar(T entity);

    /**
     * Busca uma entidade pelo ID.
     * @param id ID da entidade
     * @return Entidade encontrada, ou null se não existir
     */
    T buscarPorId(ID id);

    /**
     * Busca todas as entidades.
     * @return Lista de todas as entidades
     */
    List<T> buscarTodos();
}