import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CommerceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerOrderDetailComponent } from '../../../../../../main/webapp/app/entities/customer-order/customer-order-detail.component';
import { CustomerOrderService } from '../../../../../../main/webapp/app/entities/customer-order/customer-order.service';
import { CustomerOrder } from '../../../../../../main/webapp/app/entities/customer-order/customer-order.model';

describe('Component Tests', () => {

    describe('CustomerOrder Management Detail Component', () => {
        let comp: CustomerOrderDetailComponent;
        let fixture: ComponentFixture<CustomerOrderDetailComponent>;
        let service: CustomerOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CommerceTestModule],
                declarations: [CustomerOrderDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerOrderService,
                    EventManager
                ]
            }).overrideComponent(CustomerOrderDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerOrderService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerOrder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerOrder).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
