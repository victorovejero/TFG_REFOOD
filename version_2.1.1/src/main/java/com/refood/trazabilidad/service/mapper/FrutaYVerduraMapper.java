package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.FrutaYVerdura;
import com.refood.trazabilidad.service.dto.FrutaYVerduraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FrutaYVerdura} and its DTO {@link FrutaYVerduraDTO}.
 */
@Mapper(componentModel = "spring")
public interface FrutaYVerduraMapper extends EntityMapper<FrutaYVerduraDTO, FrutaYVerdura> {}
