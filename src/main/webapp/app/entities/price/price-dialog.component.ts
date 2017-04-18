import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Price } from './price.model';
import { PricePopupService } from './price-popup.service';
import { PriceService } from './price.service';
import { Currency, CurrencyService } from '../currency';

@Component({
    selector: 'jhi-price-dialog',
    templateUrl: './price-dialog.component.html'
})
export class PriceDialogComponent implements OnInit {

    price: Price;
    authorities: any[];
    isSaving: boolean;

    currencies: Currency[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private priceService: PriceService,
        private currencyService: CurrencyService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['price']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.currencyService.query().subscribe(
            (res: Response) => { this.currencies = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.price.id !== undefined) {
            this.priceService.update(this.price)
                .subscribe((res: Price) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.priceService.create(this.price)
                .subscribe((res: Price) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: Price) {
        this.eventManager.broadcast({ name: 'priceListModification', content: 'OK'});
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

    trackCurrencyById(index: number, item: Currency) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-price-popup',
    template: ''
})
export class PricePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private pricePopupService: PricePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.pricePopupService
                    .open(PriceDialogComponent, params['id']);
            } else {
                this.modalRef = this.pricePopupService
                    .open(PriceDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
