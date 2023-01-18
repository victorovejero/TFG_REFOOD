package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Intol;
import com.refood.trazabilidad.service.dto.IntolDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Intol} and its DTO {@link IntolDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntolMapper extends EntityMapper<IntolDTO, Intol> {}
