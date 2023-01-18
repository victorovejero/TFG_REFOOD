package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.AlEnt;
import com.refood.trazabilidad.domain.AlSal;
import com.refood.trazabilidad.domain.Benef;
import com.refood.trazabilidad.domain.Tupper;
import com.refood.trazabilidad.service.dto.AlEntDTO;
import com.refood.trazabilidad.service.dto.AlSalDTO;
import com.refood.trazabilidad.service.dto.BenefDTO;
import com.refood.trazabilidad.service.dto.TupperDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlSal} and its DTO {@link AlSalDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlSalMapper extends EntityMapper<AlSalDTO, AlSal> {
    @Mapping(target = "tupper", source = "tupper", qualifiedByName = "tupperId")
    @Mapping(target = "benef", source = "benef", qualifiedByName = "benefId")
    @Mapping(target = "alEnt", source = "alEnt", qualifiedByName = "alEntId")
    AlSalDTO toDto(AlSal s);

    @Named("tupperId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TupperDTO toDtoTupperId(Tupper tupper);

    @Named("benefId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BenefDTO toDtoBenefId(Benef benef);

    @Named("alEntId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlEntDTO toDtoAlEntId(AlEnt alEnt);
}
