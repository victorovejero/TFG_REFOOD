package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Socio;
import com.refood.trazabilidad.service.dto.SocioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Socio} and its DTO {@link SocioDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocioMapper extends EntityMapper<SocioDTO, Socio> {}
