import { IOrdreTravailClient, NewOrdreTravailClient } from './ordre-travail-client.model';

export const sampleWithRequiredData: IOrdreTravailClient = {
  id: 7962,
  fraisSession: 5620.92,
};

export const sampleWithPartialData: IOrdreTravailClient = {
  id: 30664,
  fraisSession: 7509.29,
};

export const sampleWithFullData: IOrdreTravailClient = {
  id: 800,
  fraisSession: 5053.86,
  article: 'au-dehors',
};

export const sampleWithNewData: NewOrdreTravailClient = {
  fraisSession: 32622.58,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
