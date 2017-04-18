import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CommerceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClassificationClassAttributeDetailComponent } from '../../../../../../main/webapp/app/entities/classification-class-attribute/classification-class-attribute-detail.component';
import { ClassificationClassAttributeService } from '../../../../../../main/webapp/app/entities/classification-class-attribute/classification-class-attribute.service';
import { ClassificationClassAttribute } from '../../../../../../main/webapp/app/entities/classification-class-attribute/classification-class-attribute.model';

describe('Component Tests', () => {

    describe('ClassificationClassAttribute Management Detail Component', () => {
        let comp: ClassificationClassAttributeDetailComponent;
        let fixture: ComponentFixture<ClassificationClassAttributeDetailComponent>;
        let service: ClassificationClassAttributeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CommerceTestModule],
                declarations: [ClassificationClassAttributeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClassificationClassAttributeService,
                    EventManager
                ]
            }).overrideComponent(ClassificationClassAttributeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClassificationClassAttributeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassificationClassAttributeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClassificationClassAttribute(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.classificationClassAttribute).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
