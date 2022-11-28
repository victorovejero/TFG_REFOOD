package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Donante;
import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.service.dto.DonanteDTO;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Donante} and its DTO {@link DonanteDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonanteMapper extends EntityMapper<DonanteDTO, Donante> {
    @Mapping(target = "nucleo", source = "nucleo", qualifiedByName = "nucleoId")
    DonanteDTO toDto(Donante s);

    @Named("nucleoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NucleoDTO toDtoNucleoId(Nucleo nucleo);
}
