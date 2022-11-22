package com.refood.trazabilidad.service.mapper;

import com.refood.trazabilidad.domain.AlimentoDeEntrada;
import com.refood.trazabilidad.domain.Donante;
import com.refood.trazabilidad.domain.FrutaYVerdura;
import com.refood.trazabilidad.domain.TipoDeAlimento;
import com.refood.trazabilidad.domain.Tupper;
import com.refood.trazabilidad.service.dto.AlimentoDeEntradaDTO;
import com.refood.trazabilidad.service.dto.DonanteDTO;
import com.refood.trazabilidad.service.dto.FrutaYVerduraDTO;
import com.refood.trazabilidad.service.dto.TipoDeAlimentoDTO;
import com.refood.trazabilidad.service.dto.TupperDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlimentoDeEntrada} and its DTO {@link AlimentoDeEntradaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlimentoDeEntradaMapper extends EntityMapper<AlimentoDeEntradaDTO, AlimentoDeEntrada> {
    @Mapping(target = "frutaYVerduras", source = "frutaYVerduras", qualifiedByName = "frutaYVerduraIdSet")
    @Mapping(target = "tupper", source = "tupper", qualifiedByName = "tupperId")
    @Mapping(target = "donante", source = "donante", qualifiedByName = "donanteId")
    @Mapping(target = "tipoDeAlimento", source = "tipoDeAlimento", qualifiedByName = "tipoDeAlimentoId")
    AlimentoDeEntradaDTO toDto(AlimentoDeEntrada s);

    @Mapping(target = "removeFrutaYVerdura", ignore = true)
    AlimentoDeEntrada toEntity(AlimentoDeEntradaDTO alimentoDeEntradaDTO);

    @Named("frutaYVerduraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FrutaYVerduraDTO toDtoFrutaYVerduraId(FrutaYVerdura frutaYVerdura);

    @Named("frutaYVerduraIdSet")
    default Set<FrutaYVerduraDTO> toDtoFrutaYVerduraIdSet(Set<FrutaYVerdura> frutaYVerdura) {
        return frutaYVerdura.stream().map(this::toDtoFrutaYVerduraId).collect(Collectors.toSet());
    }

    @Named("tupperId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TupperDTO toDtoTupperId(Tupper tupper);

    @Named("donanteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonanteDTO toDtoDonanteId(Donante donante);

    @Named("tipoDeAlimentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TipoDeAlimentoDTO toDtoTipoDeAlimentoId(TipoDeAlimento tipoDeAlimento);
}
