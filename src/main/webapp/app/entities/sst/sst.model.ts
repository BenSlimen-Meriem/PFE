export interface ISST {
  id: number;
  description?: string | null;
}

export type NewSST = Omit<ISST, 'id'> & { id: null };
