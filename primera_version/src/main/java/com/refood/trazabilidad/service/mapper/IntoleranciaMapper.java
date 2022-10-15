package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.Beneficiario;
import com.refood.trazabilidad.domain.Intolerancia;
import com.refood.trazabilidad.domain.TipoDeAlimento;
import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
import com.refood.trazabilidad.service.dto.IntoleranciaDTO;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Intolerancia} and its DTO {@link IntoleranciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntoleranciaMapper extends EntityMapper<IntoleranciaDTO, Intolerancia> {
    @Mapping(target = "tipoDeAlimentos", source = "tipoDeAlimentos", qualifiedByName = "tipoDeAlimentoIdSet")
    @Mapping(target = "beneficiarios", source = "beneficiarios", qualifiedByName = "beneficiarioIdSet")
    IntoleranciaDTO toDto(Intolerancia s);

    @Mapping(target = "removeTipoDeAlimento", ignore = true)
    @Mapping(target = "removeBeneficiario", ignore = true)
    Intolerancia toEntity(IntoleranciaDTO intoleranciaDTO);

    @Named("tipoDeAlimentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TipoDeAlimentoDTO toDtoTipoDeAlimentoId(TipoDeAlimento tipoDeAlimento);

    @Named("tipoDeAlimentoIdSet")
    default Set<TipoDeAlimentoDTO> toDtoTipoDeAlimentoIdSet(Set<TipoDeAlimento> tipoDeAlimento) {
        return tipoDeAlimento.stream().map(this::toDtoTipoDeAlimentoId).collect(Collectors.toSet());
    }

    @Named("beneficiarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BeneficiarioDTO toDtoBeneficiarioId(Beneficiario beneficiario);

    @Named("beneficiarioIdSet")
    default Set<BeneficiarioDTO> toDtoBeneficiarioIdSet(Set<Beneficiario> beneficiario) {
        return beneficiario.stream().map(this::toDtoBeneficiarioId).collect(Collectors.toSet());
    }
}
