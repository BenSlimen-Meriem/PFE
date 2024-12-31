import { IWorkOrder } from 'app/entities/work-order/work-order.model';

export interface IArticle {
  id: number;
  designation?: string | null;
  prix?: number | null;
  workOrders?: IWorkOrder[] | null;
}

export type NewArticle = Omit<IArticle, 'id'> & { id: null };
