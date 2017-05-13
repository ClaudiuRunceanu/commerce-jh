import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerOrder } from './customer-order.model';
import { CustomerOrderPopupService } from './customer-order-popup.service';
import { CustomerOrderService } from './customer-order.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-customer-order-dialog',
    templateUrl: './customer-order-dialog.component.html'
})
export class CustomerOrderDialogComponent implements OnInit {

    customerOrder: CustomerOrder;
    authorities: any[];
    isSaving: boolean;

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerOrderService: CustomerOrderService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOrder', 'orderStatus']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.customerOrder.id !== undefined) {
            this.customerOrderService.update(this.customerOrder)
                .subscribe((res: CustomerOrder) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOrderService.create(this.customerOrder)
                .subscribe((res: CustomerOrder) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: CustomerOrder) {
        this.eventManager.broadcast({ name: 'customerOrderListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-order-popup',
    template: ''
})
export class CustomerOrderPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private customerOrderPopupService: CustomerOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.customerOrderPopupService
                    .open(CustomerOrderDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerOrderPopupService
                    .open(CustomerOrderDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
