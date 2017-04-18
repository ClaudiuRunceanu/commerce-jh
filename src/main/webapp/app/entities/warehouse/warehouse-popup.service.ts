import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Warehouse } from './warehouse.model';
import { WarehouseService } from './warehouse.service';
@Injectable()
export class WarehousePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private warehouseService: WarehouseService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.warehouseService.find(id).subscribe(warehouse => {
                this.warehouseModalRef(component, warehouse);
            });
        } else {
            return this.warehouseModalRef(component, new Warehouse());
        }
    }

    warehouseModalRef(component: Component, warehouse: Warehouse): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.warehouse = warehouse;
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
