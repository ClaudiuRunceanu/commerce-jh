import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CommerceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderEntryDetailComponent } from '../../../../../../main/webapp/app/entities/order-entry/order-entry-detail.component';
import { OrderEntryService } from '../../../../../../main/webapp/app/entities/order-entry/order-entry.service';
import { OrderEntry } from '../../../../../../main/webapp/app/entities/order-entry/order-entry.model';

describe('Component Tests', () => {

    describe('OrderEntry Management Detail Component', () => {
        let comp: OrderEntryDetailComponent;
        let fixture: ComponentFixture<OrderEntryDetailComponent>;
        let service: OrderEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CommerceTestModule],
                declarations: [OrderEntryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderEntryService,
                    EventManager
                ]
            }).overrideComponent(OrderEntryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderEntryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderEntryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderEntry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderEntry).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
