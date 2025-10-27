package com.sgaap.servicos;

import com.sgaap.entidades.Fatura;
import com.sgaap.entidades.LeituraContador;
import com.sgaap.repositorios.FaturaRepositorio;
import com.sgaap.repositorios.LeituraContadorRepositorio;
import com.sgaap.servicos.interfaces.ServicoInterface;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Serviço para gerenciar operações de negócio relacionadas à entidade Fatura no sistema SGAAP.
 * Responsável por gerar faturas, aplicar multas, consultar dívidas e suportar relatórios.
 * Usa repositórios para persistência, mantendo lógica de negócio isolada.
 */
public class FaturaServico implements ServicoInterface<Fatura, Long> {
    private final FaturaRepositorio faturaRepositorio;
    private final LeituraContadorRepositorio leituraContadorRepositorio;
    private static final BigDecimal VALOR_POR_M3 = new BigDecimal("10.00"); // Exemplo: 10 unidades/m³
    private static final BigDecimal MULTA_15_DIAS = new BigDecimal("0.10"); // 10% após 15 dias
    private static final BigDecimal MULTA_20_DIAS = new BigDecimal("0.20"); // 20% total após 20 dias
    private static final BigDecimal MULTA_25_DIAS = new BigDecimal("0.50"); // 50% total após 25 dias

    /**
     * Construtor com injeção de dependências dos repositórios necessários.
     * @param faturaRepositorio Repositório para persistência de Fatura
     * @param leituraContadorRepositorio Repositório para persistência de LeituraContador
     */
    public FaturaServico(FaturaRepositorio faturaRepositorio, LeituraContadorRepositorio leituraContadorRepositorio) {
        this.faturaRepositorio = faturaRepositorio;
        this.leituraContadorRepositorio = leituraContadorRepositorio;
    }

    /**
     * Gera uma nova fatura com base na leitura do contador.
     * Calcula o valor base (consumo em m³ * valor por m³) e define data de vencimento (30 dias).
     * @param leituraId ID da leitura do contador
     * @return Fatura gerada
     */
    public Fatura gerarFatura(Long leituraId) {
        LeituraContador leitura = leituraContadorRepositorio.findById(leituraId);
        if (leitura == null) {
            throw new IllegalArgumentException("Leitura do contador não encontrada");
        }
        BigDecimal consumo = new BigDecimal(leitura.getMetrosCubicos());
        BigDecimal valorBase = consumo.multiply(VALOR_POR_M3);
        Fatura fatura = new Fatura();
        fatura.setLeitura(leitura);
        fatura.setCliente(leitura.getInstalacao().getCliente());
        fatura.setValorBase(valorBase);
        fatura.setDataVencimento(LocalDate.now().plusDays(30));
        fatura.setMultasAplicadas(BigDecimal.ZERO);
        return cadastrar(fatura);
    }

    /**
     * Aplica multas a uma fatura com base no atraso (15, 20 ou 25 dias).
     * @param faturaId ID da fatura
     * @return Fatura com multas aplicadas
     */
    public Fatura aplicarMultas(Long faturaId) {
        Fatura fatura = faturaRepositorio.findById(faturaId);
        if (fatura == null) {
            throw new IllegalArgumentException("Fatura não encontrada");
        }
        if (!fatura.isEmDivida()) {
            return fatura; // Sem multas se não está em dívida
        }
        long diasAtraso = ChronoUnit.DAYS.between(fatura.getDataVencimento(), LocalDate.now());
        BigDecimal multa;
        if (diasAtraso >= 25) {
            multa = fatura.getValorBase().multiply(MULTA_25_DIAS);
        } else if (diasAtraso >= 20) {
            multa = fatura.getValorBase().multiply(MULTA_20_DIAS);
        } else if (diasAtraso >= 15) {
            multa = fatura.getValorBase().multiply(MULTA_15_DIAS);
        } else {
            multa = BigDecimal.ZERO;
        }
        fatura.setMultasAplicadas(multa);
        return atualizar(fatura);
    }

    /**
     * Cadastra uma nova fatura.
     * @param fatura Fatura a ser cadastrada
     * @return Fatura cadastrada
     */
    @Override
    public Fatura cadastrar(Fatura fatura) {
        if (fatura == null || !fatura.validarFatura()) {
            throw new IllegalArgumentException("Fatura inválida ou dados incompletos");
        }
        return faturaRepositorio.save(fatura);
    }

    /**
     * Atualiza uma fatura existente.
     * @param fatura Fatura a ser atualizada
     * @return Fatura atualizada
     */
    @Override
    public Fatura atualizar(Fatura fatura) {
        if (fatura == null || fatura.getId() == null) {
            throw new IllegalArgumentException("Fatura inválida ou ID nulo");
        }
        return faturaRepositorio.update(fatura);
    }

    /**
     * Busca uma fatura pelo ID.
     * @param id ID da fatura
     * @return Fatura encontrada, ou null se não existir
     */
    @Override
    public Fatura buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return faturaRepositorio.findById(id);
    }

    /**
     * Busca todas as faturas.
     * @return Lista de todas as faturas
     */
    @Override
    public List<Fatura> buscarTodos() {
        return faturaRepositorio.findAll();
    }

    /**
     * Busca todas as faturas em dívida.
     * @return Lista de faturas em dívida
     */
    public List<Fatura> buscarFaturasEmDivida() {
        return faturaRepositorio.findFaturasEmDivida();
    }
}