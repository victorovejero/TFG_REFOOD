package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Beneficiario;
import com.refood.trazabilidad.domain.Intolerancia;
import com.refood.trazabilidad.domain.PersonaBeneficiaria;
import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
import com.refood.trazabilidad.service.dto.PersonaBeneficiariaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonaBeneficiaria} and its DTO {@link PersonaBeneficiariaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonaBeneficiariaMapper extends EntityMapper<PersonaBeneficiariaDTO, PersonaBeneficiaria> {
    @Mapping(target = "intolerancias", source = "intolerancias", qualifiedByName = "intoleranciaIdSet")
    @Mapping(target = "beneficiario", source = "beneficiario", qualifiedByName = "beneficiarioId")
    PersonaBeneficiariaDTO toDto(PersonaBeneficiaria s);

    @Mapping(target = "removeIntolerancia", ignore = true)
    PersonaBeneficiaria toEntity(PersonaBeneficiariaDTO personaBeneficiariaDTO);

    @Named("intoleranciaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IntoleranciaDTO toDtoIntoleranciaId(Intolerancia intolerancia);

    @Named("intoleranciaIdSet")
    default Set<IntoleranciaDTO> toDtoIntoleranciaIdSet(Set<Intolerancia> intolerancia) {
        return intolerancia.stream().map(this::toDtoIntoleranciaId).collect(Collectors.toSet());
    }

    @Named("beneficiarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BeneficiarioDTO toDtoBeneficiarioId(Beneficiario beneficiario);
}
