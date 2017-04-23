import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import {Stock} from './stock.model';
import {StockPopupService} from './stock-popup.service';
import {StockService} from './stock.service';
import {Warehouse, WarehouseService} from '../warehouse';
import {Product, ProductService} from '../product';

@Component({
    selector: 'jhi-stock-dialog',
    templateUrl: './stock-dialog.component.html'
})
export class StockDialogComponent implements OnInit {

    stock: Stock;
    authorities: any[];
    isSaving: boolean;

    warehouses: Warehouse[];

    products: Product[];

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private stockService: StockService,
                private warehouseService: WarehouseService,
                private productService: ProductService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['stock']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.warehouseService.query().subscribe(
            (res: Response) => {
                this.warehouses = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.productService.query().subscribe(
            (res: Response) => {
                this.products = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stock.id !== undefined) {
            this.stockService.update(this.stock)
                .subscribe((res: Stock) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.stockService.create(this.stock)
                .subscribe((res: Stock) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Stock) {
        this.eventManager.broadcast({name: 'stockListModification', content: 'OK'});
        this.eventManager.broadcast({ name: 'productListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackWarehouseById(index: number, item: Warehouse) {
        return item.id;
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-stock-popup',
    template: ''
})
export class StockPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private stockPopupService: StockPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {

            if (params['productId']) {
                this.modalRef = this.stockPopupService.createStockForProduct(StockDialogComponent, params['productId']);
            }

            if (params['id']) {
                this.modalRef = this.stockPopupService
                    .open(StockDialogComponent, params['id']);
            } else {
                this.modalRef = this.stockPopupService
                    .open(StockDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
