import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ExecuteurDetailComponent } from './executeur-detail.component';

describe('Executeur Management Detail Component', () => {
  let comp: ExecuteurDetailComponent;
  let fixture: ComponentFixture<ExecuteurDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExecuteurDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./executeur-detail.component').then(m => m.ExecuteurDetailComponent),
              resolve: { executeur: () => of({ id: 25266 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ExecuteurDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExecuteurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load executeur on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ExecuteurDetailComponent);

      // THEN
      expect(instance.executeur()).toEqual(expect.objectContaining({ id: 25266 }));
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
