import { Component, OnInit, OnDestroy } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Stock } from './stock.model';
import { StockPopupService } from './stock-popup.service';
import { StockService } from './stock.service';

@Component({
    selector: 'jhi-stock-delete-dialog',
    templateUrl: './stock-delete-dialog.component.html'
})
export class StockDeleteDialogComponent {

    stock: Stock;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private stockService: StockService,
        private router: Router,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['stock']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.stockService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productListModification',
                content: 'Deleted an stock'
            });
            this.eventManager.broadcast({
                name: 'stockListModification',
                content: 'Deleted an stock'
            });
            this.activeModal.dismiss(true);
        });

        this.router.navigateByUrl('product');
    }
}

@Component({
    selector: 'jhi-stock-delete-popup',
    template: ''
})
export class StockDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private stockPopupService: StockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.stockPopupService
                .open(StockDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
