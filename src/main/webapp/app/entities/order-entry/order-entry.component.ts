import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { OrderEntry } from './order-entry.model';
import { OrderEntryService } from './order-entry.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-order-entry',
    templateUrl: './order-entry.component.html'
})
export class OrderEntryComponent implements OnInit, OnDestroy {
orderEntries: OrderEntry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderEntryService: OrderEntryService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['orderEntry']);
    }

    loadAll() {
        this.orderEntryService.query().subscribe(
            (res: Response) => {
                this.orderEntries = res.json();
                console.log("Order Entries: ");
                console.log(this.orderEntries);
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOrderEntries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: OrderEntry) {
        return item.id;
    }



    registerChangeInOrderEntries() {
        this.eventSubscriber = this.eventManager.subscribe('orderEntryListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
