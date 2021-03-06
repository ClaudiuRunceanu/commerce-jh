import {Injectable, Component} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {DatePipe} from '@angular/common';
import {Stock} from './stock.model';
import {StockService} from './stock.service';
@Injectable()
export class StockPopupService {
    private isOpen = false;

    constructor(private datePipe: DatePipe,
                private modalService: NgbModal,
                private router: Router,
                private stockService: StockService) {
    }

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.stockService.find(id).subscribe(stock => {
                stock.expireDate = this.datePipe
                    .transform(stock.expireDate, 'yyyy-MM-ddThh:mm');
                stock.creationDate = this.datePipe
                    .transform(stock.creationDate, 'yyyy-MM-ddThh:mm');
                this.stockModalRef(component, stock);
            });
        } else {
            return this.stockModalRef(component, new Stock());
        }
    }

    createStockForProduct(component: Component, id?: number): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        let stock: Stock;
        stock = new Stock();
        stock.productId = id;
        return this.stockModalRef(component, stock);

    }

    stockModalRef(component: Component, stock: Stock): NgbModalRef {
        let modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.stock = stock;
        modalRef.result.then(result => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        });
        return modalRef;
    }
}
