import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
// import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { ITipoDeAlimento, defaultValue } from 'app/shared/model/tipo-de-alimento.model';

const initialState: EntityState<ITipoDeAlimento> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/tipo-de-alimentos';

// Actions
// ACTION CREADA PARA DEVOLVER TODOS LOS TIPOS DE ALIMENTOS PARA EL SELECT DE ALIMENTOS DE ENTRADA

export const getAllEntities = createAsyncThunk('tipoDeAlimento/fetch_entity_list', async({ page, size, sort}: IQueryParams)  => {
   const requestUrl = 'api/tipo-de-alimento-all'
   return axios.get<ITipoDeAlimento[]>(requestUrl);
})

export const getEntities = createAsyncThunk('tipoDeAlimento/fetch_entity_list', async ({ page, size, sort}: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  // const requestUrl = 'api/tipo-de-alimento-all'
  return axios.get<ITipoDeAlimento[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'tipoDeAlimento/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<ITipoDeAlimento>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'tipoDeAlimento/create_entity',
  async (entity: ITipoDeAlimento, thunkAPI) => {
    return axios.post<ITipoDeAlimento>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'tipoDeAlimento/update_entity',
  async (entity: ITipoDeAlimento, thunkAPI) => {
    return axios.put<ITipoDeAlimento>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'tipoDeAlimento/partial_update_entity',
  async (entity: ITipoDeAlimento, thunkAPI) => {
    return axios.patch<ITipoDeAlimento>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'tipoDeAlimento/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<ITipoDeAlimento>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

// slice

export const TipoDeAlimentoSlice = createEntitySlice({
  name: 'tipoDeAlimento',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;
        //CHANGES FOR PAGINATION
        // const links = parseHeaderForLinks(headers.link);

        return {
          ...state,
          loading: false,
          // links,
          entities:data,
          //CHANGE FOR PAGINATION
          // entities: loadMoreDataWhenScrolled(state.entities, data, links),
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = TipoDeAlimentoSlice.actions;

// Reducer
export default TipoDeAlimentoSlice.reducer;
