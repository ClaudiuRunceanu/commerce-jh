import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { OrderEntry } from './order-entry.model';
import { OrderEntryPopupService } from './order-entry-popup.service';
import { OrderEntryService } from './order-entry.service';

@Component({
    selector: 'jhi-order-entry-delete-dialog',
    templateUrl: './order-entry-delete-dialog.component.html'
})
export class OrderEntryDeleteDialogComponent {

    orderEntry: OrderEntry;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderEntryService: OrderEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderEntry']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.orderEntryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orderEntryListModification',
                content: 'Deleted an orderEntry'
            });
            this.eventManager.broadcast({ name: 'customerOrderListModification', content: 'OK'});

            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-entry-delete-popup',
    template: ''
})
export class OrderEntryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private orderEntryPopupService: OrderEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.orderEntryPopupService
                .open(OrderEntryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
