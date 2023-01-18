package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Intol;
import com.refood.trazabilidad.domain.TipoAl;
import com.refood.trazabilidad.service.dto.IntolDTO;
import com.refood.trazabilidad.service.dto.TipoAlDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoAl} and its DTO {@link TipoAlDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoAlMapper extends EntityMapper<TipoAlDTO, TipoAl> {
    @Mapping(target = "intols", source = "intols", qualifiedByName = "intolIdSet")
    TipoAlDTO toDto(TipoAl s);

    @Mapping(target = "removeIntol", ignore = true)
    TipoAl toEntity(TipoAlDTO tipoAlDTO);

    @Named("intolId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IntolDTO toDtoIntolId(Intol intol);

    @Named("intolIdSet")
    default Set<IntolDTO> toDtoIntolIdSet(Set<Intol> intol) {
        return intol.stream().map(this::toDtoIntolId).collect(Collectors.toSet());
    }
}
