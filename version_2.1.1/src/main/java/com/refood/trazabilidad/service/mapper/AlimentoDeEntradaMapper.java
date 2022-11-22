package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.AlimentoDeEntrada;
import com.refood.trazabilidad.domain.Donante;
import com.refood.trazabilidad.domain.TipoDeAlimento;
import com.refood.trazabilidad.domain.Tupper;
import com.refood.trazabilidad.service.dto.AlimentoDeEntradaDTO;
import com.refood.trazabilidad.service.dto.DonanteDTO;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import com.refood.trazabilidad.service.dto.TupperDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlimentoDeEntrada} and its DTO {@link AlimentoDeEntradaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlimentoDeEntradaMapper extends EntityMapper<AlimentoDeEntradaDTO, AlimentoDeEntrada> {
    //@Mapping(target = "tupper", source = "tupper", qualifiedByName = "tupperId")
    //@Mapping(target = "donante", source = "donante", qualifiedByName = "donanteId")
    //@Mapping(target = "tipoDeAlimento", source = "tipoDeAlimento", qualifiedByName = "tipoDeAlimentoId")
    AlimentoDeEntradaDTO toDto(AlimentoDeEntrada s);

    // @Named("tupperId")
    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    TupperDTO toDtoTupperId(Tupper tupper);

    // @Named("donanteId")
    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    DonanteDTO toDtoDonanteId(Donante donante);

    // @Named("tipoDeAlimentoId")
    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    TipoDeAlimentoDTO toDtoTipoDeAlimentoId(TipoDeAlimento tipoDeAlimento);
}
