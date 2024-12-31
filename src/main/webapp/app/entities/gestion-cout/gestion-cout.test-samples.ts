import { IGestionCout, NewGestionCout } from './gestion-cout.model';

export const sampleWithRequiredData: IGestionCout = {
  id: 12951,
  time: 9780,
  cout: 20,
};

export const sampleWithPartialData: IGestionCout = {
  id: 8752,
  time: 30389,
  cout: 13789,
};

export const sampleWithFullData: IGestionCout = {
  id: 5076,
  time: 28927,
  cout: 10725,
};

export const sampleWithNewData: NewGestionCout = {
  time: 3440,
  cout: 14508,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
