import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomFilter } from './custom-filter.model';
import { CustomFilterPopupService } from './custom-filter-popup.service';
import { CustomFilterService } from './custom-filter.service';

@Component({
    selector: 'jhi-custom-filter-dialog',
    templateUrl: './custom-filter-dialog.component.html'
})
export class CustomFilterDialogComponent implements OnInit {

    customFilter: CustomFilter;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customFilterService: CustomFilterService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customFilter']);
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
        if (this.customFilter.id !== undefined) {
            this.customFilterService.update(this.customFilter)
                .subscribe((res: CustomFilter) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customFilterService.create(this.customFilter)
                .subscribe((res: CustomFilter) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: CustomFilter) {
        this.eventManager.broadcast({ name: 'customFilterListModification', content: 'OK'});
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
    selector: 'jhi-custom-filter-popup',
    template: ''
})
export class CustomFilterPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private customFilterPopupService: CustomFilterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.customFilterPopupService
                    .open(CustomFilterDialogComponent, params['id']);
            } else {
                this.modalRef = this.customFilterPopupService
                    .open(CustomFilterDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
