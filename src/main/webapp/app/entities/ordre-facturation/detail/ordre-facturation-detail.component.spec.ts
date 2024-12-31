import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrdreFacturationDetailComponent } from './ordre-facturation-detail.component';

describe('OrdreFacturation Management Detail Component', () => {
  let comp: OrdreFacturationDetailComponent;
  let fixture: ComponentFixture<OrdreFacturationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdreFacturationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./ordre-facturation-detail.component').then(m => m.OrdreFacturationDetailComponent),
              resolve: { ordreFacturation: () => of({ id: 31844 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OrdreFacturationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdreFacturationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ordreFacturation on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OrdreFacturationDetailComponent);

      // THEN
      expect(instance.ordreFacturation()).toEqual(expect.objectContaining({ id: 31844 }));
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
