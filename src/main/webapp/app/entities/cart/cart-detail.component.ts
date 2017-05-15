import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Cart } from './cart.model';
import { CartService } from './cart.service';
import {OrderEntry} from "../order-entry/order-entry.model";

@Component({
    selector: 'jhi-cart-detail',
    templateUrl: './cart-detail.component.html'
})
export class CartDetailComponent implements OnInit, OnDestroy {

    cart: Cart;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private cartService: CartService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['cart']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInCarts();
    }

    load (id) {
        this.cartService.find(id).subscribe(cart => {
            this.cart = cart;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCarts() {
        this.eventSubscriber = this.eventManager.subscribe('cartListModification', response => this.load(this.cart.id));
    }

    trackOrderEntryId (index: number, item: OrderEntry) {
        return item.id;
    }

}
