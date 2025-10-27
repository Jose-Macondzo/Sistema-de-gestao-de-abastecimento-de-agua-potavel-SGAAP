package com.sgaap.repositorios.interfaces;

import java.util.List;

/**
 * Interface genérica para repositórios no sistema SGAAP.
 * Define contratos para operações CRUD básicas em entidades.
 * Responsável apenas por especificar métodos, sem implementação.
 * Cada repositório concreto implementará isso para uma entidade específica.
 *
 * @param <T> Tipo da entidade (ex.: Cliente, Fatura)
 * @param <ID> Tipo da chave primária (ex.: Long)
 */
public interface RepositorioInterface<T, ID> {

    /**
     * Salva uma nova entidade ou atualiza uma existente.
     * @param entity Entidade a ser salva
     * @return Entidade salva
     */
    T save(T entity);

    /**
     * Busca uma entidade pelo ID.
     * @param id ID da entidade
     * @return Entidade encontrada, ou null se não existir
     */
    T findById(ID id);

    /**
     * Busca todas as entidades.
     * @return Lista de todas as entidades
     */
    List<T> findAll();

    /**
     * Atualiza uma entidade existente.
     * @param entity Entidade a ser atualizada
     * @return Entidade atualizada
     */
    T update(T entity);

    /**
     * Deleta uma entidade pelo ID.
     * @param id ID da entidade a deletar
     */
    void delete(ID id);
}
