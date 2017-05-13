import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrderEntry } from './order-entry.model';
import { OrderEntryService } from './order-entry.service';
@Injectable()
export class OrderEntryPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private orderEntryService: OrderEntryService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.orderEntryService.find(id).subscribe(orderEntry => {
                this.orderEntryModalRef(component, orderEntry);
            });
        } else {
            return this.orderEntryModalRef(component, new OrderEntry());
        }
    }

    orderEntryModalRef(component: Component, orderEntry: OrderEntry): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderEntry = orderEntry;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
