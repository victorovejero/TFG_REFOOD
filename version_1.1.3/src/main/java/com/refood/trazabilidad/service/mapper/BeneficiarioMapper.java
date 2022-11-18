package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Beneficiario;
import com.refood.trazabilidad.domain.Intolerancia;
import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Beneficiario} and its DTO {@link BeneficiarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface BeneficiarioMapper extends EntityMapper<BeneficiarioDTO, Beneficiario> {
    @Mapping(target = "intolerancias", source = "intolerancias", qualifiedByName = "intoleranciaIdSet")
    @Mapping(target = "nucleo", source = "nucleo", qualifiedByName = "nucleoId")
    BeneficiarioDTO toDto(Beneficiario s);

    @Mapping(target = "removeIntolerancia", ignore = true)
    Beneficiario toEntity(BeneficiarioDTO beneficiarioDTO);

    @Named("intoleranciaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IntoleranciaDTO toDtoIntoleranciaId(Intolerancia intolerancia);

    @Named("intoleranciaIdSet")
    default Set<IntoleranciaDTO> toDtoIntoleranciaIdSet(Set<Intolerancia> intolerancia) {
        return intolerancia.stream().map(this::toDtoIntoleranciaId).collect(Collectors.toSet());
    }

    @Named("nucleoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NucleoDTO toDtoNucleoId(Nucleo nucleo);
}
