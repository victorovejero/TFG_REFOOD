package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.AlimentoDeEntrada;
import com.refood.trazabilidad.domain.AlimentoDeSalida;
import com.refood.trazabilidad.domain.Beneficiario;
import com.refood.trazabilidad.domain.TipoDeAlimento;
import com.refood.trazabilidad.domain.Tupper;
import com.refood.trazabilidad.service.dto.AlimentoDeEntradaDTO;
import com.refood.trazabilidad.service.dto.AlimentoDeSalidaDTO;
import com.refood.trazabilidad.service.dto.BeneficiarioDTO;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import com.refood.trazabilidad.service.dto.TupperDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlimentoDeSalida} and its DTO {@link AlimentoDeSalidaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlimentoDeSalidaMapper extends EntityMapper<AlimentoDeSalidaDTO, AlimentoDeSalida> {
    @Mapping(target = "tupper", source = "tupper", qualifiedByName = "tupperId")
    @Mapping(target = "beneficiario", source = "beneficiario", qualifiedByName = "beneficiarioId")
    @Mapping(target = "alimentoDeEntrada", source = "alimentoDeEntrada", qualifiedByName = "alimentoDeEntradaId")
    @Mapping(target = "tipoDeAlimento", source = "tipoDeAlimento", qualifiedByName = "tipoDeAlimentoId")
    AlimentoDeSalidaDTO toDto(AlimentoDeSalida s);

    @Named("tupperId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TupperDTO toDtoTupperId(Tupper tupper);

    @Named("beneficiarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BeneficiarioDTO toDtoBeneficiarioId(Beneficiario beneficiario);

    @Named("alimentoDeEntradaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlimentoDeEntradaDTO toDtoAlimentoDeEntradaId(AlimentoDeEntrada alimentoDeEntrada);

    @Named("tipoDeAlimentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TipoDeAlimentoDTO toDtoTipoDeAlimentoId(TipoDeAlimento tipoDeAlimento);
}
