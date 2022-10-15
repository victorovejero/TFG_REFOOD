package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Beneficiario;
import com.refood.trazabilidad.domain.Nucleo;
import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
import com.refood.trazabilidad.service.dto.NucleoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Beneficiario} and its DTO {@link BeneficiarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface BeneficiarioMapper extends EntityMapper<BeneficiarioDTO, Beneficiario> {
    @Mapping(target = "nucleo", source = "nucleo", qualifiedByName = "nucleoId")
    BeneficiarioDTO toDto(Beneficiario s);

    @Named("nucleoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NucleoDTO toDtoNucleoId(Nucleo nucleo);
}
