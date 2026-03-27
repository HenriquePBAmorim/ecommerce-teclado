package br.unitins.tp1.teclado.converter;

import br.unitins.tp1.teclado.model.TipoSwitch;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoSwitchConverter implements AttributeConverter<TipoSwitch, Long> {
    @Override
    public Long convertToDatabaseColumn(TipoSwitch tipoSwitch) {
        return tipoSwitch == null ? null : tipoSwitch.getId();
    }

    @Override
    public TipoSwitch convertToEntityAttribute(Long id) {
        return TipoSwitch.valueOf(id);
    }
}