import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Product } from './product.model';
import { ProductService } from './product.service';
import {Category} from "../category/category.model";
import {Media} from "../media/media.model";

import {DomSanitizer} from '@angular/platform-browser';
import {Stock} from "../stock/stock.model";

@Component({
    selector: 'jhi-product-detail',
    templateUrl: './product-detail.component.html',
    styleUrls:['./product.component.css']
})
export class ProductDetailComponent implements OnInit, OnDestroy {

    product: Product;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productService: ProductService,
        private route: ActivatedRoute,
        public _DomSanitizer: DomSanitizer
    ) {
        this.jhiLanguageService.setLocations(['product']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInProducts();
    }

    load (id) {
        this.productService.find(id).subscribe(product => {
            this.product = product;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProducts() {
        this.eventSubscriber = this.eventManager.subscribe('productListModification', response => this.load(this.product.id));
    }

    trackByCategoriId (index: number, item: Category) {
        return item.id;
    }

    trackByMediaId (index: number, item: Media) {
        return item.id;
    }

    trackByStockId (index: number, item: Stock) {
        return item.id;
    }

}
