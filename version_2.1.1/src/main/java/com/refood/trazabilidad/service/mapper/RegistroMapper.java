package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.domain.Registro;
import com.refood.trazabilidad.domain.Voluntario;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import com.refood.trazabilidad.service.dto.RegistroDTO;
import com.refood.trazabilidad.service.dto.VoluntarioDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Registro} and its DTO {@link RegistroDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegistroMapper extends EntityMapper<RegistroDTO, Registro> {
    @Mapping(target = "voluntarios", source = "voluntarios", qualifiedByName = "voluntarioIdSet")
    @Mapping(target = "nucleo", source = "nucleo", qualifiedByName = "nucleoId")
    RegistroDTO toDto(Registro s);

    @Mapping(target = "removeVoluntario", ignore = true)
    Registro toEntity(RegistroDTO registroDTO);

    @Named("voluntarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VoluntarioDTO toDtoVoluntarioId(Voluntario voluntario);

    @Named("voluntarioIdSet")
    default Set<VoluntarioDTO> toDtoVoluntarioIdSet(Set<Voluntario> voluntario) {
        return voluntario.stream().map(this::toDtoVoluntarioId).collect(Collectors.toSet());
    }

    @Named("nucleoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NucleoDTO toDtoNucleoId(Nucleo nucleo);
}
