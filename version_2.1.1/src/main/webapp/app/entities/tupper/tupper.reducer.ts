import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { ITupper, defaultValue } from 'app/shared/model/tupper.model';

const initialState: EntityState<ITupper> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/tuppers';

// Actions
//ACTION CREADA PARA SACAR TODOS LOS TUPPERS

export const getAllEntities = createAsyncThunk('tupper/fetch_entity_list', async({ page, size, sort}: IQueryParams)  => {
  const requestUrl = 'api/tuppers-all';
  return axios.get<ITupper[]>(requestUrl);
})


export const getEntities = createAsyncThunk('tupper/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  return axios.get<ITupper[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'tupper/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<ITupper>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'tupper/create_entity',
  async (entity: ITupper, thunkAPI) => {
    return axios.post<ITupper>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'tupper/update_entity',
  async (entity: ITupper, thunkAPI) => {
    return axios.put<ITupper>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'tupper/partial_update_entity',
  async (entity: ITupper, thunkAPI) => {
    return axios.patch<ITupper>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'tupper/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<ITupper>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

// slice

export const TupperSlice = createEntitySlice({
  name: 'tupper',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      // CASE FOR NEW FUNCTION GETALLENTITIES(), mete las entities en el estado cuando se ejecuta la nueva funcion.
      // .addCase(getAllEntities.fulfilled, (state,action) => {
      //   state.loading = false;
      //   state.entities = action.payload.data;
      // })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;
        // const links = parseHeaderForLinks(headers.link);

        return {
          ...state,
          loading: false,
          entities:data,
          // links,
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
      .addMatcher(isPending(getEntities, getEntity, getAllEntities), state => {
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

export const { reset } = TupperSlice.actions;

// Reducer
export default TupperSlice.reducer;
