package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.TipoDeAlimento;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDeAlimento} and its DTO {@link TipoDeAlimentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoDeAlimentoMapper extends EntityMapper<TipoDeAlimentoDTO, TipoDeAlimento> {}
