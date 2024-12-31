export interface IClient {
  id: number;
  raisonSociale?: string | null;
  identifiantUnique?: string | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
