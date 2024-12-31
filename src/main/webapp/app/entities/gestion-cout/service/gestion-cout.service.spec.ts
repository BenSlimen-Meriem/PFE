import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGestionCout } from '../gestion-cout.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../gestion-cout.test-samples';

import { GestionCoutService } from './gestion-cout.service';

const requireRestSample: IGestionCout = {
  ...sampleWithRequiredData,
};

describe('GestionCout Service', () => {
  let service: GestionCoutService;
  let httpMock: HttpTestingController;
  let expectedResult: IGestionCout | IGestionCout[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GestionCoutService);
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

    it('should create a GestionCout', () => {
      const gestionCout = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(gestionCout).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GestionCout', () => {
      const gestionCout = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(gestionCout).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GestionCout', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GestionCout', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GestionCout', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGestionCoutToCollectionIfMissing', () => {
      it('should add a GestionCout to an empty array', () => {
        const gestionCout: IGestionCout = sampleWithRequiredData;
        expectedResult = service.addGestionCoutToCollectionIfMissing([], gestionCout);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gestionCout);
      });

      it('should not add a GestionCout to an array that contains it', () => {
        const gestionCout: IGestionCout = sampleWithRequiredData;
        const gestionCoutCollection: IGestionCout[] = [
          {
            ...gestionCout,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGestionCoutToCollectionIfMissing(gestionCoutCollection, gestionCout);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GestionCout to an array that doesn't contain it", () => {
        const gestionCout: IGestionCout = sampleWithRequiredData;
        const gestionCoutCollection: IGestionCout[] = [sampleWithPartialData];
        expectedResult = service.addGestionCoutToCollectionIfMissing(gestionCoutCollection, gestionCout);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gestionCout);
      });

      it('should add only unique GestionCout to an array', () => {
        const gestionCoutArray: IGestionCout[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const gestionCoutCollection: IGestionCout[] = [sampleWithRequiredData];
        expectedResult = service.addGestionCoutToCollectionIfMissing(gestionCoutCollection, ...gestionCoutArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const gestionCout: IGestionCout = sampleWithRequiredData;
        const gestionCout2: IGestionCout = sampleWithPartialData;
        expectedResult = service.addGestionCoutToCollectionIfMissing([], gestionCout, gestionCout2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gestionCout);
        expect(expectedResult).toContain(gestionCout2);
      });

      it('should accept null and undefined values', () => {
        const gestionCout: IGestionCout = sampleWithRequiredData;
        expectedResult = service.addGestionCoutToCollectionIfMissing([], null, gestionCout, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gestionCout);
      });

      it('should return initial array if no GestionCout is added', () => {
        const gestionCoutCollection: IGestionCout[] = [sampleWithRequiredData];
        expectedResult = service.addGestionCoutToCollectionIfMissing(gestionCoutCollection, undefined, null);
        expect(expectedResult).toEqual(gestionCoutCollection);
      });
    });

    describe('compareGestionCout', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGestionCout(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 19852 };
        const entity2 = null;

        const compareResult1 = service.compareGestionCout(entity1, entity2);
        const compareResult2 = service.compareGestionCout(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 19852 };
        const entity2 = { id: 11524 };

        const compareResult1 = service.compareGestionCout(entity1, entity2);
        const compareResult2 = service.compareGestionCout(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 19852 };
        const entity2 = { id: 19852 };

        const compareResult1 = service.compareGestionCout(entity1, entity2);
        const compareResult2 = service.compareGestionCout(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
