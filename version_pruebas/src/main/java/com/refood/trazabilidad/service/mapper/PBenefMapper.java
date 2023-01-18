package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Benef;
import com.refood.trazabilidad.domain.Intol;
import com.refood.trazabilidad.domain.PBenef;
import com.refood.trazabilidad.service.dto.BenefDTO;
import com.refood.trazabilidad.service.dto.IntolDTO;
import com.refood.trazabilidad.service.dto.PBenefDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PBenef} and its DTO {@link PBenefDTO}.
 */
@Mapper(componentModel = "spring")
public interface PBenefMapper extends EntityMapper<PBenefDTO, PBenef> {
    @Mapping(target = "intols", source = "intols", qualifiedByName = "intolIdSet")
    @Mapping(target = "benef", source = "benef", qualifiedByName = "benefId")
    PBenefDTO toDto(PBenef s);

    @Mapping(target = "removeIntol", ignore = true)
    PBenef toEntity(PBenefDTO pBenefDTO);

    @Named("intolId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IntolDTO toDtoIntolId(Intol intol);

    @Named("intolIdSet")
    default Set<IntolDTO> toDtoIntolIdSet(Set<Intol> intol) {
        return intol.stream().map(this::toDtoIntolId).collect(Collectors.toSet());
    }

    @Named("benefId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BenefDTO toDtoBenefId(Benef benef);
}
