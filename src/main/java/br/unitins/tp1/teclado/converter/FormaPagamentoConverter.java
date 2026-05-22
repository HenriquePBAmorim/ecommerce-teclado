package br.unitins.tp1.teclado.converter;

import br.unitins.tp1.teclado.model.FormaPagamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FormaPagamentoConverter implements AttributeConverter<FormaPagamento, Long> {

    @Override
    public Long convertToDatabaseColumn(FormaPagamento formaPagamento) {
        return formaPagamento == null ? null : formaPagamento.getId();
    }

    @Override
    public FormaPagamento convertToEntityAttribute(Long id) {
        return FormaPagamento.valueOf(id);
    }
}
