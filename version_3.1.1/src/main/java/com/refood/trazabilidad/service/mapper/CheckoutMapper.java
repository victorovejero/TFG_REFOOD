package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.AlSal;
import com.refood.trazabilidad.domain.Benef;
import com.refood.trazabilidad.domain.Checkout;
import com.refood.trazabilidad.service.dto.AlSalDTO;
import com.refood.trazabilidad.service.dto.BenefDTO;
import com.refood.trazabilidad.service.dto.CheckoutDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Checkout} and its DTO {@link CheckoutDTO}.
 */
@Mapper(componentModel = "spring")
public interface CheckoutMapper extends EntityMapper<CheckoutDTO, Checkout> {
    @Mapping(target = "alSals", source = "alSals", qualifiedByName = "alSalIdSet")
    @Mapping(target = "benef", source = "benef", qualifiedByName = "benefId")
    CheckoutDTO toDto(Checkout s);

    @Mapping(target = "removeAlSal", ignore = true)
    Checkout toEntity(CheckoutDTO checkoutDTO);

    @Named("alSalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlSalDTO toDtoAlSalId(AlSal alSal);

    @Named("alSalIdSet")
    default Set<AlSalDTO> toDtoAlSalIdSet(Set<AlSal> alSal) {
        return alSal.stream().map(this::toDtoAlSalId).collect(Collectors.toSet());
    }

    @Named("benefId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BenefDTO toDtoBenefId(Benef benef);
}
