package br.unitins.tp1.teclado.converter;

import br.unitins.tp1.teclado.model.PerfilKeycap;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PerfilKeycapConverter implements AttributeConverter<PerfilKeycap, Long> {

    @Override
    public Long convertToDatabaseColumn(PerfilKeycap perfilKeycap) {
        return perfilKeycap == null ? null : perfilKeycap.getId();
    }

    @Override
    public PerfilKeycap convertToEntityAttribute(Long id) {
        return PerfilKeycap.valueOf(id);
    }
}
