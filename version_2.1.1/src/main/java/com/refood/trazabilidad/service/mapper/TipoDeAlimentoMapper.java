package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Intolerancia;
import com.refood.trazabilidad.domain.TipoDeAlimento;
import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDeAlimento} and its DTO {@link TipoDeAlimentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoDeAlimentoMapper extends EntityMapper<TipoDeAlimentoDTO, TipoDeAlimento> {
    @Mapping(target = "intolerancias", source = "intolerancias", qualifiedByName = "intoleranciaIdSet")
    TipoDeAlimentoDTO toDto(TipoDeAlimento s);

    @Mapping(target = "removeIntolerancia", ignore = true)
    TipoDeAlimento toEntity(TipoDeAlimentoDTO tipoDeAlimentoDTO);

    @Named("intoleranciaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IntoleranciaDTO toDtoIntoleranciaId(Intolerancia intolerancia);

    @Named("intoleranciaIdSet")
    default Set<IntoleranciaDTO> toDtoIntoleranciaIdSet(Set<Intolerancia> intolerancia) {
        return intolerancia.stream().map(this::toDtoIntoleranciaId).collect(Collectors.toSet());
    }
}
