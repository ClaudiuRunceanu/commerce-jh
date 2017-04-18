import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Warehouse } from './warehouse.model';
import { WarehousePopupService } from './warehouse-popup.service';
import { WarehouseService } from './warehouse.service';

@Component({
    selector: 'jhi-warehouse-dialog',
    templateUrl: './warehouse-dialog.component.html'
})
export class WarehouseDialogComponent implements OnInit {

    warehouse: Warehouse;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private warehouseService: WarehouseService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['warehouse']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.warehouse.id !== undefined) {
            this.warehouseService.update(this.warehouse)
                .subscribe((res: Warehouse) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.warehouseService.create(this.warehouse)
                .subscribe((res: Warehouse) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: Warehouse) {
        this.eventManager.broadcast({ name: 'warehouseListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-warehouse-popup',
    template: ''
})
export class WarehousePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private warehousePopupService: WarehousePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.warehousePopupService
                    .open(WarehouseDialogComponent, params['id']);
            } else {
                this.modalRef = this.warehousePopupService
                    .open(WarehouseDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
