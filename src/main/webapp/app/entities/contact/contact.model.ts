import { IClient } from 'app/entities/client/client.model';

export interface IContact {
  id: number;
  nom?: string | null;
  prenom?: string | null;
  email?: string | null;
  telephone?: string | null;
  client?: IClient | null;
}

export type NewContact = Omit<IContact, 'id'> & { id: null };
