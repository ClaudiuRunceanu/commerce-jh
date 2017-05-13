import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CommerceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomFilterDetailComponent } from '../../../../../../main/webapp/app/entities/custom-filter/custom-filter-detail.component';
import { CustomFilterService } from '../../../../../../main/webapp/app/entities/custom-filter/custom-filter.service';
import { CustomFilter } from '../../../../../../main/webapp/app/entities/custom-filter/custom-filter.model';

describe('Component Tests', () => {

    describe('CustomFilter Management Detail Component', () => {
        let comp: CustomFilterDetailComponent;
        let fixture: ComponentFixture<CustomFilterDetailComponent>;
        let service: CustomFilterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CommerceTestModule],
                declarations: [CustomFilterDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomFilterService,
                    EventManager
                ]
            }).overrideComponent(CustomFilterDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomFilterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomFilterService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomFilter(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customFilter).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
