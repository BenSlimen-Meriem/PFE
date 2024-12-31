import { ISST, NewSST } from './sst.model';

export const sampleWithRequiredData: ISST = {
  id: 20276,
};

export const sampleWithPartialData: ISST = {
  id: 1441,
};

export const sampleWithFullData: ISST = {
  id: 12004,
  description: 'quand',
};

export const sampleWithNewData: NewSST = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
