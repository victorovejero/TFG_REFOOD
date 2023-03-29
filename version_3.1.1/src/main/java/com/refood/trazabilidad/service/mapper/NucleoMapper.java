package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nucleo} and its DTO {@link NucleoDTO}.
 */
@Mapper(componentModel = "spring")
public interface NucleoMapper extends EntityMapper<NucleoDTO, Nucleo> {}
