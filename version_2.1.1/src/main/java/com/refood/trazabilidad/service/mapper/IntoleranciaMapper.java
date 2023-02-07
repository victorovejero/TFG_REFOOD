package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Intolerancia;
import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Intolerancia} and its DTO {@link IntoleranciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntoleranciaMapper extends EntityMapper<IntoleranciaDTO, Intolerancia> {}
