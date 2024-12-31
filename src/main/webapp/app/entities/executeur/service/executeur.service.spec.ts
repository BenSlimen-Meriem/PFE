import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IExecuteur } from '../executeur.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../executeur.test-samples';

import { ExecuteurService } from './executeur.service';

const requireRestSample: IExecuteur = {
  ...sampleWithRequiredData,
};

describe('Executeur Service', () => {
  let service: ExecuteurService;
  let httpMock: HttpTestingController;
  let expectedResult: IExecuteur | IExecuteur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ExecuteurService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Executeur', () => {
      const executeur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(executeur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Executeur', () => {
      const executeur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(executeur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Executeur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Executeur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Executeur', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addExecuteurToCollectionIfMissing', () => {
      it('should add a Executeur to an empty array', () => {
        const executeur: IExecuteur = sampleWithRequiredData;
        expectedResult = service.addExecuteurToCollectionIfMissing([], executeur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(executeur);
      });

      it('should not add a Executeur to an array that contains it', () => {
        const executeur: IExecuteur = sampleWithRequiredData;
        const executeurCollection: IExecuteur[] = [
          {
            ...executeur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addExecuteurToCollectionIfMissing(executeurCollection, executeur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Executeur to an array that doesn't contain it", () => {
        const executeur: IExecuteur = sampleWithRequiredData;
        const executeurCollection: IExecuteur[] = [sampleWithPartialData];
        expectedResult = service.addExecuteurToCollectionIfMissing(executeurCollection, executeur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(executeur);
      });

      it('should add only unique Executeur to an array', () => {
        const executeurArray: IExecuteur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const executeurCollection: IExecuteur[] = [sampleWithRequiredData];
        expectedResult = service.addExecuteurToCollectionIfMissing(executeurCollection, ...executeurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const executeur: IExecuteur = sampleWithRequiredData;
        const executeur2: IExecuteur = sampleWithPartialData;
        expectedResult = service.addExecuteurToCollectionIfMissing([], executeur, executeur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(executeur);
        expect(expectedResult).toContain(executeur2);
      });

      it('should accept null and undefined values', () => {
        const executeur: IExecuteur = sampleWithRequiredData;
        expectedResult = service.addExecuteurToCollectionIfMissing([], null, executeur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(executeur);
      });

      it('should return initial array if no Executeur is added', () => {
        const executeurCollection: IExecuteur[] = [sampleWithRequiredData];
        expectedResult = service.addExecuteurToCollectionIfMissing(executeurCollection, undefined, null);
        expect(expectedResult).toEqual(executeurCollection);
      });
    });

    describe('compareExecuteur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareExecuteur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 25266 };
        const entity2 = null;

        const compareResult1 = service.compareExecuteur(entity1, entity2);
        const compareResult2 = service.compareExecuteur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 25266 };
        const entity2 = { id: 20209 };

        const compareResult1 = service.compareExecuteur(entity1, entity2);
        const compareResult2 = service.compareExecuteur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 25266 };
        const entity2 = { id: 25266 };

        const compareResult1 = service.compareExecuteur(entity1, entity2);
        const compareResult2 = service.compareExecuteur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
