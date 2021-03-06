import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Product } from './product.model';
import { ProductService } from './product.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-product',
    templateUrl: './product.component.html'
})
export class ProductComponent implements OnInit, OnDestroy {
products: Product[];
    currentAccount: any;
    eventSubscriber: Subscription;

    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productService: ProductService,
        private parseLinks: ParseLinks,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private paginationUtil: PaginationUtil,
        private paginationConfig: PaginationConfig,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {

        // this.itemsPerPage = ITEMS_PER_PAGE;
        this.itemsPerPage = 10;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });

        this.jhiLanguageService.setLocations(['product']);
    }

    loadAll() {
        // this.productService.query().subscribe(
        //     (res: Response) => {
        //         this.products = res.json();
        //         console.log("products: ");
        //         console.log(this.products);
        //     },
        //     (res: Response) => this.onError(res.json())
        // );

        this.productService.paginationQuery({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: Response) => this.onSuccessPageable(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }


    ngOnInit() {
        console.log("am intrat in product init");
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProducts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: Product) {
        return item.id;
    }



    registerChangeInProducts() {
        this.eventSubscriber = this.eventManager.subscribe('productListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    private onSuccessPageable(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.products = data;
    }

    sort () {
        let result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    loadPage (page: number) {
        console.log("load page....");
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition () {
        this.router.navigate(['/product'], { queryParams:
        {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }
        });
        this.loadAll();
    }
}
