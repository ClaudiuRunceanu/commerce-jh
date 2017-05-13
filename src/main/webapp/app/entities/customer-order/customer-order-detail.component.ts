import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { CustomerOrder } from './customer-order.model';
import { CustomerOrderService } from './customer-order.service';

@Component({
    selector: 'jhi-customer-order-detail',
    templateUrl: './customer-order-detail.component.html'
})
export class CustomerOrderDetailComponent implements OnInit, OnDestroy {

    customerOrder: CustomerOrder;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerOrderService: CustomerOrderService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['customerOrder', 'orderStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerOrders();
    }

    load (id) {
        this.customerOrderService.find(id).subscribe(customerOrder => {
            this.customerOrder = customerOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomerOrders() {
        this.eventSubscriber = this.eventManager.subscribe('customerOrderListModification', response => this.load(this.customerOrder.id));
    }

}
