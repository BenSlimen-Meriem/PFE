import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IOrdreFacturation } from '../ordre-facturation.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../ordre-facturation.test-samples';

import { OrdreFacturationService, RestOrdreFacturation } from './ordre-facturation.service';

const requireRestSample: RestOrdreFacturation = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('OrdreFacturation Service', () => {
  let service: OrdreFacturationService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrdreFacturation | IOrdreFacturation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OrdreFacturationService);
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

    it('should create a OrdreFacturation', () => {
      const ordreFacturation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ordreFacturation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrdreFacturation', () => {
      const ordreFacturation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ordreFacturation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrdreFacturation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrdreFacturation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrdreFacturation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrdreFacturationToCollectionIfMissing', () => {
      it('should add a OrdreFacturation to an empty array', () => {
        const ordreFacturation: IOrdreFacturation = sampleWithRequiredData;
        expectedResult = service.addOrdreFacturationToCollectionIfMissing([], ordreFacturation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ordreFacturation);
      });

      it('should not add a OrdreFacturation to an array that contains it', () => {
        const ordreFacturation: IOrdreFacturation = sampleWithRequiredData;
        const ordreFacturationCollection: IOrdreFacturation[] = [
          {
            ...ordreFacturation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrdreFacturationToCollectionIfMissing(ordreFacturationCollection, ordreFacturation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrdreFacturation to an array that doesn't contain it", () => {
        const ordreFacturation: IOrdreFacturation = sampleWithRequiredData;
        const ordreFacturationCollection: IOrdreFacturation[] = [sampleWithPartialData];
        expectedResult = service.addOrdreFacturationToCollectionIfMissing(ordreFacturationCollection, ordreFacturation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ordreFacturation);
      });

      it('should add only unique OrdreFacturation to an array', () => {
        const ordreFacturationArray: IOrdreFacturation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ordreFacturationCollection: IOrdreFacturation[] = [sampleWithRequiredData];
        expectedResult = service.addOrdreFacturationToCollectionIfMissing(ordreFacturationCollection, ...ordreFacturationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ordreFacturation: IOrdreFacturation = sampleWithRequiredData;
        const ordreFacturation2: IOrdreFacturation = sampleWithPartialData;
        expectedResult = service.addOrdreFacturationToCollectionIfMissing([], ordreFacturation, ordreFacturation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ordreFacturation);
        expect(expectedResult).toContain(ordreFacturation2);
      });

      it('should accept null and undefined values', () => {
        const ordreFacturation: IOrdreFacturation = sampleWithRequiredData;
        expectedResult = service.addOrdreFacturationToCollectionIfMissing([], null, ordreFacturation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ordreFacturation);
      });

      it('should return initial array if no OrdreFacturation is added', () => {
        const ordreFacturationCollection: IOrdreFacturation[] = [sampleWithRequiredData];
        expectedResult = service.addOrdreFacturationToCollectionIfMissing(ordreFacturationCollection, undefined, null);
        expect(expectedResult).toEqual(ordreFacturationCollection);
      });
    });

    describe('compareOrdreFacturation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrdreFacturation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 31844 };
        const entity2 = null;

        const compareResult1 = service.compareOrdreFacturation(entity1, entity2);
        const compareResult2 = service.compareOrdreFacturation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 31844 };
        const entity2 = { id: 11283 };

        const compareResult1 = service.compareOrdreFacturation(entity1, entity2);
        const compareResult2 = service.compareOrdreFacturation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 31844 };
        const entity2 = { id: 31844 };

        const compareResult1 = service.compareOrdreFacturation(entity1, entity2);
        const compareResult2 = service.compareOrdreFacturation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
