package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Tupper;
import com.refood.trazabilidad.service.dto.TupperDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tupper} and its DTO {@link TupperDTO}.
 */
@Mapper(componentModel = "spring")
public interface TupperMapper extends EntityMapper<TupperDTO, Tupper> {}
