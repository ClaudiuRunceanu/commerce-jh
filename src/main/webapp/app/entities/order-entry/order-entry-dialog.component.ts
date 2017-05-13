import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderEntry } from './order-entry.model';
import { OrderEntryPopupService } from './order-entry-popup.service';
import { OrderEntryService } from './order-entry.service';
import { Product, ProductService } from '../product';
import { Cart, CartService } from '../cart';
import { CustomerOrder, CustomerOrderService } from '../customer-order';

@Component({
    selector: 'jhi-order-entry-dialog',
    templateUrl: './order-entry-dialog.component.html'
})
export class OrderEntryDialogComponent implements OnInit {

    orderEntry: OrderEntry;
    authorities: any[];
    isSaving: boolean;

    products: Product[];

    carts: Cart[];

    customerorders: CustomerOrder[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderEntryService: OrderEntryService,
        private productService: ProductService,
        private cartService: CartService,
        private customerOrderService: CustomerOrderService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderEntry']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productService.query().subscribe(
            (res: Response) => { this.products = res.json(); }, (res: Response) => this.onError(res.json()));
        this.cartService.query().subscribe(
            (res: Response) => { this.carts = res.json(); }, (res: Response) => this.onError(res.json()));
        this.customerOrderService.query().subscribe(
            (res: Response) => { this.customerorders = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.orderEntry.id !== undefined) {
            this.orderEntryService.update(this.orderEntry)
                .subscribe((res: OrderEntry) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderEntryService.create(this.orderEntry)
                .subscribe((res: OrderEntry) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: OrderEntry) {
        this.eventManager.broadcast({ name: 'orderEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    trackCartById(index: number, item: Cart) {
        return item.id;
    }

    trackCustomerOrderById(index: number, item: CustomerOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-entry-popup',
    template: ''
})
export class OrderEntryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private orderEntryPopupService: OrderEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.orderEntryPopupService
                    .open(OrderEntryDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderEntryPopupService
                    .open(OrderEntryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
