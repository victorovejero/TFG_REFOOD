package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.AlEnt;
import com.refood.trazabilidad.domain.Donante;
import com.refood.trazabilidad.domain.TipoAl;
import com.refood.trazabilidad.domain.Tupper;
import com.refood.trazabilidad.service.dto.AlEntDTO;
import com.refood.trazabilidad.service.dto.DonanteDTO;
import com.refood.trazabilidad.service.dto.TipoAlDTO;
import com.refood.trazabilidad.service.dto.TupperDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlEnt} and its DTO {@link AlEntDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlEntMapper extends EntityMapper<AlEntDTO, AlEnt> {
    @Mapping(target = "tupper", source = "tupper", qualifiedByName = "tupperId")
    @Mapping(target = "donante", source = "donante", qualifiedByName = "donanteId")
    @Mapping(target = "tipoAl", source = "tipoAl", qualifiedByName = "tipoAlId")
    AlEntDTO toDto(AlEnt s);

    @Named("tupperId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TupperDTO toDtoTupperId(Tupper tupper);

    @Named("donanteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonanteDTO toDtoDonanteId(Donante donante);

    @Named("tipoAlId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TipoAlDTO toDtoTipoAlId(TipoAl tipoAl);
}
