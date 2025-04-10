package br.com.foxinline.controlebens.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.foxinline.controlebens.model.Bem;

public class DepreciacaoService {

    public BigDecimal calcularDepreciacaoAnual(Bem bem) {
        if (bem.getPrecoCompra() == null || bem.getValorResidual() == null || bem.getVidaUtil() == null || bem.getVidaUtil() == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal diferenca = bem.getPrecoCompra().subtract(bem.getValorResidual());
        return diferenca.divide(BigDecimal.valueOf(bem.getVidaUtil()), 2, RoundingMode.HALF_UP);
    }
}
