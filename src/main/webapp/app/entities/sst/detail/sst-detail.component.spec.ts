import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SSTDetailComponent } from './sst-detail.component';

describe('SST Management Detail Component', () => {
  let comp: SSTDetailComponent;
  let fixture: ComponentFixture<SSTDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SSTDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./sst-detail.component').then(m => m.SSTDetailComponent),
              resolve: { sST: () => of({ id: 28827 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SSTDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SSTDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sST on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SSTDetailComponent);

      // THEN
      expect(instance.sST()).toEqual(expect.objectContaining({ id: 28827 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
