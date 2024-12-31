import { IEmployee } from 'app/entities/employee/employee.model';

export interface IExecuteur {
  id: number;
  nom?: string | null;
  niveauExpertise?: string | null;
  disponibilite?: boolean | null;
  employee?: IEmployee | null;
}

export type NewExecuteur = Omit<IExecuteur, 'id'> & { id: null };
