import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPlanificateur } from '../planificateur.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../planificateur.test-samples';

import { PlanificateurService } from './planificateur.service';

const requireRestSample: IPlanificateur = {
  ...sampleWithRequiredData,
};

describe('Planificateur Service', () => {
  let service: PlanificateurService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanificateur | IPlanificateur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PlanificateurService);
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

    it('should create a Planificateur', () => {
      const planificateur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planificateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Planificateur', () => {
      const planificateur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planificateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Planificateur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Planificateur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Planificateur', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanificateurToCollectionIfMissing', () => {
      it('should add a Planificateur to an empty array', () => {
        const planificateur: IPlanificateur = sampleWithRequiredData;
        expectedResult = service.addPlanificateurToCollectionIfMissing([], planificateur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planificateur);
      });

      it('should not add a Planificateur to an array that contains it', () => {
        const planificateur: IPlanificateur = sampleWithRequiredData;
        const planificateurCollection: IPlanificateur[] = [
          {
            ...planificateur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanificateurToCollectionIfMissing(planificateurCollection, planificateur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Planificateur to an array that doesn't contain it", () => {
        const planificateur: IPlanificateur = sampleWithRequiredData;
        const planificateurCollection: IPlanificateur[] = [sampleWithPartialData];
        expectedResult = service.addPlanificateurToCollectionIfMissing(planificateurCollection, planificateur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planificateur);
      });

      it('should add only unique Planificateur to an array', () => {
        const planificateurArray: IPlanificateur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const planificateurCollection: IPlanificateur[] = [sampleWithRequiredData];
        expectedResult = service.addPlanificateurToCollectionIfMissing(planificateurCollection, ...planificateurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planificateur: IPlanificateur = sampleWithRequiredData;
        const planificateur2: IPlanificateur = sampleWithPartialData;
        expectedResult = service.addPlanificateurToCollectionIfMissing([], planificateur, planificateur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planificateur);
        expect(expectedResult).toContain(planificateur2);
      });

      it('should accept null and undefined values', () => {
        const planificateur: IPlanificateur = sampleWithRequiredData;
        expectedResult = service.addPlanificateurToCollectionIfMissing([], null, planificateur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planificateur);
      });

      it('should return initial array if no Planificateur is added', () => {
        const planificateurCollection: IPlanificateur[] = [sampleWithRequiredData];
        expectedResult = service.addPlanificateurToCollectionIfMissing(planificateurCollection, undefined, null);
        expect(expectedResult).toEqual(planificateurCollection);
      });
    });

    describe('comparePlanificateur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanificateur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 18052 };
        const entity2 = null;

        const compareResult1 = service.comparePlanificateur(entity1, entity2);
        const compareResult2 = service.comparePlanificateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 18052 };
        const entity2 = { id: 1147 };

        const compareResult1 = service.comparePlanificateur(entity1, entity2);
        const compareResult2 = service.comparePlanificateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 18052 };
        const entity2 = { id: 18052 };

        const compareResult1 = service.comparePlanificateur(entity1, entity2);
        const compareResult2 = service.comparePlanificateur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
