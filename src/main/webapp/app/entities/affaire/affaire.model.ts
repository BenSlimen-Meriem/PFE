import { IClient } from 'app/entities/client/client.model';

export interface IAffaire {
  id: number;
  reference?: string | null;
  description?: string | null;
  client?: IClient | null;
}

export type NewAffaire = Omit<IAffaire, 'id'> & { id: null };
