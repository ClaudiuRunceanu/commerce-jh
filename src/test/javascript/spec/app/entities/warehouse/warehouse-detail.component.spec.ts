import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CommerceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WarehouseDetailComponent } from '../../../../../../main/webapp/app/entities/warehouse/warehouse-detail.component';
import { WarehouseService } from '../../../../../../main/webapp/app/entities/warehouse/warehouse.service';
import { Warehouse } from '../../../../../../main/webapp/app/entities/warehouse/warehouse.model';

describe('Component Tests', () => {

    describe('Warehouse Management Detail Component', () => {
        let comp: WarehouseDetailComponent;
        let fixture: ComponentFixture<WarehouseDetailComponent>;
        let service: WarehouseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CommerceTestModule],
                declarations: [WarehouseDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WarehouseService,
                    EventManager
                ]
            }).overrideComponent(WarehouseDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WarehouseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WarehouseService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Warehouse(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.warehouse).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
