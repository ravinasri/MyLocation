import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyLocationTestModule } from '../../../test.module';
import { PunchComponent } from 'app/entities/punch/punch.component';
import { PunchService } from 'app/entities/punch/punch.service';
import { Punch } from 'app/shared/model/punch.model';

describe('Component Tests', () => {
  describe('Punch Management Component', () => {
    let comp: PunchComponent;
    let fixture: ComponentFixture<PunchComponent>;
    let service: PunchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyLocationTestModule],
        declarations: [PunchComponent],
      })
        .overrideTemplate(PunchComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PunchComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PunchService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Punch(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.punches && comp.punches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
