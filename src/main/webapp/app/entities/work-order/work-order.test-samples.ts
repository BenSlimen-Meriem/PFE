import dayjs from 'dayjs/esm';

import { IWorkOrder, NewWorkOrder } from './work-order.model';

export const sampleWithRequiredData: IWorkOrder = {
  id: 10128,
  demandeur: 'reculer afin de',
  nature: 'ha ha',
  dateHeureDebutPrevisionnelle: dayjs('2024-12-30T13:50'),
  dateHeureFinPrevisionnelle: dayjs('2024-12-30T21:50'),
  statut: 'ANNULE',
};

export const sampleWithPartialData: IWorkOrder = {
  id: 29483,
  demandeur: 'a toc',
  nature: 'sans',
  dateHeureDebutPrevisionnelle: dayjs('2024-12-30T19:31'),
  dateHeureFinPrevisionnelle: dayjs('2024-12-30T10:52'),
  materielUtilise: 'membre à vie',
  membreMission: 'tellement céans',
  responsableMission: 'de peur que hi sans que',
  statut: 'BROUILLON',
};

export const sampleWithFullData: IWorkOrder = {
  id: 3653,
  demandeur: 'énergique',
  nature: 'incognito',
  motifDescription: 'fort en faveur de',
  dateHeureDebutPrevisionnelle: dayjs('2024-12-30T10:28'),
  dateHeureFinPrevisionnelle: dayjs('2024-12-30T12:40'),
  vehicule: 'cot cot',
  materielUtilise: 'secours en face de',
  article: 'transporter oups dessus',
  membreMission: 'alors que',
  responsableMission: "proche de à l'instar de",
  statut: 'TERMINE',
};

export const sampleWithNewData: NewWorkOrder = {
  demandeur: 'tellement souvent',
  nature: 'rédaction',
  dateHeureDebutPrevisionnelle: dayjs('2024-12-30T23:39'),
  dateHeureFinPrevisionnelle: dayjs('2024-12-30T22:43'),
  statut: 'BROUILLON',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
