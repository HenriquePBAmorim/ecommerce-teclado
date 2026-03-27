package br.unitins.tp1.teclado.converter;

import br.unitins.tp1.teclado.model.Formato;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FormatoConverter implements AttributeConverter<Formato, Long> {
    @Override
    public Long convertToDatabaseColumn(Formato formato) {
        return formato == null ? null : formato.getId();
    }

    @Override
    public Formato convertToEntityAttribute(Long id) {
        return Formato.valueOf(id);
    }
}