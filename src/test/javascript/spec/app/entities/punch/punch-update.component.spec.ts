import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyLocationTestModule } from '../../../test.module';
import { PunchUpdateComponent } from 'app/entities/punch/punch-update.component';
import { PunchService } from 'app/entities/punch/punch.service';
import { Punch } from 'app/shared/model/punch.model';

describe('Component Tests', () => {
  describe('Punch Management Update Component', () => {
    let comp: PunchUpdateComponent;
    let fixture: ComponentFixture<PunchUpdateComponent>;
    let service: PunchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyLocationTestModule],
        declarations: [PunchUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PunchUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PunchUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PunchService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Punch(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Punch();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
