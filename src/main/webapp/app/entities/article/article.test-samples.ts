import { IArticle, NewArticle } from './article.model';

export const sampleWithRequiredData: IArticle = {
  id: 17774,
  designation: 'étant donné que disposer épouser',
};

export const sampleWithPartialData: IArticle = {
  id: 7338,
  designation: 'glouglou',
};

export const sampleWithFullData: IArticle = {
  id: 28493,
  designation: 'parmi',
  prix: 32446.96,
};

export const sampleWithNewData: NewArticle = {
  designation: 'cuicui',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
