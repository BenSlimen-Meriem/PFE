import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOrdreTravailClient } from '../ordre-travail-client.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../ordre-travail-client.test-samples';

import { OrdreTravailClientService } from './ordre-travail-client.service';

const requireRestSample: IOrdreTravailClient = {
  ...sampleWithRequiredData,
};

describe('OrdreTravailClient Service', () => {
  let service: OrdreTravailClientService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrdreTravailClient | IOrdreTravailClient[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OrdreTravailClientService);
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

    it('should create a OrdreTravailClient', () => {
      const ordreTravailClient = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ordreTravailClient).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrdreTravailClient', () => {
      const ordreTravailClient = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ordreTravailClient).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrdreTravailClient', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrdreTravailClient', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrdreTravailClient', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrdreTravailClientToCollectionIfMissing', () => {
      it('should add a OrdreTravailClient to an empty array', () => {
        const ordreTravailClient: IOrdreTravailClient = sampleWithRequiredData;
        expectedResult = service.addOrdreTravailClientToCollectionIfMissing([], ordreTravailClient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ordreTravailClient);
      });

      it('should not add a OrdreTravailClient to an array that contains it', () => {
        const ordreTravailClient: IOrdreTravailClient = sampleWithRequiredData;
        const ordreTravailClientCollection: IOrdreTravailClient[] = [
          {
            ...ordreTravailClient,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrdreTravailClientToCollectionIfMissing(ordreTravailClientCollection, ordreTravailClient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrdreTravailClient to an array that doesn't contain it", () => {
        const ordreTravailClient: IOrdreTravailClient = sampleWithRequiredData;
        const ordreTravailClientCollection: IOrdreTravailClient[] = [sampleWithPartialData];
        expectedResult = service.addOrdreTravailClientToCollectionIfMissing(ordreTravailClientCollection, ordreTravailClient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ordreTravailClient);
      });

      it('should add only unique OrdreTravailClient to an array', () => {
        const ordreTravailClientArray: IOrdreTravailClient[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ordreTravailClientCollection: IOrdreTravailClient[] = [sampleWithRequiredData];
        expectedResult = service.addOrdreTravailClientToCollectionIfMissing(ordreTravailClientCollection, ...ordreTravailClientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ordreTravailClient: IOrdreTravailClient = sampleWithRequiredData;
        const ordreTravailClient2: IOrdreTravailClient = sampleWithPartialData;
        expectedResult = service.addOrdreTravailClientToCollectionIfMissing([], ordreTravailClient, ordreTravailClient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ordreTravailClient);
        expect(expectedResult).toContain(ordreTravailClient2);
      });

      it('should accept null and undefined values', () => {
        const ordreTravailClient: IOrdreTravailClient = sampleWithRequiredData;
        expectedResult = service.addOrdreTravailClientToCollectionIfMissing([], null, ordreTravailClient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ordreTravailClient);
      });

      it('should return initial array if no OrdreTravailClient is added', () => {
        const ordreTravailClientCollection: IOrdreTravailClient[] = [sampleWithRequiredData];
        expectedResult = service.addOrdreTravailClientToCollectionIfMissing(ordreTravailClientCollection, undefined, null);
        expect(expectedResult).toEqual(ordreTravailClientCollection);
      });
    });

    describe('compareOrdreTravailClient', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrdreTravailClient(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 24849 };
        const entity2 = null;

        const compareResult1 = service.compareOrdreTravailClient(entity1, entity2);
        const compareResult2 = service.compareOrdreTravailClient(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 24849 };
        const entity2 = { id: 1464 };

        const compareResult1 = service.compareOrdreTravailClient(entity1, entity2);
        const compareResult2 = service.compareOrdreTravailClient(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 24849 };
        const entity2 = { id: 24849 };

        const compareResult1 = service.compareOrdreTravailClient(entity1, entity2);
        const compareResult2 = service.compareOrdreTravailClient(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
