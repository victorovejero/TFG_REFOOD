package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.AlimentoDeSalida;
import com.refood.trazabilidad.domain.Beneficiario;
import com.refood.trazabilidad.domain.Checkout;
import com.refood.trazabilidad.service.dto.AlimentoDeSalidaDTO;
import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
import com.refood.trazabilidad.service.dto.CheckoutDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Checkout} and its DTO {@link CheckoutDTO}.
 */
@Mapper(componentModel = "spring")
public interface CheckoutMapper extends EntityMapper<CheckoutDTO, Checkout> {
    // @Mapping(target = "alimentoDeSalidas", source = "alimentoDeSalidas",
    // qualifiedByName = "alimentoDeSalidaIdSet")
    // @Mapping(target = "beneficiario", source = "beneficiario", qualifiedByName =
    // "beneficiarioId")
    CheckoutDTO toDto(Checkout s);

    @Mapping(target = "removeAlimentoDeSalida", ignore = true)
    Checkout toEntity(CheckoutDTO checkoutDTO);

    // @Named("alimentoDeSalidaId")
    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    AlimentoDeSalidaDTO toDtoAlimentoDeSalidaId(AlimentoDeSalida alimentoDeSalida);

    @Named("alimentoDeSalidaIdSet")
    default Set<AlimentoDeSalidaDTO> toDtoAlimentoDeSalidaIdSet(Set<AlimentoDeSalida> alimentoDeSalida) {
        return alimentoDeSalida.stream().map(this::toDtoAlimentoDeSalidaId).collect(Collectors.toSet());
    }

    // @Named("beneficiarioId")
    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    BeneficiarioDTO toDtoBeneficiarioId(Beneficiario beneficiario);
}
