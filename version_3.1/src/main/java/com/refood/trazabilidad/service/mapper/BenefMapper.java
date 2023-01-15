package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Benef;
import com.refood.trazabilidad.domain.Intol;
import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.service.dto.BenefDTO;
import com.refood.trazabilidad.service.dto.IntolDTO;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Benef} and its DTO {@link BenefDTO}.
 */
@Mapper(componentModel = "spring")
public interface BenefMapper extends EntityMapper<BenefDTO, Benef> {
    @Mapping(target = "intols", source = "intols", qualifiedByName = "intolIdSet")
    @Mapping(target = "nucleo", source = "nucleo", qualifiedByName = "nucleoId")
    BenefDTO toDto(Benef s);

    @Mapping(target = "removeIntol", ignore = true)
    Benef toEntity(BenefDTO benefDTO);

    @Named("intolId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IntolDTO toDtoIntolId(Intol intol);

    @Named("intolIdSet")
    default Set<IntolDTO> toDtoIntolIdSet(Set<Intol> intol) {
        return intol.stream().map(this::toDtoIntolId).collect(Collectors.toSet());
    }

    @Named("nucleoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NucleoDTO toDtoNucleoId(Nucleo nucleo);
}
