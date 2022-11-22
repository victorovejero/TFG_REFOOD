package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.domain.Socio;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import com.refood.trazabilidad.service.dto.SocioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Socio} and its DTO {@link SocioDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocioMapper extends EntityMapper<SocioDTO, Socio> {
    @Mapping(target = "nucleo", source = "nucleo", qualifiedByName = "nucleoId")
    SocioDTO toDto(Socio s);

    @Named("nucleoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NucleoDTO toDtoNucleoId(Nucleo nucleo);
}
