import dayjs from 'dayjs/esm';

import { IOrdreFacturation, NewOrdreFacturation } from './ordre-facturation.model';

export const sampleWithRequiredData: IOrdreFacturation = {
  id: 28800,
  date: dayjs('2024-12-30'),
};

export const sampleWithPartialData: IOrdreFacturation = {
  id: 32242,
  date: dayjs('2024-12-31'),
  facture: 'lectorat biathlète malade',
};

export const sampleWithFullData: IOrdreFacturation = {
  id: 12904,
  date: dayjs('2024-12-31'),
  bonDeCommande: 'en dedans de à bas de',
  facture: 'clientèle porte-parole finir',
};

export const sampleWithNewData: NewOrdreFacturation = {
  date: dayjs('2024-12-31'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
