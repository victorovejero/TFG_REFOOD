package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.domain.Voluntario;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import com.refood.trazabilidad.service.dto.VoluntarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Voluntario} and its DTO {@link VoluntarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface VoluntarioMapper extends EntityMapper<VoluntarioDTO, Voluntario> {
    @Mapping(target = "nucleo", source = "nucleo", qualifiedByName = "nucleoId")
    VoluntarioDTO toDto(Voluntario s);

    @Named("nucleoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NucleoDTO toDtoNucleoId(Nucleo nucleo);
}
