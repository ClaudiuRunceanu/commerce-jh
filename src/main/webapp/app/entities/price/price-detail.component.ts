import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Price } from './price.model';
import { PriceService } from './price.service';

@Component({
    selector: 'jhi-price-detail',
    templateUrl: './price-detail.component.html'
})
export class PriceDetailComponent implements OnInit, OnDestroy {

    price: Price;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private priceService: PriceService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['price']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInPrices();
    }

    load (id) {
        this.priceService.find(id).subscribe(price => {
            this.price = price;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrices() {
        this.eventSubscriber = this.eventManager.subscribe('priceListModification', response => this.load(this.price.id));
    }

}
