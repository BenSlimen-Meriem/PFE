import { IWorkOrder } from 'app/entities/work-order/work-order.model';

export interface IOrdreTravailClient {
  id: number;
  fraisSession?: number | null;
  article?: string | null;
  workOrder?: IWorkOrder | null;
}

export type NewOrdreTravailClient = Omit<IOrdreTravailClient, 'id'> & { id: null };
