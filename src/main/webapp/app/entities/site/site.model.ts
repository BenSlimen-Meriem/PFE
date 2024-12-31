import { ISociete } from 'app/entities/societe/societe.model';

export interface ISite {
  id: number;
  nom?: string | null;
  adresse?: string | null;
  societe?: ISociete | null;
}

export type NewSite = Omit<ISite, 'id'> & { id: null };
