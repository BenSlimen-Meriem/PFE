import { ISociete } from 'app/entities/societe/societe.model';
import { IDepartement } from 'app/entities/departement/departement.model';
import { IWorkOrder } from 'app/entities/work-order/work-order.model';

export interface IEmployee {
  id: number;
  nom?: string | null;
  qualification?: string | null;
  societe?: ISociete | null;
  departement?: IDepartement | null;
  workOrders?: IWorkOrder[] | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
